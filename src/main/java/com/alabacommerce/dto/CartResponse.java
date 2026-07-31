package com.alabacommerce.dto;

import java.math.BigDecimal;
import java.util.List;

public class CartResponse {
    private Long id;
    private List<CartItemResponse> items;
    private BigDecimal totalPrice;

    public CartResponse(){}

    public void setId(Long id){
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setItems(List<CartItemResponse> items){
        this.items = items;
    }

    public List<CartItemResponse> getItems() {
        return items;
    }

    
    public void setTotalPrice(BigDecimal totalPrice){
        this.totalPrice = totalPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
}
