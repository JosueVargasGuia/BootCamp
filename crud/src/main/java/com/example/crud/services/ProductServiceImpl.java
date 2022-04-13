package com.example.crud.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.crud.models.ProductDTO;
import com.example.crud.repositories.IProductDAO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private IProductDAO repository;

	@Override
	public Flux<ProductDTO> findAll() {
		// TODO Auto-generated method stub
		return Flux.fromIterable(repository.findAll());
	}

	@Override
	public Mono<ProductDTO> findOneProduct(String id) {

		Flux<ProductDTO> products = (Flux<ProductDTO>) repository.findAll();

		Mono<ProductDTO> product = products.filter(p -> p.getId().equals(id)).next();

		return product;
	}

	@Override
	public Mono<ProductDTO> addProduct(ProductDTO productDTO) {
		// TODO Auto-generated method stub
		return Mono.just(repository.save(productDTO));
	}

	@Override
	public Mono<ProductDTO> updateProduct(ProductDTO productDTO) {
		// TODO Auto-generated method stub
		return Mono.just(repository.save(productDTO));
	}

	@Override
	public Mono<Void> deleteProduct(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
