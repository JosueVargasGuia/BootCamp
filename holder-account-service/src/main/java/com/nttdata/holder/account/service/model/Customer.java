package com.nttdata.holder.account.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Customer {
	
	private Long id;
	private String firstname;
	private String lastname;
	private String documentNumber;
	private TypeDocument typeDocument;
	private TypeCustomer typeCustomer;
	private String emailAddress;
	private String phoneNumber;
	private String homeAddress;
	
	@Override
	public String toString() {
		return "Customer [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", documentNumber="
				+ documentNumber + ", typeDocument=" + typeDocument + ", typeCustomer=" + typeCustomer
				+ ", emailAddress=" + emailAddress + ", phoneNumber=" + phoneNumber + ", homeAddress=" + homeAddress
				+ "]";
	}
}
