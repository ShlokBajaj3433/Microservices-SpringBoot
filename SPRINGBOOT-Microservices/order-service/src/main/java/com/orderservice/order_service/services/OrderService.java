package com.orderservice.order_service.services;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;
import java.util.List;
import java.util.UUID;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orderservice.order_service.client.InventoryClient;
import com.orderservice.order_service.dto.OrderRequest;
import com.orderservice.order_service.event.OrderPlacedMessage;
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
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void placeOrder(OrderRequest orderRequest) {
        
        boolean isProductInStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());
        
        if (isProductInStock) {
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            BigDecimal unitPrice = BigDecimal.valueOf(orderRequest.price());
            order.setPrice(unitPrice.multiply(BigDecimal.valueOf(orderRequest.quantity())));
            order.setSkuCode(orderRequest.skuCode());
            order.setQuantity(orderRequest.quantity());
            orderRepository.save(order);
         
            // Kafka producer code can be added here to send order details to a topic for further processing 
            
                OrderPlacedMessage orderPlacedMessage = OrderPlacedMessage.builder()
                    .orderNumber(order.getOrderNumber())
                    .email(orderRequest.userDetails().email())
                    .firstName(orderRequest.userDetails().firstName())
                    .lastName(orderRequest.userDetails().lastName())
                    .build();
                CompletableFuture.runAsync(() -> {
                    log.info("Start - Sending OrderPlacedMessage {} to Kafka topic order-placed", orderPlacedMessage);
                    try {
                        kafkaTemplate.send("order-placed", orderPlacedMessage);
                        log.info("End - Sending OrderPlacedMessage {} to Kafka topic order-placed", orderPlacedMessage);
                    } catch (RuntimeException ex) {
                        // Do not fail order creation when event publishing infrastructure is temporarily unavailable.
                        log.warn("Kafka publish failed for order {}, order is still persisted. Cause: {}",
                                order.getOrderNumber(), ex.getMessage());
                    }
                });

        }
        else {
            throw new IllegalArgumentException("Product is not in stock, please try again later");
        }
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderByNumber(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber)
            .orElseThrow(() -> new IllegalArgumentException("Order not found with number: " + orderNumber));
    }

}
