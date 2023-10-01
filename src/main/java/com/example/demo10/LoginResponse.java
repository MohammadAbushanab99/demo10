package com.example.demo10;

public class LoginResponse {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    private String userType;

    public LoginResponse(String token, String userType) {
        this.token = token;
        this.userType = userType;
    }

    // Getters and setters
}
