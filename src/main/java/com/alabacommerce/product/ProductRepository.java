package com.alabacommerce.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.alabacommerce.entity.Category;
import com.alabacommerce.entity.Product;
import com.alabacommerce.entity.User;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findBySeller(User seller, Pageable pageable);
    Page<Product> findByCategory(Category category, Pageable pageable);
    Page<Product> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
        String name,
        String description,
        Pageable pageable);
    Page<Product> findBySellerAndCategory(
        User seller,
        Category category,
        Pageable pageable);
    Page<Product> findByCategoryAndNameContainingIgnoreCaseOrCategoryAndDescriptionContainingIgnoreCase(
        Category category1,
        String name,
        Category category2,
        String description,
        Pageable pageable
    );
}