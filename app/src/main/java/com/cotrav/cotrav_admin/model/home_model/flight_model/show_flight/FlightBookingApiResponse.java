package com.cotrav.cotrav_admin.model.home_model.flight_model.show_flight;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FlightBookingApiResponse {

    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("Bookings")
    @Expose
    private List<FlightBooking> bookings = null;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public List<FlightBooking> getBookings() {
        return bookings;
    }

    public void setBookings(List<FlightBooking> bookings) {
        this.bookings = bookings;
    }

    }