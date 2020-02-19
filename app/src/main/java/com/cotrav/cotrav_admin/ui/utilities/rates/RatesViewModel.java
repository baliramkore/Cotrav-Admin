package com.cotrav.cotrav_admin.ui.utilities.rates;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cotrav.cotrav_admin.model.rates_model.CorporateRates;
import java.util.List;

public class RatesViewModel extends AndroidViewModel {

    private RatesRepository ratesRepository;
    private LiveData<List<CorporateRates>> corporateRatesLiveData;
    private LiveData<String>  entitiesConnectionError;

    public RatesViewModel(@NonNull Application application) {
        super(application);

        ratesRepository =new RatesRepository(this.getApplication());


    }
    public void InitCorporateRatesViewModel(String authorization, String usertype, String spoc_id){
        corporateRatesLiveData=ratesRepository.getAllCorporateRates(authorization,usertype,spoc_id);

    }

    public LiveData<List<CorporateRates>> getCorporateRatesLiveData(String authorization, String usertype, String spoc_id) {
        ratesRepository.getAllCorporateRates(authorization,usertype,spoc_id);
        return corporateRatesLiveData;
    }

    public LiveData<String> getRatesConnectionError() {
        entitiesConnectionError= ratesRepository.getRatesConnectionError();
        return entitiesConnectionError;
    }

    public void refreshTodaysBooking(String authorization, String usertype,String spoc_id)
    {
        ratesRepository.getAllCorporateRates(authorization,usertype,spoc_id);
    }
}
