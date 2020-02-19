package com.cotrav.cotrav_admin.ui.home.taxi;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cotrav.cotrav_admin.model.taxi_types_model.TaxiType;
import com.cotrav.cotrav_admin.ui.home.HomeRepository;

import java.util.List;

public class TaxiTypeViewModel extends AndroidViewModel {

    private LiveData<List<TaxiType>> taxiTypeList;
    HomeRepository taxiTypeRepository;

    public TaxiTypeViewModel(Application application) {
        super(application);
        taxiTypeRepository=new HomeRepository(application);
    }

    public void initTaxiType(String Authorization, String usertype)
    {
        taxiTypeList=taxiTypeRepository.getTaxiType(Authorization,usertype);

    }

    public LiveData<List<TaxiType>> getTaxiTypeInfo(String Authorization, String usertype) {
        if (taxiTypeList==null)
        {
            taxiTypeList=taxiTypeRepository.getTaxiType(Authorization,usertype);

        }
        return taxiTypeList;
    }
}
