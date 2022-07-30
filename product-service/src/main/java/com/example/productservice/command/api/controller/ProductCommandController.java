package com.example.productservice.command.api.controller;

import com.example.productservice.command.api.commands.ProductCreatedCommand;
import com.example.productservice.command.api.commands.ProductDeletedCommand;
import com.example.productservice.command.api.commands.ProductUpdatedCommand;
import com.example.productservice.global.data.model.rest.ProductRest;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/command/product")
public class ProductCommandController {

    private CommandGateway commandGateway;

    public ProductCommandController(CommandGateway gateway) {
        this.commandGateway = gateway;
    }


    /**
     * Add product
     *
     * @param productRest
     * @return
     */
    @PostMapping(path = "/add/")
    public String addProduct(@RequestBody final ProductRest productRest)
    {
        ProductCreatedCommand productCreatedCommand = ProductCreatedCommand.builder()
                .productId(UUID.randomUUID().toString())
                .name(productRest.getName())
                .price(productRest.getPrice())
                .quantity(productRest.getQuantity())
                .build();

        commandGateway.sendAndWait(productCreatedCommand);
        return "Successfully added the Product with this productId :" + productCreatedCommand.getProductId();
    }


    /**
     * Update product
     *
     * @param productId
     * @param productRest
     * @return
     */
    @PostMapping(path = "/update/")
    public String updateProduct(
            @RequestParam("productId") String productId ,
            @RequestBody final ProductRest productRest
    )
    {
        ProductUpdatedCommand productUpdatedCommand = ProductUpdatedCommand.builder()
                .productId(productId)
                .name(productRest.getName())
                .price(productRest.getPrice())
                .quantity(productRest.getQuantity())
                .build();

        commandGateway.sendAndWait(productUpdatedCommand);
        return "Successfully updated the Product with this productId :" + productUpdatedCommand.getProductId();
    }

    /**
     *  Delete Product
     *
     * @param productId
     * @return
     */
    @GetMapping(path = "/delete/")
    public String updateProduct(@RequestParam("productId") String productId)
    {
       ProductDeletedCommand productDeletedCommand = ProductDeletedCommand.builder().productId(productId).build();

        return "Successfully Deleted the Product with this productId :" + commandGateway.sendAndWait(productDeletedCommand);
    }

}
