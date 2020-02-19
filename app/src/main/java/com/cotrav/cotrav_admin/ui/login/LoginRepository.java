package com.cotrav.cotrav_admin.ui.login;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.cotrav.cotrav_admin.GsonStringConvertor;
import com.cotrav.cotrav_admin.model.login_model.LoginApiResponse;
import com.cotrav.cotrav_admin.model.login_model.User;
import com.cotrav.cotrav_admin.retrofit.ConfigRetrofit;
import com.cotrav.cotrav_admin.retrofit.LoginAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class LoginRepository
{


        private final String TAG="LoginRepositoty";
        private LoginAPI loginAPI;
        private SharedPreferences userPreference,spocPref;
        private MutableLiveData<String> access_token;
        private MutableLiveData<String> access_OTP;

        private MutableLiveData<List<User>> userMutableLiveData;
        private boolean is_login;
        Application application;
        Context context;
        private MutableLiveData<String> error;
        private MutableLiveData<String> verification_code;
        private MutableLiveData<String> loginSuccessful;
        private MutableLiveData<String> verificationSuccessful;

    public LoginRepository(Application application) {
        this.application=application;
        userPreference=application.getSharedPreferences("login_info", MODE_PRIVATE);
        spocPref = application.getSharedPreferences("spoc_info",MODE_PRIVATE);
        loginAPI= ConfigRetrofit.configRetrofit(LoginAPI.class);
        is_login=userPreference.getBoolean("is_login",false);
        access_token=new MutableLiveData<>();
        access_OTP=new MutableLiveData<>();
        userMutableLiveData=new MutableLiveData<>();
        verification_code=new MutableLiveData<>();
        loginSuccessful=new MutableLiveData<>();
        verificationSuccessful=new MutableLiveData<>();

        error=new MutableLiveData<>();
        Log.d(TAG,Boolean.toString(is_login));
        Log.d(TAG,userPreference.getString("login_info","n"));
    }

        public boolean isLogin(){
        Log.d(TAG,Boolean.toString(userPreference.getBoolean("is_login",false)));
        return userPreference.getBoolean("is_login",false);
    }
        public MutableLiveData<List<User>> getEmployeeInfo(){
        return userMutableLiveData;
    }

        public MutableLiveData<String> getAccessToken(){
        return  access_token;
    }

        public MutableLiveData<String> getAccessOTP(){
        return  access_OTP;
    }

        public MutableLiveData<String> getLoginSuccessful() {
        return loginSuccessful;
    }
        public MutableLiveData<String> getError() {
        return error;
    }


        public void performLogin(String username,String password)
        {

            loginAPI.performLogin(username,password,"1").enqueue(new Callback<LoginApiResponse>() {
                @Override
                public void onResponse(Call<LoginApiResponse> call, Response<LoginApiResponse> response) {
                    if (response.isSuccessful())
                    {
                        if (response.body().getUser()!=null)
                        {

                            access_token.setValue(response.body().getAccessToken());
                            userMutableLiveData.setValue(response.body().getUser());
                            loginSuccessful.setValue("Otp sent Successfully");
                            verification_code.setValue(response.body().getOTP());
                            access_OTP.setValue(response.body().getOTP());
                            SharedPreferences.Editor editor=userPreference.edit();
                            editor.putString("user_name",response.body().getUser().get(0).getName());
                            editor.putString("emp_id",response.body().getUser().get(0).getId());
                            editor.putString("email",response.body().getUser().get(0).getEmail());
                            editor.putString("access_token",access_token.getValue());
                            editor.putString("user_contact", response.body().getUser().get(0).getContactNo());
                            editor.putString("corporate_id", response.body().getUser().get(0).getId());
                            editor.putBoolean("is_login",false);
                            editor.putString("login_info", GsonStringConvertor.gsonToString(userMutableLiveData.getValue()));
                            editor.commit();
                            error.setValue("1");
                            // Toast.makeText(LoginActivity.this, response.body().response.accessToken+"..."+response.body().success, Toast.LENGTH_LONG).show();
                            Log.d(TAG, response.body().getSuccess());
                        }
                        if (response.body().getSuccess().equals("0")){
                            error.setValue(response.body().getMessage());
                            Log.d(TAG,".."+response.body().getMessage());
                        }
                        if (response.body().getSuccess().equals("0")){
                            SharedPreferences.Editor editor=userPreference.edit();
                            editor.putBoolean("is_login",false);
                            editor.putString("access_token"," ");
                            editor.commit();
                            error.setValue("0");
                            Log.d(TAG,".."+response.body().getMessage());
                        }
                    }else {
                        error.setValue("Connection Unsuccessful");
                        Log.e(TAG,"Connection Unsuccessful");
                    }
                }

                @Override
                public void onFailure(Call<LoginApiResponse> call, Throwable t) {

                    Log.e(TAG,"Connection failed");
                    error.setValue("Connection Failed");
                }
            });
        }
        public void reSetText(){
        Log.d("Vcode","s " +verification_code.getValue());

        loginSuccessful.setValue("RESET_DATA");

    }
        public void validateVerificationCode(String vCode){
        Log.d("Vcode","s " +verification_code.getValue());
        if (vCode.equals(verification_code.getValue())){
            loginSuccessful.setValue("Successful");
             error.setValue("1");
            SharedPreferences.Editor editor=userPreference.edit();
            editor.putString("access_token",access_token.getValue());
            editor.putBoolean("is_login",true);
            editor.putString("usertype","1");
            //editor.putString("Authorization","Token "+userMutableLiveData.getValue().get(0).;
            editor.putString("user_name",userMutableLiveData.getValue().get(0).getName());
            editor.putString("user_id",userMutableLiveData.getValue().get(0).getId().toString());
            editor.putString("admin_id",userMutableLiveData.getValue().get(0).getId().toString());
            editor.putString("corporate_id", userMutableLiveData.getValue().get(0).getCorporateId().toString());
            editor.putString("email",userMutableLiveData.getValue().get(0).getEmail());
            editor.putString("user_contact",userMutableLiveData.getValue().get(0).getContactNo());
            editor.putString("access_token",access_token.getValue());
            editor.putString("is_local", userMutableLiveData.getValue().get(0).getIsLocal().toString());
            editor.putString("is_radio", userMutableLiveData.getValue().get(0).getIsRadio().toString());
            editor.putString("is_outstation",userMutableLiveData.getValue().get(0).getIsOutstation().toString());
            editor.putString("is_bus", userMutableLiveData.getValue().get(0).getIsLocal().toString());
            editor.putString("is_train", userMutableLiveData.getValue().get(0).getIsLocal().toString());
            editor.putString("is_hotel", userMutableLiveData.getValue().get(0).getIsLocal().toString());
            editor.putString("is_flight", userMutableLiveData.getValue().get(0).getIsLocal().toString());
            editor.putString("is_created",userMutableLiveData.getValue().get(0).getCreated().toString());
            editor.putString("is_created",userMutableLiveData.getValue().get(0).getIsDeleted().toString());
            editor.putString("is_reverse",userMutableLiveData.getValue().get(0).getIsReverseLogistics().toString());
            editor.putString("employee_info",GsonStringConvertor.gsonToString(userMutableLiveData.getValue()));
            editor.commit();
            verificationSuccessful.setValue("verified");
        }else {
            error.setValue("Verification Unsuccessful \n Please Check Email"+" "+verification_code.getValue());
        }
    }
}
