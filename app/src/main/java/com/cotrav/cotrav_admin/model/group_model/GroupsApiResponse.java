package com.cotrav.cotrav_admin.model.group_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GroupsApiResponse
{
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("Groups")
    @Expose
    private List<GroupResponse> groups = null;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public List<GroupResponse> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupResponse> groups) {
        this.groups = groups;
    }
}
