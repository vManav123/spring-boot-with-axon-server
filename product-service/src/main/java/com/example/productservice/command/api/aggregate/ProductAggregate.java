package com.example.productservice.command.api.aggregate;

import com.example.productservice.command.api.commands.ProductCreatedCommand;
import com.example.productservice.command.api.commands.ProductDeletedCommand;
import com.example.productservice.command.api.commands.ProductUpdatedCommand;
import com.example.productservice.command.api.events.ProductCreatedEvent;
import com.example.productservice.command.api.events.ProductDeletedEvent;
import com.example.productservice.command.api.events.ProductUpdatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@Aggregate
@Slf4j
public class ProductAggregate {
    @AggregateIdentifier
    private String productId;
    private String name;
    private BigDecimal price;
    private Integer quantity;


    public ProductAggregate() {
    }

    // Product Created Event

    @CommandHandler
    public void aggregate(ProductCreatedCommand productCreatedCommand) {
        //  You can perform validation here
        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent();
        BeanUtils.copyProperties(productCreatedCommand ,productCreatedEvent);

        // Publish the event
        AggregateLifecycle.apply(productCreatedEvent);
    }

    @EventSourcingHandler
    public void on(ProductCreatedEvent event)
    {
        this.productId=event.getProductId();
        this.name=event.getName();
        this.price=event.getPrice();
        this.quantity=event.getQuantity();
    }


    // Product Updated Event

    @CommandHandler
    public void aggregate(ProductUpdatedCommand productUpdatedCommand) {
        //  You can perform validation here
        ProductUpdatedEvent productUpdatedEvent = new ProductUpdatedEvent();
        BeanUtils.copyProperties(productUpdatedCommand ,productUpdatedEvent);

        // Publish the event
        AggregateLifecycle.apply(productUpdatedEvent);
    }


    @EventSourcingHandler
    public void on(ProductUpdatedEvent event)
    {
        this.productId=event.getProductId();
        this.name=event.getName();
        this.price=event.getPrice();
        this.quantity=event.getQuantity();
    }


    // Product Deleted Event

    @CommandHandler
    public void aggregate(ProductDeletedCommand productDeletedCommand) {
        //  You can perform validation here
        ProductDeletedEvent productDeletedEvent = new ProductDeletedEvent();
        BeanUtils.copyProperties(productDeletedCommand ,productDeletedEvent);

        // Publish the event
        AggregateLifecycle.apply(productDeletedEvent);
    }


    @EventSourcingHandler
    public void on(ProductDeletedEvent event)
    {
        this.productId=event.getProductId();
    }
}
