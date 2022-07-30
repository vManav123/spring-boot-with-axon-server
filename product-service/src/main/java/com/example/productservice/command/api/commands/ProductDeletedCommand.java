package com.example.productservice.command.api.commands;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;

@Data
@Builder
public class ProductDeletedCommand {
    @TargetAggregateIdentifier
    private String productId;
}
