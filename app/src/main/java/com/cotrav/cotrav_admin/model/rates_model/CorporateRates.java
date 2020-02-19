package com.cotrav.cotrav_admin.model.rates_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CorporateRates
{
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("corporate_id")
    @Expose
    private Integer corporateId;
    @SerializedName("package_name")
    @Expose
    private String packageName;
    @SerializedName("city_id")
    @Expose
    private Integer cityId;
    @SerializedName("taxi_type_id")
    @Expose
    private Integer taxiTypeId;
    @SerializedName("tour_type")
    @Expose
    private Integer tourType;
    @SerializedName("kms")
    @Expose
    private Integer kms;
    @SerializedName("hours")
    @Expose
    private Integer hours;
    @SerializedName("km_rate")
    @Expose
    private Double kmRate;
    @SerializedName("hour_rate")
    @Expose
    private Double hourRate;
    @SerializedName("base_rate")
    @Expose
    private Double baseRate;
    @SerializedName("night_rate")
    @Expose
    private Double nightRate;
    @SerializedName("is_deleted")
    @Expose
    private Integer isDeleted;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("modified")
    @Expose
    private String modified;
    @SerializedName("city_name")
    @Expose
    private String cityName;
    @SerializedName("taxi_type")
    @Expose
    private String taxiType;
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

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getTaxiTypeId() {
        return taxiTypeId;
    }

    public void setTaxiTypeId(Integer taxiTypeId) {
        this.taxiTypeId = taxiTypeId;
    }

    public Integer getTourType() {
        return tourType;
    }

    public void setTourType(Integer tourType) {
        this.tourType = tourType;
    }

    public Integer getKms() {
        return kms;
    }

    public void setKms(Integer kms) {
        this.kms = kms;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public Double getKmRate() {
        return kmRate;
    }

    public void setKmRate(Double kmRate) {
        this.kmRate = kmRate;
    }

    public Double getHourRate() {
        return hourRate;
    }

    public void setHourRate(Double hourRate) {
        this.hourRate = hourRate;
    }

    public Double getBaseRate() {
        return baseRate;
    }

    public void setBaseRate(Double baseRate) {
        this.baseRate = baseRate;
    }

    public Double getNightRate() {
        return nightRate;
    }

    public void setNightRate(Double nightRate) {
        this.nightRate = nightRate;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
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

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getTaxiType() {
        return taxiType;
    }

    public void setTaxiType(String taxiType) {
        this.taxiType = taxiType;
    }

    public String getCorporateName() {
        return corporateName;
    }

    public void setCorporateName(String corporateName) {
        this.corporateName = corporateName;
    }

}
