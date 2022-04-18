package com.nttdata.account.service.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.nttdata.account.service.model.Account;
import com.nttdata.account.service.model.Customer;
import com.nttdata.account.service.model.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {

	//Flux<ResponseEntity<Account>> getAllAccounts();
	Flux<Account> getAllAccounts();
	Mono<ResponseEntity<Account>> saveAccount(Account account);
	Mono<ResponseEntity<Account>> updateAccount(Account account);
	Mono<ResponseEntity<Account>> getById(String id);
	Mono<ResponseEntity<Void>> delete(String id);
	
	Map<String, Object> registerAccount(Account account);
	
	Product findProduct(Long id);
	Customer findCustomer(String id);
}
