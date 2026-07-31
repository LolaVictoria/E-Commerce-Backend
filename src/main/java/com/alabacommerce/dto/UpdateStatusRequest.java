package com.alabacommerce.dto;

import com.alabacommerce.entity.OrderStatus;

public class UpdateStatusRequest {

    private OrderStatus status;

    public UpdateStatusRequest() {
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}