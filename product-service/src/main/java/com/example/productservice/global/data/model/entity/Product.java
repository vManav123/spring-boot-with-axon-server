package com.example.productservice.global.data.model.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "product")
public class Product {
    @Id
    private String productId;
    private String name;
    private BigDecimal price;
    private Integer quantity;
}
