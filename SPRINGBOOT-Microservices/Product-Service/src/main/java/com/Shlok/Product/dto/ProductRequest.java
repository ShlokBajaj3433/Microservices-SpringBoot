package com.Shlok.Product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    private String id;
    private String name;
    private String description;
    private String skuCode;
    private Double price;

}
