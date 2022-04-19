package com.nttdata.holder.account.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {

	private Long idAccount;
	private Long idProduct;
	private Long idCustomer;

	@Override
	public String toString() {
		return "Account [idAccount=" + idAccount + ", idProduct=" + idProduct + ", idCustomer=" + idCustomer + "]";
	}
	 
	
	
}