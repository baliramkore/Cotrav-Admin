package com.cotrav.cotrav_admin.ui.home.hotel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cotrav.cotrav_admin.GsonStringConvertor;
import com.cotrav.cotrav_admin.local_database.AdminRoomDatabase;
import com.cotrav.cotrav_admin.model.home_model.hotel_model.add_hotel.hotel_type.HotelType;
import com.cotrav.cotrav_admin.model.home_model.hotel_model.add_hotel.hotel_type.HotelTypeApiResponse;
import com.cotrav.cotrav_admin.model.home_model.hotel_model.add_hotel.room_type.RoomType;
import com.cotrav.cotrav_admin.model.home_model.hotel_model.add_hotel.room_type.RoomTypeApiResponse;
import com.cotrav.cotrav_admin.model.home_model.hotel_model.show_hotel.HotelBooking;
import com.cotrav.cotrav_admin.model.home_model.hotel_model.show_hotel.HotelBookingApiResponse;
import com.cotrav.cotrav_admin.model.home_model.hotel_model.view_hotel.HotelDetailApiResponse;
import com.cotrav.cotrav_admin.model.home_model.hotel_model.view_hotel.ViewHotelBooking;
import com.cotrav.cotrav_admin.retrofit.ConfigRetrofit;
import com.cotrav.cotrav_admin.retrofit.HotelBookingAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotelBookingRepository {

    private String TAG = "HotelBookingRepository";
    private HotelBookingAPI hotelBookingAPI;
    private MutableLiveData<List<HotelBooking>> todaysLiveData, pastLiveData, upcomingLiveData, cancelledLiveData;
    private MutableLiveData<String> todaysConnectionError, pastConnectionError, upcomingConnectionError, cancelledConnectionError, detailsConnectionError;
    Application application;
    private MutableLiveData<ViewHotelBooking> bookingDetails;
    private LiveData<List<HotelBooking>> todaysDBLiveData, pastDBLiveData, upcomingDBLiveData, cancelledDBLiveData;
    AdminRoomDatabase adminRoomDatabase;
    private MutableLiveData<List<RoomType>> roomTypeLiveData;
    private MutableLiveData<List<HotelType>> hotelTypeLiveData;
    private SharedPreferences hotelTypePreferences;

    public HotelBookingRepository(Application application) {
        this.application = application;
        adminRoomDatabase = AdminRoomDatabase.getDatabase(application);
        hotelBookingAPI = ConfigRetrofit.configRetrofit(HotelBookingAPI.class);
        hotelTypePreferences = application.getSharedPreferences("hotelTypeInfo", Context.MODE_PRIVATE);

        todaysLiveData = new MutableLiveData<>();
        upcomingLiveData = new MutableLiveData<>();
        cancelledLiveData = new MutableLiveData<>();
        pastLiveData = new MutableLiveData<>();
        roomTypeLiveData = new MutableLiveData<>();
        hotelTypeLiveData = new MutableLiveData<>();
        roomTypeLiveData = new MutableLiveData<>();


        todaysConnectionError = new MutableLiveData<>();
        upcomingConnectionError = new MutableLiveData<>();
        pastConnectionError = new MutableLiveData<>();
        cancelledConnectionError = new MutableLiveData<>();
        detailsConnectionError = new MutableLiveData<>();
        bookingDetails = new MutableLiveData<>();


        todaysDBLiveData = adminRoomDatabase.daoHotel().getTodaysDBHotelBookings();
        upcomingDBLiveData = adminRoomDatabase.daoHotel().getUpcomingDBHotelBookings();
        pastDBLiveData = adminRoomDatabase.daoHotel().getPastDBHotelBookings();
        cancelledDBLiveData = adminRoomDatabase.daoHotel().getCancelledDBHotelBookings();

    }

    public LiveData<List<HotelBooking>> getTodaysHotelBooking(String authorization, String usertype, String corporate_id) {
        hotelBookingAPI.getHotelBookings(authorization, usertype, corporate_id, "1","1").enqueue(new Callback<HotelBookingApiResponse>() {
            @Override
            public void onResponse(Call<HotelBookingApiResponse> call, Response<HotelBookingApiResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getBookings() != null && response.body().getBookings().size() > 0) {
                        //todaysLiveData.setValue(response.body().getBookings());
                        adminRoomDatabase.daoHotel().addHotelBookings(response.body().getBookings());

                    } else {
                        todaysConnectionError.setValue("No Hotel Booking Available");
                    }
                } else {
                    todaysConnectionError.setValue("Connection Error");
                }
            }

            @Override
            public void onFailure(Call<HotelBookingApiResponse> call, Throwable t) {
                Log.e(TAG, "Connection Error");
                todaysConnectionError.setValue("Please Check Internet Connection");
            }
        });
        return todaysDBLiveData;
    }


    public MutableLiveData<String> getTodaysConnectionError() {

        return todaysConnectionError;

    }


    public LiveData<List<HotelBooking>> getUpcomingHotelBooking(String authorization, String usertype, String corporate_id) {
        hotelBookingAPI.getHotelBookings(authorization, usertype, corporate_id, "2","1").enqueue(
                new Callback<HotelBookingApiResponse>() {
                    @Override
                    public void onResponse(Call<HotelBookingApiResponse> call, Response<HotelBookingApiResponse> response) {

                        if (response.isSuccessful()) {
                            if (response.body().getBookings() != null && response.body().getBookings().size() > 0) {
                                adminRoomDatabase.daoHotel().addHotelBookings(response.body().getBookings());
                            } else {
                                upcomingConnectionError.setValue("No Hotel Booking Available");
                            }
                        } else {
                            upcomingConnectionError.setValue("Connection Error");
                        }
                    }

                    @Override
                    public void onFailure(Call<HotelBookingApiResponse> call, Throwable t) {
                        Log.e(TAG, "Connection Error");
                        upcomingConnectionError.setValue("Please Check Internet Connection");
                    }
                });

                /*Callback<HotelBookingApiResponse>() {


            @Override
            public void onResponse(Call<HotelBookingApiResponse> call, Response<HotelBookingApiResponse> response) {
                if (response.isSuccessful())
                {
                    if (response.body().getBookings()!=null && response.body().getBookings().size()>0)
                    {

                        //upcomingLiveData.setValue(response.body().getBookings());
                        adminRoomDatabase.daoHotel().addHotelBookings(response.body().getBookings());
                    }
                    else {
                        upcomingConnectionError.setValue("No Hotel Booking Available");
                    }
                }else {
                    upcomingConnectionError.setValue("Connection Error");
                }
            }

            @Override
            public void onFailure(Call<HotelBookingApiResponse> call, Throwable t) {
                Log.e(TAG,"Connection Error");
                upcomingConnectionError.setValue("Please Check Internet Connection");
            }
        });*/
        return upcomingDBLiveData;
    }


    public MutableLiveData<String> getUpcomingConnectionError() {
        return upcomingConnectionError;
    }


    public LiveData<List<HotelBooking>> getPastHotelBooking(String authorization, String usertype, String corporate_id) {
        hotelBookingAPI.getHotelBookings(authorization, usertype, corporate_id, "4","1").enqueue(new Callback<HotelBookingApiResponse>() {
            @Override
            public void onResponse(Call<HotelBookingApiResponse> call, Response<HotelBookingApiResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getBookings() != null && response.body().getBookings().size() > 0) {
                        // pastLiveData.setValue(response.body().getBookings());
                        adminRoomDatabase.daoHotel().addHotelBookings(response.body().getBookings());
                    } else {
                        pastConnectionError.setValue("No Hotel Booking Available");
                    }
                } else {
                    pastConnectionError.setValue("Connection Error");
                }
            }

            @Override
            public void onFailure(Call<HotelBookingApiResponse> call, Throwable t) {
                Log.e(TAG, "Connection Error");
                pastConnectionError.setValue("Please Check Internet Connection");
            }
        });
        return pastDBLiveData;
    }


    public MutableLiveData<String> getPastConnectionError() {
        return pastConnectionError;
    }


    public LiveData<List<HotelBooking>> getCancelledHotelBooking(String authorization, String usertype, String corporate_id) {
        hotelBookingAPI.getHotelBookings(authorization, usertype, corporate_id, "6","1").enqueue(new Callback<HotelBookingApiResponse>() {
            @Override
            public void onResponse(Call<HotelBookingApiResponse> call, Response<HotelBookingApiResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getBookings() != null && response.body().getBookings().size() > 0) {
                        //cancelledLiveData.setValue(response.body().getBookings());
                        adminRoomDatabase.daoHotel().addHotelBookings(response.body().getBookings());

                    } else {
                        cancelledConnectionError.setValue("No Hotel Booking Available");
                    }
                } else {
                    cancelledConnectionError.setValue("Connection Error");
                }
            }

            @Override
            public void onFailure(Call<HotelBookingApiResponse> call, Throwable t) {
                Log.e(TAG, "Connection Error");
                cancelledConnectionError.setValue("Please Check Internet Connection");
            }
        });
        return cancelledDBLiveData;
    }


    public MutableLiveData<String> getCancelledConnectionError() {
        return cancelledConnectionError;
    }


    public MutableLiveData<ViewHotelBooking> getBookingDetails(String authorization, String usertype, String bookingId) {


        hotelBookingAPI.getHotelBookingDetails(authorization, usertype, bookingId).enqueue(new Callback<HotelDetailApiResponse>() {
            @Override
            public void onResponse(Call<HotelDetailApiResponse> call, Response<HotelDetailApiResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess().equals("1") && response.body() != null) {
                        bookingDetails.setValue(response.body().getBookings().get(0));
                    } else {
                        detailsConnectionError.setValue(" Error");
                    }
                } else {
                    detailsConnectionError.setValue("Connection Error");
                }
            }

            @Override
            public void onFailure(Call<HotelDetailApiResponse> call, Throwable t) {
                detailsConnectionError.setValue("Connection Error: " + t.getMessage());
            }
        });

        return bookingDetails;
    }


    public LiveData<List<RoomType>> getRoomType(String Authorization, String usertype) {

        hotelBookingAPI.getRoomType(Authorization, usertype).enqueue(new Callback<RoomTypeApiResponse>() {
            @Override
            public void onResponse(Call<RoomTypeApiResponse> call, Response<RoomTypeApiResponse> response) {

                if (response.body().getSuccess().equals("1")) {
                    roomTypeLiveData.setValue(response.body().getTypes());
                }
            }

            @Override
            public void onFailure(Call<RoomTypeApiResponse> call, Throwable t) {

            }
        });

        return roomTypeLiveData;
    }


    public LiveData<List<HotelType>> getHotelType(String Authorization, String usertype) {

        hotelBookingAPI.getHotelType(Authorization, usertype).enqueue(new Callback<HotelTypeApiResponse>() {
            @Override
            public void onResponse(Call<HotelTypeApiResponse> call, Response<HotelTypeApiResponse> response) {

                SharedPreferences.Editor editor = hotelTypePreferences.edit();
                if (response.body().getSuccess().equals("1")) {
                    hotelTypeLiveData.setValue(response.body().getTypes());
                    editor.putString("hotel_type", GsonStringConvertor.gsonToString(response));
                    editor.commit();
                }
            }

            @Override
            public void onFailure(Call<HotelTypeApiResponse> call, Throwable t) {

            }
        });

        return hotelTypeLiveData;
    }

    public MutableLiveData<String> getDetailsConnectionError() {
        return detailsConnectionError;
    }


}
