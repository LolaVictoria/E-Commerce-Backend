package com.alabacommerce.dto;

import com.alabacommerce.entity.NigerianState;

public class SellerProfileResponse {

    private Long id;

    private String businessName;

    private String businessDescription;

    private String phoneNumber;

    private String address;

    private String city;

    private NigerianState state;

    public SellerProfileResponse() {
    }

    public SellerProfileResponse(
            Long id,
            String businessName,
            String businessDescription,
            String phoneNumber,
            String address,
            String city,
            NigerianState state) {

        this.id = id;
        this.businessName = businessName;
        this.businessDescription = businessDescription;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.city = city;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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