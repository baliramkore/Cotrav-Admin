package com.cotrav.cotrav_admin.ui.utilities.asscity;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.cotrav.cotrav_admin.model.assesmentcity_model.AssesmentCities;
import java.util.List;

public class AssesmentCityViewModel extends AndroidViewModel {


    private AssesmentCityRepository assesmentCityRepository;
    private LiveData<List<AssesmentCities>> corporateCitiesLiveData;
    private LiveData<String>  todaysConnectionError;

        public AssesmentCityViewModel(@NonNull Application application) {
        super(application);
            assesmentCityRepository =new AssesmentCityRepository(this.getApplication());
    }
    public void InitCorporateCitiesViewModel(String authorization, String usertype, String spoc_id){
        corporateCitiesLiveData=assesmentCityRepository.getAllCorporateCities(authorization,usertype,spoc_id);

    }

    public LiveData<List<AssesmentCities>> getCorporateCitiesLiveData(String authorization, String usertype, String spoc_id) {
        corporateCitiesLiveData=assesmentCityRepository.getAllCorporateCities(authorization,usertype,spoc_id);
        return corporateCitiesLiveData;
    }

    public LiveData<String> getCityConnectionError() {
        todaysConnectionError= assesmentCityRepository.getAssesmentCityConnectionError();
        return todaysConnectionError;
    }

    public void refreshTodaysBooking(String authorization, String usertype,String spoc_id)
    {
        assesmentCityRepository.getAllCorporateCities(authorization,usertype,spoc_id);
    }
}
