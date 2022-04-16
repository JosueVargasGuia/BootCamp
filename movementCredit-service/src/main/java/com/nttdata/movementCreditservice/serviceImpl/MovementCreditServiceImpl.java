package com.nttdata.movementCreditservice.serviceImpl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.nttdata.movementCreditservice.entity.MovementCredit;
import com.nttdata.movementCreditservice.model.Credit;
import com.nttdata.movementCreditservice.repository.MovementCreditRepository;
import com.nttdata.movementCreditservice.service.MovementCreditService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MovementCreditServiceImpl implements MovementCreditService {
	Logger log = LoggerFactory.getLogger(MovementCreditService.class);
	@Autowired
	MovementCreditRepository movementCreditRepository;

	@Autowired
	RestTemplate restTemplate;

	@Override
	public Flux<MovementCredit> findAll() {
		return movementCreditRepository.findAll().sort((movementCredit1, movementCredit2) -> movementCredit1
				.getIdMovementCredit().compareTo(movementCredit2.getIdMovementCredit()));
	}

	@Override
	public Mono<MovementCredit> save(MovementCredit movementCredit) {
		return movementCreditRepository.insert(movementCredit);
	}

	@Override
	public Mono<MovementCredit> findById(Long idMovementCredit) {
		return movementCreditRepository.findById(idMovementCredit);
	}

	@Override
	public Mono<MovementCredit> update(MovementCredit movementCredit) {
		return movementCreditRepository.save(movementCredit);
	}

	@Override
	public Mono<Void> delete(Long idMovementCredit) {
		return movementCreditRepository.deleteById(idMovementCredit);
	}

	@Override
	public Map<String, Object> recordsMovement(MovementCredit movementCredit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Credit findByIdProduc(Long idCredit) {
		// TODO Auto-generated method stub
		return null;
	}

}
