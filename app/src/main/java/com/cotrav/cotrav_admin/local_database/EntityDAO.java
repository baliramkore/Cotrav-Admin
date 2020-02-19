package com.cotrav.cotrav_admin.local_database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cotrav.cotrav_admin.model.entities_model.Entities;
import com.cotrav.cotrav_admin.model.home_model.DashBoardData;

import java.util.List;
import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface EntityDAO
{
/*

    @Insert(onConflict = REPLACE)
    void addEntities(List<Entities> entitiesList);

    @Query("SELECT * from entities")
    LiveData<List<Entities>> getEntities();

    @Query("DELETE FROM entities")
    public void nukeTable();
*/

}
