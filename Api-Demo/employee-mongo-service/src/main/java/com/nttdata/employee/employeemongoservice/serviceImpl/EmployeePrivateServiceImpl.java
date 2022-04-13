package com.nttdata.employee.employeemongoservice.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.employee.employeemongoservice.model.EmployeePrivate;
import com.nttdata.employee.employeemongoservice.repository.EmployeePrivateRepository;
import com.nttdata.employee.employeemongoservice.service.EmployeePrivateService;

@Service
public class EmployeePrivateServiceImpl implements EmployeePrivateService{
@Autowired 
EmployeePrivateRepository employeePrivateRepository;
	
	@Override
	public List<EmployeePrivate> findAll() {
		// TODO Auto-generated method stub
		return employeePrivateRepository.findAll();
	}

	@Override
	public EmployeePrivate save(EmployeePrivate employeePrivate) {
		// TODO Auto-generated method stub
		return employeePrivateRepository.save(employeePrivate);
	}

}
