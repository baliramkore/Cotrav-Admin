package com.cotrav.cotrav_admin.model.home_model.train_model.add_train.train_type;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrainTypesApiRespose {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("Types")
    @Expose
    private List<TrainType> types = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<TrainType> getTypes() {
        return types;
    }

    public void setTypes(List<TrainType> types) {
        this.types = types;
    }
}
