package com.cotrav.cotrav_admin.model.subgroup_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubGroupApiResponse
{
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("Subgroups")
    @Expose
    private List<Subgroup> subgroups = null;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public List<Subgroup> getSubgroups() {
        return subgroups;
    }

    public void setSubgroups(List<Subgroup> subgroups) {
        this.subgroups = subgroups;
    }
}
