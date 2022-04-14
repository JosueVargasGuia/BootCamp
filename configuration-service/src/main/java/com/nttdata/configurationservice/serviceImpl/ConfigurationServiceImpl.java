package com.nttdata.configurationservice.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.configurationservice.model.Configuration;
import com.nttdata.configurationservice.repository.ConfigurationRepository;
import com.nttdata.configurationservice.service.ConfigurationService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ConfigurationServiceImpl implements ConfigurationService {
	@Autowired
	ConfigurationRepository configurationRepository;

	@Override
	public Flux<Configuration> findAll() {
		// TODO Auto-generated method stub
		return  configurationRepository.findAll();
	}

	@Override
	public Mono<Configuration> save(Configuration configuration ) {		
		return   configurationRepository.save(configuration) ;
	}

	@Override
	public Mono<Configuration> findById(long idConfiguration) {
		// TODO Auto-generated method stub
		return  configurationRepository.findById(idConfiguration) ;
	}

	@Override
	public Mono<Configuration> update(Configuration configuration) {
		// TODO Auto-generated method stub
		return  configurationRepository.save(configuration) ;
	}

	@Override
	public Mono<Void>  delete(Long id) {	 
		return configurationRepository.deleteById( id);
	}

	 

}
