package com.cotrav.cotrav_admin.model.login_model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cotrav.cotrav_admin.ui.login.LoginRepository;

import java.util.List;

public class UserViewModel extends AndroidViewModel
{
    private LiveData<List<User>> userLiveData;
    private LiveData<String> accessToken;
    private LiveData<String> accessOTP;
    private LiveData<String> error;
    private LiveData<String> loginSuccessful;
    private LoginRepository loginRepositoty;

    public UserViewModel(Application application) {
        super(application);
        loginRepositoty=new LoginRepository(this.getApplication());
        userLiveData=loginRepositoty.getEmployeeInfo();
        accessToken =loginRepositoty.getAccessToken();
        accessOTP =loginRepositoty.getAccessOTP();

        error=loginRepositoty.getError();
        loginSuccessful=loginRepositoty.getLoginSuccessful();
    }
    public LiveData<String> getAccessToken() {
        return accessToken;
    }
    public void verifyCode(String code){
        loginRepositoty.validateVerificationCode(code);
    }
    public Boolean isLogin() {
        return loginRepositoty.isLogin();
    }
    public void reSetText(){
        loginRepositoty.reSetText();
    }

    public LiveData<String> getError() {
        return error;
    }

    public LiveData<String> getLoginSuccessful() {
        return loginSuccessful;
    }


    public void performLogin(String username,String password){
        loginRepositoty.performLogin(username,password);
    }
}
