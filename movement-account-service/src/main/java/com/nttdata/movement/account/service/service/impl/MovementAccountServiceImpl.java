package com.nttdata.movement.account.service.service.impl;

import java.util.Calendar;
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

import com.nttdata.movement.account.service.entity.MovementAccount;
import com.nttdata.movement.account.service.entity.TypeMovementAccount;
import com.nttdata.movement.account.service.model.Account;
import com.nttdata.movement.account.service.repository.MovementAccountRepository;
import com.nttdata.movement.account.service.service.MovementAccountService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MovementAccountServiceImpl implements MovementAccountService {

	Logger logger = LoggerFactory.getLogger(MovementAccountServiceImpl.class);

	@Autowired
	private MovementAccountRepository repository;

	@Autowired
	RestTemplate restTemplate;

	@Value("${api.account-service.uri}")
	private String accountService ;//"http://localhost:8086/account";

	@Override
	public Flux<MovementAccount> findAll() {
		return repository.findAll();
	}

	@Override
	public Mono<MovementAccount> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public Mono<MovementAccount> save(MovementAccount movementAccount) {
		return repository.insert(movementAccount);
	}

	@Override
	public Mono<MovementAccount> update(MovementAccount movementAccount) {
		return repository.save(movementAccount);
	}

	@Override
	public Mono<Void> delete(Long id) {
		return repository.deleteById(id);
	}

	@Override
	public Mono<Map<String, Object>> recordsMovement(MovementAccount movementAccount) {
		// metodo para registrar los movimientos de la cuenta
		
		Map<String, Object> hashMap = new HashMap<String, Object>();
		Account account = this.findByIdAccount(movementAccount.getIdAccount());
		if (account != null) {
			if(movementAccount.getTypeMovementAccount() == TypeMovementAccount.withdrawal) {
				return this.findAll()
						.filter(c -> (c.getIdAccount() == movementAccount.getIdAccount()))
						.map(mov -> {
							logger.info("Amount:"+mov.getAmount());
							if (mov.getTypeMovementAccount() == TypeMovementAccount.withdrawal) {
								mov.setAmount(-1*mov.getAmount());
							}
							return mov;
						})
						.collect(Collectors.summingDouble(MovementAccount::getAmount)).map(_saldo -> {							
							if ( movementAccount.getAmount() <= (_saldo /*+account.getAmount()*/)) { 
								movementAccount.setDateMovementAccount(Calendar.getInstance().getTime());
								this.save(movementAccount).subscribe(e->logger.info("Movimiento de retiro registrado: "+e.toString()));
								hashMap.put("Account success: ", "Registro de movimiento de retiro. Valor retirado: "+ movementAccount.getAmount());
								logger.info("Saldo disponible: " + (_saldo-movementAccount.getAmount())); 
							} else {
								hashMap.put("Message Account", "No cuenta con saldo suficiente para retiro. Saldo disponible: "+(_saldo /*+account.getAmount()*/));
								logger.info("No cuenta con saldo suficiente para retiro."+" Saldo disponible: " +( _saldo /*+account.getAmount() */));
							}
							return hashMap;
						});
			} else {
				movementAccount.setDateMovementAccount(Calendar.getInstance().getTime());
				return this.save(movementAccount).map(_value -> {
					hashMap.put("Account success: ", "Registro de movimiento de depósito. Valor depositado: "+_value.getAmount());
					logger.info("Registro de movimiento de depósito.  Valor depositado: "+_value.getAmount()); 
					//logger.info("Saldo disponible: " + (_value.getAmount())); 
					return hashMap;
				});

			}
		} else {
			hashMap.put("Message: ", "Error. Cuenta no existe.");
			return Mono.just(hashMap);
		}

	}

	@Override
	public Account findByIdAccount(Long idAccount) {
		// buscar la cuenta donde se realizan los movimientos
		logger.info(accountService + "/" + idAccount);
		ResponseEntity<Account> response = restTemplate.exchange(accountService + "/" + idAccount, HttpMethod.GET, null,
				new ParameterizedTypeReference<Account>() {});
		if (response.getStatusCode() == HttpStatus.OK) {
			return response.getBody();
		} else {
			return null;
		}
	}

	@Override
	public Mono<Map<String, Object>> balanceInquiry(Account account) {
		// metodo para la consulta de saldo en la cuenta
		Map<String, Object> hashMap = new HashMap<String, Object>();
		Account _account = this.findByIdAccount(account.getIdAccount());
		if(_account!=null) { //act obj MovementAccount
		return this.findAll().filter(act -> (act.getIdAccount() == _account.getIdAccount())).map(mov -> {
			if (mov.getTypeMovementAccount() == TypeMovementAccount.withdrawal) {
				mov.setAmount(-1*mov.getAmount());
			}
			return mov;
		}).collect(Collectors.summingDouble(MovementAccount::getAmount)).map(_value -> {
			logger.info("Consulta de saldo: "+_value);
			return _value;
		}).map(value->{
			hashMap.put("Status Consulta de saldo:","El saldo de la cuenta es de:" + value);
			//hashMap.put("creditBalance", value);
			logger.info("Account", account);
		return hashMap;
		}
				);	
		}else {
			hashMap.put("Message account", "Cuenta no existe.");
			return Mono.just(hashMap);
		}
	}

}
