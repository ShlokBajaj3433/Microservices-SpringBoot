package com.orderservice.order_service;

import com.orderservice.order_service.stubs.InventoryClientStub;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.extension.RegisterExtension;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderServiceApplicationTests {

	@RegisterExtension
	static WireMockExtension wireMockServer = WireMockExtension.newInstance()
			.options(wireMockConfig().dynamicPort())
			.build();

	@ServiceConnection
	static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.3.0");
	
	@LocalServerPort
	private Integer port;

	@DynamicPropertySource
	static void configureProperties(DynamicPropertyRegistry registry) {
		registry.add("inventory.service.url", 
			() -> "http://localhost:" + wireMockServer.getPort());
	}

	@BeforeEach
	void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
		wireMockServer.resetAll();
	}

	static {
		mySQLContainer.start();
	}

	@Test
	void shouldSubmitOrder() {
		String submitOrderJson = """
                {
                     "skuCode": "iphone_15",
                     "price": 1000,
                     "quantity": 1
                }
                """;
		InventoryClientStub.stubInventoryCall(wireMockServer.getRuntimeInfo().getWireMock(), "iphone_15", 1);

		var responseBodyString = RestAssured.given()
				.contentType("application/json")
				.body(submitOrderJson)
				.when()
				.post("/api/order")
				.then()
				.log().all()
				.statusCode(201)
				.extract()
				.body().asString();

		System.out.println("✅ Order Submitted Successfully!");
		System.out.println("Response: " + responseBodyString);
		assertThat(responseBodyString, Matchers.is("Order placed successfully"));
	}

	@Test
	void shouldFailOrderWhenProductIsNotInStock() {
		String submitOrderJson = """
                {
                     "skuCode": "iphone_15",
                     "price": 1000,
                     "quantity": 1000
                }
                """;
		InventoryClientStub.stubInventoryCall(wireMockServer.getRuntimeInfo().getWireMock(), "iphone_15", 1000);

		var response = RestAssured.given()
				.contentType("application/json")
				.body(submitOrderJson)
				.when()
				.post("/api/order")
				.then()
				.log().all()
				.statusCode(500)
				.extract()
				.response();

		System.out.println("❌ Order Failed as Expected!");
		System.out.println("Response Status: " + response.getStatusCode());
		System.out.println("Response Body: " + response.getBody().asString());
	}
}
