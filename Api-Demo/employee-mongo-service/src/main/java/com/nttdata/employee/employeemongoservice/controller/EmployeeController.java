package com.nttdata.employee.employeemongoservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.employee.employeemongoservice.model.Employee;
import com.nttdata.employee.employeemongoservice.service.EmployeeService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	@GetMapping
	public Flux<Employee> findAll() {
		return employeeService.findAll();
	}
	@PostMapping
	public Mono<Employee> save(@RequestBody Employee employee) {
		return employeeService.save(employee);
	}
}
