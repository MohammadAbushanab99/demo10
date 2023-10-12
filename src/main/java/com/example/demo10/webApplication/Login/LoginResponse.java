package com.example.demo10.webApplication.Login;

public class LoginResponse {
    private String token;
    private String userType;

    public LoginResponse(String token, String userType) {
        this.token = token;
        this.userType = userType;
    }
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

}
