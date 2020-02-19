package com.cotrav.cotrav_admin.model.home_model.hotel_model.view_hotel;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HotelDetailApiResponse {

        @SerializedName("success")
        @Expose
        private String success;
        @SerializedName("Bookings")
        @Expose
        private List<ViewHotelBooking> bookings = null;

        public String getSuccess() {
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }

        public List<ViewHotelBooking> getBookings() {
            return bookings;
        }

        public void setBookings(List<ViewHotelBooking> bookings) {
            this.bookings = bookings;
        }

    }