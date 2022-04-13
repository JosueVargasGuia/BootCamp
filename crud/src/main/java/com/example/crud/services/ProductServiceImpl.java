package com.example.crud.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.crud.models.ProductDTO;
import com.example.crud.repositories.IProductDAO;

import reactor.core.publisher.Flux;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private IProductDAO repository;
	
	@Override
	public Flux<ProductDTO> findAll() {
		// TODO Auto-generated method stub
		return Flux.fromIterable(repository.findAll());
	}

}
