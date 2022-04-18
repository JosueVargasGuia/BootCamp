package com.nttdata.movement.account.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {
	
	private Long id;
	private Long idProduct;
	private Long idCustomer;
	private Double amount;
	
	@Override
	public String toString() {
		return "Account [id=" + id + ", idProduct=" + idProduct + ", idCustomer=" + idCustomer + ", amount=" + amount
				+ "]";
	}
}