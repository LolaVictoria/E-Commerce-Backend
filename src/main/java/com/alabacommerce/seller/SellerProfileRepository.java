package com.alabacommerce.seller;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alabacommerce.entity.SellerProfile;
import com.alabacommerce.entity.User;

public interface SellerProfileRepository
        extends JpaRepository<SellerProfile, Long> {
    Optional<SellerProfile> findByUser(User user);
    boolean existsByUser(User user);

}