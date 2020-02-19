package com.cotrav.cotrav_admin.ui.utilities.entities;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.cotrav.cotrav_admin.model.entities_model.Entities;
import java.util.List;

public class EntitiesViewModel extends AndroidViewModel
{
    private EntityRepository entityRepository;
    private LiveData<List<Entities>> entitiesLiveData;
    private LiveData<String>  entitiesConnectionError;

    public EntitiesViewModel(@NonNull Application application) {
        super(application);

        entityRepository =new EntityRepository(this.getApplication());


    }
    public void InitEntitiesViewModel(String authorization, String usertype, String corporateId){
        entitiesLiveData=entityRepository.getAllEntities(authorization,usertype,corporateId);

    }

    public LiveData<List<Entities>> getEntitiesLiveData(String authorization, String usertype, String spoc_id) {
        entityRepository.getAllEntities(authorization,usertype,spoc_id);
        return entitiesLiveData;
    }

    public LiveData<String> getEntitiesConnectionError() {
        entitiesConnectionError= entityRepository.getEntitiesConnectionError();
         return entitiesConnectionError;
    }

    public void refreshEntities(String authorization, String usertype,String spoc_id)
    {
        entityRepository.getAllEntities(authorization,usertype,spoc_id);
    }
}
