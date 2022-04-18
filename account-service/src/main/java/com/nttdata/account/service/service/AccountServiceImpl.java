package com.nttdata.account.service.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nttdata.account.service.model.Account;
import com.nttdata.account.service.model.Customer;
import com.nttdata.account.service.model.Product;
import com.nttdata.account.service.model.TypeCustomer;
import com.nttdata.account.service.model.TypeDocument;
import com.nttdata.account.service.model.TypeProduct;
import com.nttdata.account.service.repository.AccountRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountServiceImpl implements AccountService {
	
	Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

	@Autowired
	private AccountRepository repository;
	
	@Autowired
	RestTemplate restTemplate;

	private String customerService = "http://localhost:8084/customer";

	private String productService = "http://localhost:8083/product";

	@Override
	public Flux<Account> getAllAccounts() {
		//return repository.findAll().map(account -> ResponseEntity.ok(account));
		return repository.findAll().sort((a,b)->a.getId().compareTo(b.getId()));
	}

	
	@Override
	public Mono<ResponseEntity<Account>> saveAccount(Account account) {
		
		return repository.save(account).map(savedAccount -> ResponseEntity.ok(savedAccount));
	}
	


	@Override
	public Mono<ResponseEntity<Account>> updateAccount(Account account) {
		return repository.findById(account.getId())
				.flatMap(currentAccount -> {
					account.setIdProduct(currentAccount.getIdProduct());
					account.setIdCustomer(currentAccount.getIdCustomer());
					return repository.save(account);
				})
				.map(updatedAccount -> ResponseEntity.ok(updatedAccount))
				.defaultIfEmpty(ResponseEntity.badRequest().build());
	}

	@Override
	public Mono<ResponseEntity<Account>> getById(String id) {
		return repository.findById(id)
				.map(account -> ResponseEntity.ok(account))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@Override
	public Mono<ResponseEntity<Void>> delete(String id) {
		return repository.findById(id)
				.flatMap(account -> repository.delete(account).then(Mono.just(ResponseEntity.ok().build())));
	}

	@Override
	public Map<String, Object> registerAccount(Account account) {
		Map<String, Object> hashMap = new HashMap<String, Object>();
		boolean isValidAhorro=true;
		boolean isValidCorriente=true;
		boolean isValidFijo = true;
		Product product = findProduct(account.getIdProduct()); //obteniendo producto para luego comparar su tipo
		Customer customer = findCustomer(account.getIdCustomer()); // obteniendo cliente para luego comparar su tipo
		
		log.error(customer.getFirstname() + product.getDescriptionProducto());
		
		if (customer.getTypeCustomer() == TypeCustomer.personal) {
			if(product.getTypeProduct() == TypeProduct.pasivos) {
				if (isValidAhorro || isValidCorriente || isValidFijo) { // validar si es true para que al seguir la condicion la proxima vez ya sea false
					if (product.getDescriptionProducto() == "Ahorro") {
						Mono.fromRunnable(() -> this.saveAccount(account)).subscribe(e -> System.out.println("fromRunnable:" + e.toString()));
						isValidAhorro=false;
						hashMap.put("Account: ", account);
					}
					if (product.getDescriptionProducto() == "Cuenta corriente") {
						Mono.fromRunnable(() -> this.saveAccount(account)).subscribe(e -> System.out.println("fromRunnable:" + e.toString()));
						isValidCorriente=false;
						hashMap.put("Account: ", account);
					}
					if (product.getDescriptionProducto() == "Plazo fijo") {
						Mono.fromRunnable(() -> this.saveAccount(account)).subscribe(e -> System.out.println("fromRunnable:" + e.toString()));
						isValidFijo=false;
						hashMap.put("Account: ", account);
					}
				}else {
					hashMap.put("Account", "Se ha registro el límite máximo de cuentas.");
				}
			}
		}else { // si es del tipo empresarial permitir solo multiples cuentas corrientes
			if(product.getTypeProduct() == TypeProduct.pasivos) {
				if (product.getDescriptionProducto() == "Cuenta corriente") {
					Mono.fromRunnable(() -> this.saveAccount(account)).subscribe(e -> System.out.println("fromRunnable:" + e.toString()));
					hashMap.put("Account: ", account);
				}else {
					hashMap.put("Account: ", "No es posible abrir una cuenta de " + product.getDescriptionProducto());
				}
			}
		}	
		
		System.out.println("Hola: "+ hashMap);
		return hashMap;

	}

	
	
	//busqueda de producto por su id
	@Override
	public Product findProduct(Long id) {
		ResponseEntity<Product> response = restTemplate.exchange(productService + "/" + id, HttpMethod.GET,
				null, new ParameterizedTypeReference<Product>() {});
		if (response.getStatusCode() == HttpStatus.OK) {
			return response.getBody();
		} else {
			return null;
		}
	}

	//busqueda de cliente por id 
	@Override
	public Customer findCustomer(String id) {
		ResponseEntity<Customer> response = restTemplate.exchange(customerService + "/" + id, HttpMethod.GET,
				null, new ParameterizedTypeReference<Customer>() {});
		if (response.getStatusCode() == HttpStatus.OK) {
			return response.getBody();
		} else {
			return null;
		}
	}
}