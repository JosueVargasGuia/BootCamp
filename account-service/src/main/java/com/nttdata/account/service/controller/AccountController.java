package com.nttdata.account.service.controller;

import java.util.Map;

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

import com.nttdata.account.service.model.Account;
import com.nttdata.account.service.model.Customer;
import com.nttdata.account.service.model.Product;
import com.nttdata.account.service.service.AccountService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
	private AccountService service;
	
	@GetMapping
	public Flux<Account> listAllAccounts(){
		return service.getAllAccounts();
	}
	
	@GetMapping("/{id}")
	public Mono<ResponseEntity<Account>> listOneAccount(@PathVariable("id") String id){
		return service.getById(id);
	}
	
	@PostMapping("/save")
	public Mono<ResponseEntity<Account>> saveAccount(@RequestBody Account account){
		return service.saveAccount(account);
	}
	
	@PutMapping("/update/{id}")
	public Mono<ResponseEntity<Account>> updateAccount(@PathVariable("id") String id,@RequestBody Account account){
		return service.saveAccount(account);
	}
	
	@DeleteMapping("/delete/{id}")
	public Mono<ResponseEntity<Void>> deleteAccount(@PathVariable("id") String id){
		return service.delete(id);
	}
	
	
	@PostMapping("/registerAccount")
	public Mono<ResponseEntity<Map<String, Object>>> registerAccount(@RequestBody Account account) {
		return Mono.just(service.registerAccount(account)).map(_object -> ResponseEntity.ok().body(_object))
				.onErrorResume(e -> {
					System.out.println("Error:" + e.getMessage());
					return Mono.just(ResponseEntity.badRequest().build());
				}).defaultIfEmpty(ResponseEntity.noContent().build());
	}
	
	
	
	
	
	
	
	
	
	@GetMapping("/findProduct/{id}")
	public Product find(@PathVariable("id") Long id) {
		return service.findProduct(id);
	}
	
	@GetMapping("/findCustomer/{id}")
	public Customer findCustomer(@PathVariable("id") String id) {
		return service.findCustomer(id);
	}
	

}