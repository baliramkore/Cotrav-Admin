package com.cotrav.cotrav_admin.local_database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cotrav.cotrav_admin.model.home_model.hotel_model.show_hotel.HotelBooking;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface HotelDao
{
    @Insert(onConflict = REPLACE)
    void addHotelBookings(List<HotelBooking> bookingList);

    @Query("SELECT * from hotel_bookings ")
    LiveData<List<HotelBooking>> getLocalHotelBookings();

    @Query("SELECT * FROM hotel_bookings WHERE bookingType = '1' ")
    LiveData<List<HotelBooking>> getTodaysDBHotelBookings();

    @Query("SELECT * FROM hotel_bookings WHERE bookingType = '2' ")
    LiveData<List<HotelBooking>> getUpcomingDBHotelBookings();

    @Query("SELECT * FROM hotel_bookings WHERE bookingType = '4' ")
    LiveData<List<HotelBooking>> getPastDBHotelBookings();

    @Query("SELECT * FROM hotel_bookings WHERE bookingType = '6' ")
    LiveData<List<HotelBooking>> getCancelledDBHotelBookings();

    @Query("DELETE FROM hotel_bookings")
    public void deleteTabled();

}
