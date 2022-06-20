package com.line_deposit.bd.model;

public class User {
    public  String username;
    public  String password;
    public  UserType userType;
    public  String mobile;
    public String affiliateUser;
    public String deviceToken;
    public int balance = 0;
    public User(String username, String password, UserType userType, String mobile) {
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.mobile = mobile;
    }

    public User() {
    }

//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public UserType getUserType() {
//        return userType;
//    }
//
//    public void setUserType(UserType userType) {
//        this.userType = userType;
//    }
//    public void setUserType(String userType) {
//
//        this.userType =  UserType.valueOf(userType);
//    }
}
