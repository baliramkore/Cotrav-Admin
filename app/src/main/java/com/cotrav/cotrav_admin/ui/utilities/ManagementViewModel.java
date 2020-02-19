package com.cotrav.cotrav_admin.ui.utilities;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ManagementViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ManagementViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Management fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }


}