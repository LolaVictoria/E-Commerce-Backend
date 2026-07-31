package com.alabacommerce.mapper;

import org.springframework.stereotype.Component;

import com.alabacommerce.dto.ProductResponse;
import com.alabacommerce.entity.Product;

@Component
public class ProductMapper {

    public ProductResponse mapToResponse(Product product) {

        ProductResponse response = new ProductResponse();

        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setStock(product.getStock());
        response.setCategory(product.getCategory());
        response.setImageUrl(product.getImageUrl());
        response.setSellerName(product.getSeller().getName());

        return response;
    }
}