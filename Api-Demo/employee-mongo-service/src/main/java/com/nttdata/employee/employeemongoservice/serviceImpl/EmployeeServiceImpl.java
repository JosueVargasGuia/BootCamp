package com.nttdata.employee.employeemongoservice.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.employee.employeemongoservice.model.Employee;
import com.nttdata.employee.employeemongoservice.repository.EmployeeRepository;
import com.nttdata.employee.employeemongoservice.service.EmployeeService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Service
public class EmployeeServiceImpl implements EmployeeService {

	
	@Autowired
	EmployeeRepository employeeRepository;
	@Override
	public Flux<Employee> findAll() {
		// TODO Auto-generated method stub
		return Flux.fromIterable(employeeRepository.findAll());
	}
	@Override
	public Mono<Employee> save(Employee employee) {
		// TODO Auto-generated method stub
		return Mono.just(employeeRepository.save(employee));
	}

}
