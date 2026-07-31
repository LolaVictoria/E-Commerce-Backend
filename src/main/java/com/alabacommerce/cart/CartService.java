package com.alabacommerce.cart;

import com.alabacommerce.dto.CartItemRequest;
import com.alabacommerce.dto.CartResponse;

public interface CartService {
    CartResponse addItem(CartItemRequest request); 
    CartResponse getCart();
    CartResponse updateItem(Long cartItemId,
                            CartItemRequest request);
    void removeItem(Long cartItemId);
    void clearCart();
    
}
