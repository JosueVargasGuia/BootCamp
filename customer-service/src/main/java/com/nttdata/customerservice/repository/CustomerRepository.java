package com.nttdata.customerservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.customerservice.model.Customer;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String>{

}
