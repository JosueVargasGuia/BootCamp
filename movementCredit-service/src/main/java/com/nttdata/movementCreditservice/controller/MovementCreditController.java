package com.nttdata.movementCreditservice.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.nttdata.movementCreditservice.entity.MovementCredit;
import com.nttdata.movementCreditservice.model.Product;
import com.nttdata.movementCreditservice.service.MovementCreditService;
 

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/movementCredit")
public class MovementCreditController {
	Logger log = LoggerFactory.getLogger(MovementCreditController.class);
	@Autowired
	MovementCreditService movementCreditService;

	
	//RestTemplate restTemplate=new RestTemplate();
	
	@GetMapping
	public Flux<MovementCredit> findAll() {
		return movementCreditService.findAll();
	}

	@PostMapping
	public Mono<ResponseEntity<MovementCredit>> save(@RequestBody MovementCredit movementCredit) {
		return movementCreditService.save(movementCredit)
				.map(_movementCredit -> ResponseEntity.ok().body(_movementCredit)).onErrorResume(e -> {
					log.info("Error:" + e.getMessage());
					return Mono.just(ResponseEntity.badRequest().build());
				});
	}

	@GetMapping("/{idMovementCredit}")
	public Mono<ResponseEntity<MovementCredit>> findById(
			@PathVariable(name = "idMovementCredit") long idMovementCredit) {
		return movementCreditService.findById(idMovementCredit)
				.map(movementCredit -> ResponseEntity.ok().body(movementCredit)).onErrorResume(e -> {
					log.info(e.getMessage());
					return Mono.just(ResponseEntity.badRequest().build());
				}).defaultIfEmpty(ResponseEntity.noContent().build());
	}

	@PutMapping
	public Mono<ResponseEntity<MovementCredit>> update(@RequestBody MovementCredit movementCredit) {

		Mono<MovementCredit> mono = movementCreditService.findById(movementCredit.getIdMovementCredit())
				.flatMap(objMovementCredit -> {
					log.info("Update:[new]" + movementCredit + " [Old]:" + movementCredit);
					return movementCreditService.update(movementCredit);
				});

		return mono.map(_movementCredit -> {
			log.info("Status:" + HttpStatus.OK);
			return ResponseEntity.ok().body(_movementCredit);
		}).onErrorResume(e -> {
			log.info("Status:" + HttpStatus.BAD_REQUEST + " menssage" + e.getMessage());
			return Mono.just(ResponseEntity.badRequest().build());
		}).defaultIfEmpty(ResponseEntity.noContent().build());

	}

	@DeleteMapping("/{idMovementCredit}")
	public Mono<ResponseEntity<Void>> delete(@PathVariable(name = "idMovementCredit") long idMovementCredit) {
		return movementCreditService.findById(idMovementCredit).flatMap(movementCredit -> {
			return movementCreditService.delete(movementCredit.getIdMovementCredit())
					.then(Mono.just(ResponseEntity.ok().build()));
		});
	}
	@GetMapping("/test")
	public List<Product> test() {
	 /*try {
		 
			List<Product>lista=movementCreditService.findAllProducto();
	 
			
			for (Product product : lista) {
				log.info("Product:" + product.toString());
			}
	} catch (Exception e) {
		log.info(e.getMessage());
		e.printStackTrace();
	}*/
		
		//return ResponseEntity.ok().build();
	 return movementCreditService.findAllProducto();
	}
}
