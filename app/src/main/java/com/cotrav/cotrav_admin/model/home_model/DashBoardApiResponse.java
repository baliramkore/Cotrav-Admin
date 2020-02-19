package com.cotrav.cotrav_admin.model.home_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DashBoardApiResponse
{
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("Dashboard")
    @Expose
    private List<DashBoardData> dashboard = null;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public List<DashBoardData> getDashboard() {
        return dashboard;
    }

    public void setDashboard(List<DashBoardData> dashboard) {
        this.dashboard = dashboard;
    }

}
