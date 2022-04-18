package com.nttdata.customerservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.customerservice.model.Customer;
import com.nttdata.customerservice.service.CustomerService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	Logger logger = LoggerFactory.getLogger(CustomerController.class);
	
	@Autowired
	private CustomerService service;
	
	@GetMapping
	public Flux<Customer> findAll(){
		return service.findAll();
	}
	
	@GetMapping("/{id}")
	public Mono<ResponseEntity<Customer>> findById(@PathVariable("id") Long id){
		return service.findById(id).map(_customer -> ResponseEntity.ok().body(_customer))
				.onErrorResume(e -> {
					logger.info("Error:" + e.getMessage());
					return Mono.just(ResponseEntity.badRequest().build());
				}).defaultIfEmpty(ResponseEntity.noContent().build());
	}
	
	@PostMapping
	public Mono<ResponseEntity<Customer>> saveCustomer(@RequestBody Customer customer){
		return service.save(customer).map(_customer -> ResponseEntity.ok().body(_customer)).onErrorResume(e -> {
			logger.info("Error:" + e.getMessage());
			return Mono.just(ResponseEntity.badRequest().build());
		});
	}
	
	@PutMapping
	public Mono<ResponseEntity<Customer>> updateCustomer(@RequestBody Customer customer){
		Mono<Customer> objCustomer = service.findById(customer.getId()).flatMap(_customer -> {
			logger.info("Update: [new] " + customer + " [Old]: " + _customer);
			return service.update(customer);
		});

		return objCustomer.map(_cust -> {
			logger.info("Status: " + HttpStatus.OK);
			return ResponseEntity.ok().body(_cust);
		}).onErrorResume(e -> {
			logger.info("Status: " + HttpStatus.BAD_REQUEST + " Message:  " + e.getMessage());
			return Mono.just(ResponseEntity.badRequest().build());
		}).defaultIfEmpty(ResponseEntity.noContent().build());
	}
	
	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> deleteCustomer(@PathVariable("id") Long id){
		return service.findById(id).flatMap(customer -> {
			return service.delete(customer.getId()).then(Mono.just(ResponseEntity.ok().build()));
		});
	}
	

}
