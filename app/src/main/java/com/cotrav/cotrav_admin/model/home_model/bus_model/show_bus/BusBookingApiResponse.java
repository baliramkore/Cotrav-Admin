package com.cotrav.cotrav_admin.model.home_model.bus_model.show_bus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BusBookingApiResponse {

    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("Bookings")
    @Expose
    private List<BusBooking> busBookings= null;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public List<BusBooking> getBookings() {
        return busBookings;
    }

    public void setBookings(List<BusBooking> bookings) {
        this.busBookings = busBookings;
    }

}