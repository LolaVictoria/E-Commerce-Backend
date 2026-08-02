package com.alabacommerce.seller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.alabacommerce.dto.SellerProfileRequest;
import com.alabacommerce.dto.SellerProfileResponse;
import com.alabacommerce.dto.SellerProfileUpdateRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/seller-profile")
public class SellerProfileController {

    private final SellerProfileService sellerProfileService;

    public SellerProfileController(SellerProfileService sellerProfileService) {
        this.sellerProfileService = sellerProfileService;
    }

    /**
     * Create seller profile
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SellerProfileResponse createSellerProfile(
            @Valid @RequestBody SellerProfileRequest request) {

        return sellerProfileService.createSellerProfile(request);
    }

    /**
     * Get current user's seller profile
     */
    @GetMapping("/me")
    public SellerProfileResponse getMySellerProfile() {

        return sellerProfileService.getMySellerProfile();
    }

    /**
     * Update seller profile
     */
    @PutMapping
    public SellerProfileResponse updateSellerProfile(
            @Valid @RequestBody SellerProfileUpdateRequest request) {

        return sellerProfileService.updateSellerProfile(request);
    }

    /**
     * Delete seller profile
     */
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSellerProfile() {

        sellerProfileService.deleteSellerProfile();
    }
}