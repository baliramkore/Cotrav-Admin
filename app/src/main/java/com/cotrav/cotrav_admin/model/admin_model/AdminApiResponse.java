package com.cotrav.cotrav_admin.model.admin_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AdminApiResponse
{
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("Admins")
    @Expose
    private List<Admins> admins = null;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public List<Admins> getAdmins() {
        return admins;
    }

    public void setAdmins(List<Admins> admins) {
        this.admins = admins;
    }
}
