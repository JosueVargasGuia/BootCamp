package com.nttdata.movementCreditservice.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	@Value("${api.credit-service.uri}")
	private String creditService ;

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
		Map<String, Object> hashMap = new HashMap<String, Object>();
		boolean isValid = true;
		Credit credit = null;
		if (this.findByIdProduc(movementCredit.getIdCredit()) != null) {
			credit = this.findByIdProduc(movementCredit.getIdCredit());				 
			/*Flux.fromIterable( (Iterable<? extends List>) this.findAll()
			 .takeUntil(objCredit->objCredit.getIdCredit()==movementCredit.getIdCredit())
			 .collectList()).subscribe(z->log.info(z.toString()));*/
			 
			
			 
			 
			 //.collect(Collectors.summingInt(MovementCredit::get))	
		} else {
			hashMap.put("credit", "Cuenta de credito no existe.");
			isValid = false;
		}
		
		return null;
	}

	@Override
	public Credit findByIdProduc(Long idCredit) {
		log.info(creditService + "/" + idCredit);
		ResponseEntity<Credit> responseGet = restTemplate.exchange(creditService + "/" + idCredit, HttpMethod.GET,
				null, new ParameterizedTypeReference<Credit>() {
				});
		if (responseGet.getStatusCode() == HttpStatus.OK) {
			return responseGet.getBody();
		} else {
			return null;
		}
	}

}
