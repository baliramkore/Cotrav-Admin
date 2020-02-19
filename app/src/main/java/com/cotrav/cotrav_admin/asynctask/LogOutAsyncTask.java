package com.cotrav.cotrav_admin.asynctask;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.cotrav.cotrav_admin.R;
import com.cotrav.cotrav_admin.local_database.AdminRoomDatabase;
import com.cotrav.cotrav_admin.retrofit.APIurls;
import com.cotrav.cotrav_admin.ui.login.LoginActivity;

import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LogOutAsyncTask extends AsyncTask<Activity,Integer,String> {

    String apiUrl = APIurls.LOGOUT;
    OkHttpClient client = new OkHttpClient();
    Response response;
    Activity activity;
    String token;
    ProgressDialog pd;
    Dialog dialog;
    AdminRoomDatabase adminRoomDatabase;


    public LogOutAsyncTask(Activity activity, String token) {
        this.activity = activity;
        this.token=token;
    }

    @Override
    protected String doInBackground(Activity... params) {

        adminRoomDatabase = AdminRoomDatabase.getDatabase(activity);
        Request.Builder request = new Request.Builder();
        request.url(apiUrl)
                .addHeader("Authorization","Token "+token)
                .addHeader("usertype","1")
                .build();
        try {
            response = client.newCall(request.build()).execute();
            if (response.code()==200) {
                String value=response.body().string();
                return value;
            }
            else {
                return "Network Error";
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Network Error";

    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd=new ProgressDialog(activity);
        pd.setMessage("Logging Out");
        pd.show();
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialog_box);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView mDialogmsg = dialog.findViewById(R.id.dialog_message);
        mDialogmsg.setText("Logout failed");

        TextView mDialogtitle = dialog.findViewById(R.id.dialog_title);
        mDialogtitle.setText("CONNECTION ERROR");

        Button myes = dialog.findViewById(R.id.yes_txt);
        myes.setText("OK");
        Button mNo = dialog.findViewById(R.id.no_txt);
        mNo.setVisibility(View.GONE);

        mNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        myes.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();

                                    }
                                }
        );
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
       pd.dismiss();
        if (response!=null)
        {
            response.close();
        }
        if (s.equals("Network Error")){
            dialog.show();
        }
        else {
            adminRoomDatabase.daoTaxi().deleteTabled();
            adminRoomDatabase.daoBus().deleteTabled();
            adminRoomDatabase.daoHotel().deleteTabled();
            adminRoomDatabase.daoTrain().deleteTabled();
            adminRoomDatabase.daoFlight().deleteTabled();
            adminRoomDatabase.dashBoardDao().deleteDashboard();

            Log.d("Logout","Logging out");
            SharedPreferences loginpref=activity.getSharedPreferences("login_info",activity.MODE_PRIVATE);
            SharedPreferences employeepref=activity.getSharedPreferences("employeeinfo", activity.MODE_PRIVATE);
            SharedPreferences assesmentpref=activity.getSharedPreferences("assesmentinfo", activity.MODE_PRIVATE);
            SharedPreferences  taxitypepref=activity.getSharedPreferences("taxitypeinfo",activity.MODE_PRIVATE);
            SharedPreferences placepref=activity.getSharedPreferences("placesinfo",activity.MODE_PRIVATE);
            SharedPreferences citiesPref=activity.getSharedPreferences("cities_info",activity.MODE_PRIVATE);
            SharedPreferences assessmentCitiesPref=activity.getSharedPreferences("assessment_cities", activity.MODE_PRIVATE);
            SharedPreferences fcmPreference=activity.getSharedPreferences("fcmTokenPref", Context.MODE_PRIVATE);
            SharedPreferences dashboardInfo = activity.getSharedPreferences("dashboard_info",activity.MODE_PRIVATE);
            SharedPreferences settingInfo = activity.getSharedPreferences("settings_info",activity.MODE_PRIVATE);
            SharedPreferences.Editor editor=loginpref.edit();
            editor.clear().commit();

            editor=employeepref.edit();
            editor.clear().commit();
            editor=assesmentpref.edit();
            editor.clear().commit();
            editor=taxitypepref.edit();
            editor.clear().commit();
            editor=placepref.edit();
            editor.clear().commit();
            editor=citiesPref.edit();
            editor.clear().commit();

            editor=assessmentCitiesPref.edit();
            editor.clear().commit();

            editor=fcmPreference.edit();
            editor.clear().commit();

            editor=dashboardInfo.edit();
            editor.clear().commit();

            editor=settingInfo.edit();
            editor.clear().commit();

            activity.finishAffinity();

            Intent intent=new Intent(activity, LoginActivity.class);
            activity.startActivity(intent);
            activity.finish();

        }
    }

}
