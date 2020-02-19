package com.cotrav.cotrav_admin.ui.home.hotel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cotrav.cotrav_admin.model.home_model.hotel_model.add_hotel.hotel_type.HotelType;
import com.cotrav.cotrav_admin.model.home_model.hotel_model.add_hotel.room_type.RoomType;
import com.cotrav.cotrav_admin.model.home_model.hotel_model.show_hotel.HotelBooking;
import com.cotrav.cotrav_admin.model.home_model.hotel_model.view_hotel.ViewHotelBooking;

import java.util.List;

public class HotelBookingsViewModel extends AndroidViewModel
{


    private HotelBookingRepository hotelBookingRepository;
    private LiveData<List<HotelBooking>> todaysLiveData;
    private LiveData<List<HotelBooking>> upcomingLiveData;
    private LiveData<List<HotelBooking>> pastLiveData;
    private LiveData<List<HotelBooking>> cancelledLiveData;
    private LiveData<List<HotelType>> hotelTypeList;
    private LiveData<List<RoomType>> roomTypeList;

    private LiveData<String> todaysConnectionError;
    private LiveData<String> upcomingConnectionError;
    private LiveData<String> pastConnectionError;
    private LiveData<String> cancelledConnectionError,hoteldetailsConnectionError;

    private MutableLiveData<ViewHotelBooking> hotelBookingDetails;

    public HotelBookingsViewModel(@NonNull Application application) {
        super(application);

        hotelBookingRepository =new HotelBookingRepository(this.getApplication());
        todaysConnectionError=hotelBookingRepository.getTodaysConnectionError();
        upcomingConnectionError=hotelBookingRepository.getUpcomingConnectionError();
        pastConnectionError=hotelBookingRepository.getPastConnectionError();
        cancelledConnectionError=hotelBookingRepository.getCancelledConnectionError();
        hoteldetailsConnectionError=hotelBookingRepository.getDetailsConnectionError();




    }
    public void InitHotelBookingViewModel(String authorization, String usertype, String corporate_id){
        todaysLiveData=hotelBookingRepository.getTodaysHotelBooking(authorization,usertype,corporate_id);
        upcomingLiveData=hotelBookingRepository.getUpcomingHotelBooking(authorization,usertype,corporate_id);
        pastLiveData=hotelBookingRepository.getPastHotelBooking(authorization,usertype,corporate_id);
        cancelledLiveData=hotelBookingRepository.getCancelledHotelBooking(authorization,usertype,corporate_id);

    }


    ///Todays live data
    public LiveData<List<HotelBooking>> getTodaysLiveData(String authorization, String usertype, String corporate_id) {
        hotelBookingRepository.getTodaysHotelBooking(authorization,usertype,corporate_id);
        return todaysLiveData;
    }

    public LiveData<String> getTodaysConnectionError() {
        return todaysConnectionError;
    }

    public void refreshTodaysBooking(String authorization, String usertype,String corporate_id){
        hotelBookingRepository.getTodaysHotelBooking(authorization,usertype,corporate_id);
    }


    ////Upcoming live data
    public LiveData<List<HotelBooking>> getUpcomingLiveData(String authorization, String usertype, String corporate_id) {
        hotelBookingRepository.getUpcomingHotelBooking(authorization,usertype,corporate_id);
        return upcomingLiveData;
    }

    public LiveData<String> getUpcomingConnectionError() {
        return upcomingConnectionError;
    }

    public void refreshUpcomingBooking(String authorization, String usertype,String corporate_id){
        hotelBookingRepository.getUpcomingHotelBooking(authorization,usertype,corporate_id);
    }


    /////past live data
    public LiveData<List<HotelBooking>> getPastLiveData(String authorization, String usertype, String corporate_id) {
        hotelBookingRepository.getPastHotelBooking(authorization,usertype,corporate_id);
        return pastLiveData;
    }

    public LiveData<String> getPastConnectionError() {
        return pastConnectionError;
    }

    public void refreshPastBooking(String authorization, String usertype,String corporate_id){
        hotelBookingRepository.getPastHotelBooking(authorization,usertype,corporate_id);
    }


    /////Cancelled live data
    public LiveData<List<HotelBooking>> getCancelledLiveData(String authorization, String usertype, String corporate_id) {
        hotelBookingRepository.getCancelledHotelBooking(authorization,usertype,corporate_id);
        return cancelledLiveData;
    }

    public LiveData<String> getCancelledConnectionError() {
        return cancelledConnectionError;
    }

    public void refreshCancelledBooking(String authorization, String usertype,String corporate_id){
        hotelBookingRepository.getCancelledHotelBooking(authorization,usertype,corporate_id);
    }

    ///detail live data
    public LiveData<ViewHotelBooking> getHotelBookingDetails(String authorization, String usertype, String bookingId) {
        hotelBookingDetails=hotelBookingRepository.getBookingDetails(authorization,usertype,bookingId);
        return hotelBookingDetails;
    }

    public LiveData<String> getHoteldetailsConnectionError() {
        hoteldetailsConnectionError=hotelBookingRepository.getDetailsConnectionError();
        return hoteldetailsConnectionError;
    }

    public void initHotelData(String Authorization, String usertype)
    {
        hotelTypeList=hotelBookingRepository.getHotelType(Authorization,usertype);
        roomTypeList=hotelBookingRepository.getRoomType(Authorization,usertype);

    }

    public LiveData<List<HotelType>> getHotelTypeInfo(String Authorization, String usertype) {
        if (hotelTypeList==null)
        {
            hotelTypeList=hotelBookingRepository.getHotelType(Authorization,usertype);

        }
        return hotelTypeList;
    }

    public LiveData<List<RoomType>> getRoomTypeList(String Authorization, String usertype) {
        if (roomTypeList==null)
        {
            roomTypeList=hotelBookingRepository.getRoomType(Authorization,usertype);

        }
        return roomTypeList;
    }

}
