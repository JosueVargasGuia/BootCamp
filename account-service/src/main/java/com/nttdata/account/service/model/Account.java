package com.nttdata.account.service.model;

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
@Document(collection = "accounts")
public class Account {
	
	@Id
	private String id;
	private String idProduct; //typeAccount = typeProduct
	private String idCustomer;
	private Integer quantityAccount; //limitar la cuenta si es cliente personal
	//account
}