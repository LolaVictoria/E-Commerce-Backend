package com.alabacommerce.seller;

import org.springframework.stereotype.Service;

import com.alabacommerce.dto.SellerProfileRequest;
import com.alabacommerce.dto.SellerProfileResponse;
import com.alabacommerce.dto.SellerProfileUpdateRequest;
import com.alabacommerce.entity.Role;
import com.alabacommerce.entity.SellerProfile;
import com.alabacommerce.entity.User;
import com.alabacommerce.exception.ResourceAlreadyExistsException;
import com.alabacommerce.exception.ResourceNotFoundException;
import com.alabacommerce.repository.UserRepository;
import com.alabacommerce.service.CurrentUserService;
import com.alabacommerce.service.JwtService;

@Service
public class SellerProfileServiceImpl implements SellerProfileService {
    private final UserRepository userRepository;
    private final SellerProfileRepository sellerProfileRepository;
    private final CurrentUserService currentUserService;
    private final JwtService jwtService;
    public SellerProfileServiceImpl(
        UserRepository userRepository,
        SellerProfileRepository sellerProfileRepository,
        CurrentUserService currentUserService,
        JwtService jwtService) {
        this.userRepository = userRepository;
        this.sellerProfileRepository = sellerProfileRepository;
        this.currentUserService = currentUserService;
        this.jwtService = jwtService;
    }

    @Override
    public boolean doesSellerProfileExistForUser(Long userId) {
        return userRepository.findById(userId)
                .map(sellerProfileRepository::existsByUser)
                .orElse(false);
    }

    @Override
    public SellerProfileResponse createSellerProfile(SellerProfileRequest request) {
        Long userId = currentUserService.getCurrentUser().getId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (sellerProfileRepository.existsByUser(user)) {
            throw new ResourceAlreadyExistsException("Seller profile already exists for this user");
        }

        SellerProfile sellerProfile = new SellerProfile();
        sellerProfile.setBusinessName(request.getBusinessName());
        sellerProfile.setBusinessDescription(request.getBusinessDescription());
        sellerProfile.setPhoneNumber(request.getPhoneNumber());
        sellerProfile.setAddress(request.getAddress());
        sellerProfile.setCity(request.getCity());
        sellerProfile.setState(request.getState());
        sellerProfile.setUser(user);
        user.setRole(Role.SELLER);
        userRepository.save(user);

        SellerProfile savedSellerProfile = sellerProfileRepository.save(sellerProfile);

        return new SellerProfileResponse(
                savedSellerProfile.getId(),
                savedSellerProfile.getBusinessName(),
                savedSellerProfile.getBusinessDescription(),
                savedSellerProfile.getPhoneNumber(),
                savedSellerProfile.getAddress(),
                savedSellerProfile.getCity(),
                savedSellerProfile.getState(),
                jwtService.generateToken(user.getEmail())
        );
    }

    @Override
    public SellerProfileResponse getMySellerProfile() {
        Long userId = currentUserService.getCurrentUser().getId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        SellerProfile sellerProfile = sellerProfileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Seller profile not found"));

        return new SellerProfileResponse(
                sellerProfile.getId(),
                sellerProfile.getBusinessName(),
                sellerProfile.getBusinessDescription(),
                sellerProfile.getPhoneNumber(),
                sellerProfile.getAddress(),
                sellerProfile.getCity(),
                sellerProfile.getState(),
                jwtService.generateToken(user.getEmail())
        );
    }

    @Override
    public SellerProfileResponse updateSellerProfile(SellerProfileUpdateRequest request) {
        Long userId = currentUserService.getCurrentUser().getId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        SellerProfile sellerProfile = sellerProfileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Seller profile not found"));

        sellerProfile.setBusinessName(request.getBusinessName());
        sellerProfile.setBusinessDescription(request.getBusinessDescription());
        sellerProfile.setPhoneNumber(request.getPhoneNumber());
        sellerProfile.setAddress(request.getAddress());
        sellerProfile.setCity(request.getCity());
        sellerProfile.setState(request.getState());

        SellerProfile updatedSellerProfile = sellerProfileRepository.save(sellerProfile);

        return new SellerProfileResponse(
                updatedSellerProfile.getId(),
                updatedSellerProfile.getBusinessName(),
                updatedSellerProfile.getBusinessDescription(),
                updatedSellerProfile.getPhoneNumber(),
                updatedSellerProfile.getAddress(),
                updatedSellerProfile.getCity(),
                updatedSellerProfile.getState(),
                jwtService.generateToken(user.getEmail())
        );
    }

    @Override
    public void deleteSellerProfile() {

        Long userId = currentUserService.getCurrentUser().getId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        SellerProfile sellerProfile = sellerProfileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Seller profile not found"));

        sellerProfileRepository.delete(sellerProfile);

        user.setRole(Role.USER);
        userRepository.save(user);
    }
}
