package com.test.fund_transfer.respository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.test.fund_transfer.model.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String> {

}
