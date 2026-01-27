package com.shlok.inventory_service;

import com.shlok.inventory_service.model.Inventory;
import com.shlok.inventory_service.repository.InventoryRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InventoryServiceApplicationTests {

	@ServiceConnection
	static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.3.0");

	@LocalServerPort
	private Integer port;

	@Autowired
	private InventoryRepository inventoryRepository;

	@BeforeEach
	void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	static {
		mySQLContainer.start();
	}

	@Test
	void shouldCheckInventoryInStock() {
		// Create test inventory
		var inventory = new Inventory();
		inventory.setSkuCode("iphone_15");
		inventory.setQuantity(100);
		inventoryRepository.save(inventory);

		// Test with sufficient quantity
		var isInStock = RestAssured.given()
				.param("skuCode", "iphone_15")
				.param("quantity", 10)
				.when()
				.get("/api/inventory")
				.then()
				.log().all()
				.statusCode(200)
				.extract().response().as(Boolean.class);

		assertTrue(isInStock);
	}

	@Test
	void shouldCheckInventoryOutOfStock() {
		// Create test inventory with limited quantity
		var inventory = new Inventory();
		inventory.setSkuCode("pixel_8");
		inventory.setQuantity(5);
		inventoryRepository.save(inventory);

		// Test with insufficient quantity
		var isInStock = RestAssured.given()
				.param("skuCode", "pixel_8")
				.param("quantity", 10)
				.when()
				.get("/api/inventory")
				.then()
				.log().all()
				.statusCode(200)
				.extract().response().as(Boolean.class);

		assertFalse(isInStock);
	}

	@Test
	void shouldReturnFalseForNonExistentProduct() {
		// Test with product that doesn't exist
		var isInStock = RestAssured.given()
				.param("skuCode", "non_existent_product")
				.param("quantity", 1)
				.when()
				.get("/api/inventory")
				.then()
				.log().all()
				.statusCode(200)
				.extract().response().as(Boolean.class);

		assertFalse(isInStock);
	}
}
