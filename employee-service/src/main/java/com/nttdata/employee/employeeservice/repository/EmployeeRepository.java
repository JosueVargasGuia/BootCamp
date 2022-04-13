package com.nttdata.employee.employeeservice.repository;
 

 
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.employee.employeeservice.model.Employee;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, Integer> {
 
 
	
}
