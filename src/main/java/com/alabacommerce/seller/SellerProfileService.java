package com.alabacommerce.seller;

import com.alabacommerce.dto.SellerProfileRequest;
import com.alabacommerce.dto.SellerProfileResponse;
import com.alabacommerce.dto.SellerProfileUpdateRequest;

public interface SellerProfileService {

    SellerProfileResponse createSellerProfile(
            SellerProfileRequest request);

    SellerProfileResponse getMySellerProfile();

    SellerProfileResponse updateSellerProfile(
            SellerProfileUpdateRequest request);

    void deleteSellerProfile();

    boolean doesSellerProfileExistForUser(Long userId);
}