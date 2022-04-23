package com.nttdata.productservice.FeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.nttdata.productservice.model.TableId;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
 
 

@FeignClient(name="tableIdFeignClient",
url = "${api.tableId-service.uri}"  
)
public interface TableIdFeignClient {
	@GetMapping("/generateKey/{nameTable}")
	Long generateKey(@PathVariable(name = "nameTable") String nameTable);
	
}
