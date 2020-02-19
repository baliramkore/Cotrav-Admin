package com.cotrav.cotrav_admin.model.login_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginApiResponse
{
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("access_token")
    @Expose
    private String accessToken;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("User")
    @Expose
    private List<User> user = null;
    @SerializedName("OTP")
    @Expose
    private String oTP;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

    public String getOTP() {
        return oTP;
    }

    public void setOTP(String oTP) {
        this.oTP = oTP;
    }
}
