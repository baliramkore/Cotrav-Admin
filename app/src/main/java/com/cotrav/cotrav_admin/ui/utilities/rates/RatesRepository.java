package com.cotrav.cotrav_admin.ui.utilities.rates;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cotrav.cotrav_admin.local_database.AdminRoomDatabase;
import com.cotrav.cotrav_admin.model.rates_model.CorporateRates;
import com.cotrav.cotrav_admin.model.rates_model.RatesApiResponse;
import com.cotrav.cotrav_admin.retrofit.ConfigRetrofit;
import com.cotrav.cotrav_admin.retrofit.GetUtilitiesApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RatesRepository {
    private LiveData<List<CorporateRates>> corporateRatesDBLiveData;
    AdminRoomDatabase adminDb;
    private GetUtilitiesApi getUtilitiesApi;
    private MutableLiveData<List<CorporateRates>> corporateRatesLiveData;
    private MutableLiveData<String> rateConnectionError;
    Application application;

    public RatesRepository(Application application){
        this.application = application;
        //adminDb=AdminRoomDatabase.getDatabase(application);
        getUtilitiesApi = ConfigRetrofit.configRetrofit(GetUtilitiesApi.class);

        corporateRatesLiveData = new MutableLiveData<>();
        rateConnectionError = new MutableLiveData<>();

        //entitiesDBLiveData= adminDb.daoEntity().getEntities();


    }
    public LiveData<List<CorporateRates>> getAllCorporateRates(String authorization, String usertype, String corporate_id)
    {
        getUtilitiesApi.getcompanyRates(authorization,usertype,corporate_id).enqueue(
                new Callback<RatesApiResponse>() {

                    @Override

                    public void onResponse(Call<RatesApiResponse> call, Response<RatesApiResponse> response)
                    {
                        if (response.isSuccessful()){
                            if (response.body().getCorporateRates()!=null && response.body().getCorporateRates().size()>0) {

                                //todaysLiveData.setValue(response.body().getBookings());
                                //adminDb.daoEntity().addEnties(response.body().getEntitys());
                                corporateRatesLiveData.setValue(response.body().getCorporateRates());


                            }
                            else {
                                rateConnectionError.setValue("No Rates Available");
                            }
                        }else {
                            rateConnectionError.setValue("Connection Error");
                        }
                    }

                    @Override
                    public void onFailure(Call<RatesApiResponse> call, Throwable t)
                    {
                        rateConnectionError.setValue("Please Check Internet Connection");

                    }
                });
        return corporateRatesLiveData;
    }


    public MutableLiveData<String> getRatesConnectionError() {
        return rateConnectionError;
    }
}
