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
	public Flux<ResponseEntity<Customer>> getAllCustomers() {
		return repository.findAll().map(customer -> ResponseEntity.ok(customer));
	}
	
	@Override
	public Mono<ResponseEntity<Customer>> getById(String id) {
		return repository.findById(id)
				.map(customer -> ResponseEntity.ok(customer))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@Override
	public Mono<ResponseEntity<Customer>> saveCustomer(Customer customer) {
		return repository.save(customer).map(savedCustomer -> ResponseEntity.ok(savedCustomer));
	}

	@Override
	public Mono<ResponseEntity<Customer>> updateCustomer(Customer customer) {

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
				})
				.map(updatedCustomer -> ResponseEntity.ok(updatedCustomer))
				.defaultIfEmpty(ResponseEntity.badRequest().build());
	}


	@Override
	public Mono<ResponseEntity<Void>> delete(String id) {
		return repository.findById(id)
				.flatMap(customer -> repository.delete(customer).then(Mono.just(ResponseEntity.ok().build())));
	}

}
