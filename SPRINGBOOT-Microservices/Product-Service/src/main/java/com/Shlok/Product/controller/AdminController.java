package com.Shlok.Product.controller;

import org.springframework.web.bind.annotation.*;
import java.util.Arrays;
import java.util.List;

import com.Shlok.Product.Service.ProductService;
import com.Shlok.Product.dto.ProductRequest;
import com.Shlok.Product.dto.ProductResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final ProductService productService;

    @PostMapping("/initialize-products")
    public String initializeProducts() {
        log.info("Starting manual product initialization...");
        
        var testProducts = Arrays.asList(
            ProductRequest.builder().id(null).name("iPhone 15").description("Latest Apple iPhone with A17 chip").skuCode("iphone_15").price(999.99).build(),
            ProductRequest.builder().id(null).name("Pixel 8").description("Google Pixel 8 with advanced AI capabilities").skuCode("pixel_8").price(799.99).build(),
            ProductRequest.builder().id(null).name("Galaxy 24").description("Samsung Galaxy S24 Ultra with S Pen").skuCode("galaxy_24").price(1199.99).build(),
            ProductRequest.builder().id(null).name("OnePlus 12").description("OnePlus 12 with ultra-fast charging").skuCode("oneplus_12").price(749.99).build()
        );
        
        int count = 0;
        for (ProductRequest product : testProducts) {
            try {
                productService.createProduct(product);
                count++;
            } catch (Exception e) {
                log.error("Failed to create product {}: {}", product.getSkuCode(), e.getMessage());
            }
        }
        
        log.info("Successfully initialized {} products", count);
        return "Initialized " + count + " products";
    }
}
