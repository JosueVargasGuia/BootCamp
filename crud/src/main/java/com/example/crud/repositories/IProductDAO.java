package com.example.crud.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.crud.models.ProductDTO;

@Repository
public interface IProductDAO extends MongoRepository<ProductDTO, String> {

}
