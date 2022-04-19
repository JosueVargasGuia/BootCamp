package com.nttdata.TableIdservice.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.TableIdservice.entity.TableId;

@Repository
public interface TableIdRepository extends ReactiveMongoRepository<TableId, String>{
	

}
