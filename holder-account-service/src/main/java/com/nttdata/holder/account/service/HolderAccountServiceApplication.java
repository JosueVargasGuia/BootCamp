package com.nttdata.holder.account.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class HolderAccountServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HolderAccountServiceApplication.class, args);
	}

}
