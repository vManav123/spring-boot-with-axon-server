package com.example.productservice.query.api.projection;

import com.example.productservice.global.data.dao.ProductRepository;
import com.example.productservice.global.data.model.entity.Product;
import com.example.productservice.global.data.model.rest.ProductRest;
import com.example.productservice.query.api.query.GetProductQuery;
import com.example.productservice.query.api.query.GetProductsQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductProjection {

    private ProductRepository productRepository;

    public ProductProjection(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @QueryHandler
    public List<ProductRest> handler(GetProductsQuery getProductsQuery) {
        List<Product> products = productRepository.findAll();

        return products
                .stream()
                .map(product -> ProductRest.builder()
                        .name(product.getName())
                        .price(product.getPrice())
                        .quantity(product.getQuantity())
                        .build()
                )
                .collect(Collectors.toList());
    }

    @QueryHandler
    public ProductRest handler(GetProductQuery getProductQuery) {
        Product product = productRepository
                .findById(getProductQuery.getProductId())
                .orElse(null);
        ProductRest productRest = new ProductRest();
        assert product != null;
        BeanUtils.copyProperties(product, productRest);
        return productRest;
    }
}
