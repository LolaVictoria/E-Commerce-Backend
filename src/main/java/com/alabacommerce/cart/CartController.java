package com.alabacommerce.cart;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.alabacommerce.dto.CartItemRequest;
import com.alabacommerce.dto.CartResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/cart")
@Tag(name = "Cart", description = "Shopping cart management APIs")
@PreAuthorize("hasRole('USER')")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/items")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add an item to my cart")
    public CartResponse addItem(@Valid @RequestBody CartItemRequest request) {

        return cartService.addItem(request);
    }


    @GetMapping
    @Operation(summary = "Get my cart")
    public CartResponse getCart() {
        return cartService.getCart();
    }

    @PutMapping("/items/{id}")
    @Operation(summary = "Update a cart item")
    public CartResponse updateCartItem(
        @PathVariable Long id,
        @Valid @RequestBody CartItemRequest request) {

        return cartService.updateItem(id, request);
    }

    @DeleteMapping("/items/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove an item from my cart")
    public void removeItem(@PathVariable Long id) {
        cartService.removeItem(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Clear my cart")
    public void clearCart() {
        cartService.clearCart();
    }
    
}
