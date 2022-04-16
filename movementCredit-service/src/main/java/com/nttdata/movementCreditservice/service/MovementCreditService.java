package com.nttdata.movementCreditservice.service;

import java.util.List;

import com.nttdata.movementCreditservice.entity.MovementCredit;
import com.nttdata.movementCreditservice.model.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovementCreditService {
	Flux<MovementCredit> findAll();

	Mono<MovementCredit> findById(Long idMovementCredit);

	Mono<MovementCredit> save(MovementCredit movementCredit);

	Mono<MovementCredit> update(MovementCredit movementCredit);

	Mono<Void> delete(Long idMovementCredit);

	List<Product> findAllProducto();
}
