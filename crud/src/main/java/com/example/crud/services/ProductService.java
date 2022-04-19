package com.example.crud.services;

import com.example.crud.models.ProductDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {

	Flux<ProductDTO> findAll();
	
	Mono<ProductDTO> findOneProduct(String id);
	
	Mono<ProductDTO> addProduct(ProductDTO productDTO);
	
	Mono<ProductDTO> updateProduct(ProductDTO productDTO);
	
	Mono<Void> deleteProduct(String id);
	
}
