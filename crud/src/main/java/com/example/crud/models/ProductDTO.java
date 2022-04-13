package com.example.crud.models;

import java.time.LocalDate;

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
@Document(collection = "products")
public class ProductDTO {
	
	@Id
	private String id;
	
	private String name;
	
	private Double price;
	
	private LocalDate expiry_date;

	
	

}
