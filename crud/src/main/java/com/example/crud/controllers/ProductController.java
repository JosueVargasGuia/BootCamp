package com.example.crud.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.crud.models.ProductDTO;
import com.example.crud.repositories.IProductDAO;
import com.example.crud.services.ProductService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/products")
public class ProductController {

	@Autowired
	private ProductService service;
	
	
	@PostMapping("/save")
	public Mono<ProductDTO> saveProduct(@Validated @RequestBody ProductDTO dto) {
		
		return service.addProduct(dto);
	}
	
	
	@GetMapping("/list")
	public Flux<ProductDTO> findAllProducts(){
		return service.findAll();
	}
	
	
	@GetMapping("/listOne/{id}")
	public Mono<ProductDTO> findOneProduct(@PathVariable("id") String id){
		return service.findOneProduct(id);
	}
	
}
