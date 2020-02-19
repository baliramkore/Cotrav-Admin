package com.cotrav.cotrav_admin.ui.utilities.spocs;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cotrav.cotrav_admin.local_database.AdminRoomDatabase;
import com.cotrav.cotrav_admin.model.spoc_model.SpocApiResponse;
import com.cotrav.cotrav_admin.model.spoc_model.Spocs;
import com.cotrav.cotrav_admin.retrofit.ConfigRetrofit;
import com.cotrav.cotrav_admin.retrofit.GetUtilitiesApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpocsRepository {
    private LiveData<List<Spocs>> spocsDBLiveData;
    AdminRoomDatabase adminDb;
    private GetUtilitiesApi getUtilitiesApi;
    private MutableLiveData<List<Spocs>> spocsLiveData;
    private MutableLiveData<String> spocsConnectionError;
    Application application;

    public SpocsRepository(Application application){
        this.application = application;
        getUtilitiesApi = ConfigRetrofit.configRetrofit(GetUtilitiesApi.class);

        spocsLiveData = new MutableLiveData<>();
        spocsConnectionError = new MutableLiveData<>();


    }
    public LiveData<List<Spocs>> getAllSpocs(String authorization, String usertype, String corporate_id)
    {
        getUtilitiesApi.getSpocs(authorization,usertype,corporate_id).enqueue(
                new Callback<SpocApiResponse>() {

                    @Override

                    public void onResponse(Call<SpocApiResponse> call, Response<SpocApiResponse> response)
                    {
                        if (response.isSuccessful()){
                            if (response.body().getSpocs()!=null && response.body().getSpocs().size()>0) {

                                spocsLiveData.setValue(response.body().getSpocs());


                            }
                            else {
                                spocsConnectionError.setValue("No Spoc Available");
                            }
                        }else {
                            spocsConnectionError.setValue("Connection Error");
                        }
                    }

                    @Override
                    public void onFailure(Call<SpocApiResponse> call, Throwable t)
                    {
                        spocsConnectionError.setValue("Please Check Internet Connection");
                    }
                });
        return spocsLiveData;
    }


    public MutableLiveData<String> getSpocsConnectionError() {
        return spocsConnectionError;
    }
}
