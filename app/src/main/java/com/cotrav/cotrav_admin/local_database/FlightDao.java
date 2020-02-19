package com.cotrav.cotrav_admin.local_database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cotrav.cotrav_admin.model.home_model.flight_model.show_flight.FlightBooking;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface FlightDao
{
    @Insert(onConflict = REPLACE)
    void addFlightBookings(List<FlightBooking> bookingList);

    @Query("SELECT * from flight_bookings ")
    LiveData<List<FlightBooking>> getLocalFlightBookings();

    @Query("SELECT * FROM flight_bookings WHERE bookingType = '1' ")
    LiveData<List<FlightBooking>> getTodaysDBFlightBookings();

    @Query("SELECT * FROM flight_bookings WHERE bookingType = '2' ")
    LiveData<List<FlightBooking>> getUpcomingDBFlightBookings();

    @Query("SELECT * FROM flight_bookings WHERE bookingType = '4' ")
    LiveData<List<FlightBooking>> getPastDBFlightBookings();

    @Query("SELECT * FROM flight_bookings WHERE bookingType = '6' ")
    LiveData<List<FlightBooking>> getCancelledDBFlightBookings();

    @Query("DELETE FROM flight_bookings")
    public void deleteTabled();
}
