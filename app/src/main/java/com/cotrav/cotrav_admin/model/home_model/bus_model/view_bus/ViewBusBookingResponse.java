package com.cotrav.cotrav_admin.model.home_model.bus_model.view_bus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ViewBusBookingResponse

    {

        @SerializedName("success")
        @Expose
        private String success;
        @SerializedName("Bookings")
        @Expose
        private List<ViewBusBooking> bookings = null;

        public String getSuccess() {
        return success;
    }

        public void setSuccess(String success) {
        this.success = success;
    }

        public List<ViewBusBooking> getBookings() {
        return bookings;
    }

        public void setBookings(List<ViewBusBooking> bookings) {
        this.bookings = bookings;
    }
}
