package com.nttdata.creditservice.serviceImpl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.nttdata.creditservice.entity.Credit;
import com.nttdata.creditservice.model.Customer;
import com.nttdata.creditservice.model.Product;
import com.nttdata.creditservice.repository.CreditRepository;
import com.nttdata.creditservice.service.CreditService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CreditServiceImpl implements CreditService {
	Logger log = LoggerFactory.getLogger(CreditServiceImpl.class);

	@Autowired
	CreditRepository creditRepository;
	@Autowired
	RestTemplate restTemplate;

	@Value("${api.customer-service.uri}")
	private String customerService = "";
	@Value("${api.product-service.uri}")
	private String productService = "";

	@Override
	public Flux<Credit> findAll() {
		return creditRepository.findAll()
				.sort((credit1, credit2) -> credit1.getIdCredit().compareTo(credit2.getIdCredit()));
	}

	@Override
	public Mono<Credit> save(Credit credit) {
		return creditRepository.insert(credit);
	}

	@Override
	public Mono<Credit> findById(Long idCredit) {
		return creditRepository.findById(idCredit);
	}

	@Override
	public Mono<Credit> update(Credit credit) {
		return creditRepository.save(credit);
	}

	@Override
	public Mono<Void> delete(Long idCredit) {
		return creditRepository.deleteById(idCredit);
	}

	/*
	 * <b>Rule 1</b>:Un cliente puede tener un producto de crédito sin la obligación
	 * de tener una cuenta bancaria en la institución<br/>
	 */
	@Override
	public Map<String, Object> registerAccountCredit(Credit credit) {
		Map<String, Object> hashMap = new HashMap<String, Object>();
		if(this.findByIdProduct(credit.getIdProducto())!=null) {
			log.info(this.findByIdProduct(credit.getIdProducto()).toString());
		}else {
			hashMap.put("Product", "Producto no encontrado.");
		}
		
		/*
		 * ResponseEntity<Customer> responseGet = restTemplate.exchange(
		 * customerService+"/8", HttpMethod.GET, null, new
		 * ParameterizedTypeReference<Customer>(){}); Customer
		 * product=responseGet.getBody();
		 */
		log.info(hashMap.toString());
		return hashMap;

	}

	@Override
	public Product findByIdProduct(Long idProducto) {
		ResponseEntity<Product> responseGet = restTemplate.exchange(productService + "/" + idProducto, HttpMethod.GET,
				null, new ParameterizedTypeReference<Product>() {
				});
		if (responseGet.getStatusCode() == HttpStatus.OK) {
			return responseGet.getBody();
		} else {
			return null;
		}

	}

}
