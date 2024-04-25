package com.test.fund_transfer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.test.fund_transfer.request.FundTransferRequest;
import com.test.fund_transfer.response.FundTransferResponse;
import com.test.fund_transfer.service.FundTransferService;

@Controller
@RequestMapping("fund")
public class FundTransferController {

	@Autowired FundTransferService fundTransferService;
	
	@Autowired CircuitBreakerFactory circuitBreakerFactory;
	
	Logger logger = LoggerFactory.getLogger(FundTransferController.class);
	
	@PostMapping("/transfer")
	public ResponseEntity transfer(
	  @RequestBody FundTransferRequest request)  {
		
		logger.info("start api fund transfer with params {}", request);
		
		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
	    
	    return circuitBreaker.run(() -> fundTransferService.transfer(request),
	    		throwable -> ResponseEntity.ok(getErrorResponse()));
		
	}
	
	private FundTransferResponse getErrorResponse() {
		FundTransferResponse fundTransferResponse = new FundTransferResponse();
		fundTransferResponse.setStatus(HttpStatus.BAD_REQUEST);
		fundTransferResponse.setMessage("Internal Server Error");
		
		return fundTransferResponse;
	}
}
