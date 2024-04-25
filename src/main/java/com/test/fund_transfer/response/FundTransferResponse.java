package com.test.fund_transfer.response;

import org.springframework.http.HttpStatus;

public class FundTransferResponse {
	HttpStatus status;
	String message;
	
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
