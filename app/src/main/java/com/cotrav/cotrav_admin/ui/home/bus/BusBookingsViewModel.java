package com.cotrav.cotrav_admin.ui.home.bus;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.cotrav.cotrav_admin.model.home_model.bus_model.show_bus.BusBooking;
import com.cotrav.cotrav_admin.model.home_model.bus_model.view_bus.ViewBusBooking;
import java.util.List;

public class BusBookingsViewModel extends AndroidViewModel {


    private BusBookingRepository busBookingRepository;
    private LiveData<List<BusBooking>> todaysLiveData;
    private LiveData<List<BusBooking>> upcomingLiveData;
    private LiveData<List<BusBooking>> pastLiveData;
    private LiveData<List<BusBooking>> cancelledLiveData;

    private LiveData<String> todaysConnectionError;
    private LiveData<String> upcomingConnectionError;
    private LiveData<String> pastConnectionError;
    private LiveData<String> cancelledConnectionError,busdetailsConnectionError;

    private LiveData<ViewBusBooking> busBookingDetails;

    public BusBookingsViewModel(@NonNull Application application) {
        super(application);

        busBookingRepository =new BusBookingRepository(this.getApplication());
        todaysConnectionError=busBookingRepository.getTodaysConnectionError();
        upcomingConnectionError=busBookingRepository.getUpcomingConnectionError();
        pastConnectionError=busBookingRepository.getPastConnectionError();
        cancelledConnectionError=busBookingRepository.getCancelledConnectionError();
        busdetailsConnectionError=busBookingRepository.getDetailsConnectionError();

    }
    public void InitBusBookingViewModel(String authorization, String usertype, String spoc_id){
        todaysLiveData=busBookingRepository.getTodaysBusBooking(authorization,usertype,spoc_id);
        upcomingLiveData=busBookingRepository.getUpcomingBusBooking(authorization,usertype,spoc_id);
        pastLiveData=busBookingRepository.getPastBusBooking(authorization,usertype,spoc_id);
        cancelledLiveData=busBookingRepository.getCancelledBusBooking(authorization,usertype,spoc_id);

    }


    ///Todays live data
    public LiveData<List<BusBooking>> getTodaysLiveData(String authorization, String usertype, String spoc_id) {
        busBookingRepository.getTodaysBusBooking(authorization,usertype,spoc_id);
        return todaysLiveData;
    }

    public LiveData<String> getTodaysConnectionError() {
        return todaysConnectionError;
    }

    public void refreshTodaysBooking(String authorization, String usertype,String spoc_id)
    {
        busBookingRepository.getTodaysBusBooking(authorization,usertype,spoc_id);
    }


    ////Upcoming live data
    public LiveData<List<BusBooking>> getUpcomingLiveData(String authorization, String usertype, String spoc_id) {
        busBookingRepository.getUpcomingBusBooking(authorization,usertype,spoc_id);
        return upcomingLiveData;
    }

    public LiveData<String> getUpcomingConnectionError() {
        return upcomingConnectionError;
    }

    public void refreshUpcomingBooking(String authorization, String usertype,String spoc_id){
        busBookingRepository.getUpcomingBusBooking(authorization,usertype,spoc_id);
    }


    /////past live data
    public LiveData<List<BusBooking>> getPastLiveData(String authorization, String usertype, String spoc_id) {
        busBookingRepository.getPastBusBooking(authorization,usertype,spoc_id);
        return pastLiveData;
    }

    public LiveData<String> getPastConnectionError() {
        return pastConnectionError;
    }

    public void refreshPastBooking(String authorization, String usertype,String spoc_id){
        busBookingRepository.getPastBusBooking(authorization,usertype,spoc_id);
    }


    /////Cancelled live data
    public LiveData<List<BusBooking>> getCancelledLiveData(String authorization, String usertype, String spoc_id) {
       busBookingRepository.getCancelledBusBooking(authorization,usertype,spoc_id);
        return cancelledLiveData;
    }

    public LiveData<String> getCancelledConnectionError() {
        return cancelledConnectionError;
    }

    public void refreshCancelledBooking(String authorization, String usertype,String spoc_id){
        busBookingRepository.getCancelledBusBooking(authorization,usertype,spoc_id);
    }

    ///detail live data
    public LiveData<ViewBusBooking> getBusBookingDetails(String authorization, String usertype, String bookingId,String booking) {
        busBookingDetails=busBookingRepository.getBookingDetails(authorization,usertype,bookingId);
        return busBookingDetails;
    }

    public LiveData<String> getBusdetailsConnectionError() {
        busdetailsConnectionError=busBookingRepository.getDetailsConnectionError();
        return busdetailsConnectionError;
    }
}
