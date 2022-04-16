package com.nttdata.movementCreditservice.serviceImpl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nttdata.movementCreditservice.entity.MovementCredit;
import com.nttdata.movementCreditservice.feing.ProducFeingClients;
import com.nttdata.movementCreditservice.model.Product;
import com.nttdata.movementCreditservice.repository.MovementCreditRepository;
import com.nttdata.movementCreditservice.service.MovementCreditService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MovementCreditServiceImpl implements MovementCreditService {
	Logger log = LoggerFactory.getLogger(MovementCreditService.class);
	@Autowired
	MovementCreditRepository movementCreditRepository;
	 //@Autowired
	 //ProducFeingClients producFeingClients;

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
	public List<Product> findAllProducto() {
		//https://www.baeldung.com/spring-webclient-resttemplate?msclkid=fadb8d80bd2a11eca83e384e61a5fdd4
		final String uri="http://localhost:8083/product";
		ResponseEntity<List<Product>> response = restTemplate.exchange(
			      uri, HttpMethod.GET, null,
			      new ParameterizedTypeReference<List<Product>>(){});
		List<Product> list=response.getBody();
		//list.forEach(product->log.info(product.toString()));
		 
		
		
		ResponseEntity<Product> responseGet = restTemplate.exchange(
			      uri+"/8", HttpMethod.GET, null,
			      new ParameterizedTypeReference<Product>(){});
		Product product=responseGet.getBody();
		log.info("HttpStatus:"+(responseGet.getStatusCode()==HttpStatus.OK));
		log.info("product:"+product.toString());
		
		
 		return list;
 
	}

}
