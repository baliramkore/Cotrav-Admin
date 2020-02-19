package com.cotrav.cotrav_admin.ui.utilities.groups;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cotrav.cotrav_admin.local_database.AdminRoomDatabase;
import com.cotrav.cotrav_admin.model.group_model.AuthenticatorResponse;
import com.cotrav.cotrav_admin.model.group_model.Group;
import com.cotrav.cotrav_admin.model.group_model.GroupResponse;
import com.cotrav.cotrav_admin.model.group_model.GroupsApiResponse;
import com.cotrav.cotrav_admin.retrofit.ConfigRetrofit;
import com.cotrav.cotrav_admin.retrofit.DeleteUtilitiesApi;
import com.cotrav.cotrav_admin.retrofit.GetUtilitiesApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupsRepository {
    private LiveData<List<GroupResponse>> entitiesDBLiveData;
    AdminRoomDatabase adminDb;
    private GetUtilitiesApi getUtilitiesApi;
    private DeleteUtilitiesApi deleteUtilitiesApi;
    private MutableLiveData<List<GroupResponse>> groupsLiveData;
    private MutableLiveData<List<Group>> groupsAuthLiveData;
    private MutableLiveData<String> groupsConnectionError;
    Application application;
    String deleteStrResponse="";

    public GroupsRepository(Application application){
        this.application = application;
        //adminDb=AdminRoomDatabase.getDatabase(application);
        getUtilitiesApi = ConfigRetrofit.configRetrofit(GetUtilitiesApi.class);
        deleteUtilitiesApi=ConfigRetrofit.configRetrofit(DeleteUtilitiesApi.class);
        groupsLiveData = new MutableLiveData<>();
        groupsAuthLiveData = new MutableLiveData<>();
        groupsConnectionError = new MutableLiveData<>();


    }
    public LiveData<List<GroupResponse>> getAllAdminGroups(String authorization, String usertype, String corporate_id)
    {
        getUtilitiesApi.getAllAdminGroups(authorization,usertype,corporate_id).enqueue(
                new Callback<GroupsApiResponse>() {

                    @Override

                    public void onResponse(Call<GroupsApiResponse> call, Response<GroupsApiResponse> response)
                    {
                        if (response.isSuccessful()){
                            if (response.body().getGroups()!=null && response.body().getGroups().size()>0) {

                                //todaysLiveData.setValue(response.body().getBookings());
                                //adminDb.daoEntity().addEnties(response.body().getEntitys());
                                groupsLiveData.setValue(response.body().getGroups());


                            }
                            else {
                                groupsConnectionError.setValue("No Group Available");
                            }
                        }else {
                            groupsConnectionError.setValue("Connection Error");
                        }
                    }

                    @Override
                    public void onFailure(Call<GroupsApiResponse> call, Throwable t)
                    {
                        groupsConnectionError.setValue("Please Check Internet Connection");

                    }
                });
        return groupsLiveData;
    }


    public LiveData<List<Group>> getAllAuthenticator(String authorization, String usertype, String group_id)
    {
        getUtilitiesApi.getAuthenticator(authorization,usertype,group_id).enqueue(
                new Callback<AuthenticatorResponse>() {
                    @Override
                    public void onResponse(Call<AuthenticatorResponse> call, Response<AuthenticatorResponse> response)
                    {
                        if (response.isSuccessful()){
                            if (response.body().getGroups()!=null && response.body().getGroups().size()>0) {

                                //todaysLiveData.setValue(response.body().getBookings());
                                //adminDb.daoEntity().addEnties(response.body().getEntitys());
                                groupsAuthLiveData.setValue(response.body().getGroups());


                            }
                            else {
                                groupsConnectionError.setValue("No Group Available");
                            }
                        }else {
                            groupsConnectionError.setValue("Connection Error");
                        }

                    }

                    @Override
                    public void onFailure(Call<AuthenticatorResponse> call, Throwable t) {

                    }
                });
        return groupsAuthLiveData;
    }




    public LiveData<List<Group>> getAllSubAuthenticator(String authorization, String usertype, String group_id)
    {
        getUtilitiesApi.getSubAuthenticator(authorization,usertype,group_id).enqueue(
                new Callback<AuthenticatorResponse>() {
                    @Override
                    public void onResponse(Call<AuthenticatorResponse> call, Response<AuthenticatorResponse> response)
                    {
                        if (response.isSuccessful()){
                            if (response.body().getGroups()!=null && response.body().getGroups().size()>0) {

                                groupsAuthLiveData.setValue(response.body().getGroups());
                            }
                            else {
                                groupsConnectionError.setValue("No Group Available");
                            }
                        }else {
                            groupsConnectionError.setValue("Connection Error");
                        }

                    }

                    @Override
                    public void onFailure(Call<AuthenticatorResponse> call, Throwable t) {
                        groupsConnectionError.setValue("Please Check Internet Connection");

                    }
                });
        return groupsAuthLiveData;
    }







    public MutableLiveData<String> getGroupsConnectionError() {
        return groupsConnectionError;
    }
}
