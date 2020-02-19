package com.cotrav.cotrav_admin.model.taxi_types_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TaxiTypeApiResponse {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("taxi_types")
    @Expose
    private List<TaxiType> taxiTypes = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<TaxiType> getTaxiTypes() {
        return taxiTypes;
    }

    public void setTaxiTypes(List<TaxiType> taxiTypes) {
        this.taxiTypes = taxiTypes;
    }

}
