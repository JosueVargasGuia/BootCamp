package com.nttdata.TableIdservice.service;

import com.nttdata.TableIdservice.entity.TableId;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TableIdService {

	Flux<TableId> findAll();

	Mono<TableId> findById(String nameTable);

	Mono<TableId> save(TableId tableId);

	Mono<TableId> update(TableId tableId);

	Mono<Void> delete(String name);

	Mono<Long> generateKey(String nameTable);
}
