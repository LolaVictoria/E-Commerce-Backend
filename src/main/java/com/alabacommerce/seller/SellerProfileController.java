package com.alabacommerce.seller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.alabacommerce.dto.SellerProfileRequest;
import com.alabacommerce.dto.SellerProfileResponse;
import com.alabacommerce.dto.SellerProfileUpdateRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/seller-profile")
@Tag(name = "Seller Profile", description = "Seller profile management APIs")
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
    @Operation(summary = "Create seller profile")
    @PreAuthorize("hasRole('USER')")
    public SellerProfileResponse createSellerProfile(
            @Valid @RequestBody SellerProfileRequest request) {

        return sellerProfileService.createSellerProfile(request);
    }

    /**
     * Get current user's seller profile
     */
    @GetMapping("/me")
    @Operation(summary = "Get my seller profile")
    @PreAuthorize("hasRole('SELLER')")
    public SellerProfileResponse getMySellerProfile() {

        return sellerProfileService.getMySellerProfile();
    }

    /**
     * Update seller profile
     */
    @PutMapping
    @Operation(summary = "Update seller profile")
    @PreAuthorize("hasRole('SELLER')")
    public SellerProfileResponse updateSellerProfile(
            @Valid @RequestBody SellerProfileUpdateRequest request) {

        return sellerProfileService.updateSellerProfile(request);
    }

    /**
     * Delete seller profile
     */
    @DeleteMapping
    @Operation(summary = "Delete seller profile")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('SELLER')")
    public void deleteSellerProfile() {

        sellerProfileService.deleteSellerProfile();
    }
}
