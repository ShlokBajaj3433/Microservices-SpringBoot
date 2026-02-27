package com.orderservice.order_service.services;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orderservice.order_service.client.InventoryClient;
import com.orderservice.order_service.dto.OrderRequest;
import com.orderservice.order_service.event.OrderPlacedEvent;
import com.orderservice.order_service.model.Order;
import com.orderservice.order_service.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public void placeOrder(OrderRequest orderRequest) {
        
        boolean isProductInStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());
        
        if (isProductInStock) {
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setPrice(orderRequest.price().multiply(BigDecimal.valueOf(orderRequest.quantity())));
            order.setSkuCode(orderRequest.skuCode());
            order.setQuantity(orderRequest.quantity());
            orderRepository.save(order);
         
            // Kafka producer code can be added here to send order details to a topic for further processing 
            OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent(order.getOrderNumber() , orderRequest.userDetails().email());
            log.info("Start: Send order placed event to Kafka topic: {}", orderPlacedEvent);
            kafkaTemplate.send("order-placed", orderPlacedEvent);
            log.info("End: Send order placed event to Kafka topic: {}", orderPlacedEvent);
        }
        else {
            throw new IllegalArgumentException("Product is not in stock, please try again later");
        }
    }

   
}
