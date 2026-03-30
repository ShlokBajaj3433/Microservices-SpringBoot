package com.orderservice.order_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.orderservice.order_service.dto.OrderRequest;
import com.orderservice.order_service.model.Order;
import com.orderservice.order_service.services.OrderService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    
    private final OrderService orderService;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest) {
        orderService.placeOrder(orderRequest);
        return "Order placed successfully";
    }
    
    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }
    
    @GetMapping("/{orderNumber}")
    public Order getOrderByNumber(@PathVariable String orderNumber) {
        return orderService.getOrderByNumber(orderNumber);
    }
    
}
