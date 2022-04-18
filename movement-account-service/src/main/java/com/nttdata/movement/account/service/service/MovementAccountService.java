package com.nttdata.movement.account.service.service;

import com.nttdata.movement.account.service.model.MovementAccount;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovementAccountService {

	Flux<MovementAccount> findAll();
	Mono<MovementAccount> findById(Long id);
	Mono<MovementAccount> save(MovementAccount movementAccount);
	Mono<MovementAccount> update(MovementAccount movementAccount);
	Mono<Void> delete(Long id);
}
