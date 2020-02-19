package com.cotrav.cotrav_admin.ui.utilities.entities;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cotrav.cotrav_admin.local_database.AdminRoomDatabase;
import com.cotrav.cotrav_admin.model.entities_model.Entities;
import com.cotrav.cotrav_admin.model.entities_model.EntityApiResponse;
import com.cotrav.cotrav_admin.retrofit.ConfigRetrofit;
import com.cotrav.cotrav_admin.retrofit.DeleteUtilitiesApi;
import com.cotrav.cotrav_admin.retrofit.GetUtilitiesApi;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EntityRepository
{
    private LiveData<List<Entities>> entitiesDBLiveData;
    AdminRoomDatabase adminDb;
    private GetUtilitiesApi getUtilitiesApi;
    private DeleteUtilitiesApi deleteUtilitiesApi;
    private MutableLiveData<List<Entities>> entitiesLiveData;
    private MutableLiveData<String> entitiesConnectionError;
    Application application;
    String deleteStrResponse="";

    public EntityRepository(Application application){
        this.application = application;
        //adminDb=AdminRoomDatabase.getDatabase(application);
        getUtilitiesApi = ConfigRetrofit.configRetrofit(GetUtilitiesApi.class);
        deleteUtilitiesApi=ConfigRetrofit.configRetrofit(DeleteUtilitiesApi.class);
        entitiesLiveData = new MutableLiveData<>();
        entitiesConnectionError = new MutableLiveData<>();
    }
    public LiveData<List<Entities>> getAllEntities(String authorization, String usertype, String corporate_id)
    {
    getUtilitiesApi.getBillingEntities(authorization,usertype,corporate_id).enqueue(
            new Callback<EntityApiResponse>() {

                @Override

                public void onResponse(Call<EntityApiResponse> call, Response<EntityApiResponse> response)
                {
                    if (response.isSuccessful()){
                        if (response.body().getEntitys()!=null && response.body().getEntitys().size()>0) {

                            entitiesLiveData.setValue(response.body().getEntitys());
                        }
                        else {
                            entitiesConnectionError.setValue("No Entity Available");
                        }
                    }else {
                        entitiesConnectionError.setValue("Connection Error");
                    }
                }

                @Override
                public void onFailure(Call<EntityApiResponse> call, Throwable t)
                {
                    entitiesConnectionError.setValue("Please Check Internet Connection");
                }
            });
    return entitiesLiveData;
}


    public MutableLiveData<String> getEntitiesConnectionError() {
        return entitiesConnectionError;
    }
}
