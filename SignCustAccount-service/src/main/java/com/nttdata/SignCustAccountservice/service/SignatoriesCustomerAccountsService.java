package com.nttdata.SignCustAccountservice.service;

import java.util.Map;

import com.nttdata.SignCustAccountservice.entity.SignatoriesCustomerAccounts;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SignatoriesCustomerAccountsService {

	Flux<SignatoriesCustomerAccounts> findAll();

	Mono<SignatoriesCustomerAccounts> findById(Long idSignatoriesCustomerAccounts);

	Mono<SignatoriesCustomerAccounts> save(SignatoriesCustomerAccounts signatoriesCustomerAccounts);

	Mono<SignatoriesCustomerAccounts> update(SignatoriesCustomerAccounts signatoriesCustomerAccounts);

	Mono<Void> delete(Long idSignatoriesCustomerAccounts);

	  Mono<Map<String, Object>> recordsMovement(SignatoriesCustomerAccounts signatoriesCustomerAccounts);
}
