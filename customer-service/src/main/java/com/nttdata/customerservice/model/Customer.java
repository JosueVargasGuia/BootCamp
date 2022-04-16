package com.nttdata.customerservice.model;

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
@Document(collection="customers")
public class Customer {
	
	@Id
	private String id;
	private String firstname;
	private String lastname;
	private String documentNumber;
	private TypeDocument typeDocument;
	private String emailAddress;
	private String phoneNumber;
	private String homeAddress;
	
}
