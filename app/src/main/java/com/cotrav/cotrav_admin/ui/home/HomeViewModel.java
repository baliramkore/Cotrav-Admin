package com.cotrav.cotrav_admin.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.cotrav.cotrav_admin.model.home_model.DashBoardData;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {
    HomeRepository dashBoardRepository;
    LiveData<List<DashBoardData>> dashBoard;


    public HomeViewModel(@NonNull Application application) {
        super(application);
        dashBoardRepository=new HomeRepository(application);

    }

    public void initViewModelDashBoard(String Authorization, String usertype,String spoc_id){

        dashBoard = dashBoardRepository.getDashBoardLiveData(Authorization, usertype,spoc_id);

    }

    public LiveData<List<DashBoardData>> getDashBoardData(String Authorization, String usertype,String spoc_id) {

        if (dashBoard==null)
        {
            dashBoard = dashBoardRepository.getDashBoardLiveData(Authorization, usertype,spoc_id);

        }
        return dashBoard;
    }
}