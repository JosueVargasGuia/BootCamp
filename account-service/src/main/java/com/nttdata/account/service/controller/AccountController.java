package com.nttdata.account.service.controller;

import java.util.Map;

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

//import com.netflix.discovery.EurekaClient;
import com.nttdata.account.service.entity.Account;
import com.nttdata.account.service.model.Customer;
import com.nttdata.account.service.model.MovementAccount;
import com.nttdata.account.service.model.Product;
import com.nttdata.account.service.service.AccountService;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@RestController
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
	private AccountService service;
	
	@GetMapping
	public Flux<Account> findAll(){
		return service.findAll();

	}
	
	@GetMapping("/{id}")
	public Mono<ResponseEntity<Account>> findById(@PathVariable("id") Long id){
		return service.findById(id).map(_account -> ResponseEntity.ok().body(_account))
				.onErrorResume(e -> {
					log.error("Error: " + e.getMessage());
					return Mono.just(ResponseEntity.badRequest().build());
				}).defaultIfEmpty(ResponseEntity.noContent().build());
	}
	
	@PostMapping
	public Mono<ResponseEntity<Account>> saveAccount(@RequestBody Account account){
		return service.save(account).map(_account -> ResponseEntity.ok().body(_account)).
				onErrorResume(e -> {
			log.error("Error:" + e.getMessage());
			return Mono.just(ResponseEntity.badRequest().build());
		});
	}
	
	@PutMapping
	public Mono<ResponseEntity<Account>> updateAccount(@RequestBody Account account){
		Mono<Account> objAccount = service.findById(account.getIdAccount()).flatMap(_act -> {
			log.info("Update: [new] " + account + " [Old]: " + _act);
			return service.update(account);
		});
		//objAccount.subscribe();
		return objAccount.map(_account -> {
			log.info("Status: " + HttpStatus.OK);
			return ResponseEntity.ok().body(_account);
		}).onErrorResume(e -> {
			log.error("Status: " + HttpStatus.BAD_REQUEST + " Message:  " + e.getMessage());
			return Mono.just(ResponseEntity.badRequest().build());
		}).defaultIfEmpty(ResponseEntity.noContent().build());
	}
	
	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> deleteAccount(@PathVariable("id") Long id){
		return service.findById(id).flatMap(account -> {
			return service.delete(account.getIdAccount()).then(Mono.just(ResponseEntity.ok().build()));
		});
	}
 
	//@Autowired 
	//EurekaClient eurekaClient;
	
	@PostMapping("/registerAccount")
	public Mono<ResponseEntity<Map<String, Object>>> registerAccount(@RequestBody Account account) {
		return service.registerAccount(account).
				map(_object ->{ 
					//_object.put("IntanceName", eurekaClient.getApplicationInfoManager().getInfo().getInstanceId());
				return ResponseEntity.ok().body(_object);})
				.onErrorResume(e -> {
					log.error("Error:" + e.getMessage());
					return Mono.just(ResponseEntity.badRequest().build());
				}).defaultIfEmpty(ResponseEntity.noContent().build());
	}
	
	
	@GetMapping("/consultMovementsAccount/{idAccount}")
	public Flux<MovementAccount> consultMovementsAccount(@PathVariable("idAccount") Long idAccount) {
		return service.consultMovementsAccount(idAccount);
	}
	
	
	@GetMapping("/findProduct/{id}")
	public Product find(@PathVariable("id") Long id) {
		return service.findProduct(id);
	}
	
	@GetMapping("/findCustomer/{id}")
	public Customer findCustomer(@PathVariable("id") Long id) {
		return service.findCustomer(id);
	}
	

}