package com.cotrav.cotrav_admin.model.spoc_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SpocApiResponse
{
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("Spocs")
    @Expose
    private List<Spocs> spocs = null;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public List<Spocs> getSpocs() {
        return spocs;
    }

    public void setSpocs(List<Spocs> spocs) {
        this.spocs = spocs;
    }
}