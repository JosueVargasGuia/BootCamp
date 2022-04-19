package com.nttdata.holder.account.service.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.holder.account.service.entity.HolderAccount;

@Repository
public interface HolderAccountRepository extends ReactiveMongoRepository<HolderAccount, Long> {

}
