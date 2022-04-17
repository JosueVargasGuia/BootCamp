package com.nttdata.movement.account.service.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.movement.account.service.model.MovementAccount;

@Repository
public interface MovementAccountRepository extends ReactiveMongoRepository<MovementAccount, String> {

}
