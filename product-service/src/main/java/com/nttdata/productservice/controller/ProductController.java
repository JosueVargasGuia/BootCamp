package com.nttdata.productservice.controller;

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

import com.nttdata.productservice.entity.Product;
import com.nttdata.productservice.service.ProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/product")
public class ProductController {
	@Autowired
	ProductService productService;

	@GetMapping
	public Flux<Product> findAll() {
		return productService.findAll();
	}

	@PostMapping
	public Mono<Product> save(@RequestBody Product product) {
		return productService.save(product);
	}

	@GetMapping("/{idProducto}")
	public Mono<Product> findById(@PathVariable(name = "idProducto") long idProducto) {
		return productService.findById(idProducto);
	}

	@PutMapping
	public Mono<Product> update(@RequestBody Product product) {
		return productService.update(product);
	}

	@DeleteMapping("/{idProducto}")
	public Mono<Void> delete(@PathVariable(name = "idProducto") long idProducto) {
		return productService.delete(idProducto);
	}

	@GetMapping("/fillData")
	public Mono<Void>  fillData() {
		  return productService.fillData();
		//Mono<Void> e = productService.fillData();
		//HttpStatus status = (e != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
		//return new ResponseEntity<Mono<Void>>(e, status);
	}
}
