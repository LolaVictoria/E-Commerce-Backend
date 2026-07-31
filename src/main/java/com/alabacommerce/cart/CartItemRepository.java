package com.alabacommerce.cart;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alabacommerce.entity.Cart;
import com.alabacommerce.entity.CartItem;
import com.alabacommerce.entity.Product;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartAndProduct(
            Cart cart,
            Product product);

    List<CartItem> findByCart(Cart cart);

        

}