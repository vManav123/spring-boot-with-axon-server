package com.example.orderservice.query.api.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class GetOrderQuery {
    private String orderId;
}
