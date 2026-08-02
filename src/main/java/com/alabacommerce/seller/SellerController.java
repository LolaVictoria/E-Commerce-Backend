package com.alabacommerce.seller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.alabacommerce.dto.OrderResponse;
import com.alabacommerce.dto.UpdateStatusRequest;
import com.alabacommerce.order.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Seller", description = "Seller APIs")
@RequestMapping("/seller")
@PreAuthorize("hasRole('SELLER')")
public class SellerController {

    private final OrderService orderService;

    public SellerController(OrderService orderService) {
        this.orderService = orderService;
    }

    
    @Operation(summary = "Get seller orders", description = "Retrieve a list of orders associated with the logged-in seller")
    @GetMapping("/orders")
    public List<OrderResponse> sellerOrders() {

        return orderService.getSellerOrders();
    }
    
    
    @Operation(summary = "Update order status", description = "Update the status of a specific order for the logged-in seller")
    @PatchMapping("/orders/{id}/status")
    public OrderResponse updateStatus(
        @PathVariable Long id,
        @Valid @RequestBody UpdateStatusRequest request) {

        return orderService.updateStatus(id, request);
    }
}
