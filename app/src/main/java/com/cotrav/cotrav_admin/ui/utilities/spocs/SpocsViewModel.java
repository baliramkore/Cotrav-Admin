package com.cotrav.cotrav_admin.ui.utilities.spocs;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.cotrav.cotrav_admin.model.spoc_model.Spocs;

import java.util.List;

public class SpocsViewModel extends AndroidViewModel {
    private SpocsRepository spocsRepository;
    private LiveData<List<Spocs>> corporateSpocsLiveData;
    private LiveData<String>  spocsConnectionError;

    public SpocsViewModel(@NonNull Application application) {
        super(application);
        spocsRepository =new SpocsRepository(this.getApplication());
    }
    public void InitSpocsViewModel(String authorization, String usertype, String corporate_id){
        corporateSpocsLiveData=spocsRepository.getAllSpocs(authorization,usertype,corporate_id);

    }

    public LiveData<List<Spocs>> getCorporateSpocsLiveData(String authorization, String usertype, String corporate_id) {
        spocsRepository.getAllSpocs(authorization,usertype,corporate_id);
        return corporateSpocsLiveData;
    }
    public LiveData<String> getRatesConnectionError() {
        spocsConnectionError= spocsRepository.getSpocsConnectionError();
        return spocsConnectionError;
    }
    public void refreshTodaysBooking(String authorization, String usertype,String corporate_id)
    {
        spocsRepository.getAllSpocs(authorization,usertype,corporate_id);
    }
}
