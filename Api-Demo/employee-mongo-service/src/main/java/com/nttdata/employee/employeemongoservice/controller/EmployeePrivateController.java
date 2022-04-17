package com.nttdata.employee.employeemongoservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.employee.employeemongoservice.model.EmployeePrivate;
import com.nttdata.employee.employeemongoservice.service.EmployeePrivateService;

@RestController
@RequestMapping("/employeePrivate")
public class EmployeePrivateController {
	@Autowired
	EmployeePrivateService employeePrivateService;

	@GetMapping
	public List<EmployeePrivate> findAll() {
		return employeePrivateService.findAll();
	}

	@PostMapping
	public EmployeePrivate save(@RequestBody EmployeePrivate employeePrivate) {
		return employeePrivateService.save(employeePrivate);
	}
}
