package com.orderservice.order_service.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.orderservice.order_service.dto.OrderLineItemsDto;
import com.orderservice.order_service.dto.OrderRequest;
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

    @InjectMocks
    private OrderService orderService;

    private OrderRequest orderRequest;
    private List<OrderLineItemsDto> orderLineItemsList;

    @BeforeEach
    void setUp() {
        // Arrange - Setup test data
        OrderLineItemsDto item1 = new OrderLineItemsDto();
        item1.setSkuCode("IPHONE_13");
        item1.setPrice(1000.0);
        item1.setQuantity(2);

        OrderLineItemsDto item2 = new OrderLineItemsDto();
        item2.setSkuCode("MACBOOK_PRO");
        item2.setPrice(2500.0);
        item2.setQuantity(1);

        orderLineItemsList = Arrays.asList(item1, item2);
        orderRequest = new OrderRequest(orderLineItemsList);
    }

    @Test
    void placeOrder_ShouldSaveOrder_WhenValidOrderRequest() {
        // Arrange
        when(orderRepository.save(any(Order.class))).thenReturn(new Order());

        // Act
        orderService.placeOrder(orderRequest);

        // Assert
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void placeOrder_ShouldCreateOrderWithCorrectNumberOfItems() {
        // Arrange
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order savedOrder = invocation.getArgument(0);
            // Assert - Verify order has correct number of items
            assertEquals(2, savedOrder.getOrderItems().size(), 
                "Order should contain exactly 2 order line items");
            return savedOrder;
        });

        // Act
        orderService.placeOrder(orderRequest);

        // Assert
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void placeOrder_ShouldGenerateOrderNumber() {
        // Arrange
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
    void placeOrder_ShouldMapDtoFieldsCorrectly() {
        // Arrange
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order savedOrder = invocation.getArgument(0);
            
            // Assert - Verify first item mapping
            assertEquals("IPHONE_13", savedOrder.getOrderItems().get(0).getSkuCode(), 
                "First item SKU code should match");
            assertEquals(1000.0, savedOrder.getOrderItems().get(0).getPrice(), 
                "First item price should match");
            assertEquals(2, savedOrder.getOrderItems().get(0).getQuantity(), 
                "First item quantity should match");
            
            // Assert - Verify second item mapping
            assertEquals("MACBOOK_PRO", savedOrder.getOrderItems().get(1).getSkuCode(), 
                "Second item SKU code should match");
            assertEquals(2500.0, savedOrder.getOrderItems().get(1).getPrice(), 
                "Second item price should match");
            assertEquals(1, savedOrder.getOrderItems().get(1).getQuantity(), 
                "Second item quantity should match");
            
            return savedOrder;
        });

        // Act
        orderService.placeOrder(orderRequest);

        // Assert
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void placeOrder_WithEmptyList_ShouldSaveOrderWithNoItems() {
        // Arrange
        OrderRequest emptyRequest = new OrderRequest(List.of());
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order savedOrder = invocation.getArgument(0);
            // Assert - Verify empty order items list
            assertTrue(savedOrder.getOrderItems().isEmpty(), 
                "Order items list should be empty");
            return savedOrder;
        });

        // Act
        orderService.placeOrder(emptyRequest);

        // Assert
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void placeOrder_WithSingleItem_ShouldCreateOrderSuccessfully() {
        // Arrange - Create order with single item
        OrderLineItemsDto singleItem = new OrderLineItemsDto();
        singleItem.setSkuCode("AIRPODS_PRO");
        singleItem.setPrice(249.0);
        singleItem.setQuantity(1);
        
        OrderRequest singleItemRequest = new OrderRequest(List.of(singleItem));
        when(orderRepository.save(any(Order.class))).thenReturn(new Order());

        // Act
        orderService.placeOrder(singleItemRequest);

        // Assert
        verify(orderRepository, times(1)).save(any(Order.class));
    }
}
