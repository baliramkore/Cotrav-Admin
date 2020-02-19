package com.cotrav.cotrav_admin.ui.utilities.admins;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.cotrav.cotrav_admin.local_database.AdminRoomDatabase;
import com.cotrav.cotrav_admin.model.admin_model.AdminApiResponse;
import com.cotrav.cotrav_admin.model.admin_model.Admins;
import com.cotrav.cotrav_admin.retrofit.ConfigRetrofit;
import com.cotrav.cotrav_admin.retrofit.GetUtilitiesApi;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminsRepository
{
    private LiveData<List<Admins>> adminsDBLiveData;
    AdminRoomDatabase adminDb;
    private GetUtilitiesApi getUtilitiesApi;
    private MutableLiveData<List<Admins>> adminsLiveData;
    private MutableLiveData<String> adminsConnectionError;
    Application application;

    public AdminsRepository(Application application){
        this.application = application;
        getUtilitiesApi = ConfigRetrofit.configRetrofit(GetUtilitiesApi.class);

        adminsLiveData = new MutableLiveData<>();
        adminsConnectionError = new MutableLiveData<>();


    }
    public LiveData<List<Admins>> getAllAdmins(String authorization, String usertype, String corporate_id)
    {
        getUtilitiesApi.getAdmins(authorization,usertype,corporate_id).enqueue(
                new Callback<AdminApiResponse>() {

                    @Override

                    public void onResponse(Call<AdminApiResponse> call, Response<AdminApiResponse> response)
                    {
                        if (response.isSuccessful()){
                            if (response.body().getAdmins()!=null && response.body().getAdmins().size()>0) {

                                adminsLiveData.setValue(response.body().getAdmins());


                            }
                            else {
                                adminsConnectionError.setValue("No Admin Available");
                            }
                        }else {
                            adminsConnectionError.setValue("Connection Error");
                        }
                    }

                    @Override
                    public void onFailure(Call<AdminApiResponse> call, Throwable t)
                    {
                        adminsConnectionError.setValue("Please Check Internet Connection");
                    }
                });
        return adminsLiveData;
    }


    public MutableLiveData<String> getAdminsConnectionError() {
        return adminsConnectionError;
    }
}
