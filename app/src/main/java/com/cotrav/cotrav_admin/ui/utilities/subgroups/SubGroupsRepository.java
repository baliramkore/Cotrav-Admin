package com.cotrav.cotrav_admin.ui.utilities.subgroups;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cotrav.cotrav_admin.local_database.AdminRoomDatabase;
import com.cotrav.cotrav_admin.model.subgroup_model.SubGroupApiResponse;
import com.cotrav.cotrav_admin.model.subgroup_model.Subgroup;
import com.cotrav.cotrav_admin.retrofit.ConfigRetrofit;
import com.cotrav.cotrav_admin.retrofit.DeleteUtilitiesApi;
import com.cotrav.cotrav_admin.retrofit.GetUtilitiesApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubGroupsRepository {
    private LiveData<List<Subgroup>> subgroupDBLiveData;
    AdminRoomDatabase adminDb;
    private GetUtilitiesApi getUtilitiesApi;
    private DeleteUtilitiesApi deleteUtilitiesApi;
    private MutableLiveData<List<Subgroup>> groupsLiveData;
    private MutableLiveData<String> subGroupConnectionError;
    Application application;
    String deleteStrResponse="";

    public SubGroupsRepository(Application application){
        this.application = application;
        //adminDb=AdminRoomDatabase.getDatabase(application);
        getUtilitiesApi = ConfigRetrofit.configRetrofit(GetUtilitiesApi.class);
        deleteUtilitiesApi=ConfigRetrofit.configRetrofit(DeleteUtilitiesApi.class);
        groupsLiveData = new MutableLiveData<>();
        subGroupConnectionError = new MutableLiveData<>();


    }
    public LiveData<List<Subgroup>> getAllAdminSubGroups(String authorization, String usertype, String corporate_id)
    {
        getUtilitiesApi.getAllAdminSubGroups(authorization,usertype,corporate_id).enqueue(
                new Callback<SubGroupApiResponse>() {

                    @Override
                    public void onResponse(Call<SubGroupApiResponse> call, Response<SubGroupApiResponse> response)
                    {
                        if (response.isSuccessful()){
                            if (response.body().getSubgroups()!=null && response.body().getSubgroups().size()>0) {

                                groupsLiveData.setValue(response.body().getSubgroups());


                            }
                            else {
                                subGroupConnectionError.setValue("No Sub Group Available");
                            }
                        }else {
                            subGroupConnectionError.setValue("Connection Error");
                        }
                    }

                    @Override
                    public void onFailure(Call<SubGroupApiResponse> call, Throwable t)
                    {
                        subGroupConnectionError.setValue("Please Check Internet Connection");
                    }
                });
        return groupsLiveData;
    }


    public MutableLiveData<String> getSubGroupConnectionError() {
        return subGroupConnectionError;
    }
}
