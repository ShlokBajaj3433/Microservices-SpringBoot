package com.orderservice.order_service.services;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.orderservice.order_service.client.InventoryClient;
import com.orderservice.order_service.dto.OrderRequest;
import com.orderservice.order_service.model.Order;
import com.orderservice.order_service.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;
    private final InventoryClient inventoryClient;

    public void placeOrder(OrderRequest orderRequest) {
        
        boolean isProductInStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());
        
        if (isProductInStock) {
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setPrice(orderRequest.price().multiply(BigDecimal.valueOf(orderRequest.quantity())));
            order.setSkuCode(orderRequest.skuCode());
            order.setQuantity(orderRequest.quantity());
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Product is not in stock, please try again later");
        }
    }

   
}
