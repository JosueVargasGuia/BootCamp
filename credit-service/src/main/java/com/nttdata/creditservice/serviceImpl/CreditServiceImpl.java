package com.nttdata.creditservice.serviceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nttdata.creditservice.entity.Credit;
import com.nttdata.creditservice.repository.CreditRepository;
import com.nttdata.creditservice.service.CreditService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CreditServiceImpl implements CreditService {
	Logger log = LoggerFactory.getLogger(CreditServiceImpl.class);

	@Autowired
	CreditRepository creditRepository;

	@Override
	public Flux<Credit> findAll() {
		return creditRepository.findAll()
				.sort((credit1, credit2) -> credit1.getIdProductCredit().compareTo(credit2.getIdProductCredit()));
	}

	@Override
	public Mono<Credit> save(Credit configuration) {
		return creditRepository.insert(configuration);
	}

	@Override
	public Mono<Credit> findById(long idConfiguration) {
		return creditRepository.findById(idConfiguration);
	}

	@Override
	public Mono<Credit> update(Credit credit) {
		return creditRepository.save(credit);
	}

	@Override
	public Mono<Void> delete(Long idProductCredit) {
		return creditRepository.deleteById(idProductCredit);
	}

}
