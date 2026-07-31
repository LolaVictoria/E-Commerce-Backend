/**
 * Retrieves the currently authenticated user.
 *
 * Used by services that require information
 * about the logged-in user.
 */

package com.alabacommerce.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.alabacommerce.entity.User;
import com.alabacommerce.repository.UserRepository;

@Service
public class CurrentUserService {
    private final UserRepository userRepository;

    public CurrentUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email);
    }
}
