package com.nttdata.configurationservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.configurationservice.model.Configuration;
import com.nttdata.configurationservice.service.ConfigurationService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/configuration")
public class ConfigurationController {
	@Autowired
	ConfigurationService configurationService;

	@GetMapping
	public Flux<Configuration> findAll() {
		return configurationService.findAll();
	}
	@PostMapping
	public Mono<Configuration> save(@RequestBody Configuration configuration) {
		return configurationService.save(configuration);
	}
}
