package com.orderservice.order_service.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderPlacedMessage {
    private String orderNumber;
    private String email;
    private String firstName;
    private String lastName;
}
