package com.nttdata.SignCustAccountservice.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nttdata.SignCustAccountservice.entity.SignatoriesCustomerAccounts;
import com.nttdata.SignCustAccountservice.service.SignatoriesCustomerAccountsService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/signCustAccount")
public class SignatoriesCustomerAccountsController {
	Logger log = LoggerFactory.getLogger(SignatoriesCustomerAccountsController.class);
	@Autowired
	SignatoriesCustomerAccountsService accountsService;

	@GetMapping
	public Flux<SignatoriesCustomerAccounts> findAll() {
		return accountsService.findAll();
	}

	@PostMapping
	public Mono<ResponseEntity<SignatoriesCustomerAccounts>> save(
			@RequestBody SignatoriesCustomerAccounts customerAccounts) {
		return accountsService.save(customerAccounts)
				.map(_customerAccounts -> ResponseEntity.ok().body(_customerAccounts)).onErrorResume(e -> {
					log.info("Error:" + e.getMessage());
					return Mono.just(ResponseEntity.badRequest().build());
				});
	}

	@GetMapping("/{idSignCustAccount}")
	public Mono<ResponseEntity<SignatoriesCustomerAccounts>> findById(
			@PathVariable(name = "idSignCustAccount") long idSignCustAccount) {
		return accountsService.findById(idSignCustAccount).map(product -> ResponseEntity.ok().body(product))
				.onErrorResume(e -> {
					log.info(e.getMessage());
					return Mono.just(ResponseEntity.badRequest().build());
				}).defaultIfEmpty(ResponseEntity.noContent().build());
	}

	@PutMapping
	public Mono<ResponseEntity<SignatoriesCustomerAccounts>> update(
			@RequestBody SignatoriesCustomerAccounts customerAccounts) {
		Mono<SignatoriesCustomerAccounts> mono = accountsService.findById(customerAccounts.getIdSignCustAccount())
				.flatMap(objCustomerAccounts -> {
					log.info("Update:[new]" + customerAccounts + " [Old]:" + objCustomerAccounts);
					return accountsService.update(customerAccounts);
				});
		return mono.map(_product -> {
			log.info("Status:" + HttpStatus.OK);
			return ResponseEntity.ok().body(_product);
		}).onErrorResume(e -> {
			log.info("Status:" + HttpStatus.BAD_REQUEST + " menssage" + e.getMessage());
			return Mono.just(ResponseEntity.badRequest().build());
		}).defaultIfEmpty(ResponseEntity.noContent().build());

	};

	@DeleteMapping("/{idSignCustAccount}")
	public Mono<ResponseEntity<Void>> delete(@PathVariable(name = "idSignCustAccount") long idSignCustAccount) {
		return accountsService.findById(idSignCustAccount).flatMap(producto -> {
			return accountsService.delete(producto.getIdSignCustAccount()).then(Mono.just(ResponseEntity.ok().build()));
		});
	}

	@PostMapping("/registerSignature")
	public Mono<ResponseEntity<Map<String, Object>>> registerSignature(
			@RequestBody SignatoriesCustomerAccounts customerAccounts) {
		return accountsService.registerSignature(customerAccounts).map(_val -> ResponseEntity.ok().body(_val))
				.onErrorResume(e -> {
					log.info("Status:" + HttpStatus.BAD_REQUEST + " menssage" + e.getMessage());
					Map<String, Object> hashMap = new HashMap<>();
					hashMap.put("Error", e.getMessage());
					return Mono.just(ResponseEntity.badRequest().body(hashMap));
				}).defaultIfEmpty(ResponseEntity.noContent().build());
	}
}
