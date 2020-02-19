package com.cotrav.cotrav_admin.model.spoc_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Spocs
{
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("access_token")
    @Expose
    private String accessToken;
    @SerializedName("corporate_id")
    @Expose
    private int corporateId;
    @SerializedName("group_id")
    @Expose
    private int groupId;
    @SerializedName("subgroup_id")
    @Expose
    private int subgroupId;
    @SerializedName("user_cid")
    @Expose
    private String userCid;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("user_contact")
    @Expose
    private String userContact;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("old_password")
    @Expose
    private String oldPassword;
    @SerializedName("profile_image")
    @Expose
    private String profileImage;
    @SerializedName("budget")
    @Expose
    private Double budget;
    @SerializedName("expense")
    @Expose
    private Double expense;
    @SerializedName("is_radio")
    @Expose
    private int isRadio;
    @SerializedName("is_local")
    @Expose
    private int isLocal;
    @SerializedName("is_outstation")
    @Expose
    private int isOutstation;
    @SerializedName("is_bus")
    @Expose
    private int isBus;
    @SerializedName("is_train")
    @Expose
    private int isTrain;
    @SerializedName("is_hotel")
    @Expose
    private int isHotel;
    @SerializedName("is_meal")
    @Expose
    private int isMeal;
    @SerializedName("is_flight")
    @Expose
    private int isFlight;
    @SerializedName("is_water_bottles")
    @Expose
    private int isWaterBottles;
    @SerializedName("is_reverse_logistics")
    @Expose
    private int isReverseLogistics;
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("has_single_employee")
    @Expose
    private int hasSingleEmployee;
    @SerializedName("fcm_regid")
    @Expose
    private String fcmRegid;
    @SerializedName("last_login")
    @Expose
    private String lastLogin;
    @SerializedName("is_deleted")
    @Expose
    private int isDeleted;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("modified")
    @Expose
    private String modified;
    @SerializedName("corporate_name")
    @Expose
    private String corporateName;
    @SerializedName("subgroup_name")
    @Expose
    private String subgroupName;
    @SerializedName("group_name")
    @Expose
    private String groupName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getCorporateId() {
        return corporateId;
    }

    public void setCorporateId(int corporateId) {
        this.corporateId = corporateId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getSubgroupId() {
        return subgroupId;
    }

    public void setSubgroupId(int subgroupId) {
        this.subgroupId = subgroupId;
    }

    public String getUserCid() {
        return userCid;
    }

    public void setUserCid(String userCid) {
        this.userCid = userCid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserContact() {
        return userContact;
    }

    public void setUserContact(String userContact) {
        this.userContact = userContact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public Double getExpense() {
        return expense;
    }

    public void setExpense(Double expense) {
        this.expense = expense;
    }

    public int getIsRadio() {
        return isRadio;
    }

    public void setIsRadio(int isRadio) {
        this.isRadio = isRadio;
    }

    public int getIsLocal() {
        return isLocal;
    }

    public void setIsLocal(int isLocal) {
        this.isLocal = isLocal;
    }

    public int getIsOutstation() {
        return isOutstation;
    }

    public void setIsOutstation(int isOutstation) {
        this.isOutstation = isOutstation;
    }

    public int getIsBus() {
        return isBus;
    }

    public void setIsBus(int isBus) {
        this.isBus = isBus;
    }

    public int getIsTrain() {
        return isTrain;
    }

    public void setIsTrain(int isTrain) {
        this.isTrain = isTrain;
    }

    public int getIsHotel() {
        return isHotel;
    }

    public void setIsHotel(int isHotel) {
        this.isHotel = isHotel;
    }

    public int getIsMeal() {
        return isMeal;
    }

    public void setIsMeal(int isMeal) {
        this.isMeal = isMeal;
    }

    public int getIsFlight() {
        return isFlight;
    }

    public void setIsFlight(int isFlight) {
        this.isFlight = isFlight;
    }

    public int getIsWaterBottles() {
        return isWaterBottles;
    }

    public void setIsWaterBottles(int isWaterBottles) {
        this.isWaterBottles = isWaterBottles;
    }

    public int getIsReverseLogistics() {
        return isReverseLogistics;
    }

    public void setIsReverseLogistics(int isReverseLogistics) {
        this.isReverseLogistics = isReverseLogistics;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getHasSingleEmployee() {
        return hasSingleEmployee;
    }

    public void setHasSingleEmployee(int hasSingleEmployee) {
        this.hasSingleEmployee = hasSingleEmployee;
    }

    public String getFcmRegid() {
        return fcmRegid;
    }

    public void setFcmRegid(String fcmRegid) {
        this.fcmRegid = fcmRegid;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
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

    public String getSubgroupName() {
        return subgroupName;
    }

    public void setSubgroupName(String subgroupName) {
        this.subgroupName = subgroupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
