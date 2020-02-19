package com.cotrav.cotrav_admin.model.home_model.train_model.add_train.train_station;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllStationCityResponse {


    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("Stations")
    @Expose
    private List<TrainStation> stations = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<TrainStation> getStations() {
        return stations;
    }

    public void setStations(List<TrainStation> stations) {
        this.stations = stations;
    }

}
