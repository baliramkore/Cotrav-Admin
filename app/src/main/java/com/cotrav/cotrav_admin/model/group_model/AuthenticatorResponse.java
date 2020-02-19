package com.cotrav.cotrav_admin.model.group_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AuthenticatorResponse
{
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("Groups")
    @Expose
    private List<Group> groups = null;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}
