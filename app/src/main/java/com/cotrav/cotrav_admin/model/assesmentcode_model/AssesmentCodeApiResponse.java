package com.cotrav.cotrav_admin.model.assesmentcode_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AssesmentCodeApiResponse
{
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("Codes")
    @Expose
    private List<AssesmentCodes> codes = null;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public List<AssesmentCodes> getCodes() {
        return codes;
    }

    public void setCodes(List<AssesmentCodes> codes) {
        this.codes = codes;
    }
}
