package com.example.orderservice.query.api.controller;


import com.example.orderservice.global.data.model.rest.OrderRest;
import com.example.orderservice.query.api.query.GetOrderQuery;
import com.example.orderservice.query.api.query.GetOrdersQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/query/order")
public class OrderQueryController {

    private QueryGateway queryGateway;

    public OrderQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping(path = "/getAll/")
    public List<OrderRest> getAllOrder() {
        GetOrdersQuery getOrdersQuery = new GetOrdersQuery();
        return queryGateway
        .query(getOrdersQuery , ResponseTypes.multipleInstancesOf(OrderRest.class))
        .join();
    }

    @GetMapping(path = "/get/{orderId}")
    public OrderRest getAllOrder(@PathVariable("orderId") String orderId) {
        GetOrderQuery getOrderQuery = new GetOrderQuery(orderId);
        return queryGateway
                .query(getOrderQuery , ResponseTypes.instanceOf(OrderRest.class))
                .join();
    }
}
