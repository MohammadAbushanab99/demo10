package com.example.demo10.webApplication.Admin;

public class User {


        private String userId;

        private String name;

        private String phone;

        private String address;

        private String password;


     public User(){

     }
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

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPassword(String password) {
        this.password = password;
    }    }

