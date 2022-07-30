package com.example.orderservice.command.api.aggregate;


import com.example.commonservice.commands.CompleteOrderCommand;
import com.example.commonservice.events.OrderCompletedEvent;
import com.example.orderservice.command.api.commands.OrderCreatedCommand;
import com.example.orderservice.command.api.commands.OrderDeletedCommand;
import com.example.orderservice.command.api.commands.OrderUpdatedCommand;
import com.example.orderservice.command.api.events.OrderCreatedEvent;
import com.example.orderservice.command.api.events.OrderDeletedEvent;
import com.example.orderservice.command.api.events.OrderUpdatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
@Slf4j
public class OrderAggregate {
    @AggregateIdentifier
    private String orderId;
    private String productId;
    private String userId;
    private String addressId;
    private Integer quantity;
    private String orderStatus;


    public OrderAggregate() {
    }


    // Order Created Event

    @CommandHandler
    public void aggregate(OrderCreatedCommand orderCreatedCommand) {
        //  You can perform validation here
        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent();
        BeanUtils.copyProperties(orderCreatedCommand ,orderCreatedEvent);

        // Publish the event
        AggregateLifecycle.apply(orderCreatedEvent);
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent event)
    {
        this.orderStatus = event.getOrderStatus();
        this.orderId=event.getOrderId();
        this.productId=event.getProductId();
        this.addressId=event.getAddressId();
        this.userId=event.getUserId();
        this.quantity=event.getQuantity();
    }



    { /* Order Completed Event */ }

    @CommandHandler
    public void aggregate(CompleteOrderCommand completeOrderCommand) {
        //  You can perform validation here
        OrderCompletedEvent completeOrderEvent = new OrderCompletedEvent();
        BeanUtils.copyProperties(completeOrderCommand ,completeOrderEvent);

        // Publish the event
        AggregateLifecycle.apply(completeOrderEvent);
    }

    @EventSourcingHandler
    public void on(OrderCompletedEvent event)
    {
        this.orderId=event.getOrderId();
        this.orderStatus = event.getOrderStatus();
    }



    {/* Order Updated Event */}

    @CommandHandler
    public void aggregate(OrderUpdatedCommand orderUpdatedCommand) {
        //  You can perform validation here
        OrderUpdatedEvent orderUpdatedEvent = new OrderUpdatedEvent();
        BeanUtils.copyProperties(orderUpdatedCommand ,orderUpdatedEvent);

        // Publish the event
        AggregateLifecycle.apply(orderUpdatedEvent);
    }


    @EventSourcingHandler
    public void on(OrderUpdatedEvent event)
    {
        this.orderId=event.getOrderId();
        this.productId=event.getProductId();
        this.addressId=event.getAddressId();
        this.userId=event.getUserId();
        this.quantity=event.getQuantity();
        this.orderStatus = event.getOrderStatus();
    }


    // Order Deleted Event

    @CommandHandler
    public void aggregate(OrderDeletedCommand orderDeletedCommand) {
        //  You can perform validation here
        OrderDeletedEvent orderDeletedEvent = new OrderDeletedEvent();
        BeanUtils.copyProperties(orderDeletedCommand ,orderDeletedEvent);

        // Publish the event
        AggregateLifecycle.apply(orderDeletedEvent);
    }


    @EventSourcingHandler
    public void on(OrderDeletedEvent event)
    {
        this.orderId=event.getOrderId();
    }


}
