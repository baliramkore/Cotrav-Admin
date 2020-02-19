package com.cotrav.cotrav_admin.model.rates_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RatesApiResponse
{
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("Corporate_Retes")
    @Expose
    private List<CorporateRates> corporateRates = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<CorporateRates> getCorporateRates() {
        return corporateRates;
    }

    public void setCorporateRetes(List<CorporateRates> corporateRates) {
        this.corporateRates = corporateRates;
    }

}
