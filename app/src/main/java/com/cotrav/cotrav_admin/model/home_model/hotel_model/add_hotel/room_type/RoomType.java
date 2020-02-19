package com.cotrav.cotrav_admin.model.home_model.hotel_model.add_hotel.room_type;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RoomType {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price_1")
    @Expose
    private String price1;
    @SerializedName("price_2")
    @Expose
    private String price2;

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

    public String getPrice1() {
        return price1;
    }

    public void setPrice1(String price1) {
        this.price1 = price1;
    }

    public String getPrice2() {
        return price2;
    }

    public void setPrice2(String price2) {
        this.price2 = price2;
    }
}
