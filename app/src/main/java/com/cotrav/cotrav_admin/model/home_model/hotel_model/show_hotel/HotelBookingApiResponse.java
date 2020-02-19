package com.cotrav.cotrav_admin.model.home_model.hotel_model.show_hotel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HotelBookingApiResponse {

    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("Bookings")
    @Expose
    private List<HotelBooking> bookings = null;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public List<HotelBooking> getBookings() {
        return bookings;
    }

    public void setBookings(List<HotelBooking> bookings) {
        this.bookings = bookings;
    }

}