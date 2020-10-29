package com.ecart.shop.service;

import com.ecart.shop.entity.InventoryEntity;
import com.ecart.shop.entity.OrderEntity;
import com.ecart.shop.entity.ProductEntity;
import com.ecart.shop.model.Order;
import com.ecart.shop.model.Product;
import com.ecart.shop.model.Status;
import com.ecart.shop.repository.InventoryRepository;
import com.ecart.shop.repository.OrderRepository;
import com.ecart.shop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ShopService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private InventoryRepository inventoryRepository;

	@Autowired
	private OrderRepository orderRepository;

	public List<ProductEntity> getAvailableItems() {
		List<ProductEntity> products = (List<ProductEntity>) productRepository.findAll();
		if(null == products || products.size()<=0) {
			products = new ArrayList<>();
			products.add(createAndReturnProduct());
		}
		return products;
	}

	private ProductEntity createAndReturnProduct() {
		ProductEntity product = new ProductEntity();
		product.setInventory(buildInventoryEntity());
		product.setItemCode("ITEM0001");
		product.setColor("GREEN");
		product.setName("TOY");
		return productRepository.save(product);
		
	}

	private InventoryEntity buildInventoryEntity() {
		InventoryEntity inventoryEntity = new InventoryEntity();
		inventoryEntity.setQuantity(25);
		inventoryEntity.setReservedInventory(0);
		inventoryEntity.setSettledInventory(0);
		return inventoryRepository.save(inventoryEntity);
	}

	public Order createOrder(Order order) {
		ProductEntity product = productRepository.findById(order.getProduct().getId()).get();
		product.getInventory().setQuantity(product.getInventory().getQuantity() - order.getQuantity());
		product.getInventory().setReservedInventory(product.getInventory().getReservedInventory() + order.getQuantity());
		productRepository.save(product);
		OrderEntity orderEntity = orderRepository.save(buildOrderEntity(order));
		return buildOrder(orderEntity);

	}

	private Order buildOrder(OrderEntity entity) {
		Order order = new Order();
		order.setOrderId(entity.getId());
		order.setProduct(buildProduct(entity.getProduct()));
		order.setQuantity(entity.getQuantity());
		order.setStatus(entity.getStatus());
		return order;
	}

	private Product buildProduct(ProductEntity entity) {
		Product product = new Product();
		product.setColor(entity.getColor());
		product.setItemCode(entity.getItemCode());
		product.setName(entity.getName());
		product.setId(entity.getProductId());
		return product;
	}

	private OrderEntity buildOrderEntity(Order order) {
		OrderEntity entity = new OrderEntity();
		entity.setProduct(productRepository.findById(order.getProduct().getId()).get());
		entity.setQuantity(order.getQuantity());
		entity.setStatus(Status.CREATED);
		return entity;
	}

	public Order getOrder(Long orderId) {
		return buildOrder(orderRepository.findById(orderId).get());
	}

	public Order updateOrder(Order order) {
		OrderEntity orderEntity = orderRepository.findById(order.getOrderId()).get();
		if (orderEntity == null) {
			throw new RuntimeException("order is not available in system");
		}
		orderEntity.setQuantity(order.getQuantity());
		return buildOrder(orderRepository.save(orderEntity));
	}

	public Boolean cancelOrder(Long orderId) {
		OrderEntity orderEntity = orderRepository.findById(orderId).get();
		orderEntity.setStatus(Status.CANCELLED);
		orderRepository.save(orderEntity);
		return Boolean.TRUE ;
	}
}
