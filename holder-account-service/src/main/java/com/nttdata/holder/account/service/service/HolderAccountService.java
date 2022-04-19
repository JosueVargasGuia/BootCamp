package com.nttdata.holder.account.service.service;

import java.util.Map;
import com.nttdata.holder.account.service.entity.HolderAccount;
import com.nttdata.holder.account.service.model.Account;
import com.nttdata.holder.account.service.model.Customer;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface HolderAccountService {
	
	Mono<HolderAccount> findById(Long id);
	Flux<HolderAccount> findAll();
	Mono<HolderAccount> save(HolderAccount holderAccount);
	Mono<HolderAccount> update(HolderAccount holderAccount);
	Mono<Void> delete(Long id);
	
	Mono<Map<String, Object>> registerHolderAccount(HolderAccount holderAccount);
	
	Customer findCustomer(Long id);
	Account findAccount(Long id);
	

}
