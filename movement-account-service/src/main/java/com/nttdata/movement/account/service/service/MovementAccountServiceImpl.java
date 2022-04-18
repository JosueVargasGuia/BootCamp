package com.nttdata.movement.account.service.service;

import org.springframework.beans.factory.annotation.Autowired;
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
	public Flux<MovementAccount> findAll() {
		return repository.findAll();
	}

	@Override
	public Mono<MovementAccount> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public Mono<MovementAccount> save(MovementAccount movementAccount) {
		return repository.save(movementAccount);
	}

	@Override
	public Mono<MovementAccount> update(MovementAccount movementAccount) {
		return repository.save(movementAccount);
	}

	@Override
	public Mono<Void> delete(Long id) {
		return repository.deleteById(id);
	}

}
