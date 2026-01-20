package com.Shlok.Product.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ProductResponse {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
}
