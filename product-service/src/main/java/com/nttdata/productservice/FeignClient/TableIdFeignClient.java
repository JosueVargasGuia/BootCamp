package com.nttdata.productservice.FeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.nttdata.productservice.model.TableId;
 

 
 
 

@FeignClient(name="tableIdFeignClient",
//url = "${api.tableId-service.uri}"
url = "http://localhost:8091/tableid"
)
public interface TableIdFeignClient {
	
	@GetMapping("/generateKey/{nameTable}")
	TableId generateKey(@PathVariable(name = "nameTable") String nameTable);
	
}
