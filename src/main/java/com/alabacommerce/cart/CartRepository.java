package com.alabacommerce.cart;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alabacommerce.entity.Cart;
import com.alabacommerce.entity.User;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUser(User user);

}