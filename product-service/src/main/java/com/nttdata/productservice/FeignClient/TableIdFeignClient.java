package com.nttdata.productservice.FeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.nttdata.productservice.model.TableId;

@FeignClient(name = "tableIdFeignClient", url = "http://localhost:8091/tableid")
public interface TableIdFeignClient {

	@GetMapping("/generateKey/{nameTable}")
	TableId testgenerateKey(@PathVariable(name = "nameTable") String nameTable);

}
