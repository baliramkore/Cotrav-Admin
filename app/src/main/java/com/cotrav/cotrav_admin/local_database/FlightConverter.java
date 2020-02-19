package com.cotrav.cotrav_admin.local_database;

import androidx.room.TypeConverter;

import com.cotrav.cotrav_admin.model.home_model.flight_model.view_flight.Flight;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class FlightConverter
{

    static Gson gson = new Gson();
    @TypeConverter
    public static List<Flight> stringToSomeObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Flight>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String someObjectListToString(List<Flight> someObjects) {
        return gson.toJson(someObjects);
    }
}
