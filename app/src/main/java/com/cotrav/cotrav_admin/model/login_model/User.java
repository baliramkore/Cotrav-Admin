package com.cotrav.cotrav_admin.model.login_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User 
{
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("corporate_id")
    @Expose
    private String corporateId;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("contact_no")
    @Expose
    private String contactNo;
    @SerializedName("is_deleted")
    @Expose
    private String isDeleted;
    @SerializedName("last_login")
    @Expose
    private String lastLogin;
    @SerializedName("is_radio")
    @Expose
    private String isRadio;
    @SerializedName("is_local")
    @Expose
    private String isLocal;
    @SerializedName("is_outstation")
    @Expose
    private String isOutstation;
    @SerializedName("is_bus")
    @Expose
    private String isBus;
    @SerializedName("is_train")
    @Expose
    private String isTrain;
    @SerializedName("is_hotel")
    @Expose
    private String isHotel;
    @SerializedName("is_meal")
    @Expose
    private String isMeal;
    @SerializedName("is_flight")
    @Expose
    private String isFlight;
    @SerializedName("is_water_bottles")
    @Expose
    private String isWaterBottles;
    @SerializedName("is_reverse_logistics")
    @Expose
    private String isReverseLogistics;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("modified")
    @Expose
    private String modified;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCorporateId() {
        return corporateId;
    }

    public void setCorporateId(String corporateId) {
        this.corporateId = corporateId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getIsRadio() {
        return isRadio;
    }

    public void setIsRadio(String isRadio) {
        this.isRadio = isRadio;
    }

    public String getIsLocal() {
        return isLocal;
    }

    public void setIsLocal(String isLocal) {
        this.isLocal = isLocal;
    }

    public String getIsOutstation() {
        return isOutstation;
    }

    public void setIsOutstation(String isOutstation) {
        this.isOutstation = isOutstation;
    }

    public String getIsBus() {
        return isBus;
    }

    public void setIsBus(String isBus) {
        this.isBus = isBus;
    }

    public String getIsTrain() {
        return isTrain;
    }

    public void setIsTrain(String isTrain) {
        this.isTrain = isTrain;
    }

    public String getIsHotel() {
        return isHotel;
    }

    public void setIsHotel(String isHotel) {
        this.isHotel = isHotel;
    }

    public String getIsMeal() {
        return isMeal;
    }

    public void setIsMeal(String isMeal) {
        this.isMeal = isMeal;
    }

    public String getIsFlight() {
        return isFlight;
    }

    public void setIsFlight(String isFlight) {
        this.isFlight = isFlight;
    }

    public String getIsWaterBottles() {
        return isWaterBottles;
    }

    public void setIsWaterBottles(String isWaterBottles) {
        this.isWaterBottles = isWaterBottles;
    }

    public String getIsReverseLogistics() {
        return isReverseLogistics;
    }

    public void setIsReverseLogistics(String isReverseLogistics) {
        this.isReverseLogistics = isReverseLogistics;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

}
