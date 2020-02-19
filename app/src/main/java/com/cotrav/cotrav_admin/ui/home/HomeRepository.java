package com.cotrav.cotrav_admin.ui.home;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cotrav.cotrav_admin.GsonStringConvertor;
import com.cotrav.cotrav_admin.local_database.AdminRoomDatabase;
import com.cotrav.cotrav_admin.model.home_model.DashBoardApiResponse;
import com.cotrav.cotrav_admin.model.home_model.DashBoardData;
import com.cotrav.cotrav_admin.model.taxi_types_model.TaxiType;
import com.cotrav.cotrav_admin.model.taxi_types_model.TaxiTypeApiResponse;
import com.cotrav.cotrav_admin.retrofit.AddTaxiBookingAPI;
import com.cotrav.cotrav_admin.retrofit.ConfigRetrofit;
import com.cotrav.cotrav_admin.retrofit.GetDashBoardDataAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeRepository {

    MutableLiveData<List<DashBoardData>> trainStations;
    SharedPreferences dashBoardPref;
    GetDashBoardDataAPI dashBoardAPI;
    private List<DashBoardData> localDashBoardData;
    AdminRoomDatabase adminDb;
    MutableLiveData<List<DashBoardData>> dashBoardLiveData;

    private Application application;
    private SharedPreferences taxiTypePreferences;
    private AddTaxiBookingAPI addTaxiBookingAPI;
    private MutableLiveData<List<TaxiType>> taxiTypeLiveData;

    public HomeRepository(Application application) {
        trainStations=new MutableLiveData<>();
        adminDb=AdminRoomDatabase.getDatabase(application);
        taxiTypeLiveData=new MutableLiveData<>();
        taxiTypePreferences=application.getSharedPreferences("taxiTypeInfo", Context.MODE_PRIVATE);
        addTaxiBookingAPI= ConfigRetrofit.configRetrofit(AddTaxiBookingAPI.class);
        dashBoardAPI= ConfigRetrofit.configRetrofit(GetDashBoardDataAPI.class);
        dashBoardPref=(SharedPreferences)application.getSharedPreferences("dashboardinfo", Context.MODE_PRIVATE);
        if (!dashBoardPref.getString("dash_board","n").equals("n")){
            trainStations.setValue(GsonStringConvertor.stringToGson(dashBoardPref.getString("dash_board","n"), DashBoardApiResponse.class).getDashboard());
        }
//        localDashBoardData= adminDb.dashBoardDao().getLocalDashBoard();
        dashBoardLiveData = new MutableLiveData<>();

    }

    public MutableLiveData<List<DashBoardData>> getDashBoardLiveData(String Authorization, String usertype, String corporate_id)
    {

        dashBoardAPI.getDashBoardData(Authorization,usertype,corporate_id).enqueue(new Callback<DashBoardApiResponse>() {
            @Override
            public void onResponse(Call<DashBoardApiResponse> call, Response<DashBoardApiResponse> response) {
                SharedPreferences.Editor editor=dashBoardPref.edit();
                dashBoardLiveData.setValue(response.body().getDashboard());

            }

            @Override
            public void onFailure(Call<DashBoardApiResponse> call, Throwable t) {

            }
        });
        return dashBoardLiveData;
    }

    public LiveData<List<TaxiType>> getTaxiType(String Authorization, String usertype){


        addTaxiBookingAPI.getTaxiType(Authorization,usertype).enqueue(new Callback<TaxiTypeApiResponse>() {
            @Override
            public void onResponse(Call<TaxiTypeApiResponse> call, Response<TaxiTypeApiResponse> response) {

                SharedPreferences.Editor editor=taxiTypePreferences.edit();
                if (response.body().getSuccess().equals("1")){
                    taxiTypeLiveData.setValue(response.body().getTaxiTypes());
                    editor.putString("taxi_type", GsonStringConvertor.gsonToString(response));
                    editor.commit();
                }
            }

            @Override
            public void onFailure(Call<TaxiTypeApiResponse> call, Throwable t) {

            }
        });

        return taxiTypeLiveData;
    }
}
