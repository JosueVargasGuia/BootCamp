package com.nttdata.holder.account.service.entity;

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
@Document(collection = "holder-accounts")
public class HolderAccount {
	
	@Id
	private Long idHolderAccount;
	private Long idCustomer;
	private Long idAccount;
	@Override
	public String toString() {
		return "HolderAccount [idHolderAccount=" + idHolderAccount + ", idCustomer=" + idCustomer + ", idAccount="
				+ idAccount + "]";
	}
}
