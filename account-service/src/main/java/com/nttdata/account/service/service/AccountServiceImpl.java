package com.nttdata.account.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nttdata.account.service.model.Account;
import com.nttdata.account.service.repository.AccountRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository repository;
	
	@Autowired
	RestTemplate restTemplate;

	private String customerService;

	private String productService;

	@Override
	public Flux<ResponseEntity<Account>> getAllAccounts() {
		return repository.findAll().map(account -> ResponseEntity.ok(account));
	}

	@Override
	public Mono<ResponseEntity<Account>> saveAccount(Account account) {
		return repository.save(account).map(savedAccount -> ResponseEntity.ok(savedAccount));
	}

	@Override
	public Mono<ResponseEntity<Account>> updateAccount(Account account) {
		return repository.findById(account.getId())
				.flatMap(currentAccount -> {
					account.setIdProduct(currentAccount.getIdProduct());
					account.setIdCustomer(currentAccount.getIdCustomer());
					account.setQuantityAccount(currentAccount.getQuantityAccount());
					return repository.save(account);
				})
				.map(updatedAccount -> ResponseEntity.ok(updatedAccount))
				.defaultIfEmpty(ResponseEntity.badRequest().build());
	}

	@Override
	public Mono<ResponseEntity<Account>> getById(String id) {
		return repository.findById(id)
				.map(account -> ResponseEntity.ok(account))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@Override
	public Mono<ResponseEntity<Void>> delete(String id) {
		return repository.findById(id)
				.flatMap(account -> repository.delete(account).then(Mono.just(ResponseEntity.ok().build())));
	}
}