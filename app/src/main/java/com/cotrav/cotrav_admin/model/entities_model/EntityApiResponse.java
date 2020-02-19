package com.cotrav.cotrav_admin.model.entities_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EntityApiResponse
{
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("Entitys")
    @Expose
    private List<Entities> entitys = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<Entities> getEntitys() {
        return entitys;
    }

    public void setEntitys(List<Entities> entitys) {
        this.entitys = entitys;
    }
}
