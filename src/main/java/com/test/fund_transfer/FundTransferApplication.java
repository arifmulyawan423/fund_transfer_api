package com.test.fund_transfer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class FundTransferApplication{
	
	public static void main(String[] args) {
		System.out.println("Server is starting");
		
		SpringApplication.run(FundTransferApplication.class, args);
	}

}
