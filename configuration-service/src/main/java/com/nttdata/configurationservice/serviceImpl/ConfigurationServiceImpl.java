package com.nttdata.configurationservice.serviceImpl;

import java.util.ArrayList;
 
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import com.nttdata.configurationservice.entity.Configuration;
import com.nttdata.configurationservice.repository.ConfigurationRepository;
import com.nttdata.configurationservice.service.ConfigurationService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ConfigurationServiceImpl implements ConfigurationService {
	Logger log = LoggerFactory.getLogger(ConfigurationService.class);
	@Autowired
	ConfigurationRepository configurationRepository;

	@Override
	public Flux<Configuration> findAll() {
		// TODO Auto-generated method stub
		return configurationRepository.findAll()
				.sort((config1, config2) -> config1.getIdConfiguration().compareTo(config2.getIdConfiguration()));
	}

	@Override
	public Mono<Configuration> save(Configuration configuration) {
		return configurationRepository.save(configuration);
	}

	@Override
	public Mono<Configuration> findById(long idConfiguration) {
		// TODO Auto-generated method stub
		return configurationRepository.findById(idConfiguration);
	}

	@Override
	public Mono<Configuration> update(Configuration configuration) {
		// TODO Auto-generated method stub
		return configurationRepository.save(configuration);
	}

	@Override
	public Mono<Void> delete(Long id) {
		Mono<Void> mono = configurationRepository.findById(id).flatMap(configuration -> {
			return configurationRepository.delete(configuration);
		});
		return mono;
		// return configurationRepository.deleteById( id);
	}

	@Override
	public Mono<Void> fillData() {
		return configurationRepository.findAll().count().flatMap(x -> {
			log.info("Cantidad[X]:" + x);
			if (x <= 0) {
				List<Configuration> listaConfigurations = new ArrayList<Configuration>();
				/*
				 * Long idConfiguration; double costMaintenance;// Costo de mantenimiento int
				 * quantityMovement;// Total de movimientos // TypeMovement TypeMovement;//tipo
				 * int quantityCredit;// Cantidad de movimientos permitidos, si solo permite un
				 * dia de moviento se // especifica fecha int specificDate;
				 */
				/*
				 * idConfiguration,costMaintenance,quantityMovement,quantityCredit,specificDate
				 */
				// Ahorro: libre de comisión por mantenimiento y con un límite máximo de
				// movimientos mensuales.
				listaConfigurations.add(new Configuration(Long.valueOf(1), null, 5, null, null));
				//  Cuenta corriente: posee comisión de mantenimiento y sin límite de
				// movimientos mensuales.
				listaConfigurations.add(new Configuration(Long.valueOf(2), 10.00, null, null, null));
				//  Plazo fijo: libre de comisión por mantenimiento, solo permite un movimiento
				// de retiro o depósito en un día específico del mes.
				listaConfigurations.add(new Configuration(Long.valueOf(3), null, 1, null, "12"));
				//  Personal: solo se permite un solo crédito por persona.
				listaConfigurations.add(new Configuration(Long.valueOf(4), null, null, 1, null));
				//  Empresarial: se permite más de un crédito por empresa.
				listaConfigurations.add(new Configuration(Long.valueOf(5), null, null, null, null));
				//  Tarjeta de Crédito empresarial.
				listaConfigurations.add(new Configuration(Long.valueOf(6), null, null, null, null));
				//  Tarjeta de Crédito empresarial.
				listaConfigurations.add(new Configuration(Long.valueOf(6), null, null, null, null));
				log.info("Fill data succefull configurations");
				return Flux.fromIterable(listaConfigurations).flatMap(configurations -> {
					log.info("[product]:" + configurations);
					return configurationRepository.save(configurations);
				}).then();

			} else {
				log.info("There are data");
				return Mono.just("There are data configurations" );
			}
		}).then();
	}

}
