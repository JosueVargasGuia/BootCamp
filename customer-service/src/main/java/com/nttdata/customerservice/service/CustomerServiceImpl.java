package com.nttdata.customerservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nttdata.customerservice.model.Customer;
import com.nttdata.customerservice.repository.CustomerRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository repository;

	@Override
	public Flux<Customer> getAllCustomers() {
		//return repository.findAll().map(customer -> ResponseEntity.ok(customer));
		return repository.findAll().sort((objA,objB)->objA.getId().compareTo(objA.getId()));
	}
	
	@Override
	public Mono<Customer> getById(String id) {
		return repository.findById(id)
				;
	}

	@Override
	public Mono<Customer> saveCustomer(Customer customer) {
		return repository.insert(customer);
	}

	@Override
	public Mono<Customer> updateCustomer(Customer customer) {

		return repository.findById(customer.getId())
				.flatMap(currentCustomer -> {
					customer.setFirstname(currentCustomer.getFirstname());
					customer.setLastname(currentCustomer.getLastname());
					customer.setDocumentNumber(currentCustomer.getDocumentNumber());
					customer.setTypeDocument(currentCustomer.getTypeDocument());
					customer.setTypeCustomer(currentCustomer.getTypeCustomer());
					customer.setEmailAddress(currentCustomer.getEmailAddress());
					customer.setHomeAddress(currentCustomer.getHomeAddress());
					customer.setPhoneNumber(currentCustomer.getPhoneNumber());
					return repository.save(customer);
				});
	}


	@Override
	public Mono<Void> delete(String id) {
		return repository.findById(id)
				.flatMap(customer -> repository.delete(customer));
	}

}
