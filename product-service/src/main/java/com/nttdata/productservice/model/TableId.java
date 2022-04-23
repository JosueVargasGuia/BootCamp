package com.nttdata.productservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TableId {
	private String nameTable;
	private Long secuencia;
	@Override
	public String toString() {
		return "TableId [nameTable=" + nameTable + ", secuencia=" + secuencia + "]";
	}
}
