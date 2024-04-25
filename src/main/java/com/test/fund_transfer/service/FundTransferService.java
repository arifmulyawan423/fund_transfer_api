package com.test.fund_transfer.service;

import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.test.fund_transfer.model.Customer;
import com.test.fund_transfer.model.TransferHistory;
import com.test.fund_transfer.request.FundTransferRequest;
import com.test.fund_transfer.response.FundTransferResponse;
import com.test.fund_transfer.respository.CustomerRepository;
import com.test.fund_transfer.respository.TransferHistoryRepository;

@Service
public class FundTransferService {
	
	@Autowired CustomerRepository customerRepository;
	@Autowired FundTransferMQService fundTransferMQService;
	@Autowired TransferHistoryRepository transferHistoryRepository;
	
	Logger logger = LoggerFactory.getLogger(FundTransferService.class);
	
	public ResponseEntity<FundTransferResponse> transfer(FundTransferRequest request) {
		
		logger.info("FundTransferService start with params {}", request);
		
		FundTransferResponse fundTransferResponse = new FundTransferResponse();
		fundTransferResponse.setStatus(HttpStatus.OK);
		fundTransferResponse.setMessage("Your Fund is Successfully Transferred");
		
		Optional<Customer> optCustomerFrom = customerRepository.findById(request.getFromAccountNumer());
		Optional<Customer> optCustomerTo = customerRepository.findById(request.getToAccountNumber());
		
		ResponseEntity<FundTransferResponse> result = ResponseEntity.ok(fundTransferResponse);
		
		//check account number exists
		if (optCustomerFrom.isEmpty() || optCustomerTo.isEmpty()) {
			logger.info("FundTransferService fail check account number exists");
			fundTransferResponse.setStatus(HttpStatus.BAD_REQUEST);
			fundTransferResponse.setMessage("Your Account Number is not exist");
			return result;
		}
		
		//check balance
		if (optCustomerFrom.get().getBalanceAmount().compareTo(request.getAmountTransfer()) < 0) {
			logger.info("FundTransferService fail check balance");
			fundTransferResponse.setStatus(HttpStatus.BAD_REQUEST);
			fundTransferResponse.setMessage("Your Balance is not enough to transfer");
			return result;
		}
		
		String[] mqParams = {request.getFromAccountNumer(), request.getToAccountNumber(), request.getAmountTransfer().toPlainString()};
		
		try {
			fundTransferMQService.run(mqParams);
			
			TransferHistory transferHistory = new TransferHistory();
			transferHistory.setFromAccountNumber(request.getFromAccountNumer());
			transferHistory.setToAccountNumber(request.getToAccountNumber());
			transferHistory.setAmount(request.getAmountTransfer());
			transferHistory.setTransferDate(new Date());
			transferHistoryRepository.save(transferHistory);
			
			Customer customerFrom = optCustomerFrom.get();
			Customer customerTo = optCustomerTo.get();
			
			customerFrom.setBalanceAmount(customerFrom.getBalanceAmount().subtract(request.getAmountTransfer()));
			customerTo.setBalanceAmount(customerTo.getBalanceAmount().add(request.getAmountTransfer()));
			
			customerRepository.save(customerFrom);
			customerRepository.save(customerTo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	private FundTransferResponse getErrorResponse() {
		FundTransferResponse fundTransferResponse = new FundTransferResponse();
		fundTransferResponse.setStatus(HttpStatus.BAD_REQUEST);
		fundTransferResponse.setMessage("Internal Server Error");
		
		return fundTransferResponse;
	}
}
