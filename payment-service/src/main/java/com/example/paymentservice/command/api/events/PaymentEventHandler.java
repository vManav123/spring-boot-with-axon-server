package com.example.paymentservice.command.api.events;

import com.example.commonservice.events.PaymentProcessedEvent;
import com.example.paymentservice.global.data.dao.PaymentRepository;
import com.example.paymentservice.global.data.model.entity.Payment;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PaymentEventHandler {

    private PaymentRepository paymentRepository;

    public PaymentEventHandler(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @EventHandler
    public void on(PaymentProcessedEvent event)
    {
        Payment payment = Payment.builder()
                .paymentId(event.getPaymentId())
                .orderId(event.getOrderId())
                .paymentStatus("Completed")
                .timestamp(LocalDateTime.now())
                .build();

        paymentRepository.save(payment);
    }
}
