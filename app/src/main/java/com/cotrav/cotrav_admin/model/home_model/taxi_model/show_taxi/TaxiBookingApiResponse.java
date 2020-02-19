package com.cotrav.cotrav_admin.model.home_model.taxi_model.show_taxi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TaxiBookingApiResponse {



    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("Bookings")
    @Expose
    private List<TaxiBooking> bookings = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<TaxiBooking> getBookings() {
        return bookings;
    }

    public void setBookings(List<TaxiBooking> bookings) {
        this.bookings = bookings;
    }

}
