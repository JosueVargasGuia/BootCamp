package com.nttdata.movement.account.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.movement.account.service.model.MovementAccount;
import com.nttdata.movement.account.service.service.MovementAccountService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/movements")
public class MovementAccountController {
	
	@Autowired
	private MovementAccountService service;
	
	@GetMapping("/getAll")
	public Flux<MovementAccount> getAll(){
		return service.getAll();
	}
	
	@GetMapping("/getOneMovement/{id}")
	public Mono<ResponseEntity<MovementAccount>> getOneMovementAccount(@PathVariable("id") String id){
		return service.getOne(id);
	}

	@PostMapping("/save")
	public Mono<ResponseEntity<MovementAccount>> saveMovementAccount(@RequestBody MovementAccount movementAccount){
		return service.save(movementAccount);
	}
	
	@PutMapping("/update/{id}")
	public Mono<ResponseEntity<MovementAccount>> updateMovementAccount(@PathVariable("id") String id ,@RequestBody MovementAccount movementAccount){
		return service.update(movementAccount);
	}
	
	@DeleteMapping("/delete/{id}")
	public Mono<ResponseEntity<Void>> deleteMovementAccount(@PathVariable("id") String id){
		return service.delete(id);
	}

}
