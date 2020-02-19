package com.cotrav.cotrav_admin.ui.utilities.admins;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cotrav.cotrav_admin.model.admin_model.Admins;
import java.util.List;

public class AdminsViewModel extends AndroidViewModel {
    private AdminsRepository adminsRepository;
    private LiveData<List<Admins>> corporateAdminsLiveData;
    private LiveData<String>  todaysConnectionError;

    public AdminsViewModel(@NonNull Application application) {
        super(application);

        adminsRepository =new AdminsRepository(this.getApplication());


    }
    public void InitAdminsViewModel(String authorization, String usertype, String spoc_id){
        corporateAdminsLiveData=adminsRepository.getAllAdmins(authorization,usertype,spoc_id);

    }

    public LiveData<List<Admins>> getCorporateAdminsLiveData(String authorization, String usertype, String spoc_id) {
        adminsRepository.getAllAdmins(authorization,usertype,spoc_id);
        return corporateAdminsLiveData;
    }

    public LiveData<String> getAdminsConnectionError() {
        todaysConnectionError= adminsRepository.getAdminsConnectionError();
        return todaysConnectionError;
    }
    public void refreshTodaysBooking(String authorization, String usertype,String spoc_id)
    {
        adminsRepository.getAllAdmins(authorization,usertype,spoc_id);
    }
}
