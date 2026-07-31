package com.alabacommerce.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Positive;

public class CartItemResponse {

    private Long id;
    @Positive
    private Integer quantity;

    private BigDecimal priceAtTime;

    private ProductResponse product;

    public CartItemResponse() {}

    public void setId(Long id)  {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setPriceAtTime(BigDecimal priceAtTime) {
        this.priceAtTime = priceAtTime;
    }

    public BigDecimal getPriceAtTime() {
        return priceAtTime;
    }

    public void setProduct(ProductResponse product) {
        this.product = product;
    }

    public ProductResponse getProduct() {
        return product;
    }

}