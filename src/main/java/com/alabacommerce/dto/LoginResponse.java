package com.alabacommerce.dto;

public class LoginResponse {

    private String token;
    private String firstName;
    private String lastName;
    private String email;
    private String role;

    public LoginResponse(String token, String firstName, String lastName, String email, String role) {
        this.token = token;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }
}