package com.alabacommerce.order;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.alabacommerce.dto.OrderResponse;
import com.alabacommerce.dto.UpdateStatusRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/seller")
public class SellerController {

    private final OrderService orderService;

    public SellerController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public List<OrderResponse> sellerOrders() {

        return orderService.getSellerOrders();
    }

    @PatchMapping("/orders/{id}/status")
    public OrderResponse updateStatus(
        @PathVariable Long id,
        @Valid @RequestBody UpdateStatusRequest request) {

        return orderService.updateStatus(id, request);
    }
}