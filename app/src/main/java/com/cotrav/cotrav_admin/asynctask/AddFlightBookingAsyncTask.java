package com.cotrav.cotrav_admin.asynctask;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.cotrav.cotrav_admin.R;
import com.cotrav.cotrav_admin.retrofit.APIurls;
import com.cotrav.cotrav_admin.show_booking.ShowFlightBookingActivity;
import com.cotrav.cotrav_admin.ui.home.flight.AddFlightBookingActivity;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddFlightBookingAsyncTask extends AsyncTask<Activity,Integer,String> {

    String apiURL = APIurls.addFlightBooking;
    OkHttpClient client = new OkHttpClient();
    Response response;

    Activity activity;
    private  String empId,corporateId,spocId , groupId, subgroupId,from_city, to_city,selectedDateTime
            ,booking_datetime,entity_id,assessment_code,assessment_city_id
            ,bookingReason,prefFlight,token,usage_type,seat_type,trip_type;

    ProgressDialog pd;
    Dialog sucessDialog,errDialog;

    Dialog dialog;
    public AddFlightBookingAsyncTask(Activity activity, String token, String empId, String corporateId,
                                     String spocId,
                                     String groupId, String subgroupId,
                                     String selectedDateTime, String booking_datetime,
                                     String from_city, String to_city,
                                     String entity_id, String assessment_code, String assessment_city_id, String usage_type,
                                     String seat_type, String  bookingReason, String prefFlight,
                                     String trip_type) {
        this.activity = activity;
        this.token=token;
        this.empId = empId;
        this.corporateId = corporateId;
        this.spocId = spocId;
        this.groupId = groupId;
        this.subgroupId = subgroupId;
        this.selectedDateTime = selectedDateTime;
        this.from_city=from_city;
        this.to_city=to_city;
        this.seat_type=seat_type;
        this.booking_datetime=booking_datetime;
        this.entity_id=entity_id;
        this.assessment_code = assessment_code;
        this.assessment_city_id=assessment_city_id;
        this.usage_type=usage_type;
        this.bookingReason=bookingReason;
        this.prefFlight=prefFlight;
        this.trip_type=trip_type;
//        this.packageName=packageName;
//        this.instructions=instructions;
    }

    @Override
    protected String doInBackground(Activity... params) {
        RequestBody formBody = new FormBody.Builder()
                .add("user_id",empId)
                .add("corporate_id", corporateId)
                .add("spoc_id",spocId)
                .add("group_id",groupId)
                .add("subgroup_id",subgroupId)
                .add("usage_type",usage_type)
                .add("trip_type",trip_type)
                .add("seat_type",seat_type)
                .add("from_city",from_city)
                .add("to_city",to_city)
                .add("booking_datetime",booking_datetime)
                .add("departure_datetime",selectedDateTime)
                .add("billing_entity_id",entity_id)
                .add("preferred_flight",prefFlight)
                .add("assessment_code",assessment_code)
                .add("assessment_city_id",assessment_city_id)
                .add("reason_booking",bookingReason)
                .add("no_of_seats","1")
                .add("employees",empId)

                .build();

        Request.Builder request = new Request.Builder();
        request.url(apiURL)
                .post(formBody)
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
        pd = new ProgressDialog(activity);
        pd.setMessage("Adding Booking");
        pd.show();

        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialog_box);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView mDialogmsg = dialog.findViewById(R.id.dialog_message);
        mDialogmsg.setText("Something went worng ");

        TextView mDialogtitle = dialog.findViewById(R.id.dialog_title);
        mDialogtitle.setText("CONNECTION ERROR");

        Button myes = dialog.findViewById(R.id.yes_txt);
        myes.setText("OK");
        Button mNo = dialog.findViewById(R.id.no_txt);
        mNo.setVisibility(View.GONE);

        myes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


/*
        dialog = new AlertDialog.Builder(activity);
        dialog.setTitle("CONNECTION ERROR");
        dialog.setMessage("Something went worng, ");
        dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });*/


    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        pd.dismiss();


        if (response != null) {
            response.close();
        }
        if (s.equals("Network Error")) {
            dialog.show();
        } else {


            JSONObject json = (JSONObject) JSONValue.parse(s);
        Log.d("addBooking","MSG "+s);
            if (json.get("success").toString().equals("1")) {
                Log.d("addBooking","msg"+json.get("message").toString());


                sucessDialog = new Dialog(activity);
                sucessDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                sucessDialog.setCancelable(false);
                sucessDialog.setContentView(R.layout.custom_dialog_box);
                sucessDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                TextView mDialogmsg = sucessDialog.findViewById(R.id.dialog_message);
                mDialogmsg.setText("Booking " +json.get("message").toString());

                TextView mDialogtitle = sucessDialog.findViewById(R.id.dialog_title);
                mDialogtitle.setText("Success");

                Button myes = sucessDialog.findViewById(R.id.yes_txt);
                myes.setText("Okey");
                Button mNo = sucessDialog.findViewById(R.id.no_txt);
                mNo.setVisibility(View.GONE);


                myes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(activity, ShowFlightBookingActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.startActivity(intent);
                        sucessDialog.dismiss();
                    }
                });
                sucessDialog.show();

            }
            else {
            if (json.get("success").toString().equals("0")) {
                Log.d("addBooking","msg"+json.get("message").toString());

                errDialog = new Dialog(activity);
                errDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                errDialog.setCancelable(false);
                errDialog.setContentView(R.layout.custom_dialog_box);
                errDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                TextView mDialogmsg = errDialog.findViewById(R.id.dialog_message);
                mDialogmsg.setText("Booking " +json.get("message").toString());

                TextView mDialogtitle = errDialog.findViewById(R.id.dialog_title);
                mDialogtitle.setText("Error");

                Button myes = errDialog.findViewById(R.id.yes_txt);
                myes.setText("Okey");
                Button mNo = errDialog.findViewById(R.id.no_txt);
                mNo.setVisibility(View.GONE);


                myes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(activity, AddFlightBookingActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.startActivity(intent);
                        errDialog.dismiss();
                    }
                });



                /*
                errDialog= new AlertDialog.Builder(activity);
                errDialog.setTitle("Error ");
                errDialog.setMessage("Booking " +json.get("message").toString());
                errDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(activity, AddFlightBookingActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.startActivity(intent);
                    }
                });*/
                errDialog.show();

            }else
                Log.d("error","error");}

        }
    }
}
