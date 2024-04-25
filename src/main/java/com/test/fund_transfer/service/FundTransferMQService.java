package com.test.fund_transfer.service;

import java.math.BigDecimal;

import javax.sound.midi.Receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.test.fund_transfer.request.FundTransferRequest;

@Component
public class FundTransferMQService implements CommandLineRunner {
	
	private final RabbitTemplate rabbitTemplate;
	private final Receiver receiver;
	
	Logger logger = LoggerFactory.getLogger(FundTransferMQService.class);
	
	public FundTransferMQService(RabbitTemplate rabbitTemplate, Receiver receiver) {
		super();
		this.rabbitTemplate = rabbitTemplate;
		this.receiver = receiver;
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		logger.info("FundTransferMQService Sending message");
		FundTransferRequest fundTransfer = new FundTransferRequest();
		fundTransfer.setFromAccountNumer(args[0]);
		fundTransfer.setToAccountNumber(args[1]);
		fundTransfer.setAmountTransfer(new BigDecimal(args[2]));
	    rabbitTemplate.convertAndSend("fund-transfer-mq", "fund.transfer.mq", fundTransfer);
	}
	
	
}
