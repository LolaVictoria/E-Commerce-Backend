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

    private SellerProfileResponse buildResponse(SellerProfile profile, User user) {
        return new SellerProfileResponse(
                profile.getId(),
                profile.getBusinessName(),
                profile.getBusinessDescription(),
                profile.getPhoneNumber(),
                profile.getAddress(),
                profile.getCity(),
                profile.getState(),
                jwtService.generateToken(user.getEmail())
        );
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

       return buildResponse(savedSellerProfile, user);
    }

    @Override
    public SellerProfileResponse getMySellerProfile() {
        Long userId = currentUserService.getCurrentUser().getId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        SellerProfile sellerProfile = sellerProfileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Seller profile not found"));

        return buildResponse(sellerProfile, user);
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

       return buildResponse(updatedSellerProfile, user);
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
