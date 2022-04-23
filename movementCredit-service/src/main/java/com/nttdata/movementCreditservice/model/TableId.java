package com.nttdata.movementCreditservice.model;

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
@Document(collection = "tableId")
public class TableId {
	@Id
	private String nameTable;
	private Long secuencia;
	@Override
	public String toString() {
		return "TableId [nameTable=" + nameTable + ", secuencia=" + secuencia + "]";
	}
	
}
