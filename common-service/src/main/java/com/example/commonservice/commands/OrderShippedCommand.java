package com.example.commonservice.commands;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderShippedCommand {
    @TargetAggregateIdentifier
    private String shipmentId;
    private String orderId;
}
