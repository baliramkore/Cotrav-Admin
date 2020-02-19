package com.cotrav.cotrav_admin.local_database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.cotrav.cotrav_admin.model.home_model.DashBoardData;
import com.cotrav.cotrav_admin.model.home_model.bus_model.show_bus.BusBooking;
import com.cotrav.cotrav_admin.model.home_model.bus_model.view_bus.ViewBusBooking;
import com.cotrav.cotrav_admin.model.home_model.flight_model.show_flight.FlightBooking;
import com.cotrav.cotrav_admin.model.home_model.flight_model.view_flight.FlightBookingDetails;
import com.cotrav.cotrav_admin.model.home_model.hotel_model.show_hotel.HotelBooking;
import com.cotrav.cotrav_admin.model.home_model.hotel_model.view_hotel.ViewHotelBooking;
import com.cotrav.cotrav_admin.model.home_model.taxi_model.show_taxi.TaxiBooking;
import com.cotrav.cotrav_admin.model.home_model.taxi_model.view_taxi.ViewTaxiBooking;
import com.cotrav.cotrav_admin.model.home_model.train_model.show_train.TrainBooking;
import com.cotrav.cotrav_admin.model.home_model.train_model.view_train.ViewTrainBooking;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {TaxiBooking.class, BusBooking.class, HotelBooking.class, TrainBooking.class, FlightBooking.class,
        ViewTaxiBooking.class,
        ViewBusBooking.class,
        ViewTrainBooking.class,
        ViewHotelBooking.class,
        FlightBookingDetails.class,
        DashBoardData.class}, version = 8, exportSchema = false)
public abstract class AdminRoomDatabase extends RoomDatabase
 {
     public abstract EntityDAO daoEntity();
     public abstract TaxiDao daoTaxi();
     public abstract BusDao daoBus();
     public abstract HotelDao daoHotel();
     public abstract TrainDao daoTrain();
     public abstract FlightDao daoFlight();
     public abstract DashBoardDao dashBoardDao();
     private static volatile AdminRoomDatabase INSTANCE;
     private static final int NUMBER_OF_THREADS = 4;
     static final ExecutorService databaseWriteExecutor =
             Executors.newFixedThreadPool(NUMBER_OF_THREADS);

     public static AdminRoomDatabase getDatabase(final Context context) {
         if (INSTANCE == null) {
             synchronized (AdminRoomDatabase.class) {
                 if (INSTANCE == null) {
                     INSTANCE =
                             Room.databaseBuilder(context.getApplicationContext(),
                                     AdminRoomDatabase.class, "admin_database.db")
                                     .fallbackToDestructiveMigration()
                                     .allowMainThreadQueries()
                                     .build();
                 }
             }
         }
         return INSTANCE;
     }
}
