package com.test.fund_transfer.request;

import java.math.BigDecimal;

public class FundTransferRequest {
	String fromAccountNumer;
	String toAccountNumber;
	BigDecimal amountTransfer;
	
	public String getFromAccountNumer() {
		return fromAccountNumer;
	}
	public void setFromAccountNumer(String fromAccountNumer) {
		this.fromAccountNumer = fromAccountNumer;
	}
	public String getToAccountNumber() {
		return toAccountNumber;
	}
	public void setToAccountNumber(String toAccountNumber) {
		this.toAccountNumber = toAccountNumber;
	}
	public BigDecimal getAmountTransfer() {
		return amountTransfer;
	}
	public void setAmountTransfer(BigDecimal amountTransfer) {
		this.amountTransfer = amountTransfer;
	}
}
