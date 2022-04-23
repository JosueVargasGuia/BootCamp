package com.nttdata.holder.account.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nttdata.holder.account.service.FeignClient.AccountFeignClient;
import com.nttdata.holder.account.service.FeignClient.CustomerFeignClient;
import com.nttdata.holder.account.service.entity.HolderAccount;
import com.nttdata.holder.account.service.model.Account;
import com.nttdata.holder.account.service.model.Customer;
import com.nttdata.holder.account.service.model.TypeCustomer;
import com.nttdata.holder.account.service.repository.HolderAccountRepository;
import com.nttdata.holder.account.service.service.HolderAccountService;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@Service
public class HolderAccountServiceImpl implements HolderAccountService {

	@Autowired
	private HolderAccountRepository repository;

	@Autowired
	CustomerFeignClient customerFeignClient;
	
	@Autowired
	AccountFeignClient accountFeignClient;
	
	@Autowired
	RestTemplate restTemplate;

	@Value("${api.account-service.uri}")
	private String accountService;

	@Value("${api.customer-service.uri}")
	private String customerService;

	@Value("${api.tableId-service.uri}")
	String tableIdService;

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

		Long key = generateKey(HolderAccount.class.getSimpleName());
		if (key >= 1) {
			holderAccount.setIdHolderAccount(key);
			// log.info("SAVE[product]:"+holderAccount.toString());
		}
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

		log.info("Customer: " + customer + "\nAccount: " + account);

		if (customer != null && account != null) {
			if (account.getIdCustomer() == holderAccount.getIdCustomer()) {
				return this.findAll()
						.filter(obj -> (obj.getIdCustomer() == customer.getId()
								&& obj.getIdAccount() == account.getIdAccount()))
						.collect(Collectors.counting()).map(value -> {
							if (customer.getTypeCustomer() == TypeCustomer.company) {
								this.save(holderAccount).subscribe(e -> log.info("Message:" + e.toString()));
								log.info("Titular de cuenta registrado.");
								hashMap.put("Holder Account: ", "Titular de cuenta registrado.");
							} else {
								if (value <= 0) {
									this.save(holderAccount).subscribe(e -> log.info("Message:" + e.toString()));
									log.info("Titular de cuenta registrado.");
									hashMap.put("Holder Account: ", "Titular de cuenta registrado.");
								} else {
									log.info("Ya tienes un titular registrado.");
									hashMap.put("Holder Account: ", "Ya tienes un titular registrado.");
								}
							}
							return hashMap;
						});
			} else {
				log.info("La cuenta ingresada no le pertenece.");
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
		Customer customer = customerFeignClient.customerfindById(id);
		log.info("Customer encontrado -> " + customer);	
		return customer;
	}

	@Override
	public Account findAccount(Long id) {
		// metodo que busca por id la cuenta asociada
		Account account = accountFeignClient.accountFindById(id);
		log.info("Account encontrado -> " + account);	
		return account;
	}

	@Override
	public Long generateKey(String nameTable) {
//		/log.info(tableIdService + "/generateKey/" + nameTable);
		ResponseEntity<Long> responseGet = restTemplate.exchange(tableIdService + "/generateKey/" + nameTable,
				HttpMethod.GET, null, new ParameterizedTypeReference<Long>() {
				});
		if (responseGet.getStatusCode() == HttpStatus.OK) {
			// log.info("Body:"+ responseGet.getBody());
			return responseGet.getBody();
		} else {
			return Long.valueOf(0);
		}
	}
}
