package com.cotrav.cotrav_admin;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cotrav.cotrav_admin.local_database.AdminRoomDatabase;
import com.cotrav.cotrav_admin.model.all_cities_model.AllCitiesApiResponse;
import com.cotrav.cotrav_admin.model.all_cities_model.City;
import com.cotrav.cotrav_admin.retrofit.ConfigRetrofit;
import com.cotrav.cotrav_admin.retrofit.GetUtilitiesApi;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class AllCityRepository
{
    AdminRoomDatabase adminDb;
    private GetUtilitiesApi getUtilitiesApi;
    private MutableLiveData<List<City>> cityLiveData;
    private MutableLiveData<String> todaysConnectionError;
    Application application;
    String deleteStrResponse="";

    public AllCityRepository(Application application){
        this.application = application;
        //adminDb=AdminRoomDatabase.getDatabase(application);
        getUtilitiesApi = ConfigRetrofit.configRetrofit(GetUtilitiesApi.class);
        cityLiveData = new MutableLiveData<>();
        todaysConnectionError = new MutableLiveData<>();


    }
    public LiveData<List<City>> getAllCities(String authorization, String usertype, String corporate_id)
    {
        getUtilitiesApi.getAllCities(authorization,usertype,corporate_id).enqueue(
                new Callback<AllCitiesApiResponse>() {

                    @Override

                    public void onResponse(Call<AllCitiesApiResponse> call, Response<AllCitiesApiResponse> response)
                    {
                        if (response.isSuccessful()){
                            if (response.body().getCities()!=null) {

                                //todaysLiveData.setValue(response.body().getBookings());
                                //adminDb.daoEntity().addEnties(response.body().getEntitys());
                                cityLiveData.setValue(response.body().getCities());


                            }
                            else {
                                todaysConnectionError.setValue("No Entity Available");
                            }
                        }else {
                            todaysConnectionError.setValue("Connection Error");
                        }
                    }

                    @Override
                    public void onFailure(Call<AllCitiesApiResponse> call, Throwable t)
                    {
                    }
                });
        return cityLiveData;
    }

    public MutableLiveData<String> getTodaysConnectionError() {
        return todaysConnectionError;
    }
}
