package com.example.orderservice;

import com.example.orderservice.command.api.handler.exception.OrderServiceEventsExceptionHandler;
import org.axonframework.config.EventProcessingConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class , args);
    }

    @Autowired
    public void configure(EventProcessingConfigurer configurer)
    {
        configurer.registerListenerInvocationErrorHandler(
                "productErrorEvent",
                configuration -> new OrderServiceEventsExceptionHandler()
        );
    }
}
