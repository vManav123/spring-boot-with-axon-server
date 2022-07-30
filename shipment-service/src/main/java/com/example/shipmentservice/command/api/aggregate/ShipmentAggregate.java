package com.example.shipmentservice.command.api.aggregate;

import com.example.commonservice.commands.OrderShippedCommand;
import com.example.commonservice.events.OrderShippedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
public class ShipmentAggregate {

    @AggregateIdentifier
    private String shipmentId;
    private String orderId;
    private String shipmentStatus;

    public ShipmentAggregate(){}

    @CommandHandler
    public ShipmentAggregate(OrderShippedCommand orderShippedCommand)
    {
        OrderShippedEvent shipOrderEvent = new OrderShippedEvent();
        BeanUtils.copyProperties(orderShippedCommand ,shipOrderEvent);
        shipOrderEvent.setShipmentStatus("Completed");

        AggregateLifecycle.apply(shipOrderEvent);
    }

    @EventSourcingHandler
    public void on(OrderShippedEvent event)
    {
        this.shipmentId = event.getShipmentId();
        this.orderId=event.getOrderId();
        this.shipmentStatus=event.getShipmentStatus();
    }
}
