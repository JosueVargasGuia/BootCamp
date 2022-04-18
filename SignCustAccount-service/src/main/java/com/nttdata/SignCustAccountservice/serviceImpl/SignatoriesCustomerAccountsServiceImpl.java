package com.nttdata.SignCustAccountservice.serviceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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

import com.nttdata.SignCustAccountservice.entity.SignatoriesCustomerAccounts;
import com.nttdata.SignCustAccountservice.model.Account;

import com.nttdata.SignCustAccountservice.model.Customer;
import com.nttdata.SignCustAccountservice.model.Product;
import com.nttdata.SignCustAccountservice.model.ProductId;
import com.nttdata.SignCustAccountservice.model.TypeCustomer;
import com.nttdata.SignCustAccountservice.repository.SignatoriesCustomerAccountsRepository;
import com.nttdata.SignCustAccountservice.service.SignatoriesCustomerAccountsService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SignatoriesCustomerAccountsServiceImpl implements SignatoriesCustomerAccountsService {
	Logger log = LoggerFactory.getLogger(SignatoriesCustomerAccountsServiceImpl.class);

	@Value("${api.account-service.ur}")
	private String accountService;

	@Value("${api.product-service.uri}")
	private String productService;

	@Value("${api.customer-service.uri}")
	private String customerService;

	@Autowired
	SignatoriesCustomerAccountsRepository accountsRepository;

	@Autowired
	RestTemplate restTemplate;

	@Override
	public Flux<SignatoriesCustomerAccounts> findAll() {
		// TODO Auto-generated method stub
		return accountsRepository.findAll()
				.sort((objA, objB) -> objA.getIdSignCustAccount().compareTo(objB.getIdSignCustAccount()));
	}

	@Override
	public Mono<SignatoriesCustomerAccounts> findById(Long idSignatoriesCustomerAccounts) {
		// TODO Auto-generated method stub
		return accountsRepository.findById(idSignatoriesCustomerAccounts);
	}

	@Override
	public Mono<SignatoriesCustomerAccounts> save(SignatoriesCustomerAccounts signatoriesCustomerAccounts) {
		// TODO Auto-generated method stub
		return accountsRepository.insert(signatoriesCustomerAccounts);
	}

	@Override
	public Mono<SignatoriesCustomerAccounts> update(SignatoriesCustomerAccounts signatoriesCustomerAccounts) {
		// TODO Auto-generated method stub
		return accountsRepository.save(signatoriesCustomerAccounts);
	}

	@Override
	public Mono<Void> delete(Long idSignatoriesCustomerAccounts) {
		// TODO Auto-generated method stub
		return accountsRepository.deleteById(idSignatoriesCustomerAccounts);
	}

	@Override
	public Mono<Map<String, Object>> registerSignature(SignatoriesCustomerAccounts signatoriesCustomerAccounts) {
		Account account = this.findIdCredit(signatoriesCustomerAccounts.getIdAccount());
		Customer customer = this.findIdCustomer(signatoriesCustomerAccounts.getIdCustomer());
		Map<String, Object> hasMap = new HashMap<>();
		if (account != null) {
			if (customer != null) {
				Product product = this.findIdProducto(account.getIdProduct());
				if (customer.getTypeCustomer() == TypeCustomer.company) {
					// mas de un registro pero solo de tres cuentas
					//  Cuenta corriente:
					//  Empresarial
					//  Empresarial

					if (product.getProductId() == ProductId.CuentaCorriente
							|| product.getProductId() == ProductId.Empresarial
							|| product.getProductId() == ProductId.TarjetaCreditoEmpresarial) {						
						return this.save(signatoriesCustomerAccounts).map(_obj->{
							log.info("SignatoriesCustomerAccounts:Firma autorisante registrado.");
							hasMap.put("SignatoriesCustomerAccounts", "Firma autorisante registrado.");	
							return hasMap;
						});
						
					}else {
						hasMap.put("SignatoriesCustomerAccounts", "No se puede registrar la firma autorisante en la cuenta");
						return Mono.just(hasMap);
					}

				}
				if (customer.getTypeCustomer() == TypeCustomer.personal) {
					// Solo un registro
					return this.findAll()
							.filter(_filter -> _filter.getIdAccount() == signatoriesCustomerAccounts.getIdAccount()
									&& _filter.getIdCustomer() == signatoriesCustomerAccounts.getIdCustomer())
							.collect(Collectors.counting()).map(_value -> {
								if (_value <= 0) {
									hasMap.put("SignatoriesCustomerAccounts", "Firma autorisante registrado.");
									this.save(signatoriesCustomerAccounts).map(_obj->{
										log.info("SignatoriesCustomerAccounts:Firma autorisante registrado.");
										hasMap.put("SignatoriesCustomerAccounts", "Firma autorisante registrado.");	
										return hasMap;
									}); 
								} else {
									hasMap.put("customer",
											"Existe  una firma registrada para  el cliente " + customer.getFirstname());
								}
								return hasMap;
							});

				}
				return Mono.just(hasMap);
			} else {
				hasMap.put("customer", "El cliente no exite.");
				return Mono.just(hasMap);
			}
		} else {
			hasMap.put("account", "La cuenta no exite.");
			return Mono.just(hasMap);

		}

	}

	@Override
	public Product findIdProducto(Long idProducto) {
		log.info(productService + "/" + idProducto);
		ResponseEntity<Product> responseGet = restTemplate.exchange(productService + "/" + idProducto, HttpMethod.GET,
				null, new ParameterizedTypeReference<Product>() {
				});
		if (responseGet.getStatusCode() == HttpStatus.OK) {
			return responseGet.getBody();
		} else {
			return null;
		}
	}

	@Override
	public Customer findIdCustomer(Long id) {
		log.info(customerService + "/" + id);
		ResponseEntity<Customer> responseGet = restTemplate.exchange(customerService + "/" + id, HttpMethod.GET, null,
				new ParameterizedTypeReference<Customer>() {
				});
		if (responseGet.getStatusCode() == HttpStatus.OK) {
			return responseGet.getBody();
		} else {
			return null;
		}
	}

	@Override
	public Account findIdCredit(Long idCredit) {
		log.info(accountService + "/" + idCredit);
		ResponseEntity<Account> responseGet = restTemplate.exchange(accountService + "/" + idCredit, HttpMethod.GET,
				null, new ParameterizedTypeReference<Account>() {
				});
		if (responseGet.getStatusCode() == HttpStatus.OK) {
			return responseGet.getBody();
		} else {
			return null;
		}
	}
}
