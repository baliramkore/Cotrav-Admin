package com.cotrav.cotrav_admin.model.home_model.hotel_model.add_hotel.room_type;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RoomTypeApiResponse {

        @SerializedName("success")
        @Expose
        private String success;
        @SerializedName("Types")
        @Expose
        private List<RoomType> types = null;

        public String getSuccess() {
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }

        public List<RoomType> getTypes() {
            return types;
        }

        public void setTypes(List<RoomType> types) {
            this.types = types;
        }

    }