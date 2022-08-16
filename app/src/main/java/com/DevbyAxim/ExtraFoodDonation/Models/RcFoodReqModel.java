package com.DevbyAxim.ExtraFoodDonation.Models;

public class RcFoodReqModel {
    String DonateFoodRequestId, DonateFoodRequestTime, RequestDescription, Response, UserName, userType;

    public RcFoodReqModel() {
    }

    public RcFoodReqModel(String donateFoodRequestId, String donateFoodRequestTime, String requestDescription, String response, String userName, String userType) {
        DonateFoodRequestId = donateFoodRequestId;
        DonateFoodRequestTime = donateFoodRequestTime;
        RequestDescription = requestDescription;
        Response = response;
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

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
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
