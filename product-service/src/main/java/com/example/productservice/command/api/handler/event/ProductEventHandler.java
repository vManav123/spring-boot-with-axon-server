package com.example.productservice.command.api.handler.event;

import com.example.productservice.command.api.exception.ProductNotFoundException;
import com.example.productservice.global.data.model.entity.Product;
import com.example.productservice.command.api.events.ProductCreatedEvent;
import com.example.productservice.command.api.events.ProductDeletedEvent;
import com.example.productservice.command.api.events.ProductUpdatedEvent;
import com.example.productservice.global.data.dao.ProductRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("productErrorEvent")
@Slf4j
public class ProductEventHandler {

    private final ProductRepository productRepository;

    public ProductEventHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @EventHandler
    @SneakyThrows
    public void on(ProductCreatedEvent event) {
        Product product = new Product();
        BeanUtils.copyProperties(event , product);
        productRepository.save(product);
    }

    @EventHandler
    @SneakyThrows
    public void on(ProductUpdatedEvent event) {
        Product product = new Product();
        if (productRepository.existsById(event.getProductId())) {
            BeanUtils.copyProperties(event , product);
            productRepository.save(product);
        }
        else
            throw new ProductNotFoundException("Product Not Found with this id : "+event.getProductId());
    }

    @EventHandler
    @SneakyThrows
    public void on(ProductDeletedEvent event) {
        productRepository
                .findById(event.getProductId())
                .ifPresent(productRepository::delete);
    }

    @SneakyThrows
    @ExceptionHandler
    public void handle(Exception e)
    {
        log.info("ExceptionHandler handles the exception {}",e.getMessage());
        throw e;
    }

}
