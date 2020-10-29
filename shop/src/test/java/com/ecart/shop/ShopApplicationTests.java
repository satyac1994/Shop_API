package com.ecart.shop;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.ecart.shop.entity.ProductEntity;
import com.ecart.shop.model.Order;
import com.ecart.shop.model.Product;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShopApplicationTests {

	@LocalServerPort
	private int port;

	public ShopApplicationTests() {

	}

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();

	@Test
	public void testAGetProducts() throws Exception {
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<ProductEntity[]> response = restTemplate.exchange(createURLWithPort("/orders/products"),
				HttpMethod.GET, entity, ProductEntity[].class);

		ProductEntity[] body = response.getBody();
		assertTrue(body.length > 0);
	}

	@Test
	public void testBCreateOrder() throws Exception {
		HttpEntity<Order> entity = new HttpEntity<>(createOrderModel(), headers);

		ResponseEntity<Order> response = restTemplate.exchange(createURLWithPort("/orders"), HttpMethod.POST, entity,
				Order.class);

		Order order = response.getBody();
		assertTrue(null != order.getOrderId());
	}

	private Order createOrderModel() {
		Order order = new Order();
		order.setQuantity(5);
		order.setProduct(createProduct());
		return order;
	}

	private Product createProduct() {

		Product product = new Product();
		product.setId(1L);
		return product;
	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}
}
