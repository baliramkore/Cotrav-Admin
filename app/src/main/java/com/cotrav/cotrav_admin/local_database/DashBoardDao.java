package com.cotrav.cotrav_admin.local_database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.cotrav.cotrav_admin.model.home_model.DashBoardData;
import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface DashBoardDao
{
    @Insert(onConflict = REPLACE)
    void addDashBoardDetails(List<DashBoardData> bookingList);

    @Query("SELECT * from dash_board ")
    List<DashBoardData> getLocalDashBoard();

    @Query("DELETE FROM flight_bookings")
    public void deleteDashboard();
}
