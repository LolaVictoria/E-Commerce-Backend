package com.alabacommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alabacommerce.entity.Order;
import com.alabacommerce.entity.User;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUser(User user);
    List<Order> findDistinctByItems_Product_Seller(User seller);
}