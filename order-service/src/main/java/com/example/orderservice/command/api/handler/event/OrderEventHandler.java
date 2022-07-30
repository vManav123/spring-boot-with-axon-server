package com.example.orderservice.command.api.handler.event;

import com.example.commonservice.events.OrderCompletedEvent;
import com.example.orderservice.command.api.events.OrderCreatedEvent;
import com.example.orderservice.command.api.events.OrderDeletedEvent;
import com.example.orderservice.command.api.events.OrderUpdatedEvent;
import com.example.orderservice.command.api.exception.OrderNotFoundException;
import com.example.orderservice.global.data.dao.OrderRepository;
import com.example.orderservice.global.data.model.entity.Order;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("orderErrorEvent")
@Slf4j
public class OrderEventHandler {

    private final OrderRepository orderRepository;

    public OrderEventHandler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @EventHandler
    @SneakyThrows
    public void on(OrderCreatedEvent event) {
        Order order = new Order();
        BeanUtils.copyProperties(event , order);
        orderRepository.save(order);
    }

    @EventHandler
    @SneakyThrows
    public void on(OrderCompletedEvent event)
    {
        Order order = orderRepository.getById(event.getOrderId());
        order.setOrderStatus(event.getOrderStatus());
        orderRepository.save(order);
    }

    @EventHandler
    @SneakyThrows
    public void on(OrderUpdatedEvent event) {
        Order order = new Order();
        if (orderRepository.existsById(event.getOrderId())) {
            BeanUtils.copyProperties(event , order);
            order.setOrderStatus("Completed");
            orderRepository.save(order);
        }
        else
            throw new OrderNotFoundException("Order Not Found with this id : "+event.getOrderId());
    }

    @EventHandler
    @SneakyThrows
    public void on(OrderDeletedEvent event) {
        orderRepository
                .findById(event.getOrderId())
                .ifPresent(orderRepository::delete);
    }

    @SneakyThrows
    @ExceptionHandler
    public void handle(Exception e)
    {
        log.info("ExceptionHandler handles the exception {}",e.getMessage());
        throw e;
    }

}
