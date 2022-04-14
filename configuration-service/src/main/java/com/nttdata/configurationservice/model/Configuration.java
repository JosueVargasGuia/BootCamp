package com.nttdata.configurationservice.model;

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
@Document(collection = "configuration")
public class Configuration {
	@Id
	Long idConfiguration;
	double costMaintenance;// Costo de mantenimiento
	int quantityMovement;// Total de movimientos
	// TypeMovement TypeMovement;//tipo
	int quantityCredit;// Cantidad de movimientos permitidos, si solo permite un dia de moviento se
						// especifica fecha
	int specificDate;
}
