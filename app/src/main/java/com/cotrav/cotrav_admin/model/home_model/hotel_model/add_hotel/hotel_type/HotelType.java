package com.cotrav.cotrav_admin.model.home_model.hotel_model.add_hotel.hotel_type;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HotelType {

    @SerializedName("id")
    @Expose
    private String  id;
    @SerializedName("name")
    @Expose
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;

    }
}