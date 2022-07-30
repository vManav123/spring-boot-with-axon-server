package com.example.orderservice.command.api.exception;

import lombok.experimental.StandardException;

@StandardException
public class OrderNotFoundException extends Exception{
    public OrderNotFoundException(String message)
    {
        super(message);
    }
}
