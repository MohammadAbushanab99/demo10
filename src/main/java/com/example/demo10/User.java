package com.example.demo10;

public class User {
        private String userId;

        private String name;

        private String phone;

        private String address;

        private String password;

    public User(String userId, String name, String phone, String address, String password) {
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }

    // Getters and setters
    }

