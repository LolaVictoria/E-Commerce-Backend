package com.alabacommerce.dto;

import com.alabacommerce.entity.NigerianState;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SellerProfileRequest {

    @NotBlank(message = "Business name is required")
    private String businessName;

    @NotBlank(message = "Business description is required")
    private String businessDescription;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "City is required")
    private String city;

    @NotNull(message = "State is required")
    private NigerianState state;

    public SellerProfileRequest() {
    }

    public SellerProfileRequest(
            String businessName,
            String businessDescription,
            String phoneNumber,
            String address,
            String city,
            NigerianState state) {

        this.businessName = businessName;
        this.businessDescription = businessDescription;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.city = city;
        this.state = state;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessDescription() {
        return businessDescription;
    }

    public void setBusinessDescription(String businessDescription) {
        this.businessDescription = businessDescription;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public NigerianState getState() {
        return state;
    }

    public void setState(NigerianState state) {
        this.state = state;
    }
}