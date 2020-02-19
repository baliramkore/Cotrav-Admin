package com.cotrav.cotrav_admin.ui.home.train;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cotrav.cotrav_admin.GsonStringConvertor;
import com.cotrav.cotrav_admin.local_database.AdminRoomDatabase;
import com.cotrav.cotrav_admin.model.home_model.train_model.add_train.train_station.AllStationCityResponse;
import com.cotrav.cotrav_admin.model.home_model.train_model.add_train.train_station.TrainStation;
import com.cotrav.cotrav_admin.model.home_model.train_model.add_train.train_type.TrainType;
import com.cotrav.cotrav_admin.model.home_model.train_model.add_train.train_type.TrainTypesApiRespose;
import com.cotrav.cotrav_admin.model.home_model.train_model.show_train.TrainBooking;
import com.cotrav.cotrav_admin.model.home_model.train_model.show_train.TrainBookingApiResponse;
import com.cotrav.cotrav_admin.model.home_model.train_model.view_train.ViewTrainBooking;
import com.cotrav.cotrav_admin.model.home_model.train_model.view_train.ViewTrainBookingDetailsResponse;
import com.cotrav.cotrav_admin.retrofit.ConfigRetrofit;
import com.cotrav.cotrav_admin.retrofit.TrainBookingAPI;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrainBookingRepository {

    private String TAG="TrainBookingRepository";
    private TrainBookingAPI trainBookingAPI;
    private SharedPreferences trainTypePreferences;
    private MutableLiveData<List<TrainBooking>> todaysLiveData,pastLiveData,upcomingLiveData,cancelledLiveData;
    private MutableLiveData<String> todaysConnectionError,pastConnectionError,upcomingConnectionError,cancelledConnectionError,detailsConnectionError;
    Application application;
    private MutableLiveData<ViewTrainBooking> bookingDetails;
    private LiveData<List<TrainBooking>> todaysDBLiveData,pastDBLiveData,upcomingDBLiveData,cancelledDBLiveData;
    AdminRoomDatabase adminRoomDatabase;
    private MutableLiveData<List<TrainType>> trainTypeLiveData;
    private MutableLiveData<List<TrainStation>> trainStationsLive;


    public TrainBookingRepository(Application application){
        this.application = application;
        adminRoomDatabase=AdminRoomDatabase.getDatabase(application);
        trainBookingAPI = ConfigRetrofit.configRetrofit(TrainBookingAPI.class);
        trainTypePreferences=application.getSharedPreferences("trainTypeInfo", Context.MODE_PRIVATE);
        todaysLiveData = new MutableLiveData<>();
        upcomingLiveData = new MutableLiveData<>();
        cancelledLiveData = new MutableLiveData<>();
        pastLiveData = new MutableLiveData<>();
        trainTypeLiveData=new MutableLiveData<>();
        trainStationsLive=new MutableLiveData<>();

        todaysConnectionError = new MutableLiveData<>();
        upcomingConnectionError = new MutableLiveData<>();
        pastConnectionError = new MutableLiveData<>();
        cancelledConnectionError = new MutableLiveData<>();
        detailsConnectionError = new MutableLiveData<>();
        bookingDetails=new MutableLiveData<>();

        todaysDBLiveData= adminRoomDatabase.daoTrain().getTodaysDBTrainBookings();
        upcomingDBLiveData= adminRoomDatabase.daoTrain().getUpcomingDBTrainBookings();
        pastDBLiveData= adminRoomDatabase.daoTrain().getPastDBTrainBookings();
        cancelledDBLiveData= adminRoomDatabase.daoTrain().getCancelledDBTrainBookings();
    }
    public LiveData<List<TrainBooking>> getTodaysTrainBooking(String authorization, String usertype, String corporate_id){
        trainBookingAPI.getTrainBookings(authorization,usertype,corporate_id,"1","1").enqueue(new Callback<TrainBookingApiResponse>() {
            @Override
            public void onResponse(Call<TrainBookingApiResponse> call, Response<TrainBookingApiResponse> response) {
                if (response.isSuccessful()){

                    if (response.body().getBookings()!=null && response.body().getBookings().size()>0) {
                        //todaysLiveData.setValue(response.body().getBookings());
                        adminRoomDatabase.daoTrain().addTrainBookings(response.body().getBookings());

                    }
                    else {
                        todaysConnectionError.setValue("No Bookings Available");
                    }
                }else {
                    todaysConnectionError.setValue("Connection Error");
                }
            }

            @Override
            public void onFailure(Call<TrainBookingApiResponse> call, Throwable t) {
                Log.e(TAG,"Connection Error");
                todaysConnectionError.setValue("Please Check Internet Connection");
            }
        });
        return todaysDBLiveData;
    }


    public MutableLiveData<String> getTodaysConnectionError() {
        return todaysConnectionError;
    }


    public LiveData<List<TrainBooking>> getUpcomingTrainBooking(String authorization, String usertype, String corporate_id){
        trainBookingAPI.getTrainBookings(authorization,usertype,corporate_id,"2","1").enqueue(new Callback<TrainBookingApiResponse>() {
            @Override
            public void onResponse(Call<TrainBookingApiResponse> call, Response<TrainBookingApiResponse> response) {
                if (response.isSuccessful()){
                    if (response.body().getBookings()!=null && response.body().getBookings().size()>0)
                    {

                        adminRoomDatabase.daoTrain().addTrainBookings(response.body().getBookings());

                    }
                    else
                    {
                        upcomingConnectionError.setValue("No Bookings Available");
                    }
                }else {
                    upcomingConnectionError.setValue("Connection Error");
                }
            }

            @Override
            public void onFailure(Call<TrainBookingApiResponse> call, Throwable t) {
                Log.e(TAG,"Connection Error");
                upcomingConnectionError.setValue("Please Check Internet Connection");
            }
        });
        return upcomingDBLiveData;
    }


    public MutableLiveData<String> getUpcomingConnectionError() {
        return upcomingConnectionError;
    }



    public LiveData<List<TrainBooking>> getPastTrainBooking(String authorization, String usertype, String corporate_id){
        trainBookingAPI.getTrainBookings(authorization,usertype,corporate_id,"3","1").enqueue(new Callback<TrainBookingApiResponse>() {
            @Override
            public void onResponse(Call<TrainBookingApiResponse> call, Response<TrainBookingApiResponse> response) {
                if (response.isSuccessful()){
                    if (response.body().getBookings()!=null && response.body().getBookings().size()>0) {
                        //pastLiveData.setValue(response.body().getBookings());
                        adminRoomDatabase.daoTrain().addTrainBookings(response.body().getBookings());


                    }
                    else {
                        pastConnectionError.setValue("No Bookings Available");
                    }
                }else {
                    pastConnectionError.setValue("Connection Error");
                }
            }

            @Override
            public void onFailure(Call<TrainBookingApiResponse> call, Throwable t) {
                Log.e(TAG,"Connection Error");
                pastConnectionError.setValue("Please Check Internet Connection");
            }
        });
        return pastDBLiveData;
    }


    public MutableLiveData<String> getPastConnectionError() {
        return pastConnectionError;
    }



    public LiveData<List<TrainBooking>> getCancelledTrainBooking(String authorization, String usertype, String corporate_id){
        trainBookingAPI.getTrainBookings(authorization,usertype,corporate_id,"6","1").enqueue(new Callback<TrainBookingApiResponse>() {
            @Override
            public void onResponse(Call<TrainBookingApiResponse> call, Response<TrainBookingApiResponse> response) {
                if (response.isSuccessful()){
                    if (response.body().getBookings()!=null && response.body().getBookings().size()>0) {
                        //cancelledLiveData.setValue(response.body().getBookings());
                        adminRoomDatabase.daoTrain().addTrainBookings(response.body().getBookings());


                    }
                    else {
                        cancelledConnectionError.setValue("No Bookings Available");
                    }
                }else {
                    cancelledConnectionError.setValue("Connection Error");
                }
            }

            @Override
            public void onFailure(Call<TrainBookingApiResponse> call, Throwable t) {
                Log.e(TAG,"Connection Error");
                cancelledConnectionError.setValue("Please Check Internet Connection");
            }
        });
        return cancelledDBLiveData;
    }


    public MutableLiveData<String> getCancelledConnectionError() {
        return cancelledConnectionError;
    }



    public MutableLiveData<ViewTrainBooking> getBookingDetails(String authorization, String usertype, String bookingId){


        trainBookingAPI.getTrainBookingDetails(authorization,usertype,bookingId).enqueue(new Callback<ViewTrainBookingDetailsResponse>() {
            @Override
            public void onResponse(Call<ViewTrainBookingDetailsResponse> call, Response<ViewTrainBookingDetailsResponse> response) {
                if (response.isSuccessful()){
                    if (response.body().getSuccess().equals("1") && response.body()!=null){
                        bookingDetails.setValue(response.body().getBookings().get(0));
                        //spocDb.daoTaxi().addTaxiBookingDetail(response.body().getBookings().get(0));
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
            public void onFailure(Call<ViewTrainBookingDetailsResponse> call, Throwable t) {
                detailsConnectionError.setValue("Connection Error: "+t.getMessage());
            }
        });

        return bookingDetails;
    }

    public MutableLiveData<String> getDetailsConnectionError() {
        return detailsConnectionError;
    }

    public LiveData<List<TrainType>> getTrainType(String Authorization, String usertype){


        trainBookingAPI.getTrainType(Authorization,usertype).enqueue(new Callback<TrainTypesApiRespose>() {
            @Override
            public void onResponse(Call<TrainTypesApiRespose> call, Response<TrainTypesApiRespose> response) {

                SharedPreferences.Editor editor=trainTypePreferences.edit();
                if (response.body().getSuccess().equals("1")){
                    trainTypeLiveData.setValue(response.body().getTypes());
                    editor.putString("train_type", GsonStringConvertor.gsonToString(response));
                    editor.commit();
                }
            }

            @Override
            public void onFailure(Call<TrainTypesApiRespose> call, Throwable t) {

            }
        });

        return trainTypeLiveData;
    }
    public LiveData<List<TrainStation>>getTrainStation(String Authorization, String usertype){

        trainBookingAPI.getAllStationCities(Authorization,usertype).enqueue(new Callback<AllStationCityResponse>() {
            @Override
            public void onResponse(Call<AllStationCityResponse> call, Response<AllStationCityResponse> response) {
                if (response.body().getSuccess().equals("1")){
                    trainStationsLive.setValue(response.body().getStations());
                }
            }

            @Override
            public void onFailure(Call<AllStationCityResponse> call, Throwable t) {

            }
        });

        return trainStationsLive;
    }


}
