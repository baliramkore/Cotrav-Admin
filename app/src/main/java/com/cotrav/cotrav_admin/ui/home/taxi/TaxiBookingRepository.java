package com.cotrav.cotrav_admin.ui.home.taxi;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cotrav.cotrav_admin.local_database.AdminRoomDatabase;
import com.cotrav.cotrav_admin.model.home_model.taxi_model.show_taxi.TaxiBooking;
import com.cotrav.cotrav_admin.model.home_model.taxi_model.show_taxi.TaxiBookingApiResponse;
import com.cotrav.cotrav_admin.model.home_model.taxi_model.view_taxi.TaxiDetailApiResponse;
import com.cotrav.cotrav_admin.model.home_model.taxi_model.view_taxi.ViewTaxiBooking;
import com.cotrav.cotrav_admin.retrofit.ConfigRetrofit;
import com.cotrav.cotrav_admin.retrofit.TaxiBookingAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaxiBookingRepository {

    private LiveData<List<TaxiBooking>> todaysDBLiveData,pastDBLiveData,upcomingDBLiveData,cancelledDBLiveData;
    private LiveData<ViewTaxiBooking> taxiDetailsDbLiveData;
    AdminRoomDatabase adminRoomDatabase;
    private String TAG="BusBookingRepository";
    private TaxiBookingAPI taxiBookingAPI;
    private MutableLiveData<List<TaxiBooking>> todaysLiveData,pastLiveData,upcomingLiveData,cancelledLiveData;
    private MutableLiveData<String> todaysConnectionError,pastConnectionError,upcomingConnectionError,cancelledConnectionError,detailsConnectionError;
    Application application;
    private MutableLiveData<ViewTaxiBooking> bookingDetails;

    private List <TaxiBooking> bookingsdata;
    //LiveData<List<TaxiBooking>> pastTaxiBookings;

    public TaxiBookingRepository(Application application){
        this.application = application;
        adminRoomDatabase=AdminRoomDatabase.getDatabase(application);

        taxiBookingAPI = ConfigRetrofit.configRetrofit(TaxiBookingAPI.class);

        todaysLiveData = new MutableLiveData<>();
        upcomingLiveData = new MutableLiveData<>();
        cancelledLiveData = new MutableLiveData<>();
        pastLiveData = new MutableLiveData<>();
        todaysConnectionError = new MutableLiveData<>();
        upcomingConnectionError = new MutableLiveData<>();
        pastConnectionError = new MutableLiveData<>();
        cancelledConnectionError = new MutableLiveData<>();
        detailsConnectionError = new MutableLiveData<>();
        bookingDetails=new MutableLiveData<ViewTaxiBooking>();

        todaysDBLiveData= adminRoomDatabase.daoTaxi().getTodaysDBTaxiBookings();
        upcomingDBLiveData= adminRoomDatabase.daoTaxi().getUpcomingDBTaxiBookings();
        pastDBLiveData= adminRoomDatabase.daoTaxi().getPastDBTaxiBookings();
        cancelledDBLiveData= adminRoomDatabase.daoTaxi().getCancelledDBTaxiBookings();
        taxiDetailsDbLiveData=adminRoomDatabase.daoTaxi().getTaxiBookingDetails();

    }

    public LiveData<List<TaxiBooking>> getTodaysTaxiBooking(String authorization, String usertype, String corporate_id){
        taxiBookingAPI.getTaxiBookings(authorization,usertype,corporate_id,"1","1").enqueue(new Callback<TaxiBookingApiResponse>() {
            @Override
            public void onResponse(Call<TaxiBookingApiResponse> call, Response<TaxiBookingApiResponse> response) {
                if (response.isSuccessful()){
                    if (response.body().getBookings()!=null && response.body().getBookings().size()>0) {

                        adminRoomDatabase.daoTaxi().addTaxiBookings(response.body().getBookings());

                    }else {
                        todaysConnectionError.setValue("No Taxi Booking Available");
                    }
                }else {
                    todaysConnectionError.setValue("Connection Error");
                }
            }

            @Override
            public void onFailure(Call<TaxiBookingApiResponse> call, Throwable t) {
                Log.e(TAG,"Connection Error");
                todaysConnectionError.setValue("Please Check Internet Connection");
            }
        });
        return todaysDBLiveData;
    }


    public MutableLiveData<String> getTodaysConnectionError() {
        return todaysConnectionError;
    }


    public LiveData<List<TaxiBooking>> getUpcomingTaxiBooking(String authorization, String usertype, String corporate_id){
        taxiBookingAPI.getTaxiBookings(authorization,usertype,corporate_id,"2","1").enqueue(
                new Callback<TaxiBookingApiResponse>() {
            @Override
            public void onResponse(Call<TaxiBookingApiResponse> call, final Response<TaxiBookingApiResponse> response) {
                if (response.isSuccessful()){
                    if (response.body().getBookings()!=null && response.body().getBookings().size()>0)
                    {
                        adminRoomDatabase.daoTaxi().addTaxiBookings(response.body().getBookings());
                    }
                    else
                    {
                        upcomingConnectionError.setValue("No Taxi Booking Available");
                    }
                }else
                {
                    upcomingConnectionError.setValue("Connection Error");
                }
            }

            @Override
            public void onFailure(Call<TaxiBookingApiResponse> call, Throwable t) {
                Log.e(TAG,"Connection Error");
                upcomingConnectionError.setValue("Please Check Internet Connection");
            }
        });
        return upcomingDBLiveData;
    }


    public MutableLiveData<String> getUpcomingConnectionError() {
        return upcomingConnectionError;
    }



    public LiveData<List<TaxiBooking>> getPastTaxiBooking(String authorization, String usertype, String corporate_id){
        taxiBookingAPI.getTaxiBookings(authorization,usertype,corporate_id,"4","1").enqueue(new Callback<TaxiBookingApiResponse>() {
            @Override
            public void onResponse(Call<TaxiBookingApiResponse> call, Response<TaxiBookingApiResponse> response) {
                if (response.isSuccessful()){
                    if (response.body().getBookings()!=null && response.body().getBookings().size()>0) {
                        adminRoomDatabase.daoTaxi().addTaxiBookings(response.body().getBookings());
                    }
                    else {
                        pastConnectionError.setValue("No Taxi Booking Available");
                    }
                }else {
                    pastConnectionError.setValue("Connection Error");
                }
            }

            @Override
            public void onFailure(Call<TaxiBookingApiResponse> call, Throwable t) {
                Log.e(TAG,"Connection Error");
                pastConnectionError.setValue("Please Check Internet Connection");
            }
        });
        return pastDBLiveData;
    }


    public MutableLiveData<String> getPastConnectionError() {
        return pastConnectionError;
    }


    public LiveData<List<TaxiBooking>> getCancelledTaxiBooking(String authorization, String usertype, String corporate_id){
        taxiBookingAPI.getTaxiBookings(authorization,usertype,corporate_id,"6","1").enqueue(new Callback<TaxiBookingApiResponse>() {
            @Override
            public void onResponse(Call<TaxiBookingApiResponse> call, final Response<TaxiBookingApiResponse> response) {
                if (response.isSuccessful()){
                    if (response.body().getBookings()!=null && response.body().getBookings().size()>0)
                    {
                        adminRoomDatabase.daoTaxi().addTaxiBookings(response.body().getBookings());
                    }
                    else
                    {
                        upcomingConnectionError.setValue("No Taxi Booking Available");
                    }
                }else
                {
                    upcomingConnectionError.setValue("Connection Error");
                }
            }

            @Override
            public void onFailure(Call<TaxiBookingApiResponse> call, Throwable t) {
                Log.e(TAG,"Connection Error");
                upcomingConnectionError.setValue("Please Check Internet Connection");
            }
        });
        return cancelledDBLiveData;
    }

/*    public LiveData<List<TaxiBooking>> getCancelledTaxiBooking(String authorization, String usertype, String corporate_id){
        taxiBookingAPI.getTaxiBookings(authorization,usertype,corporate_id,"6").enqueue(new Callback<TaxiBookingApiResponse>() {
            @Override
            public void onResponse(Call<TaxiBookingApiResponse> call, Response<TaxiBookingApiResponse> response) {
                if (response.isSuccessful()){
                    if (response.body().getBookings()!=null && response.body().getBookings().size()>0)
                    {
                        adminRoomDatabase.daoTaxi().addTaxiBookings(response.body().getBookings());

                    }
                    else {
                        cancelledConnectionError.setValue("No Taxi Booking Available");
                    }
                }else {
                    cancelledConnectionError.setValue("Connection Error");
                }
            }

            @Override
            public void onFailure(Call<TaxiBookingApiResponse> call, Throwable t) {
                Log.e(TAG,"Connection Error");
                cancelledConnectionError.setValue("Please Check Internet Connection");
            }
        });
        return cancelledDBLiveData;
    }*/


    public MutableLiveData<String> getCancelledConnectionError() {
        return cancelledConnectionError;
    }






    public LiveData<ViewTaxiBooking> getBookingDetails(String authorization, String usertype, String bookingId, String corporate_id){


        taxiBookingAPI.getTaxiBookingDetails(authorization,usertype,corporate_id,bookingId).enqueue(new Callback<TaxiDetailApiResponse>() {
            @Override
            public void onResponse(Call<TaxiDetailApiResponse> call, Response<TaxiDetailApiResponse> response) {
                if (response.isSuccessful()){
                    if (response.body().getSuccess().equals("1") && response.body()!=null){

                        adminRoomDatabase.daoTaxi().addTaxiBookingDetail(response.body().getBookings());
                        //bookingDetails.setValue(response.body().getBookings().get(0));

                    }
                    else {
                        detailsConnectionError.setValue(" Error");
                    }
                }
                else {
                    detailsConnectionError.setValue("Connection Error");
                }
            }

            @Override
            public void onFailure(Call<TaxiDetailApiResponse> call, Throwable t) {
                detailsConnectionError.setValue("Connection Error: "+t.getMessage());
            }
        });

        return taxiDetailsDbLiveData;
    }

    public MutableLiveData<String> getDetailsConnectionError() {
        return detailsConnectionError;
    }

}
