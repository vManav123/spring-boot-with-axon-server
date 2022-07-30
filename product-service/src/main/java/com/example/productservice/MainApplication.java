package com.example.productservice;

import com.example.productservice.command.api.handler.exception.ProductServiceEventsExceptionHandler;
import org.axonframework.config.EventProcessingConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class , args);
    }

    @Autowired
    public void configure(EventProcessingConfigurer configurer)
    {
        configurer.registerListenerInvocationErrorHandler(
                "productErrorEvent",
                configuration -> new ProductServiceEventsExceptionHandler()
        );
    }
}