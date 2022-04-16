package com.nttdata.creditservice.service;

import com.nttdata.creditservice.entity.Credit;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditService {
	
	Flux<Credit> findAll();

	Mono<Credit> findById(Long idCredit);

	Mono<Credit> save(Credit credit);

	Mono<Credit> update(Credit credit);

	Mono<Void> delete(Long idCredit);

}
