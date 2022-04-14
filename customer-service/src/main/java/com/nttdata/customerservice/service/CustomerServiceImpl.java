package com.nttdata.customerservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.customerservice.model.Customer;
import com.nttdata.customerservice.repository.CustomerRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository repository;
	
	@Override
	public Flux<Customer> findAllCustomers() {
		return Flux.fromIterable(repository.findAll());
	}

	@Override
	public Mono<Customer> saveCustomer(Customer customer) {
		return Mono.just(repository.save(customer));
	}

	@Override
	public Mono<Customer> updateCustomer(Customer customer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Customer> listById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Void> delete(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
