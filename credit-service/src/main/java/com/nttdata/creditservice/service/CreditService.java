package com.nttdata.creditservice.service;

import com.nttdata.creditservice.entity.Credit;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditService {
	
	Flux<Credit> findAll();

	Mono<Credit> findById(long idConfiguration);

	Mono<Credit> save(Credit configuration);

	Mono<Credit> update(Credit configuration);

	Mono<Void> delete(Long id);

}
