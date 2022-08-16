package com.DevbyAxim.ExtraFoodDonation.Models;

public class UserModel {


    String fullName, username, nicNo, phoneNo, email, password, userType,
            userCategory, userCompleteAddress
          ;
    Double latitude, longitude;
    Boolean   isOrganization;

    public UserModel() {
    }

    public UserModel(String fullName, String username, String nicNo, String phoneNo, String email, String password, String userType, String userCategory, String userCompleteAddress, Double latitude, Double longitude, Boolean isOrganization) {
        this.fullName = fullName;
        this.username = username;
        this.nicNo = nicNo;
        this.phoneNo = phoneNo;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.userCategory = userCategory;
        this.userCompleteAddress = userCompleteAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isOrganization = isOrganization;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNicNo() {
        return nicNo;
    }

    public void setNicNo(String nicNo) {
        this.nicNo = nicNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserCategory() {
        return userCategory;
    }

    public void setUserCategory(String userCategory) {
        this.userCategory = userCategory;
    }

    public String getUserCompleteAddress() {
        return userCompleteAddress;
    }

    public void setUserCompleteAddress(String userCompleteAddress) {
        this.userCompleteAddress = userCompleteAddress;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Boolean getOrganization() {
        return isOrganization;
    }

    public void setOrganization(Boolean organization) {
        isOrganization = organization;
    }
}