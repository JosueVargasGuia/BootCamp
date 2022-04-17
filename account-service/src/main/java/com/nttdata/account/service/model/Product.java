package com.nttdata.account.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
	
	private Long idProducto;
	private String descriptionProducto;
	private TypeProduct typeProduct;
	private Long idConfiguration;
	
	@Override
	public String toString() {
		return "Product [idProducto=" + idProducto + ", descriptionProducto=" + descriptionProducto + ", typeProduct="
				+ typeProduct + ", idConfiguration=" + idConfiguration + "]";
	}
}
