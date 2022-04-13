package com.nttdata.employee.employeeservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.employee.employeeservice.model.Employee;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
 
@Service
public interface EmployeeService { 
	 
	Mono<Employee> save(Employee employee) ; 
	Flux<Employee> findAll();
}
