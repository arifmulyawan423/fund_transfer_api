package com.test.fund_transfer.respository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.fund_transfer.model.TransferHistory;

public interface TransferHistoryRepository extends JpaRepository<TransferHistory, BigInteger>{

}
