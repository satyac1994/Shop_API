package com.ecart.shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecart.shop.entity.ProductEntity;
import com.ecart.shop.model.Order;
import com.ecart.shop.service.ShopService;

import lombok.extern.slf4j.Slf4j;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@RestController
@RequestMapping(path = "orders")
@Slf4j
@EnableSwagger2
public class ShopController {
	
	@Autowired
	private ShopService service;

	@GetMapping(path = "/products")
	public List<ProductEntity> getAvailableItems() {

		return service.getAvailableItems();
	}

	@PostMapping
	public Order createOrder( @RequestBody Order order) {
		return service.createOrder(order);
	}

	@GetMapping("/{orderId}")
	public Order getOrder(@PathVariable("orderId") Long orderId) {
		log.info("Getting order details for orderId:"+orderId);
		return service.getOrder(orderId);
	}

	@PutMapping
	public Order updateOrder(@RequestBody Order order) {
		return service.updateOrder(order);
	}

	@DeleteMapping("/{orderId}")
	public Boolean cancelOrder(@PathVariable("orderId") Long orderId) {
		return service.cancelOrder(orderId);
	}
}
