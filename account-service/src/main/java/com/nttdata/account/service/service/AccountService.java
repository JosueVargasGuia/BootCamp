package com.nttdata.account.service.service;

import org.springframework.http.ResponseEntity;

import com.nttdata.account.service.model.Account;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {

	Flux<ResponseEntity<Account>> getAllAccounts();
	Mono<ResponseEntity<Account>> saveAccount(Account account);
	Mono<ResponseEntity<Account>> updateAccount(Account account);
	Mono<ResponseEntity<Account>> getById(String id);
	Mono<ResponseEntity<Void>> delete(String id);
}
