package com.nttdata.holder.account.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nttdata.holder.account.service.entity.HolderAccount;
import com.nttdata.holder.account.service.model.Account;
import com.nttdata.holder.account.service.model.Customer;
import com.nttdata.holder.account.service.model.TypeCustomer;
import com.nttdata.holder.account.service.repository.HolderAccountRepository;
import com.nttdata.holder.account.service.service.HolderAccountService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class HolderAccountServiceImpl implements HolderAccountService {

	Logger logger = LoggerFactory.getLogger(HolderAccountServiceImpl.class);

	@Autowired
	private HolderAccountRepository repository;

	@Autowired
	RestTemplate restTemplate;

	// @Value("${api.account-service.uri}")
	private String accountService = "http://localhost:8086/account";

	// @Value("${api.customer-service.uri}")
	private String customerService = "http://localhost:8087/customer";

	@Override
	public Mono<HolderAccount> findById(Long id) {
		// metodo para la busqueda por id del titular de la cuenta
		return repository.findById(id);
	}

	@Override
	public Flux<HolderAccount> findAll() {
		// metodo para la busqueda de todos los titulares de cuenta registrados
		return repository.findAll();
	}

	@Override
	public Mono<HolderAccount> save(HolderAccount holderAccount) {
		// metodo para insertar un nuevo titular de la cuenta
		return repository.insert(holderAccount);
	}

	@Override
	public Mono<HolderAccount> update(HolderAccount holderAccount) {
		// metodo para actualizar el titular de la cuenta
		return repository.save(holderAccount);
	}

	@Override
	public Mono<Void> delete(Long id) {
		// metodo para eliminar el titular de la cuenta
		return repository.deleteById(id);
	}

	@Override
	public Mono<Map<String, Object>> registerHolderAccount(HolderAccount holderAccount) {
		// metodo para registrar el titular de la cuenta empresarial que puede ser mas
		// de un titular

		Map<String, Object> hashMap = new HashMap<String, Object>();

		Customer customer = findCustomer(holderAccount.getIdCustomer());
		Account account = findAccount(holderAccount.getIdAccount());

		logger.info("Customer: " + customer + "\nAccount: " + account);

		if (customer != null && account != null) {
			if(account.getIdCustomer() == holderAccount.getIdCustomer()) {
				return this.findAll()
						.filter(obj -> (obj.getIdCustomer() == customer.getId()
								&& obj.getIdAccount() == account.getIdAccount()))
						.collect(Collectors.counting()).map(value -> {	
							if (customer.getTypeCustomer() == TypeCustomer.company) {
								this.save(holderAccount).subscribe(e -> logger.info("Message:" + e.toString()));
								logger.info("Titular de cuenta registrado.");
								hashMap.put("Holder Account: ", "Titular de cuenta registrado.");
							} else {
								if (value <= 0) {
									this.save(holderAccount).subscribe(e -> logger.info("Message:" + e.toString()));
									logger.info("Titular de cuenta registrado.");
									hashMap.put("Holder Account: ", "Titular de cuenta registrado.");
								} else {
									logger.info("Ya tienes un titular registrado.");
									hashMap.put("Holder Account: ", "Ya tienes un titular registrado.");
								}
							}
							return hashMap;
						});
			}else {
				logger.info("La cuenta ingresada no le pertenece.");
				hashMap.put("Holder Account: ", "La cuenta ingresada no le pertenece.");
			}
		} else {
			hashMap.put("Error message: ", "Cliente o cuenta no encontrados.");
		}

		return Mono.just(hashMap);
	}

	@Override
	public Customer findCustomer(Long id) {
		// metodo que busca por id al cliente asociado
		logger.info(customerService + "/" + id);
		ResponseEntity<Customer> response = restTemplate.exchange(customerService + "/" + id, HttpMethod.GET, null,
				new ParameterizedTypeReference<Customer>() {
				});
		if (response.getStatusCode() == HttpStatus.OK) {
			return response.getBody();
		} else {
			return null;
		}
	}

	@Override
	public Account findAccount(Long id) {
		// metodo que busca por id la cuenta asociada
		logger.info(accountService + "/" + id);
		ResponseEntity<Account> response = restTemplate.exchange(accountService + "/" + id, HttpMethod.GET, null,
				new ParameterizedTypeReference<Account>() {
				});
		if (response.getStatusCode() == HttpStatus.OK) {
			return response.getBody();
		} else {
			return null;
		}
	}

}
