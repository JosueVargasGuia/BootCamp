package com.nttdata.employee.employeeservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nttdata.employee.employeeservice.model.Employee;
import com.nttdata.employee.employeeservice.service.EmployeeService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	@GetMapping
	Flux<Employee> getAll() {
		return employeeService.findAll();
	}

	@PostMapping
	Mono<Employee> save(@RequestBody Employee employee) {
		return employeeService.save(employee);
	}
}
