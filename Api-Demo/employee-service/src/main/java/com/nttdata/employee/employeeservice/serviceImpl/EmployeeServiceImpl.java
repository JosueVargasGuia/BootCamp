package com.nttdata.employee.employeeservice.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.employee.employeeservice.model.Employee;
import com.nttdata.employee.employeeservice.repository.EmployeeRepository;
import com.nttdata.employee.employeeservice.service.EmployeeService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	EmployeeRepository employeeRepository;

	@Override
	public Mono<Employee> save(Employee employee) {
		// TODO Auto-generated method stub
		return Mono.just(employeeRepository.save(employee));
	}

	@Override
	public Flux<Employee> findAll() {
		// TODO Auto-generated method stub
		return Flux.fromIterable(employeeRepository.findAll());
	}

}
