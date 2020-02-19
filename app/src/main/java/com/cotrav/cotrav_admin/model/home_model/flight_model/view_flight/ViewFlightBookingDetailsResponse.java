package com.cotrav.cotrav_admin.model.home_model.flight_model.view_flight;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ViewFlightBookingDetailsResponse {

        @SerializedName("success")
        @Expose
        private String success;
        @SerializedName("Bookings")
        @Expose
        private List<FlightBookingDetails> bookings = null;

        public String getSuccess() {
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }

        public List<FlightBookingDetails> getBookings() {
            return bookings;
        }

        public void setBookings(List<FlightBookingDetails> bookings) {
            this.bookings = bookings;
        }

    }

