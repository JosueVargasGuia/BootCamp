package com.nttdata.creditservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
	Long idCustomer;
	TypeCustomer typeCustomer;
	String firstName;
	String lastName;
	String emailAddress;
	String phoneNumber;
	String homeAddress;
	String document;
	TypeDocumento typeDocumento;
}
