package com.cotrav.cotrav_admin.ui.utilities.asscity;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.cotrav.cotrav_admin.local_database.AdminRoomDatabase;
import com.cotrav.cotrav_admin.model.assesmentcity_model.AssesmentCities;
import com.cotrav.cotrav_admin.model.assesmentcity_model.AssesmentCityApiResponse;
import com.cotrav.cotrav_admin.retrofit.ConfigRetrofit;
import com.cotrav.cotrav_admin.retrofit.GetUtilitiesApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssesmentCityRepository {
    private LiveData<List<AssesmentCities>> assesmentCityDBLiveData;
    AdminRoomDatabase adminDb;
    private GetUtilitiesApi getUtilitiesApi;
    private MutableLiveData<List<AssesmentCities>> assesmentCityLiveData;
    private MutableLiveData<String> assesmentCityConnectionError;
    Application application;

    public AssesmentCityRepository(Application application){
        this.application = application;
        //adminDb=AdminRoomDatabase.getDatabase(application);
        getUtilitiesApi = ConfigRetrofit.configRetrofit(GetUtilitiesApi.class);

        assesmentCityLiveData = new MutableLiveData<>();
        assesmentCityConnectionError = new MutableLiveData<>();

        //entitiesDBLiveData= adminDb.daoEntity().getEntities();


    }
    public LiveData<List<AssesmentCities>> getAllCorporateCities(String authorization, String usertype, String corporate_id)
    {
        getUtilitiesApi.getAssesmentCities(authorization,usertype,corporate_id).enqueue(
                new Callback<AssesmentCityApiResponse>() {

                    @Override

                    public void onResponse(Call<AssesmentCityApiResponse> call, Response<AssesmentCityApiResponse> response)
                    {
                        if (response.isSuccessful()){
                            if (response.body().getCities()!=null && response.body().getCities().size()>0) {

                                //todaysLiveData.setValue(response.body().getBookings());
                                //adminDb.daoEntity().addEnties(response.body().getEntitys());
                                assesmentCityLiveData.setValue(response.body().getCities());


                            }
                            else {
                                assesmentCityConnectionError.setValue("No Assessment City Available");
                            }
                        }else {
                            assesmentCityConnectionError.setValue("Connection Error");
                        }
                    }

                    @Override
                    public void onFailure(Call<AssesmentCityApiResponse> call, Throwable t)
                    {
                        assesmentCityConnectionError.setValue("Please Check Internet Connection");
                    }
                });
        return assesmentCityLiveData;
    }


    public MutableLiveData<String> getAssesmentCityConnectionError() {
        return assesmentCityConnectionError;
    }
}
