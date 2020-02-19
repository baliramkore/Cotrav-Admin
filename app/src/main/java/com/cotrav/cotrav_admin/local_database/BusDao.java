package com.cotrav.cotrav_admin.local_database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cotrav.cotrav_admin.model.home_model.bus_model.show_bus.BusBooking;
import com.cotrav.cotrav_admin.model.home_model.bus_model.view_bus.ViewBusBooking;
import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface BusDao
{
    @Insert(onConflict = REPLACE)
    void addBusBookings(List<BusBooking> bookingList);

    @Insert(onConflict = REPLACE)
    void addBusBookingDetail(List<ViewBusBooking> bookingDetailsList);

    @Query("SELECT * from bus_bookings_detail")
    LiveData<ViewBusBooking> getBusBookingDetails();

    @Query("SELECT * from bus_bookings ")
    LiveData<List<BusBooking>> getLocalBusBookings();

    @Query("SELECT * FROM bus_bookings WHERE bookingType = '1' ")
    LiveData<List<BusBooking>> getTodaysDBBusBookings();

    @Query("SELECT * FROM bus_bookings WHERE bookingType = '2' ")
    LiveData<List<BusBooking>> getUpcomingDBBusBookings();

    @Query("SELECT * FROM bus_bookings WHERE bookingType = '4' ")
    LiveData<List<BusBooking>> getPastDBBusBookings();

    @Query("SELECT * FROM bus_bookings WHERE bookingType = '6' ")
    LiveData<List<BusBooking>> getCancelledDBBusBookings();

    @Query("DELETE FROM bus_bookings")
    public void deleteTabled();

}
