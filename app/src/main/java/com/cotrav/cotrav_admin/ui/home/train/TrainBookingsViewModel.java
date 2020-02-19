package com.cotrav.cotrav_admin.ui.home.train;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cotrav.cotrav_admin.model.home_model.train_model.add_train.train_station.TrainStation;
import com.cotrav.cotrav_admin.model.home_model.train_model.add_train.train_type.TrainType;
import com.cotrav.cotrav_admin.model.home_model.train_model.show_train.TrainBooking;
import com.cotrav.cotrav_admin.model.home_model.train_model.view_train.ViewTrainBooking;

import java.util.List;

public class TrainBookingsViewModel extends AndroidViewModel {
    private TrainBookingRepository trainBookingRepository;
    private LiveData<List<TrainBooking>> todaysLiveData;
    private LiveData<List<TrainBooking>> upcomingLiveData;
    private LiveData<List<TrainBooking>> pastLiveData;
    private LiveData<List<TrainBooking>> cancelledLiveData;
    private LiveData<String> todaysConnectionError;
    private LiveData<String> upcomingConnectionError;
    private LiveData<String> pastConnectionError;
    private LiveData<String> cancelledConnectionError,busdetailsConnectionError;
    private LiveData<List<TrainType>> trainTypeList;
    private LiveData<List<TrainStation>> trainStations;

    private LiveData<ViewTrainBooking> busBookingDetails;

    public TrainBookingsViewModel(@NonNull Application application) {
        super(application);

        trainBookingRepository =new TrainBookingRepository(this.getApplication());
        todaysConnectionError=trainBookingRepository.getTodaysConnectionError();
        upcomingConnectionError=trainBookingRepository.getUpcomingConnectionError();
        pastConnectionError=trainBookingRepository.getPastConnectionError();
        cancelledConnectionError=trainBookingRepository.getCancelledConnectionError();
        busdetailsConnectionError=trainBookingRepository.getDetailsConnectionError();

    }

    public void InitTrainBookingViewModel(String authorization, String usertype, String corporate_id){
        todaysLiveData=trainBookingRepository.getTodaysTrainBooking(authorization,usertype,corporate_id);
        upcomingLiveData=trainBookingRepository.getUpcomingTrainBooking(authorization,usertype,corporate_id);
        pastLiveData=trainBookingRepository.getPastTrainBooking(authorization,usertype,corporate_id);
        cancelledLiveData=trainBookingRepository.getCancelledTrainBooking(authorization,usertype,corporate_id);

    }


    ///Todays live data
    public LiveData<List<TrainBooking>> getTodaysLiveData(String authorization, String usertype, String corporate_id) {
        trainBookingRepository.getTodaysTrainBooking(authorization,usertype,corporate_id);
        return todaysLiveData;
    }

    public LiveData<String> getTodaysConnectionError() {
        return todaysConnectionError;
    }

    public void refreshTodaysBooking(String authorization, String usertype,String corporate_id){
        trainBookingRepository.getTodaysTrainBooking(authorization,usertype,corporate_id);
    }


    ////Upcoming live data
    public LiveData<List<TrainBooking>> getUpcomingLiveData(String authorization, String usertype, String corporate_id) {
        trainBookingRepository.getUpcomingTrainBooking(authorization,usertype,corporate_id);
        return upcomingLiveData;
    }

    public LiveData<String> getUpcomingConnectionError() {
        return upcomingConnectionError;
    }

    public void refreshUpcomingBooking(String authorization, String usertype,String corporate_id){
        trainBookingRepository.getUpcomingTrainBooking(authorization,usertype,corporate_id);
    }


    /////past live data
    public LiveData<List<TrainBooking>> getPastLiveData(String authorization, String usertype, String corporate_id) {
        trainBookingRepository.getPastTrainBooking(authorization,usertype,corporate_id);
        return pastLiveData;
    }

    public LiveData<String> getPastConnectionError() {
        return pastConnectionError;
    }

    public void refreshPastBooking(String authorization, String usertype,String corporate_id){
        trainBookingRepository.getPastTrainBooking(authorization,usertype,corporate_id);
    }


    /////Cancelled live data
    public LiveData<List<TrainBooking>> getCancelledLiveData(String authorization, String usertype, String corporate_id) {
        trainBookingRepository.getCancelledTrainBooking(authorization,usertype,corporate_id);
        return cancelledLiveData;
    }

    public LiveData<String> getCancelledConnectionError() {
        return cancelledConnectionError;
    }

    public void refreshCancelledBooking(String authorization, String usertype,String corporate_id){
        trainBookingRepository.getCancelledTrainBooking(authorization,usertype,corporate_id);
    }

    ///detail live data
    public LiveData<ViewTrainBooking> getTrainBookingDetails(String authorization, String usertype, String bookingId) {
        busBookingDetails=trainBookingRepository.getBookingDetails(authorization,usertype,bookingId);
        return busBookingDetails;
    }

    public LiveData<String> getTrainDetailsConnectionError() {
        busdetailsConnectionError=trainBookingRepository.getDetailsConnectionError();
        return busdetailsConnectionError;
    }

    public void initTrainType(String Authorization, String usertype)
    {
        trainTypeList=trainBookingRepository.getTrainType(Authorization,usertype);
        trainStations=trainBookingRepository.getTrainStation(Authorization,usertype);

    }

    public LiveData<List<TrainType>> getTrainTypeInfo(String Authorization, String usertype) {
        if (trainTypeList==null)
        {
            trainTypeList=trainBookingRepository.getTrainType(Authorization,usertype);

        }
        return trainTypeList;
    }
    public LiveData<List<TrainStation>> getTrainStations(String Authorization, String usertype) {
        if (trainStations==null)
        {
            trainStations=trainBookingRepository.getTrainStation(Authorization,usertype);

        }
        return trainStations;
    }
}
