package com.cotrav.cotrav_admin;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cotrav.cotrav_admin.model.all_cities_model.City;

import java.util.List;

public class AllCityViewModel extends AndroidViewModel {
    private AllCityRepository cityRepository;
    private LiveData<List<City>> entitiesLiveData;
    private LiveData<String>  todaysConnectionError;

    public AllCityViewModel(@NonNull Application application) {
        super(application);

        cityRepository =new AllCityRepository(this.getApplication());


    }
    public void InitCityViewModel(String authorization, String usertype, String corporate_id){
        entitiesLiveData=cityRepository.getAllCities(authorization,usertype,corporate_id);

    }

    public LiveData<List<City>> getCityLiveData(String authorization, String usertype, String corporate_id) {

        entitiesLiveData = cityRepository.getAllCities(authorization,usertype,corporate_id);
        return entitiesLiveData;
    }

    public LiveData<String> getTodaysConnectionError() {
        return todaysConnectionError;
    }

    public void refreshTodaysBooking(String authorization, String usertype,String corporate_id)
    {
        cityRepository.getAllCities(authorization,usertype,corporate_id);
    }
}
