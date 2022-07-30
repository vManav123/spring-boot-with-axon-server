package com.example.shipmentservice.command.api.handler;

import com.example.commonservice.events.OrderShippedEvent;
import com.example.shipmentservice.global.data.dao.ShipmentRepository;
import com.example.shipmentservice.global.data.model.entity.Shipment;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ShipEventHandler {

    private ShipmentRepository shipmentRepository;

    public ShipEventHandler(ShipmentRepository shipmentRepository)
    {
        this.shipmentRepository=shipmentRepository;
    }

    @EventHandler
    public void handle(OrderShippedEvent event)
    {
        Shipment shipment = new Shipment();
        BeanUtils.copyProperties(event,shipment);
        shipmentRepository.save(shipment);
    }
}
