package com.example.orderservice.command.api.handler.exception;

import lombok.SneakyThrows;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.eventhandling.EventMessageHandler;
import org.axonframework.eventhandling.ListenerInvocationErrorHandler;

public class OrderServiceEventsExceptionHandler implements ListenerInvocationErrorHandler {
    @Override
    @SneakyThrows
    public void onError(Exception e , EventMessage<?> eventMessage , EventMessageHandler eventMessageHandler) {
        throw e;
    }
}
