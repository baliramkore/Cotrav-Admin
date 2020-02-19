package com.cotrav.cotrav_admin.ui.utilities.addcode;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cotrav.cotrav_admin.model.assesmentcode_model.AssesmentCodes;

import java.util.List;

public class AssesmentCodeViewModel  extends AndroidViewModel {


    private AssesmentCodeRepository assesmentCodeRepository;
    private LiveData<List<AssesmentCodes>> corporateCodesLiveData;
    private LiveData<String> todaysConnectionError;

    public AssesmentCodeViewModel(@NonNull Application application) {
        super(application);

        assesmentCodeRepository =new AssesmentCodeRepository(this.getApplication());


    }
    public void InitCorporateCodesViewModel(String authorization, String usertype, String corporate_id){
        //corporateCodesLiveData=assesmentCodeRepository.getAllCorporateCodes(authorization,usertype,corporate_id);
        corporateCodesLiveData=assesmentCodeRepository.getAllCorporateCodes(authorization,usertype,corporate_id);
    }

    public LiveData<List<AssesmentCodes>> getCorporateCodesLiveData(String authorization, String usertype, String corporate_id) {
        corporateCodesLiveData=assesmentCodeRepository.getAllCorporateCodes(authorization,usertype,corporate_id);
        return corporateCodesLiveData;
    }

    public LiveData<String> getAssesmentCodeConnectionError() {
        todaysConnectionError= assesmentCodeRepository.getassesmentCodeConnectionError();
        return todaysConnectionError;
    }


    public void refreshAssesmentCode(String authorization, String usertype,String corporate_id)
    {
        corporateCodesLiveData= assesmentCodeRepository.getAllCorporateCodes(authorization,usertype,corporate_id);
    }

}
