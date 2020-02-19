package com.cotrav.cotrav_admin.model.home_model.hotel_model.add_hotel.hotel_type;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HotelTypeApiResponse{

        @SerializedName("success")
        @Expose
        private String success;
        @SerializedName("Types")
        @Expose
        private List<HotelType> types = null;

        public String getSuccess() {
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }

        public List<HotelType> getTypes() {
            return types;
        }

        public void setTypes(List<HotelType> types) {
            this.types = types;
        }
}