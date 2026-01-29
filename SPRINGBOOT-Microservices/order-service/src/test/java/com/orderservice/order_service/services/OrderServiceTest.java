package com.orderservice.order_service.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.orderservice.order_service.client.InventoryClient;
import com.orderservice.order_service.dto.OrderRequest;
import com.orderservice.order_service.dto.OrderRequest.UserDetails;
import com.orderservice.order_service.model.Order;
import com.orderservice.order_service.repository.OrderRepository;

/**
 * Unit tests for OrderService
 * Tests the core business logic for order placement
 */
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private InventoryClient inventoryClient;

    @InjectMocks
    private OrderService orderService;

    private OrderRequest orderRequest;

    @BeforeEach
    void setUp() {
        // Arrange - Setup test data
        UserDetails userDetails = new UserDetails("test@example.com", "John", "Doe");
        orderRequest = new OrderRequest(
            null,
            null,
            "IPHONE_13",
            new BigDecimal("1000.00"),
            2,
            userDetails
        );
    }

    @Test
    void placeOrder_ShouldSaveOrder_WhenProductInStock() {
        // Arrange
        when(inventoryClient.isInStock("IPHONE_13", 2)).thenReturn(true);
        when(orderRepository.save(any(Order.class))).thenReturn(new Order());

        // Act
        orderService.placeOrder(orderRequest);

        // Assert
        verify(inventoryClient, times(1)).isInStock("IPHONE_13", 2);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void placeOrder_ShouldGenerateOrderNumber() {
        // Arrange
        when(inventoryClient.isInStock("IPHONE_13", 2)).thenReturn(true);
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order savedOrder = invocation.getArgument(0);
            // Assert - Verify order number was generated
            assertNotNull(savedOrder.getOrderNumber(), 
                "Order number should not be null");
            assertFalse(savedOrder.getOrderNumber().isEmpty(), 
                "Order number should not be empty");
            return savedOrder;
        });

        // Act
        orderService.placeOrder(orderRequest);

        // Assert
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void placeOrder_ShouldMapFieldsCorrectly() {
        // Arrange
        when(inventoryClient.isInStock("IPHONE_13", 2)).thenReturn(true);
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order savedOrder = invocation.getArgument(0);
            
            // Assert - Verify order fields
            assertEquals("IPHONE_13", savedOrder.getSkuCode(), 
                "SKU code should match");
            assertEquals(new BigDecimal("2000.00"), savedOrder.getPrice(), 
                "Price should be quantity * unit price");
            assertEquals(2, savedOrder.getQuantity(), 
                "Quantity should match");
            
            return savedOrder;
        });

        // Act
        orderService.placeOrder(orderRequest);

        // Assert
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void placeOrder_ShouldThrowException_WhenProductNotInStock() {
        // Arrange
        when(inventoryClient.isInStock("IPHONE_13", 2)).thenReturn(false);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> orderService.placeOrder(orderRequest));
        verify(orderRepository, never()).save(any(Order.class));
    }
}
