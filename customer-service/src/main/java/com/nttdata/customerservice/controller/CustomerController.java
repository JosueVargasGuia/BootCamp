package com.nttdata.customerservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("api/customer")
public class CustomerController {
	
	@Autowired
	private CustomerService service;
	
	@GetMapping("/getAll")
	public Flux<ResponseEntity<Customer>> listAllCustomers(){
		return service.getAllCustomers();
	}
	
	@GetMapping("/getOneCustomer/{id}")
	public Mono<ResponseEntity<Customer>> listOneCustomer(@PathVariable("id") String id){
		return service.getById(id);
	}
	
	@PostMapping("/save")
	public Mono<ResponseEntity<Customer>> saveCustomer(@RequestBody Customer customer){
		return service.saveCustomer(customer);
	}
	
	@PutMapping("/update/{id}")
	public Mono<ResponseEntity<Customer>> updateCustomer(@PathVariable("id") String id,@RequestBody Customer customer){
		return service.saveCustomer(customer);
	}
	
	@DeleteMapping("/delete/{id}")
	public Mono<ResponseEntity<Void>> deleteCustomer(@PathVariable("id") String id){
		return service.delete(id);
	}
	

}
