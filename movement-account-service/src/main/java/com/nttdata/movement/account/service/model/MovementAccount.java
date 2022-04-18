package com.nttdata.movement.account.service.model;

import java.time.LocalDate;

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
@Document(collection="movements_accounts")
public class MovementAccount {
	
	@Id
	private Long id;
	private LocalDate date;
	private Double amount;
	private TypeMovement typeMovement;

}
