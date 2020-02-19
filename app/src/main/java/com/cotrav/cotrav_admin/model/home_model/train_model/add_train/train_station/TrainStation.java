package com.cotrav.cotrav_admin.model.home_model.train_model.add_train.train_station;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrainStation {


    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("station_name")
    @Expose
    private String stationName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public Integer getTrainsPassBy() {
        return trainsPassBy;
    }

    public void setTrainsPassBy(Integer trainsPassBy) {
        this.trainsPassBy = trainsPassBy;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    @SerializedName("station_code")
    @Expose
    private String stationCode;
    @SerializedName("trains_pass_by")
    @Expose
    private Integer trainsPassBy;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("modified")
    @Expose
    private String modified;

}
