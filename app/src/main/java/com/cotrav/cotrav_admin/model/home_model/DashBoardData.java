package com.cotrav.cotrav_admin.model.home_model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


@Entity(tableName = "dash_board")
public class DashBoardData
{
    @SerializedName("id")
    @Expose
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "did")
    private Integer id=1;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @SerializedName("total_active_users")
    @Expose
    private Integer totalActiveUsers;
    @SerializedName("total_bookings_count")
    @Expose
    private Integer totalBookingsCount;
    @SerializedName("taxi_bookings_count")
    @Expose
    private Integer taxiBookingsCount;
    @SerializedName("bus_bookings_count")
    @Expose
    private Integer busBookingsCount;
    @SerializedName("train_bookings_count")
    @Expose
    private Integer trainBookingsCount;
    @SerializedName("hotel_bookings_count")
    @Expose
    private Integer hotelBookingsCount;
    @SerializedName("flight_bookings_count")
    @Expose
    private Integer flightBookingsCount;
    @SerializedName("uc_total_bookings_count")
    @Expose
    private Integer ucTotalBookingsCount;
    @SerializedName("uc_taxi_bookings_count")
    @Expose
    private Integer ucTaxiBookingsCount;
    @SerializedName("uc_bus_bookings_count")
    @Expose
    private Integer ucBusBookingsCount;
    @SerializedName("uc_train_bookings_count")
    @Expose
    private Integer ucTrainBookingsCount;
    @SerializedName("uc_hotel_bookings_count")
    @Expose
    private Integer ucHotelBookingsCount;
    @SerializedName("uc_flight_bookings_count")
    @Expose
    private Integer ucFlightBookingsCount;
    @SerializedName("tom_total_bookings_count")
    @Expose
    private Integer tomTotalBookingsCount;
    @SerializedName("tom_taxi_bookings_count")
    @Expose
    private Integer tomTaxiBookingsCount;
    @SerializedName("tom_bus_bookings_count")
    @Expose
    private Integer tomBusBookingsCount;
    @SerializedName("tom_train_bookings_count")
    @Expose
    private Integer tomTrainBookingsCount;
    @SerializedName("tom_hotel_bookings_count")
    @Expose
    private Integer tomHotelBookingsCount;
    @SerializedName("tom_flight_bookings_count")
    @Expose
    private Integer tomFlightBookingsCount;
    @SerializedName("pa_total_bookings_count")
    @Expose
    private Integer paTotalBookingsCount;
    @SerializedName("pa_taxi_bookings_count")
    @Expose
    private Integer paTaxiBookingsCount;
    @SerializedName("pa_bus_bookings_count")
    @Expose
    private Integer paBusBookingsCount;
    @SerializedName("pa_train_bookings_count")
    @Expose
    private Integer paTrainBookingsCount;
    @SerializedName("pa_hotel_bookings_count")
    @Expose
    private Integer paHotelBookingsCount;
    @SerializedName("pa_flight_bookings_count")
    @Expose
    private Integer paFlightBookingsCount;
    @SerializedName("pi_total_bookings_count")
    @Expose
    private Integer piTotalBookingsCount;
    @SerializedName("pi_taxi_bookings_count")
    @Expose
    private Integer piTaxiBookingsCount;
    @SerializedName("pi_bus_bookings_count")
    @Expose
    private Integer piBusBookingsCount;
    @SerializedName("pi_train_bookings_count")
    @Expose
    private Integer piTrainBookingsCount;
    @SerializedName("pi_hotel_bookings_count")
    @Expose
    private Integer piHotelBookingsCount;
    @SerializedName("pi_flight_bookings_count")
    @Expose
    private Integer piFlightBookingsCount;
    @SerializedName("bucket_0_count")
    @Expose
    private Integer bucket0Count;
    @SerializedName("bucket_1_count")
    @Expose
    private Integer bucket1Count;
    @SerializedName("bucket_2_count")
    @Expose
    private Integer bucket2Count;
    @SerializedName("bucket_3_count")
    @Expose
    private Integer bucket3Count;
    @SerializedName("bucket_4_count")
    @Expose
    private Integer bucket4Count;
    @SerializedName("taxi_invoice_amount")
    @Expose
    private Double taxiInvoiceAmount;
    @SerializedName("bus_invoice_amount")
    @Expose
    private Double busInvoiceAmount;
    @SerializedName("train_invoice_amount")
    @Expose
    private Double trainInvoiceAmount;
    @SerializedName("hotel_invoice_amount")
    @Expose
    private Double hotelInvoiceAmount;
    @SerializedName("flight_invoice_amount")
    @Expose
    private Double flightInvoiceAmount;
    @SerializedName("total_invoice_amount")
    @Expose
    private Double totalInvoiceAmount;

    public Integer getTotalActiveUsers() {
        return totalActiveUsers;
    }

    public void setTotalActiveUsers(Integer totalActiveUsers) {
        this.totalActiveUsers = totalActiveUsers;
    }

    public Integer getTotalBookingsCount() {
        return totalBookingsCount;
    }

    public void setTotalBookingsCount(Integer totalBookingsCount) {
        this.totalBookingsCount = totalBookingsCount;
    }

    public Integer getTaxiBookingsCount() {
        return taxiBookingsCount;
    }

    public void setTaxiBookingsCount(Integer taxiBookingsCount) {
        this.taxiBookingsCount = taxiBookingsCount;
    }

    public Integer getBusBookingsCount() {
        return busBookingsCount;
    }

    public void setBusBookingsCount(Integer busBookingsCount) {
        this.busBookingsCount = busBookingsCount;
    }

    public Integer getTrainBookingsCount() {
        return trainBookingsCount;
    }

    public void setTrainBookingsCount(Integer trainBookingsCount) {
        this.trainBookingsCount = trainBookingsCount;
    }

    public Integer getHotelBookingsCount() {
        return hotelBookingsCount;
    }

    public void setHotelBookingsCount(Integer hotelBookingsCount) {
        this.hotelBookingsCount = hotelBookingsCount;
    }

    public Integer getFlightBookingsCount() {
        return flightBookingsCount;
    }

    public void setFlightBookingsCount(Integer flightBookingsCount) {
        this.flightBookingsCount = flightBookingsCount;
    }

    public Integer getUcTotalBookingsCount() {
        return ucTotalBookingsCount;
    }

    public void setUcTotalBookingsCount(Integer ucTotalBookingsCount) {
        this.ucTotalBookingsCount = ucTotalBookingsCount;
    }

    public Integer getUcTaxiBookingsCount() {
        return ucTaxiBookingsCount;
    }

    public void setUcTaxiBookingsCount(Integer ucTaxiBookingsCount) {
        this.ucTaxiBookingsCount = ucTaxiBookingsCount;
    }

    public Integer getUcBusBookingsCount() {
        return ucBusBookingsCount;
    }

    public void setUcBusBookingsCount(Integer ucBusBookingsCount) {
        this.ucBusBookingsCount = ucBusBookingsCount;
    }

    public Integer getUcTrainBookingsCount() {
        return ucTrainBookingsCount;
    }

    public void setUcTrainBookingsCount(Integer ucTrainBookingsCount) {
        this.ucTrainBookingsCount = ucTrainBookingsCount;
    }

    public Integer getUcHotelBookingsCount() {
        return ucHotelBookingsCount;
    }

    public void setUcHotelBookingsCount(Integer ucHotelBookingsCount) {
        this.ucHotelBookingsCount = ucHotelBookingsCount;
    }

    public Integer getUcFlightBookingsCount() {
        return ucFlightBookingsCount;
    }

    public void setUcFlightBookingsCount(Integer ucFlightBookingsCount) {
        this.ucFlightBookingsCount = ucFlightBookingsCount;
    }

    public Integer getTomTotalBookingsCount() {
        return tomTotalBookingsCount;
    }

    public void setTomTotalBookingsCount(Integer tomTotalBookingsCount) {
        this.tomTotalBookingsCount = tomTotalBookingsCount;
    }

    public Integer getTomTaxiBookingsCount() {
        return tomTaxiBookingsCount;
    }

    public void setTomTaxiBookingsCount(Integer tomTaxiBookingsCount) {
        this.tomTaxiBookingsCount = tomTaxiBookingsCount;
    }

    public Integer getTomBusBookingsCount() {
        return tomBusBookingsCount;
    }

    public void setTomBusBookingsCount(Integer tomBusBookingsCount) {
        this.tomBusBookingsCount = tomBusBookingsCount;
    }

    public Integer getTomTrainBookingsCount() {
        return tomTrainBookingsCount;
    }

    public void setTomTrainBookingsCount(Integer tomTrainBookingsCount) {
        this.tomTrainBookingsCount = tomTrainBookingsCount;
    }

    public Integer getTomHotelBookingsCount() {
        return tomHotelBookingsCount;
    }

    public void setTomHotelBookingsCount(Integer tomHotelBookingsCount) {
        this.tomHotelBookingsCount = tomHotelBookingsCount;
    }

    public Integer getTomFlightBookingsCount() {
        return tomFlightBookingsCount;
    }

    public void setTomFlightBookingsCount(Integer tomFlightBookingsCount) {
        this.tomFlightBookingsCount = tomFlightBookingsCount;
    }

    public Integer getPaTotalBookingsCount() {
        return paTotalBookingsCount;
    }

    public void setPaTotalBookingsCount(Integer paTotalBookingsCount) {
        this.paTotalBookingsCount = paTotalBookingsCount;
    }

    public Integer getPaTaxiBookingsCount() {
        return paTaxiBookingsCount;
    }

    public void setPaTaxiBookingsCount(Integer paTaxiBookingsCount) {
        this.paTaxiBookingsCount = paTaxiBookingsCount;
    }

    public Integer getPaBusBookingsCount() {
        return paBusBookingsCount;
    }

    public void setPaBusBookingsCount(Integer paBusBookingsCount) {
        this.paBusBookingsCount = paBusBookingsCount;
    }

    public Integer getPaTrainBookingsCount() {
        return paTrainBookingsCount;
    }

    public void setPaTrainBookingsCount(Integer paTrainBookingsCount) {
        this.paTrainBookingsCount = paTrainBookingsCount;
    }

    public Integer getPaHotelBookingsCount() {
        return paHotelBookingsCount;
    }

    public void setPaHotelBookingsCount(Integer paHotelBookingsCount) {
        this.paHotelBookingsCount = paHotelBookingsCount;
    }

    public Integer getPaFlightBookingsCount() {
        return paFlightBookingsCount;
    }

    public void setPaFlightBookingsCount(Integer paFlightBookingsCount) {
        this.paFlightBookingsCount = paFlightBookingsCount;
    }

    public Integer getPiTotalBookingsCount() {
        return piTotalBookingsCount;
    }

    public void setPiTotalBookingsCount(Integer piTotalBookingsCount) {
        this.piTotalBookingsCount = piTotalBookingsCount;
    }

    public Integer getPiTaxiBookingsCount() {
        return piTaxiBookingsCount;
    }

    public void setPiTaxiBookingsCount(Integer piTaxiBookingsCount) {
        this.piTaxiBookingsCount = piTaxiBookingsCount;
    }

    public Integer getPiBusBookingsCount() {
        return piBusBookingsCount;
    }

    public void setPiBusBookingsCount(Integer piBusBookingsCount) {
        this.piBusBookingsCount = piBusBookingsCount;
    }

    public Integer getPiTrainBookingsCount() {
        return piTrainBookingsCount;
    }

    public void setPiTrainBookingsCount(Integer piTrainBookingsCount) {
        this.piTrainBookingsCount = piTrainBookingsCount;
    }

    public Integer getPiHotelBookingsCount() {
        return piHotelBookingsCount;
    }

    public void setPiHotelBookingsCount(Integer piHotelBookingsCount) {
        this.piHotelBookingsCount = piHotelBookingsCount;
    }

    public Integer getPiFlightBookingsCount() {
        return piFlightBookingsCount;
    }

    public void setPiFlightBookingsCount(Integer piFlightBookingsCount) {
        this.piFlightBookingsCount = piFlightBookingsCount;
    }

    public Integer getBucket0Count() {
        return bucket0Count;
    }

    public void setBucket0Count(Integer bucket0Count) {
        this.bucket0Count = bucket0Count;
    }

    public Integer getBucket1Count() {
        return bucket1Count;
    }

    public void setBucket1Count(Integer bucket1Count) {
        this.bucket1Count = bucket1Count;
    }

    public Integer getBucket2Count() {
        return bucket2Count;
    }

    public void setBucket2Count(Integer bucket2Count) {
        this.bucket2Count = bucket2Count;
    }

    public Integer getBucket3Count() {
        return bucket3Count;
    }

    public void setBucket3Count(Integer bucket3Count) {
        this.bucket3Count = bucket3Count;
    }

    public Integer getBucket4Count() {
        return bucket4Count;
    }

    public void setBucket4Count(Integer bucket4Count) {
        this.bucket4Count = bucket4Count;
    }

    public Double getTaxiInvoiceAmount() {
        return taxiInvoiceAmount;
    }

    public void setTaxiInvoiceAmount(Double taxiInvoiceAmount) {
        this.taxiInvoiceAmount = taxiInvoiceAmount;
    }

    public Double getBusInvoiceAmount() {
        return busInvoiceAmount;
    }

    public void setBusInvoiceAmount(Double busInvoiceAmount) {
        this.busInvoiceAmount = busInvoiceAmount;
    }

    public Double getTrainInvoiceAmount() {
        return trainInvoiceAmount;
    }

    public void setTrainInvoiceAmount(Double trainInvoiceAmount) {
        this.trainInvoiceAmount = trainInvoiceAmount;
    }

    public Double getHotelInvoiceAmount() {
        return hotelInvoiceAmount;
    }

    public void setHotelInvoiceAmount(Double hotelInvoiceAmount) {
        this.hotelInvoiceAmount = hotelInvoiceAmount;
    }

    public Double getFlightInvoiceAmount() {
        return flightInvoiceAmount;
    }

    public void setFlightInvoiceAmount(Double flightInvoiceAmount) {
        this.flightInvoiceAmount = flightInvoiceAmount;
    }

    public Double getTotalInvoiceAmount() {
        return totalInvoiceAmount;
    }

    public void setTotalInvoiceAmount(Double totalInvoiceAmount) {
        this.totalInvoiceAmount = totalInvoiceAmount;
    }
}
