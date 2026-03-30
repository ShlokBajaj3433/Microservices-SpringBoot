package com.orderservice.order_service.dto;

public record OrderRequest(Long id, String orderNumber, String skuCode,
                           Double price, Integer quantity, UserDetails userDetails) {

    public record UserDetails(String email, String firstName, String lastName) {}
}
