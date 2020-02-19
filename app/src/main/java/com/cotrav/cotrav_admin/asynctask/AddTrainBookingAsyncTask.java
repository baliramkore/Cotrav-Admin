package com.cotrav.cotrav_admin.asynctask;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.cotrav.cotrav_admin.R;
import com.cotrav.cotrav_admin.retrofit.APIurls;
import com.cotrav.cotrav_admin.retrofit.ConfigRetrofit;
import com.cotrav.cotrav_admin.retrofit.TrainBookingAPI;
import com.cotrav.cotrav_admin.show_booking.ShowTrainBookingActivity;
import com.cotrav.cotrav_admin.ui.home.bus.AddBusBookingActivity;
import com.cotrav.cotrav_admin.ui.home.train.AddTrainBookingActivity;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;

public class AddTrainBookingAsyncTask extends AsyncTask<Activity,Integer,String> {

    String apiURL = APIurls.addTrainBooking;
    OkHttpClient client = new OkHttpClient();
    Response response;
    ArrayList<String> employees;
    private String assesmentvalue;
    Activity activity;
    TrainBookingAPI addTrainBookingAPI;
    String empId,corporateId,spocId , groupId, subgroupId,pickUpCity, dropCity,selectedDateTime
            ,booking_datetime,entity_id,assessment_code,assessment_city_id
            ,trainType,bookingReason,prefTrain,token,selectedDateTimeTo;

    ProgressDialog pd;
   // AlertDialog.Builder dialog,sucessDialog,errDialog;
    Dialog dialog,sucessDialog,errDialog;

    public AddTrainBookingAsyncTask(Activity activity, String token, String empId, String corporateId,
                                    String spocId,
                                    String groupId, String subgroupId,
                                    String selectedDateTime, String selectedDateTimeTo,
                                    String pickUpCity, String dropCity,
                                    String booking_datetime,
                                    String entity_id, String assessment_code, String assessment_city_id,
                                    String trainType, String  bookingReason, ArrayList<String> employees, String prefTrain) {
        this.activity = activity;
        this.token=token;
        this.empId = empId;
        this.corporateId = corporateId;
        this.spocId = spocId;
        this.groupId = groupId;
        this.subgroupId = subgroupId;
        this.selectedDateTime = selectedDateTime;
        this.selectedDateTimeTo = selectedDateTimeTo;
        this.pickUpCity=pickUpCity;
        this.dropCity=dropCity;
        this.booking_datetime=booking_datetime;
        this.entity_id=entity_id;
        this.assessment_code = assessment_code;
        this.assessment_city_id=assessment_city_id;
        this.trainType=trainType;
        this.bookingReason=bookingReason;
        this.prefTrain=prefTrain;
        this.employees=employees;
    }

    @Override
    protected String doInBackground(Activity... params) {
        final AlertDialog.Builder[] d = new AlertDialog.Builder[1];
        Log.d("posting data", params.toString());
        //System Date Added From Mobile
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String formattedDate = df.format(c);
        booking_datetime = formattedDate;
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        addTrainBookingAPI = ConfigRetrofit.configRetrofit(TrainBookingAPI.class);
        addTrainBookingAPI.addTrainBooking("Token "+token,"1",empId,
                corporateId,spocId,groupId,subgroupId,pickUpCity
                ,dropCity,booking_datetime,selectedDateTime,selectedDateTimeTo
                ,entity_id,prefTrain,assessment_code,assessment_city_id,bookingReason,"1",employees,trainType


        ).enqueue(new Callback<AddBusBookingActivity.Responce>() {
            @Override
            public void onResponse(Call<AddBusBookingActivity.Responce> call, retrofit2.Response<AddBusBookingActivity.Responce> response) {
                if (response.code() == 200) {
                    assesmentvalue = response.body().toString();
                    Toast.makeText(activity, "Booking Added :" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    final Dialog dialog = new Dialog(activity);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.custom_dialog_box);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                    TextView mDialogTitile = dialog.findViewById(R.id.dialog_title);
                    TextView mDialogmsg = dialog.findViewById(R.id.dialog_message);

                    mDialogTitile.setText("Booking Status");
                    mDialogmsg.setText("" + response.body().getMessage());

                    Button myes = dialog.findViewById(R.id.yes_txt);
                    Button mNo = dialog.findViewById(R.id.no_txt);
                    mNo.setVisibility(View.GONE);
                    myes.setText("  Ok  ");
                    myes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(activity, ShowTrainBookingActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            activity.startActivity(intent);
                            dialog.dismiss();
                        }
                    });


                    dialog.show();
                }
            }
            @Override
            public void onFailure(Call<AddBusBookingActivity.Responce> call, Throwable t) {

            }
        });

        return assesmentvalue;

    }
    android.app.AlertDialog.Builder d;
    @Override
    protected void onPreExecute() {
        {
            super.onPreExecute();
            Log.d("sucess", "Done");
            pd = new ProgressDialog(activity);
            pd.setMessage("completing booking");
            d = new android.app.AlertDialog.Builder(activity);
            d.setTitle("Train BOOKING");
            pd.show();
        }

    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        pd.dismiss();

    }
}
