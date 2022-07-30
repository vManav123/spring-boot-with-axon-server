package com.example.orderservice.command.api.commands;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class OrderDeletedCommand {
    @TargetAggregateIdentifier
    private String orderId;
}
