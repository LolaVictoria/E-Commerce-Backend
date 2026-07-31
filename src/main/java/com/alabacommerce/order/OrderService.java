package com.alabacommerce.order;

import java.util.List;

import com.alabacommerce.dto.OrderResponse;
import com.alabacommerce.dto.UpdateStatusRequest;

public interface OrderService {

    OrderResponse checkout();

    List<OrderResponse> getOrders();

    OrderResponse getOrder(Long orderId);

    List<OrderResponse> getSellerOrders();

    OrderResponse updateStatus(
            Long orderId,
            UpdateStatusRequest request);
}