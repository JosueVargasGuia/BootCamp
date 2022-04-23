package com.nttdata.movementCreditservice.FeignClient.FallBackImpl;

import org.springframework.stereotype.Component;

import com.nttdata.movementCreditservice.FeignClient.TableIdFeignClient;
 

import lombok.extern.log4j.Log4j2;

//@Component
@Log4j2
public class TableIdFeignClientFallBack { 
	public Long generateKey(String nameTable) {
		 log.info("TableIdFeignClientFallBack");
		return Long.valueOf(-1);
	}
 
	 

}
