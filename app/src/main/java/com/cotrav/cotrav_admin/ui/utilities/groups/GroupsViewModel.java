package com.cotrav.cotrav_admin.ui.utilities.groups;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cotrav.cotrav_admin.model.group_model.AuthenticatorResponse;
import com.cotrav.cotrav_admin.model.group_model.Group;
import com.cotrav.cotrav_admin.model.group_model.GroupResponse;
import java.util.List;

public class GroupsViewModel extends AndroidViewModel {


    private GroupsRepository adminGroupsRepository;
    private LiveData<List<GroupResponse>> corporateGroupsLiveData;
    private LiveData<List<Group>> corporateAuthLiveData;
    private LiveData<List<Group>> corporateSubAuthLiveData;
    private LiveData<String> todaysConnectionError;

    public GroupsViewModel(@NonNull Application application) {
        super(application);

        adminGroupsRepository =new GroupsRepository(this.getApplication());


    }
    public void InitAdminGroupsViewModel(String authorization, String usertype, String corporate_id){
        corporateGroupsLiveData=adminGroupsRepository.getAllAdminGroups(authorization,usertype,corporate_id);

    }



    public LiveData<List<GroupResponse>> getAdminGroupsLiveData(String authorization, String usertype, String corporate_id) {
        adminGroupsRepository.getAllAdminGroups(authorization,usertype,corporate_id);
        return corporateGroupsLiveData;
    }

    public void InitAdminAuthViewModel(String authorization, String usertype, String groupId){
        corporateAuthLiveData=adminGroupsRepository.getAllAuthenticator(authorization,usertype,groupId);

    }



    public LiveData<List<Group>> getAdminAuthLiveData(String authorization, String usertype, String groupId) {
        adminGroupsRepository.getAllAuthenticator(authorization,usertype,groupId);
        return corporateAuthLiveData;
    }


    public void InitAdminSubAuthViewModel(String authorization, String usertype, String groupId){
        corporateSubAuthLiveData=adminGroupsRepository.getAllSubAuthenticator(authorization,usertype,groupId);

    }



    public LiveData<List<Group>> getAdminSubAuthLiveData(String authorization, String usertype, String groupId) {
        adminGroupsRepository.getAllSubAuthenticator(authorization,usertype,groupId);
        return corporateSubAuthLiveData;
    }
    public LiveData<String> getGroupsConnectionError() {
        todaysConnectionError= adminGroupsRepository.getGroupsConnectionError();
        return todaysConnectionError;
    }


    public void refreshAdminGroups(String authorization, String usertype,String corporate_id)
    {
        adminGroupsRepository.getAllAdminGroups(authorization,usertype,corporate_id);
    }
    public void refreshAdminAuthGroups(String authorization, String usertype,String corporate_id)
    {
        adminGroupsRepository.getAllAuthenticator(authorization,usertype,corporate_id);
    }

}
