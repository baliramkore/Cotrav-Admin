package com.cotrav.cotrav_admin.model.all_cities_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllCitiesApiResponse
{
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("Cities")
    @Expose
    private List<City> cities = null;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

}
