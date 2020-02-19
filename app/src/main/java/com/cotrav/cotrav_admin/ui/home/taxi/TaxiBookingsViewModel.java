package com.cotrav.cotrav_admin.ui.home.taxi;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cotrav.cotrav_admin.model.home_model.taxi_model.show_taxi.TaxiBooking;
import com.cotrav.cotrav_admin.model.home_model.taxi_model.view_taxi.ViewTaxiBooking;

import java.util.List;

public class TaxiBookingsViewModel extends AndroidViewModel
{
    private TaxiBookingRepository taxiBookingRepository;
    private LiveData<List<TaxiBooking>> todaysLiveData;
    private LiveData<List<TaxiBooking>> upcomingLiveData;
    private LiveData<List<TaxiBooking>> pastLiveData;
    private LiveData<List<TaxiBooking>> cancelledLiveData;
    private LiveData<String> todaysConnectionError;
    private LiveData<String> upcomingConnectionError;
    private LiveData<String> pastConnectionError;
    private LiveData<String> cancelledConnectionError;
    private LiveData<ViewTaxiBooking> taxiBookingDetails;
    private LiveData<String> taxidetailsConnectionError;



    public TaxiBookingsViewModel(@NonNull Application application) {
        super(application);


       // todaysBookingList=taxiBookingRepository.getTodaysBookings();

        taxiBookingRepository =new TaxiBookingRepository(this.getApplication());
        todaysConnectionError=taxiBookingRepository.getTodaysConnectionError();
        upcomingConnectionError=taxiBookingRepository.getUpcomingConnectionError();
        pastConnectionError=taxiBookingRepository.getPastConnectionError();
        cancelledConnectionError=taxiBookingRepository.getCancelledConnectionError();
        taxidetailsConnectionError=taxiBookingRepository.getDetailsConnectionError();




    }
    public void InitPlaneBookingViewModel(String authorization, String usertype, String corporate_id){
        todaysLiveData=taxiBookingRepository.getTodaysTaxiBooking(authorization,usertype,corporate_id);
        upcomingLiveData=taxiBookingRepository.getUpcomingTaxiBooking(authorization,usertype,corporate_id);
        pastLiveData=taxiBookingRepository.getPastTaxiBooking(authorization,usertype,corporate_id);
        cancelledLiveData=taxiBookingRepository.getCancelledTaxiBooking(authorization,usertype,corporate_id);

    }


    ///Todays live data
    public LiveData<List<TaxiBooking>> getTodaysLiveData(String authorization, String usertype, String corporate_id) {
        taxiBookingRepository.getTodaysTaxiBooking(authorization,usertype,corporate_id);
        return todaysLiveData;
    }

    public LiveData<String> getTodaysConnectionError() {
        return todaysConnectionError;
    }

    public void refreshTodaysBooking(String authorization, String usertype,String corporate_id){
        taxiBookingRepository.getTodaysTaxiBooking(authorization,usertype,corporate_id);
    }


    ////Upcoming live data
    public LiveData<List<TaxiBooking>> getUpcomingLiveData(String authorization, String usertype, String corporate_id) {
        taxiBookingRepository.getUpcomingTaxiBooking(authorization,usertype,corporate_id);
        return upcomingLiveData;
    }

    public LiveData<String> getUpcomingConnectionError() {
        return upcomingConnectionError;
    }

    public void refreshUpcomingBooking(String authorization, String usertype,String corporate_id){
        taxiBookingRepository.getUpcomingTaxiBooking(authorization,usertype,corporate_id);
    }


    /////past live data
    public LiveData<List<TaxiBooking>> getPastLiveData(String authorization, String usertype, String corporate_id) {
        pastLiveData= taxiBookingRepository.getPastTaxiBooking(authorization,usertype,corporate_id);
        return pastLiveData;
    }

    public LiveData<String> getPastConnectionError() {
        return pastConnectionError;
    }

    public void refreshPastBooking(String authorization, String usertype,String corporate_id){
        taxiBookingRepository.getPastTaxiBooking(authorization,usertype,corporate_id);
    }


    /////Cancelled live data
    public LiveData<List<TaxiBooking>> getCancelledLiveData(String authorization, String usertype, String corporate_id) {
        taxiBookingRepository.getCancelledTaxiBooking(authorization,usertype,corporate_id);
        return cancelledLiveData;
    }

    public LiveData<String> getCancelledConnectionError() {
        return cancelledConnectionError;
    }

    public void refreshCancelledBooking(String authorization, String usertype,String corporate_id){
        taxiBookingRepository.getCancelledTaxiBooking(authorization,usertype,corporate_id);
    }

    ///detail live data
    public LiveData<ViewTaxiBooking> getTaxiBookingDetails(String authorization, String usertype, String bookingId, String corporate_id) {
        taxiBookingDetails=taxiBookingRepository.getBookingDetails(authorization,usertype,bookingId,corporate_id);
        return taxiBookingDetails;
    }

    public LiveData<String> getTaxiDetailsConnectionError() {
        taxidetailsConnectionError=taxiBookingRepository.getDetailsConnectionError();
        return taxidetailsConnectionError;
    }


}
