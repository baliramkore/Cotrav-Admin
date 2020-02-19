package com.cotrav.cotrav_admin.local_database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.cotrav.cotrav_admin.model.home_model.train_model.show_train.TrainBooking;
import java.util.List;
import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface TrainDao
{
    @Insert(onConflict = REPLACE)
    void addTrainBookings(List<TrainBooking> bookingList);

    @Query("SELECT * from train_bookings ")
    LiveData<List<TrainBooking>> getLocalTrainBookings();

    @Query("SELECT * FROM train_bookings WHERE bookingType = '1' ")
    LiveData<List<TrainBooking>> getTodaysDBTrainBookings();

    @Query("SELECT * FROM train_bookings WHERE bookingType = '2' ")
    LiveData<List<TrainBooking>> getUpcomingDBTrainBookings();

    @Query("SELECT * FROM train_bookings WHERE bookingType = '4' ")
    LiveData<List<TrainBooking>> getPastDBTrainBookings();

    @Query("SELECT * FROM train_bookings WHERE bookingType = '6' ")
    LiveData<List<TrainBooking>> getCancelledDBTrainBookings();

    @Query("DELETE FROM train_bookings")
    public void deleteTabled();
}
