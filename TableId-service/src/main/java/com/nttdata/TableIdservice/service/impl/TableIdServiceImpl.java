package com.nttdata.TableIdservice.service.impl;

import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nttdata.TableIdservice.entity.TableId;
import com.nttdata.TableIdservice.repository.TableIdRepository;
import com.nttdata.TableIdservice.service.TableIdService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TableIdServiceImpl implements TableIdService {
	Logger log = LoggerFactory.getLogger(TableIdService.class);
	@Autowired
	TableIdRepository tableIdRepository;

	@Override
	public Flux<TableId> findAll() {
		// TODO Auto-generated method stub
		return tableIdRepository.findAll();
	}

	@Override
	public Mono<TableId> findById(String nameTable) {
		// TODO Auto-generated method stub
		return tableIdRepository.findById(nameTable);
	}

	@Override
	public Mono<TableId> save(TableId tableId) {
		// TODO Auto-generated method stub
		return tableIdRepository.insert(tableId);
	}

	@Override
	public Mono<TableId> update(TableId tableId) {
		// TODO Auto-generated method stub
		return tableIdRepository.save(tableId);
	}

	@Override
	public Mono<Void> delete(String name) {
		// TODO Auto-generated method stub
		return tableIdRepository.deleteById(name);
	}

	@Override
	public Mono<Long> generateKey(String nameTable) {
		return this.findAll().filter(e -> e.getNameTable().equalsIgnoreCase(nameTable)).collect(Collectors.counting())
				.flatMap(value -> {
					log.info("flatMap[value]:" + value);
					if (value <= 0) {
						return this.save(new TableId(nameTable,Long.valueOf(1))).map(_obj -> {
							log.info("save:"+_obj.toString());
							return _obj.getSecuencia();
						});

					} else {
						return this.findById(nameTable).map(objU -> {
							log.info("findById:"+objU.toString());
							objU.setSecuencia(objU.getSecuencia() + 1);
							Mono<Long> mono = this.update(objU).map(_obj -> {
									log.info("update:"+objU.toString());
								 return _obj.getSecuencia();
							});
							mono.subscribe();
							return objU.getSecuencia();
						});

					}
				});
		/*
		 * .map(e->{ log.info("map[value]:"+e); return e; }) //
		 * .subscribe(e->log.info("subcribe:"+e)) ;
		 * 
		 * .map(e->{return e;}) .defaultIfEmpty(new TableId(nameTable, 0))
		 * .flatMap(obj->{ log.info("flatMap:"+obj.toString()); return Mono.just(obj);
		 * }) .subscribe(e->log.info(e.toString()));
		 */

		// return Mono.just(Integer.valueOf(0));
	}

}
