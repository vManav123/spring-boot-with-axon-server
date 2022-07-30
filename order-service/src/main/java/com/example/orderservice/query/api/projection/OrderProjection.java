package com.example.orderservice.query.api.projection;

import com.example.orderservice.global.data.dao.OrderRepository;
import com.example.orderservice.global.data.model.entity.Order;
import com.example.orderservice.global.data.model.rest.OrderRest;
import com.example.orderservice.query.api.query.GetOrderQuery;
import com.example.orderservice.query.api.query.GetOrdersQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderProjection {

    private OrderRepository orderRepository;

    public OrderProjection(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @QueryHandler
    public List<OrderRest> handler(GetOrdersQuery getOrdersQuery) {
        List<Order> orders = orderRepository.findAll();

        return orders
                .stream()
                .map(order -> OrderRest.builder()
                        .addressId(order.getAddressId())
                        .productId(order.getProductId())
                        .userId(order.getUserId())
                        .quantity(order.getQuantity())
                        .build()
                )
                .collect(Collectors.toList());
    }

    @QueryHandler
    public OrderRest handler(GetOrderQuery getOrderQuery) {
        Order order = orderRepository
                .findById(getOrderQuery.getOrderId())
                .orElse(null);
        OrderRest orderRest = new OrderRest();
        assert order != null;
        BeanUtils.copyProperties(order, orderRest);
        return orderRest;
    }
}
