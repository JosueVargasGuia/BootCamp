package com.nttdata.configurationservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.configurationservice.model.*;

 
@Repository
public interface ConfigurationRepository extends ReactiveMongoRepository<Configuration, Long> {

 
	
}
