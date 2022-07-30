package com.example.productservice.query.api.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class GetProductQuery {
    private String productId;
}
