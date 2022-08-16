package com.DevbyAxim.ExtraFoodDonation.Models;

public class FoodRequestModel {


    String DonateFoodRequestId,DonateFoodRequestTime,RequestDescription,Reponse,UserName,userType;

    public FoodRequestModel() {
    }

    public FoodRequestModel(String donateFoodRequestId, String donateFoodRequestTime, String requestDescription, String reponse, String userName, String userType) {
        DonateFoodRequestId = donateFoodRequestId;
        DonateFoodRequestTime = donateFoodRequestTime;
        RequestDescription = requestDescription;
        Reponse = reponse;
        UserName = userName;
        this.userType = userType;
    }

    public String getDonateFoodRequestId() {
        return DonateFoodRequestId;
    }

    public void setDonateFoodRequestId(String donateFoodRequestId) {
        DonateFoodRequestId = donateFoodRequestId;
    }

    public String getDonateFoodRequestTime() {
        return DonateFoodRequestTime;
    }

    public void setDonateFoodRequestTime(String donateFoodRequestTime) {
        DonateFoodRequestTime = donateFoodRequestTime;
    }

    public String getRequestDescription() {
        return RequestDescription;
    }

    public void setRequestDescription(String requestDescription) {
        RequestDescription = requestDescription;
    }

    public String getReponse() {
        return Reponse;
    }

    public void setReponse(String reponse) {
        Reponse = reponse;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
