package com.nttdata.customerservice.service;

import org.springframework.http.ResponseEntity;

import com.nttdata.customerservice.model.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {
	
	Flux<Customer> getAllCustomers();
	Mono<ResponseEntity<Customer>> saveCustomer(Customer customer);
	Mono<ResponseEntity<Customer>> updateCustomer(Customer customer);
	Mono<ResponseEntity<Customer>> getById(String id);
	Mono<ResponseEntity<Void>> delete(String id);

}
