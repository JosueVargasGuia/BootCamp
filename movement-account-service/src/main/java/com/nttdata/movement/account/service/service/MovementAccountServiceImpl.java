package com.nttdata.movement.account.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nttdata.movement.account.service.model.MovementAccount;
import com.nttdata.movement.account.service.repository.MovementAccountRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MovementAccountServiceImpl implements MovementAccountService{
	
	@Autowired
	private MovementAccountRepository repository;

	@Override
	public Flux<MovementAccount> getAll() {
		return repository.findAll();
	}

	@Override
	public Mono<ResponseEntity<MovementAccount>> getOne(String id) {
		return repository.findById(id)
				.map(movement -> ResponseEntity.ok(movement))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@Override
	public Mono<ResponseEntity<MovementAccount>> save(MovementAccount movementAccount) {
		return repository.save(movementAccount).map(savedMovement -> ResponseEntity.ok(savedMovement));
	}

	@Override
	public Mono<ResponseEntity<MovementAccount>> update(MovementAccount movementAccount) {
		return repository.findById(movementAccount.getId())
				.flatMap(currentMovement -> {
					movementAccount.setAmount(currentMovement.getAmount());
					movementAccount.setDate(currentMovement.getDate());
					movementAccount.setTypeMovement(currentMovement.getTypeMovement());
					return repository.save(movementAccount);
				})
				.map(updatedMovement -> ResponseEntity.ok(updatedMovement))
				.defaultIfEmpty(ResponseEntity.badRequest().build());
	}

	@Override
	public Mono<ResponseEntity<Void>> delete(String id) {
		return repository.findById(id)
				.flatMap(movement -> repository.delete(movement).then(Mono.just(ResponseEntity.ok().build())));
	}

}
