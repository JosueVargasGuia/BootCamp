package com.nttdata.SignCustAccountservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
 
public class Customer {
 
	private String id;
	private String firstname;
	private String lastname;
	private String documentNumber;
	private TypeDocument typeDocument;
	private TypeCustomer typeCustomer;
	private String emailAddress;
	private String phoneNumber;
	private String homeAddress;
	
}
