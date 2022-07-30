package com.example.orderservice.command.api.controller;

import com.example.orderservice.command.api.commands.OrderCreatedCommand;
import com.example.orderservice.command.api.commands.OrderDeletedCommand;
import com.example.orderservice.command.api.commands.OrderUpdatedCommand;
import com.example.orderservice.global.data.model.rest.OrderRest;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/command/order")
public class OrderCommandController {

    private CommandGateway commandGateway;

    public OrderCommandController(CommandGateway gateway) {
        this.commandGateway = gateway;
    }


    /**
     * Add order
     *
     * @param orderRest
     * @return
     */
    @PostMapping(path = "/add/")
    public String addOrder(@RequestBody final OrderRest orderRest)
    {
        OrderCreatedCommand orderCreatedCommand = OrderCreatedCommand
                .builder()
                .orderId(UUID.randomUUID().toString())
                .addressId(orderRest.getAddressId())
                .productId(orderRest.getProductId())
                .userId(orderRest.getUserId())
                .quantity(orderRest.getQuantity())
                .orderStatus("Started")
                .build();

        commandGateway.sendAndWait(orderCreatedCommand);
        return "Successfully added the Order with this orderId :" + orderCreatedCommand.getOrderId();
    }


    /**
     * Update order
     *
     * @param orderId
     * @param orderRest
     * @return
     */
    @PostMapping(path = "/update/")
    public String updateOrder(
            @RequestParam("orderId") String orderId ,
            @RequestBody final OrderRest orderRest
    )
    {
        OrderUpdatedCommand orderUpdatedCommand = OrderUpdatedCommand.builder()
                .orderId(UUID.randomUUID().toString())
                .addressId(orderRest.getAddressId())
                .productId(orderRest.getProductId())
                .userId(orderRest.getUserId())
                .quantity(orderRest.getQuantity())
                .orderStatus("Started")
                .build();

        commandGateway.sendAndWait(orderUpdatedCommand);
        return "Successfully updated the Order with this orderId :" + orderUpdatedCommand.getOrderId();
    }

    /**
     *  Delete Order
     *
     * @param orderId
     * @return
     */
    @GetMapping(path = "/delete/")
    public String deleteOrder(@RequestParam("orderId") String orderId)
    {
       OrderDeletedCommand orderDeletedCommand = OrderDeletedCommand.builder()
                .orderId(orderId)
                .build();

        return "Successfully Deleted the Order with this orderId :" + commandGateway.sendAndWait(orderDeletedCommand);
    }

}
