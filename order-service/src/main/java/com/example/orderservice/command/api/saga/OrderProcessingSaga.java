package com.example.orderservice.command.api.saga;

import com.example.commonservice.commands.CompleteOrderCommand;
import com.example.commonservice.commands.OrderShippedCommand;
import com.example.commonservice.commands.ValidatePaymentCommand;
import com.example.commonservice.events.OrderCompletedEvent;
import com.example.commonservice.events.OrderShippedEvent;
import com.example.commonservice.events.PaymentProcessedEvent;
import com.example.commonservice.model.entity.UserModel;
import com.example.commonservice.queries.GetUserPaymentDetailsQuery;
import com.example.orderservice.command.api.events.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Saga
@Slf4j
public class OrderProcessingSaga {

    private CommandGateway commandGateway;
    private QueryGateway queryGateway;

    @Autowired
    public OrderProcessingSaga(CommandGateway commandGateway , QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCreatedEvent event) {
        log.info("OrderCreatedEvent in Saga with orderId : {} " , event.getOrderId());

        GetUserPaymentDetailsQuery getUserPaymentDetailsQuery = new GetUserPaymentDetailsQuery(event.getUserId());
        UserModel userModel = null;

        try {
            userModel = queryGateway.query(getUserPaymentDetailsQuery , ResponseTypes.instanceOf(UserModel.class)).join();
        } catch (Exception e) {
            log.error("Error Occurred in OrderProcessing saga -> OrderCreatedEvent , error message : {} " , e.getMessage());
        }

        ValidatePaymentCommand validatePaymentCommand = ValidatePaymentCommand
                .builder()
                .cardDetailsModel(userModel.getCardDetailsModel())
                .orderId(event.getOrderId())
                .paymentId(UUID.randomUUID().toString())
                .build();
        commandGateway.sendAndWait(validatePaymentCommand);
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(PaymentProcessedEvent event) {
        log.info("PaymentProcessedEvent in Saga with orderId : {} , paymentId : {} ", event.getOrderId() , event.getPaymentId());
        try {
            OrderShippedCommand orderShippedCommand = OrderShippedCommand
                    .builder()
                    .orderId(event.getOrderId())
                    .shipmentId(UUID.randomUUID().toString())
                    .build();

            commandGateway.sendAndWait(orderShippedCommand);
        }
        catch (Exception e)
        {
            log.error("Error occurred in OrderProcessingSaga -> PaymentProcessedEvent , error message {}",e.getMessage());
        }
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderShippedEvent event) {
        log.info("ShipOrderEvent in Saga with orderId : {} , shipmentId : {} ", event.getOrderId() , event.getShipmentId());
        try {
            CompleteOrderCommand completeOrderCommand = CompleteOrderCommand
                    .builder()
                    .orderId(event.getOrderId())
                    .orderStatus("APPROVED")
                    .build();

            commandGateway.sendAndWait(completeOrderCommand);
        }
        catch (Exception e)
        {
            log.error("Error occurred in OrderProcessingSaga -> OrderShippedEvent , error message {}",e.getMessage());
        }
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCompletedEvent event) {
        log.info("ShipCompletedEvent in Saga with orderId : {} , orderStatus : {} ", event.getOrderId() , event.getOrderStatus());

    }

}
