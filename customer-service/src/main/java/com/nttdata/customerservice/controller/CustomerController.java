package com.nttdata.customerservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.customerservice.model.Customer;
import com.nttdata.customerservice.service.CustomerService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/customer")
public class CustomerController {
	
	@Autowired
	private CustomerService service;
	
	@GetMapping("/listAll")
	public Flux<Customer> listAllCustomers(){
		return service.findAllCustomers();
	}
	
	@PostMapping("/save")
	public Mono<Customer> saveCustomer(@RequestBody Customer customer){
		return service.saveCustomer(customer);
	}

}
