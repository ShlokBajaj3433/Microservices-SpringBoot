package com.orderservice.order_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orderservice.order_service.dto.OrderRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    
    @PostMapping
    public String placeOrder(@RequestBody OrderRequest orderRequest) {
        
        
        return "Order placed successfully";
    }
    
}
