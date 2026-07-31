package com.alabacommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alabacommerce.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}