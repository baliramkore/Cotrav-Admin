package com.cotrav.cotrav_admin.model.admin_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Admins
{
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("corporate_id")
    @Expose
    private Integer corporateId;
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
    private Integer isDeleted;
    @SerializedName("last_login")
    @Expose
    private String lastLogin;
    @SerializedName("is_radio")
    @Expose
    private Integer isRadio;
    @SerializedName("is_local")
    @Expose
    private Integer isLocal;
    @SerializedName("is_outstation")
    @Expose
    private Integer isOutstation;
    @SerializedName("is_bus")
    @Expose
    private Integer isBus;
    @SerializedName("is_train")
    @Expose
    private Integer isTrain;
    @SerializedName("is_hotel")
    @Expose
    private Integer isHotel;
    @SerializedName("is_meal")
    @Expose
    private Integer isMeal;
    @SerializedName("is_flight")
    @Expose
    private Integer isFlight;
    @SerializedName("is_water_bottles")
    @Expose
    private Integer isWaterBottles;
    @SerializedName("is_reverse_logistics")
    @Expose
    private Integer isReverseLogistics;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("modified")
    @Expose
    private String modified;
    @SerializedName("corporate_name")
    @Expose
    private String corporateName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCorporateId() {
        return corporateId;
    }

    public void setCorporateId(Integer corporateId) {
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

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Integer getIsRadio() {
        return isRadio;
    }

    public void setIsRadio(Integer isRadio) {
        this.isRadio = isRadio;
    }

    public Integer getIsLocal() {
        return isLocal;
    }

    public void setIsLocal(Integer isLocal) {
        this.isLocal = isLocal;
    }

    public Integer getIsOutstation() {
        return isOutstation;
    }

    public void setIsOutstation(Integer isOutstation) {
        this.isOutstation = isOutstation;
    }

    public Integer getIsBus() {
        return isBus;
    }

    public void setIsBus(Integer isBus) {
        this.isBus = isBus;
    }

    public Integer getIsTrain() {
        return isTrain;
    }

    public void setIsTrain(Integer isTrain) {
        this.isTrain = isTrain;
    }

    public Integer getIsHotel() {
        return isHotel;
    }

    public void setIsHotel(Integer isHotel) {
        this.isHotel = isHotel;
    }

    public Integer getIsMeal() {
        return isMeal;
    }

    public void setIsMeal(Integer isMeal) {
        this.isMeal = isMeal;
    }

    public Integer getIsFlight() {
        return isFlight;
    }

    public void setIsFlight(Integer isFlight) {
        this.isFlight = isFlight;
    }

    public Integer getIsWaterBottles() {
        return isWaterBottles;
    }

    public void setIsWaterBottles(Integer isWaterBottles) {
        this.isWaterBottles = isWaterBottles;
    }

    public Integer getIsReverseLogistics() {
        return isReverseLogistics;
    }

    public void setIsReverseLogistics(Integer isReverseLogistics) {
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

    public String getCorporateName() {
        return corporateName;
    }

    public void setCorporateName(String corporateName) {
        this.corporateName = corporateName;
    }
}
