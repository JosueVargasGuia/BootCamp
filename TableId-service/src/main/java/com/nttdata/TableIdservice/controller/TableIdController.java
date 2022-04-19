package com.nttdata.TableIdservice.controller;

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

import com.nttdata.TableIdservice.entity.TableId;
import com.nttdata.TableIdservice.service.TableIdService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/tableid")
public class TableIdController {
	Logger log = LoggerFactory.getLogger(TableIdController.class);

	@Autowired
	TableIdService tableIdService;

	@GetMapping
	public Flux<TableId> findAll() {
		return tableIdService.findAll();
	}

	@PostMapping
	public Mono<ResponseEntity<TableId>> save(@RequestBody TableId tableId) {
		return tableIdService.save(tableId).map(_tableId -> ResponseEntity.ok().body(_tableId)).onErrorResume(e -> {
			log.info("Error:" + e.getMessage());
			return Mono.just(ResponseEntity.badRequest().build());
		});
	}

	@GetMapping("/{nameTable}")
	public Mono<ResponseEntity<TableId>> findById(@PathVariable(name = "nameTable") String nameTable) {
		return tableIdService.findById(nameTable).map(tableId -> ResponseEntity.ok().body(tableId)).onErrorResume(e -> {
			log.info(e.getMessage());
			return Mono.just(ResponseEntity.badRequest().build());
		}).defaultIfEmpty(ResponseEntity.noContent().build());
	}

	@PutMapping
	public Mono<ResponseEntity<TableId>> update(@RequestBody TableId tableId) {

		Mono<TableId> mono = tableIdService.findById(tableId.getNameTable()).flatMap(objTableId -> {
			log.info("Update:[new]" + tableId + " [Old]:" + objTableId);
			return tableIdService.update(tableId);
		});

		return mono.map(_credit -> {
			log.info("Status:" + HttpStatus.OK);
			return ResponseEntity.ok().body(_credit);
		}).onErrorResume(e -> {
			log.info("Status:" + HttpStatus.BAD_REQUEST + " menssage" + e.getMessage());
			return Mono.just(ResponseEntity.badRequest().build());
		}).defaultIfEmpty(ResponseEntity.noContent().build());

	}

	@DeleteMapping("/{nameTable}")
	public Mono<ResponseEntity<Void>> delete(@PathVariable(name = "nameTable") String nameTable) {
		return tableIdService.findById(nameTable).flatMap(credit -> {
			return tableIdService.delete(credit.getNameTable()).then(Mono.just(ResponseEntity.ok().build()));
		});
	}
	@GetMapping("/generateKey/{nameTable}")
	public Mono<ResponseEntity<Long>> generateKey(@PathVariable(name = "nameTable") String nameTable) {
		return tableIdService.generateKey(nameTable)
				.map(tableId -> ResponseEntity.ok().body(tableId)).onErrorResume(e -> {
			log.info(e.getMessage());
			return Mono.just(ResponseEntity.badRequest().build());
		}).defaultIfEmpty(ResponseEntity.noContent().build());
	}
}
