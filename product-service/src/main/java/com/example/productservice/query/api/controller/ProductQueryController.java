package com.example.productservice.query.api.controller;


import com.example.productservice.global.data.model.rest.ProductRest;
import com.example.productservice.query.api.query.GetProductQuery;
import com.example.productservice.query.api.query.GetProductsQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/query/product")
public class ProductQueryController {

    private QueryGateway queryGateway;

    public ProductQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping(path = "/getAll/")
    public List<ProductRest> getAllProduct() {
        GetProductsQuery getProductsQuery = new GetProductsQuery();
        return queryGateway
        .query(getProductsQuery , ResponseTypes.multipleInstancesOf(ProductRest.class))
        .join();
    }

    @GetMapping(path = "/get/{productId}")
    public ProductRest getAllProduct(@PathVariable("productId") String productId) {
        GetProductQuery getProductQuery = new GetProductQuery(productId);
        return queryGateway
                .query(getProductQuery , ResponseTypes.instanceOf(ProductRest.class))
                .join();
    }
}
