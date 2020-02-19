package com.cotrav.cotrav_admin.ui.home.flight;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cotrav.cotrav_admin.model.home_model.flight_model.show_flight.FlightBooking;
import com.cotrav.cotrav_admin.model.home_model.flight_model.view_flight.FlightBookingDetails;

import java.util.List;

public class FlightBookingsViewModel extends AndroidViewModel
{


    private FlightBookingRepository flightBookingRepository;
    private LiveData<List<FlightBooking>> todaysLiveData;
    private LiveData<List<FlightBooking>> upcomingLiveData;
    private LiveData<List<FlightBooking>> pastLiveData;
    private LiveData<List<FlightBooking>> cancelledLiveData;

    private LiveData<String> todaysConnectionError;
    private LiveData<String> upcomingConnectionError;
    private LiveData<String> pastConnectionError;
    private LiveData<String> cancelledConnectionError,flightdetailsConnectionError;

    private MutableLiveData<FlightBookingDetails> flightBookingDetails;

    public FlightBookingsViewModel(@NonNull Application application) {
        super(application);

        flightBookingRepository =new FlightBookingRepository(this.getApplication());
        todaysConnectionError=flightBookingRepository.getTodaysConnectionError();
        upcomingConnectionError=flightBookingRepository.getUpcomingConnectionError();
        pastConnectionError=flightBookingRepository.getPastConnectionError();
        cancelledConnectionError=flightBookingRepository.getCancelledConnectionError();
        flightdetailsConnectionError=flightBookingRepository.getDetailsConnectionError();




    }
    public void InitFlightBookingViewModel(String authorization, String usertype, String spoc_id){
        todaysLiveData=flightBookingRepository.getTodaysFlightBooking(authorization,usertype,spoc_id);
        upcomingLiveData=flightBookingRepository.getUpcomingFlightBooking(authorization,usertype,spoc_id);
        pastLiveData=flightBookingRepository.getPastFlightBooking(authorization,usertype,spoc_id);
        cancelledLiveData=flightBookingRepository.getCancelledFlightBooking(authorization,usertype,spoc_id);

    }


    ///Todays live data
    public LiveData<List<FlightBooking>> getTodaysLiveData(String authorization, String usertype, String spoc_id) {
        flightBookingRepository.getTodaysFlightBooking(authorization,usertype,spoc_id);
        return todaysLiveData;
    }

    public LiveData<String> getTodaysConnectionError() {
        return todaysConnectionError;
    }

    public void refreshTodaysBooking(String authorization, String usertype,String spoc_id){
        flightBookingRepository.getTodaysFlightBooking(authorization,usertype,spoc_id);
    }


    ////Upcoming live data
    public LiveData<List<FlightBooking>> getUpcomingLiveData(String authorization, String usertype, String spoc_id) {
        flightBookingRepository.getUpcomingFlightBooking(authorization,usertype,spoc_id);
        return upcomingLiveData;
    }

    public LiveData<String> getUpcomingConnectionError() {
        return upcomingConnectionError;
    }

    public void refreshUpcomingBooking(String authorization, String usertype,String spoc_id){
        flightBookingRepository.getUpcomingFlightBooking(authorization,usertype,spoc_id);
    }


    /////past live data
    public LiveData<List<FlightBooking>> getPastLiveData(String authorization, String usertype, String spoc_id) {
        flightBookingRepository.getPastFlightBooking(authorization,usertype,spoc_id);
        return pastLiveData;
    }

    public LiveData<String> getPastConnectionError() {
        return pastConnectionError;
    }

    public void refreshPastBooking(String authorization, String usertype,String spoc_id){
        flightBookingRepository.getPastFlightBooking(authorization,usertype,spoc_id);
    }


    /////Cancelled live data
    public LiveData<List<FlightBooking>> getCancelledLiveData(String authorization, String usertype, String spoc_id) {
        flightBookingRepository.getCancelledFlightBooking(authorization,usertype,spoc_id);
        return cancelledLiveData;
    }

    public LiveData<String> getCancelledConnectionError() {
        return cancelledConnectionError;
    }

    public void refreshCancelledBooking(String authorization, String usertype,String spoc_id){
        flightBookingRepository.getCancelledFlightBooking(authorization,usertype,spoc_id);
    }

    ///detail live data
    public LiveData<FlightBookingDetails> getFlightBookingDetails(String authorization, String usertype, String bookingId) {
        flightBookingDetails=flightBookingRepository.getBookingDetails(authorization,usertype,bookingId);
        return flightBookingDetails;
    }

    public LiveData<String> getFlightdetailsConnectionError() {
        flightdetailsConnectionError=flightBookingRepository.getDetailsConnectionError();
        return flightdetailsConnectionError;
    }

}
