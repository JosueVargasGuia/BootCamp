package com.nttdata.employee.employeeservice.service;

 
 

import com.nttdata.employee.employeeservice.model.Employee;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
 
 
public interface EmployeeService { 
	 
	Mono<Employee> save(Employee employee) ; 
	Flux<Employee> findAll();
}
