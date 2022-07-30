package com.example.paymentservice.command.api.aggregate;

import com.example.commonservice.commands.ValidatePaymentCommand;
import com.example.commonservice.events.PaymentProcessedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
@Slf4j
public class PaymentAggregate {
    @AggregateIdentifier
    private String paymentId;
    private String orderId;
    private String paymentStatus;

    public PaymentAggregate()
    {

    }

    @CommandHandler
    public PaymentAggregate(ValidatePaymentCommand validatePaymentCommand)
    {
        PaymentProcessedEvent paymentProcessedEvent = new PaymentProcessedEvent();
        BeanUtils.copyProperties(validatePaymentCommand,paymentProcessedEvent);
        log.info("Payment Aggregate executing validatePaymentCommand with orderId: {} , paymentId: {} ",validatePaymentCommand.getOrderId(),validatePaymentCommand.getPaymentId());
        AggregateLifecycle.apply(paymentProcessedEvent);
        log.info("Aggregate life cycle applied on paymentProcessedEvent");
    }

    @EventSourcingHandler
    public void on(PaymentProcessedEvent event)
    {
        this.orderId=event.getOrderId();
        this.paymentId= event.getPaymentId();
    }

}
