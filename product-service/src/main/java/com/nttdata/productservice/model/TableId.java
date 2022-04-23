package com.nttdata.productservice.model;

import lombok.Data;

@Data
public class TableId {
	private String nameTable;
	private Long secuencia;
	@Override
	public String toString() {
		return "TableId [nameTable=" + nameTable + ", secuencia=" + secuencia + "]";
	}
}
