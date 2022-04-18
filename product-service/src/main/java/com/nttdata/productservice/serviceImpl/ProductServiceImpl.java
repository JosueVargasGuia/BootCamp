package com.nttdata.productservice.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import com.nttdata.productservice.entity.Product;
import com.nttdata.productservice.entity.ProductId;
import com.nttdata.productservice.entity.TypeProduct;
import com.nttdata.productservice.repository.ProductRepository;
import com.nttdata.productservice.service.ProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductServiceImpl implements ProductService {
	Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
	@Autowired
	ProductRepository productRepository;

	@Override
	public Flux<Product> findAll() {
		return productRepository.findAll()
				.sort((prodA, prodB) -> prodA.getIdProducto().compareTo(prodB.getIdProducto()));
	}

	@Override
	public Mono<Product> findById(Long idProducto) {
		return productRepository.findById(idProducto);
				//.switchIfEmpty(Mono.just(new Product(null, null, null, null)));
	}

	@Override
	public Mono<Product> save(Product product) {
		return productRepository.insert(product);
	}

	@Override
	public Mono<Product> update(Product product) {
		/*Mono<Product> mono = productRepository.findById(product.getIdProducto())
				.flatMap(objproduct -> {
					return productRepository.save(product);
				});
		return mono;*/
		return productRepository.save(product);
	}

	@Override
	public Mono<Void> delete(Long idProducto) {
		/*Mono<Void> mono = productRepository.findById(idProducto).flatMap(producto -> {
			return productRepository.delete(producto);
		});
		 mono.subscribe(p -> log.info(p.toString()));
		return productRepository.deleteById(idProducto);*/
		 return productRepository.deleteById(idProducto);
	}
	
	Long idProducto=Long.valueOf(0);
	@Override
	public Mono<Void> fillData() {
		return productRepository.findAll().count().flatMap(x -> {
			log.info("Cantidad[X]:" + x);			
				List<Product> listaProducts = new ArrayList<Product>();
				listaProducts.add(new Product(Long.valueOf(1),ProductId.Ahorro, "Ahorro", TypeProduct.pasivos, Long.valueOf(1)));
				listaProducts.add(new Product(Long.valueOf(2),ProductId.CuentaCorriente, "Cuenta corriente", TypeProduct.pasivos, Long.valueOf(2)));
				listaProducts.add(new Product(Long.valueOf(3),ProductId.PlazoFijo, "Plazo fijo", TypeProduct.pasivos, Long.valueOf(3)));
				listaProducts.add(new Product(Long.valueOf(4),ProductId.Personal, "Personal", TypeProduct.activos, Long.valueOf(4)));
				listaProducts.add(new Product(Long.valueOf(5),ProductId.Empresarial, "Empresarial", TypeProduct.activos, Long.valueOf(5)));
				listaProducts.add(new Product(Long.valueOf(6),ProductId.TarjetaCreditoEmpresarial, "Tarjeta de Credito", TypeProduct.activos, Long.valueOf(6)));
				listaProducts.add(new Product(Long.valueOf(7),ProductId.TarjetaCreditoPersonal, "Tarjeta de Credito personal", TypeProduct.activos,Long.valueOf(7)));
				log.info("Fill data succefull");				 
				idProducto=Long.valueOf(x);
				 return Flux.fromIterable(listaProducts).flatMap(product -> {
					log.info("[product]:" + product);
					idProducto=idProducto+1;
					product.setIdProducto(idProducto);
					return this.save(product);
				}).then();
		}).then();		 
	}

}
