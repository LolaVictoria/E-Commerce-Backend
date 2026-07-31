package com.alabacommerce.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CartItemRequest {

    @NotNull(message = "Product Id is required")
    private Long productId;

    @NotNull
    @Positive(message = "Quantity must be greater than 0")
    private Integer quantity;

    public CartItemRequest() {}

    public void setProductId(Long productId){
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setQuantity(Integer quantity){
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

}