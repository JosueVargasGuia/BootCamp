package com.nttdata.employee.employeemongoservice.service;

import java.util.List;

import com.nttdata.employee.employeemongoservice.model.EmployeePrivate;

public interface EmployeePrivateService {
	List<EmployeePrivate> findAll();

	EmployeePrivate save(EmployeePrivate employeePrivate);
}
