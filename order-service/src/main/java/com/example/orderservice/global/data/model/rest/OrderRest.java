package com.example.orderservice.global.data.model.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRest {
    private String productId;
    private String userId;
    private String addressId;
    private Integer quantity;
}
