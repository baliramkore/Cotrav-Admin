package com.cotrav.cotrav_admin.model.home_model.flight_model.view_flight;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Flight {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("booking_id")
    @Expose
    private String bookingId;
    @SerializedName("flight_name")
    @Expose
    private String flightName;
    @SerializedName("flight_no")
    @Expose
    private String flightNo;
    @SerializedName("pnr_no")
    @Expose
    private String pnrNo;
    @SerializedName("from_city")
    @Expose
    private String fromCity;
    @SerializedName("to_city")
    @Expose
    private String toCity;
    @SerializedName("departure_datetime")
    @Expose
    private String departureDatetime;
    @SerializedName("arrival_datetime")
    @Expose
    private String arrivalDatetime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getFlightName() {
        return flightName;
    }

    public void setFlightName(String flightName) {
        this.flightName = flightName;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public String getPnrNo() {
        return pnrNo;
    }

    public void setPnrNo(String pnrNo) {
        this.pnrNo = pnrNo;
    }

    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public String getToCity() {
        return toCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    public String getDepartureDatetime() {
        return departureDatetime;
    }

    public void setDepartureDatetime(String departureDatetime) {
        this.departureDatetime = departureDatetime;
    }

    public String getArrivalDatetime() {
        return arrivalDatetime;
    }

    public void setArrivalDatetime(String arrivalDatetime) {
        this.arrivalDatetime = arrivalDatetime;
    }

}
