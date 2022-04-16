package com.nttdata.movementCreditservice.feing;

import java.util.List;

 
import org.springframework.web.bind.annotation.GetMapping;

import com.nttdata.movementCreditservice.model.Product;

//@FeignClient(name="http://localhost:8083/product")
public interface ProducFeingClients {

	@GetMapping
	List<Product>findAll();
}
