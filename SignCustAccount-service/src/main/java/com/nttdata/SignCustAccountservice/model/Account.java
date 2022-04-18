package com.nttdata.SignCustAccountservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
 
public class Account {
	
	@Id
	private String id;
	private Long idProduct; //typeAccount = typeProduct
	private String idCustomer;
	//account
	
	
}