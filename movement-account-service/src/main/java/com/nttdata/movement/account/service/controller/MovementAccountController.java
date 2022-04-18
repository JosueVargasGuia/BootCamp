package com.nttdata.movement.account.service.controller;

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

import com.nttdata.movement.account.service.model.MovementAccount;
import com.nttdata.movement.account.service.service.MovementAccountService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/movement-account")
public class MovementAccountController {
	
	Logger logger = LoggerFactory.getLogger(MovementAccountController.class);
	
	@Autowired
	private MovementAccountService service;
	
	@GetMapping
	public Flux<MovementAccount> getAll(){
		return service.findAll();
	}
	
	@GetMapping("/{id}")
	public Mono<ResponseEntity<MovementAccount>> getOneMovementAccount(@PathVariable("id") Long id){
		return service.findById(id).map(_movement -> ResponseEntity.ok().body(_movement))
				.onErrorResume(e -> {
					logger.info("Error:" + e.getMessage());
					return Mono.just(ResponseEntity.badRequest().build());
				}).defaultIfEmpty(ResponseEntity.noContent().build());
	}

	@PostMapping
	public Mono<ResponseEntity<MovementAccount>> saveMovementAccount(@RequestBody MovementAccount movementAccount){
		return service.save(movementAccount).map(_movement -> ResponseEntity.ok().body(_movement)).onErrorResume(e -> {
			logger.info("Error:" + e.getMessage());
			return Mono.just(ResponseEntity.badRequest().build());
		});
	}
	
	@PutMapping
	public Mono<ResponseEntity<MovementAccount>> updateMovementAccount(@RequestBody MovementAccount movementAccount){
		Mono<MovementAccount> objMovementAccount = service.findById(movementAccount.getId()).flatMap(_movement -> {
			logger.info("Update: [new] " + movementAccount + "\n[Old]: " + _movement);
			return service.update(movementAccount);
		});
		
		return objMovementAccount.map(_movementAccount -> {
			logger.info("Status: " + HttpStatus.OK);
			return ResponseEntity.ok().body(_movementAccount);
		}).onErrorResume(e -> {
			logger.info("Status: " + HttpStatus.BAD_REQUEST + " Message:  " + e.getMessage());
			return Mono.just(ResponseEntity.badRequest().build());
		}).defaultIfEmpty(ResponseEntity.noContent().build());
	}
	
	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> deleteMovementAccount(@PathVariable("id") Long id){
		return service.findById(id).flatMap(_movement -> {
			return service.delete(_movement.getId()).then(Mono.just(ResponseEntity.ok().build()));
		});
	}

}
