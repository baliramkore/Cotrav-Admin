package com.cotrav.cotrav_admin.local_database;

import androidx.room.TypeConverter;

import com.cotrav.cotrav_admin.model.home_model.Passanger;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class Converter
{

    static Gson gson = new Gson();
    @TypeConverter
    public static List<Passanger> stringToSomeObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Passanger>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String someObjectListToString(List<Passanger> someObjects) {
        return gson.toJson(someObjects);
    }
   /* @TypeConverter
    public static ArrayList<String> fromString(String value) {
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromArrayLisr(ArrayList<String> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }*/


   /* @TypeConverter
    public static ArrayList<String> fromTimestamp(String value) {
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();
        return new Gson().fromJson(value, listType);
        // return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static String arraylistToString(ArrayList<String> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);

        return json;
        // return date == null ? null : date.getTime();
    }*/
}
