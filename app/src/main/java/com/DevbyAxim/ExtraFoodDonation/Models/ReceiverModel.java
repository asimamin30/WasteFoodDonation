package com.DevbyAxim.ExtraFoodDonation.Models;

public class ReceiverModel {

    String username,email,phoneNo,userCompleteAddress;

    public ReceiverModel() {
    }

    public ReceiverModel(String username, String email, String phoneNo, String userCompleteAddress) {
        this.username = username;
        this.email = email;
        this.phoneNo = phoneNo;
        this.userCompleteAddress = userCompleteAddress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getUserCompleteAddress() {
        return userCompleteAddress;
    }

    public void setUserCompleteAddress(String userCompleteAddress) {
        this.userCompleteAddress = userCompleteAddress;
    }
}
