package com.nttdata.customerservice.service;

import com.nttdata.customerservice.model.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {
	
	Flux<Customer> findAllCustomers();
	Mono<Customer> saveCustomer(Customer customer);
	Mono<Customer> updateCustomer(Customer customer);
	Mono<Customer> listById(String id);
	Mono<Void> delete(String id);

}