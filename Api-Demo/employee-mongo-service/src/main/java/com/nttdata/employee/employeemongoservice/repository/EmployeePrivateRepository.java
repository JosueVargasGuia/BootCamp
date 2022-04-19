package com.nttdata.employee.employeemongoservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.employee.employeemongoservice.model.EmployeePrivate;

@Repository
public interface EmployeePrivateRepository extends MongoRepository<EmployeePrivate, Long> {

}
