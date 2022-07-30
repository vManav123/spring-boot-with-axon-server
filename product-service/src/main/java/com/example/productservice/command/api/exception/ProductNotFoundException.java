package com.example.productservice.command.api.exception;

import lombok.experimental.StandardException;

@StandardException
public class ProductNotFoundException extends Exception{
    public ProductNotFoundException(String message)
    {
        super(message);
    }
}
