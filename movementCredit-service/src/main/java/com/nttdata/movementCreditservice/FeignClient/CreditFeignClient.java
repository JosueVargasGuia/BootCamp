package com.nttdata.movementCreditservice.FeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.nttdata.movementCreditservice.FeignClient.FallBackImpl.CreditFeignClientFallBack;
import com.nttdata.movementCreditservice.model.Credit;

@FeignClient(name="creditFeignClient",
url = "${api.credit-service.uri}" 
)

public interface CreditFeignClient {
	@GetMapping("/{idCredit}")
	Credit creditFindByIdCredit(@PathVariable(name = "idCredit") Long idCredit);
	
}
