package com.nttdata.customerservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.customerservice.entity.Customer;
import com.nttdata.customerservice.repository.CustomerRepository;
import com.nttdata.customerservice.service.CustomerService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository repository;

	@Override 
	public Flux<Customer> findAll() {
		return repository.findAll();
	}	
	@Override
	public Mono<Customer> findById(Long id) {
		return repository.findById(id);
	}
	@Override
	public Mono<Customer> save(Customer customer) {
		return repository.save(customer);
	}
	@Override
	public Mono<Customer> update(Customer customer) {
		return repository.save(customer); 
	}
	@Override 
	public Mono<Void> delete(Long id) {
		return repository.deleteById(id);
 
	}

}
