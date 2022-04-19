package com.nttdata.employee.employeemongoservice.service;

import java.util.List;

import com.nttdata.employee.employeemongoservice.model.Employee;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EmployeeService {
Flux<Employee> findAll();
Mono<Employee> save(Employee employee);
}
