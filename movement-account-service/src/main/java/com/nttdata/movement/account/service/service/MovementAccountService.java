package com.nttdata.movement.account.service.service;

import org.springframework.http.ResponseEntity;

import com.nttdata.movement.account.service.model.MovementAccount;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovementAccountService {

	Flux<MovementAccount> getAll();
	Mono<ResponseEntity<MovementAccount>> getOne(String id);
	Mono<ResponseEntity<MovementAccount>> save(MovementAccount movementAccount);
	Mono<ResponseEntity<MovementAccount>> update(MovementAccount movementAccount);
	Mono<ResponseEntity<Void>> delete(String id);
}
