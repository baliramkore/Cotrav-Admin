package com.cotrav.cotrav_admin.ui.utilities.subgroups;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cotrav.cotrav_admin.model.subgroup_model.Subgroup;

import java.util.List;

public class SubGroupsViewModel extends AndroidViewModel {


    private SubGroupsRepository subGroupsRepository;
    private LiveData<List<Subgroup>> corporateCodesLiveData;
    private LiveData<String> subGroupsConnectionError;

    public SubGroupsViewModel(@NonNull Application application) {
        super(application);
        subGroupsRepository =new SubGroupsRepository(this.getApplication());


    }
    public void InitAdminSubGroupsViewModel(String authorization, String usertype, String corporate_id){
        corporateCodesLiveData=subGroupsRepository.getAllAdminSubGroups(authorization,usertype,corporate_id);

    }

    public LiveData<List<Subgroup>> getAdminSubGroupsLiveData(String authorization, String usertype, String corporate_id) {
        subGroupsRepository.getAllAdminSubGroups(authorization,usertype,corporate_id);
        return corporateCodesLiveData;
    }

    public LiveData<String> getRatesConnectionError() {
        subGroupsConnectionError= subGroupsRepository.getSubGroupConnectionError();
        return subGroupsConnectionError;
    }
    public void refreshAdminSubGroups(String authorization, String usertype,String corporate_id)
    {
        subGroupsRepository.getAllAdminSubGroups(authorization,usertype,corporate_id);
    }

}
