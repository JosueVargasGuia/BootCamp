package com.nttdata.SignCustAccountservice.serviceImpl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.SignCustAccountservice.entity.SignatoriesCustomerAccounts;
import com.nttdata.SignCustAccountservice.repository.SignatoriesCustomerAccountsRepository;
import com.nttdata.SignCustAccountservice.service.SignatoriesCustomerAccountsService;
 

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SignatoriesCustomerAccountsServiceImpl  implements SignatoriesCustomerAccountsService{
	Logger log = LoggerFactory.getLogger(SignatoriesCustomerAccountsServiceImpl.class);
	@Autowired
	SignatoriesCustomerAccountsRepository accountsRepository;
	
	@Override
	public Flux<SignatoriesCustomerAccounts> findAll() {
		// TODO Auto-generated method stub
		log.info("INfo:");
		return accountsRepository.findAll().sort((objA,objB)->objA.getIdSignCustAccount().compareTo(objB.getIdSignCustAccount()));
	}

	@Override
	public Mono<SignatoriesCustomerAccounts> findById(Long idSignatoriesCustomerAccounts) {
		// TODO Auto-generated method stub
		return accountsRepository.findById(idSignatoriesCustomerAccounts);
	}

	@Override
	public Mono<SignatoriesCustomerAccounts> save(SignatoriesCustomerAccounts signatoriesCustomerAccounts) {
		// TODO Auto-generated method stub
		return accountsRepository.insert(signatoriesCustomerAccounts);
	}

	@Override
	public Mono<SignatoriesCustomerAccounts> update(SignatoriesCustomerAccounts signatoriesCustomerAccounts) {
		// TODO Auto-generated method stub
		return accountsRepository.save(signatoriesCustomerAccounts);
	}

	@Override
	public Mono<Void> delete(Long idSignatoriesCustomerAccounts) {
		// TODO Auto-generated method stub
		return accountsRepository.deleteById(idSignatoriesCustomerAccounts);
	}
	@Override
	public Mono<Map<String, Object>> recordsMovement(SignatoriesCustomerAccounts signatoriesCustomerAccounts) {
		
		return null;
	}
}
