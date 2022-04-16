package com.nttdata.movementCreditservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Credit { 
	Long idCredit;
	Long idCustomer;
	Long idProducto;
	Double amountCreditLimit;
}
