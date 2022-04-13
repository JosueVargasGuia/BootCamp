package com.nttdata.employee.employeeservice.model;

import javax.annotation.processing.Generated;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@Entity
//@Table(name = "employee")
 @Document(collection = "employees")
public class Employee {
	@Id	 
	private int idEmployee;
	private String firtsName;
	private String lastName;
	private String name;
}
