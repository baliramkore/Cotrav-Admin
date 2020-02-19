package com.cotrav.cotrav_admin.model.home_model.train_model.view_train;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ViewTrainBookingDetailsResponse {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("Bookings")
    @Expose
    private List<ViewTrainBooking> bookings = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<ViewTrainBooking> getBookings() {
        return bookings;
    }

    public void setBookings(List<ViewTrainBooking> bookings) {
        this.bookings = bookings;
    }

    }