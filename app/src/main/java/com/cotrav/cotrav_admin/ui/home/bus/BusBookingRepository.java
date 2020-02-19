package com.cotrav.cotrav_admin.ui.home.bus;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cotrav.cotrav_admin.local_database.AdminRoomDatabase;
import com.cotrav.cotrav_admin.model.home_model.bus_model.show_bus.BusBooking;
import com.cotrav.cotrav_admin.model.home_model.bus_model.show_bus.BusBookingApiResponse;
import com.cotrav.cotrav_admin.model.home_model.bus_model.view_bus.ViewBusBooking;
import com.cotrav.cotrav_admin.model.home_model.bus_model.view_bus.ViewBusBookingResponse;
import com.cotrav.cotrav_admin.retrofit.BusBookingAPI;
import com.cotrav.cotrav_admin.retrofit.ConfigRetrofit;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusBookingRepository {

    private LiveData<List<BusBooking>> todaysDBLiveData,pastDBLiveData,upcomingDBLiveData,cancelledDBLiveData;
    private LiveData<ViewBusBooking> busDetailsDbLiveData;
    AdminRoomDatabase adminRoomDatabase;
    private String TAG="BusBookingRepository";
    private BusBookingAPI busBookingAPI;
    private MutableLiveData<List<BusBooking>> todaysLiveData,pastLiveData,upcomingLiveData,cancelledLiveData;
    private MutableLiveData<String> todaysConnectionError,pastConnectionError,upcomingConnectionError,cancelledConnectionError,detailsConnectionError;
    Application application;
    private MutableLiveData<ViewBusBooking> bookingDetails;

    private List <BusBooking> bookingsdata;
    //LiveData<List<BusBooking>> pastBusBookings;

    public BusBookingRepository(Application application){
        this.application = application;
        adminRoomDatabase=AdminRoomDatabase.getDatabase(application);

        busBookingAPI = ConfigRetrofit.configRetrofit(BusBookingAPI.class);

        todaysLiveData = new MutableLiveData<>();
        upcomingLiveData = new MutableLiveData<>();
        cancelledLiveData = new MutableLiveData<>();
        pastLiveData = new MutableLiveData<>();
        todaysConnectionError = new MutableLiveData<>();
        upcomingConnectionError = new MutableLiveData<>();
        pastConnectionError = new MutableLiveData<>();
        cancelledConnectionError = new MutableLiveData<>();
        detailsConnectionError = new MutableLiveData<>();
        bookingDetails=new MutableLiveData<ViewBusBooking>();

        todaysDBLiveData= adminRoomDatabase.daoBus().getTodaysDBBusBookings();
        upcomingDBLiveData= adminRoomDatabase.daoBus().getUpcomingDBBusBookings();
        pastDBLiveData= adminRoomDatabase.daoBus().getPastDBBusBookings();
        cancelledDBLiveData= adminRoomDatabase.daoBus().getCancelledDBBusBookings();
        busDetailsDbLiveData=adminRoomDatabase.daoBus().getBusBookingDetails();

    }

    public LiveData<List<BusBooking>> getTodaysBusBooking(String authorization, String usertype, String corporate_id){
        busBookingAPI.getBusBookings(authorization,usertype,corporate_id,"1","1").enqueue(new Callback<BusBookingApiResponse>() {
            @Override
            public void onResponse(Call<BusBookingApiResponse> call, Response<BusBookingApiResponse> response) {
                if (response.isSuccessful()){
                    if (response.body().getBookings()!=null && response.body().getBookings().size()>0) {

                        adminRoomDatabase.daoBus().addBusBookings(response.body().getBookings());

                    }else {
                        todaysConnectionError.setValue("No Bus Booking Available");
                    }
                }else {
                    todaysConnectionError.setValue("Connection Error");
                }
            }

            @Override
            public void onFailure(Call<BusBookingApiResponse> call, Throwable t) {
                Log.e(TAG,"Connection Error");
                todaysConnectionError.setValue("Please Check Internet Connection");
            }
        });
        return todaysDBLiveData;
    }


    public MutableLiveData<String> getTodaysConnectionError() {
        return todaysConnectionError;
    }


    public LiveData<List<BusBooking>> getUpcomingBusBooking(String authorization, String usertype, String corporate_id){
        busBookingAPI.getBusBookings(authorization,usertype,corporate_id,"2","1").enqueue(new Callback<BusBookingApiResponse>() {
            @Override
            public void onResponse(Call<BusBookingApiResponse> call, final Response<BusBookingApiResponse> response) {
                if (response.isSuccessful()){
                    if (response.body().getBookings()!=null && response.body().getBookings().size()>0)
                    {
                        adminRoomDatabase.daoBus().addBusBookings(response.body().getBookings());
                    }
                    else
                    {
                        upcomingConnectionError.setValue("No Bus Booking Available");
                    }
                }else
                {
                    upcomingConnectionError.setValue("Connection Error");
                }
            }

            @Override
            public void onFailure(Call<BusBookingApiResponse> call, Throwable t) {
                Log.e(TAG,"Connection Error");
                upcomingConnectionError.setValue("Please Check Internet Connection");
            }
        });
        return upcomingDBLiveData;
    }


    public MutableLiveData<String> getUpcomingConnectionError() {
        return upcomingConnectionError;
    }



    public LiveData<List<BusBooking>> getPastBusBooking(String authorization, String usertype, String corporate_id){
        busBookingAPI.getBusBookings(authorization,usertype,corporate_id,"4","1").enqueue(new Callback<BusBookingApiResponse>() {
            @Override
            public void onResponse(Call<BusBookingApiResponse> call, Response<BusBookingApiResponse> response) {
                if (response.isSuccessful()){
                    if (response.body().getBookings()!=null && response.body().getBookings().size()>0) {
                        adminRoomDatabase.daoBus().addBusBookings(response.body().getBookings());
                    }
                    else {
                        pastConnectionError.setValue("No Bus Booking Available");
                    }
                }else {
                    pastConnectionError.setValue("Connection Error");
                }
            }

            @Override
            public void onFailure(Call<BusBookingApiResponse> call, Throwable t) {
                Log.e(TAG,"Connection Error");
                pastConnectionError.setValue("Please Check Internet Connection");
            }
        });
        return pastDBLiveData;
    }


    public MutableLiveData<String> getPastConnectionError() {
        return pastConnectionError;
    }



    public LiveData<List<BusBooking>> getCancelledBusBooking(String authorization, String usertype, String corporate_id){
        busBookingAPI.getBusBookings(authorization,usertype,corporate_id,"6","1").enqueue(new Callback<BusBookingApiResponse>() {
            @Override
            public void onResponse(Call<BusBookingApiResponse> call, Response<BusBookingApiResponse> response) {
                if (response.isSuccessful()){
                    if (response.body().getBookings()!=null && response.body().getBookings().size()>0)
                    {
                        adminRoomDatabase.daoBus().addBusBookings(response.body().getBookings());

                    }
                    else {
                        cancelledConnectionError.setValue("No Bus Booking Available");
                    }
                }else {
                    cancelledConnectionError.setValue("Connection Error");
                }
            }

            @Override
            public void onFailure(Call<BusBookingApiResponse> call, Throwable t) {
                Log.e(TAG,"Connection Error");
                cancelledConnectionError.setValue("Please Check Internet Connection");
            }
        });
        return cancelledDBLiveData;
    }


    public MutableLiveData<String> getCancelledConnectionError() {
        return cancelledConnectionError;
    }






    public LiveData<ViewBusBooking> getBookingDetails(String authorization, String usertype, String corporate_id){


        busBookingAPI.getBusBookingDetails(authorization,usertype,corporate_id).enqueue(new Callback<ViewBusBookingResponse>() {
            @Override
            public void onResponse(Call<ViewBusBookingResponse> call, Response<ViewBusBookingResponse> response) {
                if (response.isSuccessful()){
                    if (response.body().getSuccess().equals("1") && response.body()!=null){

                        adminRoomDatabase.daoBus().addBusBookingDetail(response.body().getBookings());
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
            public void onFailure(Call<ViewBusBookingResponse> call, Throwable t) {
                detailsConnectionError.setValue("Connection Error: "+t.getMessage());
            }
        });

        return busDetailsDbLiveData;
    }

    public MutableLiveData<String> getDetailsConnectionError() {
        return detailsConnectionError;
    }

}
