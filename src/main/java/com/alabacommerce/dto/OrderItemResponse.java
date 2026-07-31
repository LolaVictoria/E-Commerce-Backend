package com.alabacommerce.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Positive;

public class OrderItemResponse {

    private Long id;

    @Positive
    private Integer quantity;

    private BigDecimal priceAtPurchase;

    private ProductResponse product;

    public OrderItemResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPriceAtPurchase() {
        return priceAtPurchase;
    }

    public void setPriceAtPurchase(BigDecimal priceAtPurchase) {
        this.priceAtPurchase = priceAtPurchase;
    }

    public ProductResponse getProduct() {
        return product;
    }

    public void setProduct(ProductResponse product) {
        this.product = product;
    }
}