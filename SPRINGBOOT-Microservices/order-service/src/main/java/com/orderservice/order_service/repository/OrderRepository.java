package com.orderservice.order_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderservice.order_service.model.Order;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderNumber(String orderNumber);
}
