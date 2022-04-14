package com.nttdata.productservice.model;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Configuration {	
	Long idConfiguration;
	Double costMaintenance;// Costo de mantenimiento
	Integer quantityMovement;// Total de movimientos
	// TypeMovement TypeMovement;//tipo
	Integer quantityCredit;// Cantidad de movimientos permitidos, si solo permite un dia de moviento se
						// especifica fecha
	String specificDate;
	@Override
	public String toString() {
		return "Configuration [idConfiguration=" + idConfiguration + ", costMaintenance=" + costMaintenance
				+ ", quantityMovement=" + quantityMovement + ", quantityCredit=" + quantityCredit + ", specificDate="
				+ specificDate + "]";
	}
	
	
}
