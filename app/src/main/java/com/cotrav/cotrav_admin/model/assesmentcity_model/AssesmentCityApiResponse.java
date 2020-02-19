package com.cotrav.cotrav_admin.model.assesmentcity_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AssesmentCityApiResponse
{
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("Cities")
    @Expose
    private List<AssesmentCities> cities = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<AssesmentCities> getCities() {
        return cities;
    }

    public void setCities(List<AssesmentCities> cities) {
        this.cities = cities;
    }
}
