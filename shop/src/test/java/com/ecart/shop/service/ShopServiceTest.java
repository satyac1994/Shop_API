package com.ecart.shop.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.ecart.shop.entity.InventoryEntity;
import com.ecart.shop.entity.OrderEntity;
import com.ecart.shop.entity.ProductEntity;
import com.ecart.shop.model.Order;
import com.ecart.shop.model.Product;
import com.ecart.shop.repository.InventoryRepository;
import com.ecart.shop.repository.OrderRepository;
import com.ecart.shop.repository.ProductRepository;

@RunWith(MockitoJUnitRunner.class)
public class ShopServiceTest {

	@Mock
	private ProductRepository productRepository;

	@Mock
	private InventoryRepository inventoryRepository;

	@Mock
	private OrderRepository orderRepository;

	@InjectMocks
	private ShopService service;

	@Test
	public void createOrder() {
		ProductEntity product = createAndReturnProduct();
		when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(product));
		when(orderRepository.save(any())).thenReturn(createOrderEntity());
		service.createOrder(createOrderModle());
		verify(orderRepository, times(1)).save(any());

	}

	private OrderEntity createOrderEntity() {
		OrderEntity order = new OrderEntity();
		order.setId(1L);
		ProductEntity product = new ProductEntity();
		product.setProductId(1L);
		InventoryEntity inventory = new InventoryEntity();
		inventory.setId(1L);
		product.setInventory(inventory);
		order.setProduct(product);

		return order;
	}

	private Order createOrderModle() {
		Order order = new Order();
		order.setOrderId(1L);
		order.setProduct(createProductModel());
		order.setQuantity(5);
		return order;
	}

	private Product createProductModel() {
		Product product = new Product();
		product.setId(1L);
		return product;
	}

	private ProductEntity createAndReturnProduct() {
		ProductEntity product = new ProductEntity();
		product.setProductId(1L);
		product.setInventory(buildInventoryEntity());
		product.setItemCode("ITEM0001");
		product.setColor("GREEN");
		product.setName("TOY");
		return product;

	}

	private InventoryEntity buildInventoryEntity() {
		InventoryEntity inventoryEntity = new InventoryEntity();
		inventoryEntity.setId(1L);
		inventoryEntity.setQuantity(25);
		inventoryEntity.setReservedInventory(0);
		inventoryEntity.setSettledInventory(0);
		return inventoryEntity;
	}

	@Test
	public void getOrder() {
		when(orderRepository.findById(any())).thenReturn(Optional.of(createOrderEntity()));
		Order order = service.getOrder(1L);
		Assertions.assertThat(order.getOrderId()).isNotNull();

	}

	@Test
	public void updateOrder() {
		when(orderRepository.findById(any())).thenReturn(Optional.of(createOrderEntity()));
		when(orderRepository.save(any())).thenReturn(createOrderEntity());
		Order createOrderModle = createOrderModle();
		Order updateOrder = service.updateOrder(createOrderModle);
		Assertions.assertThat(updateOrder.getOrderId()).isNotNull();
		verify(orderRepository, times(1)).save(any());
	}

	@Test
	public void cancelOrder() {
		when(orderRepository.findById(any())).thenReturn(Optional.of(createOrderEntity()));
		service.cancelOrder(1L);
		verify(orderRepository, times(1)).save(any());
	}
}
