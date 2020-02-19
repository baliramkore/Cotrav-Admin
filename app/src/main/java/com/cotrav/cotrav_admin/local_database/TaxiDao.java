package com.cotrav.cotrav_admin.local_database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cotrav.cotrav_admin.model.home_model.taxi_model.show_taxi.TaxiBooking;
import com.cotrav.cotrav_admin.model.home_model.taxi_model.view_taxi.ViewTaxiBooking;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;


@Dao
public interface TaxiDao {

    @Insert(onConflict = REPLACE)
    void addTaxiBookings(List<TaxiBooking> bookingList);

    @Insert(onConflict = REPLACE)
    void addTaxiBookingDetail(List<ViewTaxiBooking> bookingDetailsList);

    @Query("SELECT * from taxi_bookings_detail")
    LiveData<ViewTaxiBooking> getTaxiBookingDetails();

    @Query("SELECT * from taxi_bookings ")
    LiveData<List<TaxiBooking>> getLocalTaxiBookings();

    @Query("SELECT * FROM taxi_bookings WHERE bookingType = '1' ")
    LiveData<List<TaxiBooking>> getTodaysDBTaxiBookings();

    @Query("SELECT * FROM taxi_bookings WHERE bookingType = '2' ")
    LiveData<List<TaxiBooking>> getUpcomingDBTaxiBookings();

    @Query("SELECT * FROM taxi_bookings WHERE bookingType = '4' ")
    LiveData<List<TaxiBooking>> getPastDBTaxiBookings();

    @Query("SELECT * FROM taxi_bookings WHERE bookingType = '6' ")
    LiveData<List<TaxiBooking>> getCancelledDBTaxiBookings();

    @Query("DELETE FROM taxi_bookings")
    public void deleteTabled();


    @Query("DELETE FROM taxi_bookings")
    public void nukeTable();

}
