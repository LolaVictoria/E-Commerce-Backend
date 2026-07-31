package com.alabacommerce.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.alabacommerce.dto.OrderItemResponse;
import com.alabacommerce.dto.OrderResponse;
import com.alabacommerce.entity.Order;
import com.alabacommerce.entity.OrderItem;

@Component
public class OrderMapper {

    private final ProductMapper productMapper;

    public OrderMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public OrderResponse mapToResponse(Order order) {

        OrderResponse response = new OrderResponse();

        response.setId(order.getId());

        response.setStatus(order.getStatus());

        response.setCreatedAt(order.getCreatedAt());

        response.setTotalPrice(order.getTotalPrice());

        List<OrderItemResponse> items =
                order.getItems()
                        .stream()
                        .map(this::mapItem)
                        .toList();

        response.setItems(items);

        return response;
    }

    private OrderItemResponse mapItem(OrderItem item) {

        OrderItemResponse response = new OrderItemResponse();

        response.setId(item.getId());

        response.setQuantity(item.getQuantity());

        response.setPriceAtPurchase(item.getPriceAtPurchase());

        response.setProduct(
                productMapper.mapToResponse(item.getProduct()));

        return response;
    }
}