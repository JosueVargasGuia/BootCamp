package com.example.crud.services;

import java.util.List;

import com.example.crud.models.ProductDTO;

import reactor.core.publisher.Flux;

public interface ProductService {

	Flux<ProductDTO> findAll();
	
	
}
