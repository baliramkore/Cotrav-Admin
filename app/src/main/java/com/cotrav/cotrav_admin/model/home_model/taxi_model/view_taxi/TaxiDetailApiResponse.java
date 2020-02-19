package com.cotrav.cotrav_admin.model.home_model.taxi_model.view_taxi;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TaxiDetailApiResponse {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("Bookings")
    @Expose
    private List<ViewTaxiBooking> bookings = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<ViewTaxiBooking> getBookings() {
        return bookings;
    }

    public void setBookings(List<ViewTaxiBooking> bookings) {
        this.bookings = bookings;
    }

}
