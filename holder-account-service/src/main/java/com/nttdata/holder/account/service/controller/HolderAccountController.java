package com.nttdata.holder.account.service.controller;

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

import com.nttdata.holder.account.service.entity.HolderAccount;
import com.nttdata.holder.account.service.model.Customer;
import com.nttdata.holder.account.service.service.HolderAccountService;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@RestController
@RequestMapping("/holder-account")
public class HolderAccountController {
	
	@Autowired
	private HolderAccountService service;
	
	@GetMapping
	public Flux<HolderAccount> findAll(){
		return service.findAll();
	}
	
	@GetMapping("/{id}")
	public Mono<ResponseEntity<HolderAccount>> findById(@PathVariable("id") Long id){
		return service.findById(id).map(_account -> ResponseEntity.ok().body(_account))
				.onErrorResume(e -> {
					log.error("Error: " + e.getMessage());
					return Mono.just(ResponseEntity.badRequest().build());
				}).defaultIfEmpty(ResponseEntity.noContent().build());
	}
	
	
	@PostMapping
	public Mono<ResponseEntity<HolderAccount>> save(@RequestBody HolderAccount holderAccount){
		return service.save(holderAccount).map(_hAccount -> ResponseEntity.ok().body(_hAccount)).
				onErrorResume(e -> {
			log.error("Error: " + e.getMessage());
			return Mono.just(ResponseEntity.badRequest().build());
		});
	}
	
	@PutMapping
	public Mono<ResponseEntity<HolderAccount>> update(@RequestBody HolderAccount holderAccount){
		Mono<HolderAccount> objAccount = service.findById(holderAccount.getIdHolderAccount()).flatMap(_hAct -> {
			log.info("Update: [new] " + holderAccount + " [Old]: " + _hAct);
			return service.update(holderAccount);
		});
		//objAccount.subscribe();
		return objAccount.map(_account -> {
			log.info("Status: " + HttpStatus.OK);
			return ResponseEntity.ok().body(_account);
		}).onErrorResume(e -> {
			log.info("Status: " + HttpStatus.BAD_REQUEST + " Message:  " + e.getMessage());
			return Mono.just(ResponseEntity.badRequest().build());
		}).defaultIfEmpty(ResponseEntity.noContent().build());
	}
	
	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> delete(@PathVariable("id") Long id){
		return service.findById(id).flatMap(hAccount -> {
			return service.delete(hAccount.getIdHolderAccount()).then(Mono.just(ResponseEntity.ok().build()));
		});
	}
	
	
	@PostMapping("/registerHolderAccount")
	public Mono<ResponseEntity<Map<String, Object>>> registerHolderAccount(@RequestBody HolderAccount holderAccount) {
		return service.registerHolderAccount(holderAccount).
				map(_object -> ResponseEntity.ok().body(_object))
				.onErrorResume(e -> {
					log.error("Error: " + e.getMessage());
					return Mono.just(ResponseEntity.badRequest().build());
				}).defaultIfEmpty(ResponseEntity.noContent().build());
	}
	
	
	@GetMapping("/findCustomer/{id}")
	public Customer findCustomer(@PathVariable("id") Long id) {
		return service.findCustomer(id);
	}
	

}
