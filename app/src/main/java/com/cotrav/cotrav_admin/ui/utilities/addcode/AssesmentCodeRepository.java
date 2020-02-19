package com.cotrav.cotrav_admin.ui.utilities.addcode;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cotrav.cotrav_admin.local_database.AdminRoomDatabase;
import com.cotrav.cotrav_admin.model.assesmentcode_model.AssesmentCodeApiResponse;
import com.cotrav.cotrav_admin.model.assesmentcode_model.AssesmentCodes;
import com.cotrav.cotrav_admin.retrofit.ConfigRetrofit;
import com.cotrav.cotrav_admin.retrofit.GetUtilitiesApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssesmentCodeRepository {
    private LiveData<List<AssesmentCodes>> assesmentCodeDBLiveData;
    AdminRoomDatabase adminDb;
    private GetUtilitiesApi getUtilitiesApi;
    private MutableLiveData<List<AssesmentCodes>> assesmentCodesLiveData;
    private MutableLiveData<String> assesmentCodeConnectionError;
    Application application;

    public AssesmentCodeRepository(Application application){
        this.application = application;
        getUtilitiesApi = ConfigRetrofit.configRetrofit(GetUtilitiesApi.class);
        assesmentCodesLiveData = new MutableLiveData<>();
        assesmentCodeConnectionError = new MutableLiveData<>();


    }
    public LiveData<List<AssesmentCodes>> getAllCorporateCodes(String authorization, String usertype, String corporate_id)
    {
        getUtilitiesApi.getAssesmentCodes(authorization,usertype,corporate_id).enqueue(
                new Callback<AssesmentCodeApiResponse>() {

                    @Override

                    public void onResponse(Call<AssesmentCodeApiResponse> call, Response<AssesmentCodeApiResponse> response)
                    {
                        if (response.isSuccessful()){
                            if (response.body().getCodes()!=null && response.body().getCodes().size()>0) {

                                assesmentCodesLiveData.setValue(response.body().getCodes());
                            }
                            else {
                                assesmentCodeConnectionError.setValue("No Assessment Code Available");
                            }
                        }else {
                            assesmentCodeConnectionError.setValue("Connection Error");
                        }
                    }

                    @Override
                    public void onFailure(Call<AssesmentCodeApiResponse> call, Throwable t)
                    {
                        assesmentCodeConnectionError.setValue("Please Check Internet Connection");
                    }
                });
        return assesmentCodesLiveData;
    }

    public MutableLiveData<String> getassesmentCodeConnectionError() {
        return assesmentCodeConnectionError;
    }
}
