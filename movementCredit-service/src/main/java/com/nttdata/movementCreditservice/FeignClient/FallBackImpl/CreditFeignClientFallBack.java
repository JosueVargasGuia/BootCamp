package com.nttdata.movementCreditservice.FeignClient.FallBackImpl;

import org.springframework.stereotype.Component;

import com.nttdata.movementCreditservice.FeignClient.CreditFeignClient;
import com.nttdata.movementCreditservice.model.Credit;

import lombok.extern.log4j.Log4j2;

@Log4j2
//@Component
public class CreditFeignClientFallBack   {

 
	public Credit creditFindByIdCredit(Long idCredit) {
		Credit credit=new Credit(Long.valueOf(-1), Long.valueOf(-1), Long.valueOf(-1), null);
		log.info("CreditFeignClientFallBack"+ credit.toString());
		return credit;
	}

}
