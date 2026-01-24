package com.shlok.inventory_service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.shlok.inventory_service.model.Inventory;
import com.shlok.inventory_service.repository.InventoryRepo;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(InventoryRepo inventoryRepo) {
		return args -> {
			Inventory inventory1 = new Inventory();
			inventory1.setSkuCode("Iphone_13");
			inventory1.setQuantity(100);	

			Inventory inventory2 = new Inventory();
			inventory2.setSkuCode("Iphone_13_red");
			inventory2.setQuantity(0);

			inventoryRepo.save(inventory1);
			inventoryRepo.save(inventory2);
		};
	}
	
}
