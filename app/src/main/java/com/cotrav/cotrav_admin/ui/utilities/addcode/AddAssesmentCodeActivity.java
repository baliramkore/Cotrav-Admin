package com.cotrav.cotrav_admin.ui.utilities.addcode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cotrav.cotrav_admin.GsonStringConvertor;
import com.cotrav.cotrav_admin.MasterActivity;
import com.cotrav.cotrav_admin.R;
import com.cotrav.cotrav_admin.model.assesmentcode_model.AssesmentCodes;
import com.cotrav.cotrav_admin.model.entities_model.AddUtilitiesApiResponse;
import com.cotrav.cotrav_admin.retrofit.AddUtilitiesApi;
import com.cotrav.cotrav_admin.retrofit.ConfigRetrofit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

public class AddAssesmentCodeActivity extends AppCompatActivity implements View.OnClickListener {

    static EditText txtAssCode,txtAssCodeDesc;
    static TextView txtDateFrom,txtDateTo,txtServiceDateFrom,txtServiceDateTo;
    Button buttonSubmit;
    static String start_date_send, end_date_send, start_service_date,end_service_date;
    private Toolbar mtoolbar;
    SharedPreferences loginPref;
    String corporateId = "";
    String token ="";
    String Authorization = "";
    String userType = "";
    String userId = "";
    static String venName = null;
    String entId;
    static int startdd,startmm,startyyyy;
    static AssesmentCodes assesmentCodes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assesment_code);
        loginPref = getSharedPreferences("login_info", MODE_PRIVATE);
        userType = loginPref.getString("usertype", "n");
        userId = loginPref.getString("user_id", "n");
        token = loginPref.getString("access_token", "n");
        Authorization = "Token " + loginPref.getString("access_token", "n");
        corporateId = loginPref.getString("corporate_id", "n");
        Bundle bundle = getIntent().getExtras();
        entId = bundle.getString("EntityId");
        venName = bundle.getString("EntityTask");
        mtoolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);
        if (getSupportActionBar() != null) {
            if(venName.equals("UpdateEntity")) {
                getSupportActionBar().setTitle("Update Assesment Code");
            }else {
                getSupportActionBar().setTitle("Add Assesment Code");
            }
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            mtoolbar.setNavigationIcon(R.drawable.ic_back);
        }
        txtAssCode=(EditText)findViewById(R.id.text_assCode);
        txtAssCodeDesc=(EditText)findViewById(R.id.text_assesmentDescription);
        txtDateFrom=(TextView) findViewById(R.id.text_dateFrom);
        txtDateTo=(TextView)findViewById(R.id.text_dateTo);
        txtServiceDateFrom=(TextView)findViewById(R.id.text_serviceDateFrom);
        txtServiceDateTo=(TextView)findViewById(R.id.text_serviceDateTo);
        buttonSubmit=(Button)findViewById(R.id.btnSubmit);
        buttonSubmit.setOnClickListener(this);

        txtDateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePickerFragment = new DatePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putString("start", "start");
                datePickerFragment.setArguments(bundle);
                datePickerFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        txtDateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePickerFragment = new DatePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putString("start", "end");
                datePickerFragment.setArguments(bundle);
                datePickerFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
        txtServiceDateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePickerFragment = new DatePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putString("start", "startservice");
                datePickerFragment.setArguments(bundle);
                datePickerFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        txtServiceDateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePickerFragment = new DatePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putString("start", "endservice");
                datePickerFragment.setArguments(bundle);
                datePickerFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        if(venName.equals("UpdateEntity")) {
            if (!bundle.getString("details","n").equals("n"))
            {
                buttonSubmit.setText("Update");
                assesmentCodes = GsonStringConvertor.stringToGson(bundle.getString("details", "n"), AssesmentCodes.class);
                txtAssCode.setText(assesmentCodes.getAssessmentCode());
                txtAssCodeDesc.setText(assesmentCodes.getCodeDesc());
                txtDateFrom.setText(assesmentCodes.getFromDate());
                txtDateTo.setText(assesmentCodes.getToDate());
                txtServiceDateFrom.setText(assesmentCodes.getServiceFrom());
                txtServiceDateTo.setText(assesmentCodes.getServiceTo());

            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void onBackPressed() {
        super.onBackPressed();
    }
    public static boolean isInternetConnected(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo[] = cm.getAllNetworkInfo();

        int i;
        for (i = 0; i < networkInfo.length; ++i)
        {
            if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED)
            {
                return true;
            }
        }
        return false;

    }
    @Override
    public void onClick(View view) {
        int id  = view.getId();
        switch (id)
        {
            case R.id.btnSubmit:
                if (isInternetConnected(AddAssesmentCodeActivity.this)) {

                    if (
                            txtAssCode.getText().toString().length() < 1
                                    ||txtAssCodeDesc.getText().toString().length()<1
                                    ||txtDateFrom.getText().toString().length()<1
                                    ||txtDateTo.getText().toString().length()<1
                                    ||txtServiceDateFrom.getText().toString().length()<1
                                    ||txtServiceDateTo.getText().toString().length()<1
                    ){

                        if (txtAssCode.getText().toString().length() < 1)
                        {
                            txtAssCode.setError("field required");
                        }
                        if (txtAssCodeDesc.getText().toString().length() < 1)
                        {
                            txtAssCodeDesc.setError("field required");
                        }
                        if (txtDateFrom.getText().toString().length() < 1)
                        {
                            txtDateFrom.setError("field required");
                        }
                        if (txtDateTo.getText().toString().length() < 1)
                        {
                            txtDateTo.setError("field required");
                        }
                        if (txtServiceDateFrom.getText().toString().length() < 1)
                        {
                            txtServiceDateFrom.setError("field required");
                        }
                        if (txtServiceDateTo.getText().toString().length() < 1)
                        {
                            txtServiceDateTo.setError("field required");
                        }
                    }else
                    {

                        if (venName.equals("AddEntity"))
                        {
                            final Dialog dialog = new Dialog(AddAssesmentCodeActivity.this);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setCancelable(false);
                            dialog.setContentView(R.layout.custom_dialog_box);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            TextView mDialogTitile = dialog.findViewById(R.id.dialog_title);
                            TextView mDialogmsg = dialog.findViewById(R.id.dialog_message);
                            mDialogTitile.setText("Add Assement Code");
                            mDialogmsg.setText("Please Press Yes to Add New Assesment Code");
                            Button myes = dialog.findViewById(R.id.yes_txt);
                            Button mNo = dialog.findViewById(R.id.no_txt);
                            myes.setText("Yes");
                            myes.setOnClickListener(new View.OnClickListener()
                            {
                                @Override
                                public void onClick(View v)
                                {
                                    if (venName.equals("AddEntity"))
                                    {

                                        new AddAssCodeAsyncTask().execute
                                                (
                                                        Authorization,
                                                        userType,
                                                        corporateId,
                                                        userId,
                                                        txtAssCode.getText().toString(),
                                                        txtAssCodeDesc.getText().toString(),
                                                        txtDateFrom.getText().toString(),
                                                        txtDateTo.getText().toString(),
                                                        txtServiceDateFrom.getText().toString(),
                                                        txtServiceDateTo.getText().toString()
                                                );
                                    }
                                    dialog.dismiss();
                                }
                            });
                            mNo.setText("Cancel");
                            mNo.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();
                                }
                            });

                            dialog.show();

                        }

                        if (venName.equals("UpdateEntity"))
                        {
                            final Dialog dialog = new Dialog(AddAssesmentCodeActivity.this);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setCancelable(false);
                            dialog.setContentView(R.layout.custom_dialog_box);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            TextView mDialogTitile = dialog.findViewById(R.id.dialog_title);
                            TextView mDialogmsg = dialog.findViewById(R.id.dialog_message);
                            mDialogTitile.setText("Update Assement Code");
                            mDialogmsg.setText("Please Press Yes to Update Assesment Code");
                            Button myes = dialog.findViewById(R.id.yes_txt);
                            Button mNo = dialog.findViewById(R.id.no_txt);
                            myes.setText("Yes");
                            myes.setOnClickListener(new View.OnClickListener()
                            {
                                @Override
                                public void onClick(View v)
                                {
                                    updateEntity(entId);
                                    dialog.dismiss();
                                }
                            });
                            mNo.setText("Cancel");
                            mNo.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();
                                }
                            });

                            dialog.show();

                        }
                    }
                }else
                {

                    Toast.makeText(AddAssesmentCodeActivity.this,"Please Check Internet Connection",Toast.LENGTH_LONG).show();

                }
                break;
        }
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        String id;
        String datefrom;
        String dateto;
        String servicefrom;
        String serviceto;
        String myDate;
        long millis;
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year=0;
            int month=0;
            int day=0;
            id=new String();
            id=getArguments().getString("start");

            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);

            if(venName.equals("UpdateEntity")) {

                datefrom = assesmentCodes.getFromDate();
                dateto=assesmentCodes.getToDate();
                servicefrom=assesmentCodes.getServiceFrom();
                serviceto=assesmentCodes.getServiceTo();



                if (id.equals("start"))
                {
                    //myDate = datefrom;
                    String [] dateParts = datefrom.split("-");
                    day= Integer.parseInt(dateParts[0]);
                    month= Integer.parseInt(dateParts[1]);
                    year= Integer.parseInt(dateParts[2]);


                }
                if (id.equals("end"))
                {
                    myDate = datefrom;
                    String [] dateParts = dateto.split("-");
                    day= Integer.parseInt(dateParts[0]);
                    month= Integer.parseInt(dateParts[1]);
                    year= Integer.parseInt(dateParts[2]);

                }
                if (id.equals("startservice"))
                {
                    //myDate = servicefrom;
                    String [] dateParts = servicefrom.split("-");
                    day= Integer.parseInt(dateParts[0]);
                    month= Integer.parseInt(dateParts[1]);
                    year= Integer.parseInt(dateParts[2]);

                }
                if (id.equals("endservice"))
                {
                    myDate = servicefrom;
                    String [] dateParts = serviceto.split("-");
                    day= Integer.parseInt(dateParts[0]);
                    month= Integer.parseInt(dateParts[1]);
                    year= Integer.parseInt(dateParts[2]);

                }


                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                Date date = null;
                try {
                    date = sdf.parse(myDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                millis = date.getTime();

            }else{
                millis=System.currentTimeMillis();
            }



            //month=month-1;
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month , day);
            datePickerDialog.getDatePicker().setMinDate(millis-1000);
            return datePickerDialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            final Calendar c = Calendar.getInstance();
            c.set(year, month, day);
            if (id.equals("start")) {
                if (c.compareTo(Calendar.getInstance()) >0) {
                    month = month + 1;
                    start_date_send = day + "-" + month + "-" + year;
                    startdd=day;
                    startmm=month;
                    startyyyy=year;
                    if (day < 10) {
                        String d = Integer.toString(day);
                        d = 0 + "" + d;
                        txtDateFrom.setText(d + "-" + month + "-" + year);
                    } else {
                        txtDateFrom.setText(day + "-" + month + "-" + year);
                    }

                    txtDateFrom.setError(null);
                } else
                    {
                        Toasty.error(getActivity(), "past dates are not allowed", Toast.LENGTH_SHORT).show();
                    }
                    }
            else if (id.equals("end")) {
                if (c.compareTo(Calendar.getInstance()) >0) {
                    month = month + 1;
                    end_date_send = day + "-" + month + "-" + year;
                    if (day < 10) {
                        String d = Integer.toString(day);
                        d = 0 + "" + d;
                        txtDateTo.setText(d + "-" + month + "-" + year);
                    } else {
                        txtDateTo.setText(day + "-" + month + "-" + year);
                    }
                    txtDateTo.setError(null);

                } else {
                    //Toasty.error(getActivity(), "past dates are not allowed", Toast.LENGTH_SHORT).show();
                }

            } else if (id.equals("startservice")) {

                if (c.compareTo(Calendar.getInstance()) > 0) {
                    month = month + 1;
                    start_service_date = day + "-" + month + "-" + year;
                    if (day < 10) {
                        String d = Integer.toString(day);
                        d = 0 + "" + d;
                        txtServiceDateFrom.setText(d + "-" + month + "-" + year);
                    } else {
                        txtServiceDateFrom.setText(day + "-" + month + "-" + year);
                    }
                    txtServiceDateFrom.setError(null);

                } else {
                    //Toasty.error(getActivity(), "past dates are not allowed", Toast.LENGTH_SHORT).show();
                }
            } else if (id.equals("endservice")) {

                if (c.compareTo(Calendar.getInstance()) >0) {
                    month = month + 1;
                    end_service_date = day + "-" + month + "-" + year;
                    if (day < 10) {
                        String d = Integer.toString(day);
                        d = 0 + "" + d;
                        txtServiceDateTo.setText(d + "-" + month + "-" + year);
                    } else {
                        txtServiceDateTo.setText(day + "-" + month + "-" + year);
                    }
                    txtServiceDateTo.setError(null);
/*
                    if (start_service_date.compareTo(end_service_date)>= 0)
                    {
                        txtServiceDateTo.setError("Wrong Date Selected");
                        Toasty.error(getActivity(), "End Service Date Should be Greater Than Start Service Date", Toast.LENGTH_SHORT).show();
                    }*/

                } else {
                    //Toasty.error(getActivity(), "past dates are not allowed", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }
    public class AddAssCodeAsyncTask extends AsyncTask<String, Integer, String> {
        AddUtilitiesApi addUtilitiesApi;
        private String assesmentvalue;
        public AddAssCodeAsyncTask()
        {

        }

        @Override
        protected String doInBackground(String... params) {
            String Auth = params[0].toString();
            String usertype = params[1].toString();
            String corporateId=params[2].toString();
            String userId=params[3].toString();
            final AlertDialog.Builder[] d = new AlertDialog.Builder[1];
            Log.d("posting data", params.toString());
            addUtilitiesApi = ConfigRetrofit.configRetrofit(AddUtilitiesApi.class);
            addUtilitiesApi.addAssCodes(
                    params[0].toString(),
                    params[1].toString(),
                    params[2].toString(),
                    params[3].toString(),
                    params[4].toString(),
                    params[5].toString(),
                    params[6].toString(),
                    params[7].toString(),
                    params[8].toString(),
                    params[9].toString()
            ).enqueue(new Callback<AddUtilitiesApiResponse>() {
                @Override
                public void onResponse(Call<AddUtilitiesApiResponse> call, Response<AddUtilitiesApiResponse> response) {
                    if (response.code() == 200) {
                        assesmentvalue = response.body().toString();
                        assesmentvalue = response.body().toString();
                        Toast.makeText(AddAssesmentCodeActivity.this, "Assesment Added :" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        final Dialog dialog = new Dialog(AddAssesmentCodeActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.custom_dialog_box);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                        TextView mDialogTitile = dialog.findViewById(R.id.dialog_title);
                        TextView mDialogmsg = dialog.findViewById(R.id.dialog_message);

                        mDialogTitile.setText("Assesment Status");
                        mDialogmsg.setText("" + response.body().getMessage());

                        Button myes = dialog.findViewById(R.id.yes_txt);
                        Button mNo = dialog.findViewById(R.id.no_txt);
                        mNo.setVisibility(View.GONE);
                        myes.setText("  Ok  ");
                        myes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent mIntent = new Intent(AddAssesmentCodeActivity.this, MasterActivity.class);
                                Bundle mBundle = new Bundle();
                                mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                mBundle.putString("Value","addAssCode" );
                                mIntent.putExtras(mBundle);
                                startActivity(mIntent);
                                dialog.dismiss();
                            }
                        });

                        dialog.show();
                    }
                }
                @Override
                public void onFailure(Call<AddUtilitiesApiResponse> call, Throwable t) {

                }
            });

            return assesmentvalue;

        }

        ProgressDialog pd;
        android.app.AlertDialog.Builder d;
        @Override
        protected void onPreExecute()
        {
            {
                super.onPreExecute();
                Log.d("sucess", "Done");
                pd = new ProgressDialog(AddAssesmentCodeActivity.this);
                pd.setMessage("processing request");
                d = new android.app.AlertDialog.Builder(AddAssesmentCodeActivity.this);
                d.setTitle("Add Assessment Code");
                pd.show();
            }
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();

        }
    }
    public class UpdateAssCodeAsyncTask extends AsyncTask<String, Integer, String>
    {
        AddUtilitiesApi addUtilitiesApi;
        private String assesmentvalue;
        public UpdateAssCodeAsyncTask()
        {

        }


        @Override
        protected String doInBackground(String... params) {
            String Auth = params[0].toString();
            String usertype = params[1].toString();
            String corporateId=params[2].toString();
            String userId=params[3].toString();
            final AlertDialog.Builder[] d = new AlertDialog.Builder[1];
            Log.d("posting data", params.toString());
            //System Date Added From Mobile

            addUtilitiesApi = ConfigRetrofit.configRetrofit(AddUtilitiesApi.class);
            addUtilitiesApi.updateAssCode(
                    params[0].toString(),
                    params[1].toString(),
                    params[2].toString(),
                    params[3].toString(),
                    params[4].toString(),
                    params[5].toString(),
                    params[6].toString(),
                    params[7].toString(),
                    params[8].toString(),
                    params[9].toString(),entId
            ).enqueue(new Callback<AddUtilitiesApiResponse>() {
                @Override
                public void onResponse(Call<AddUtilitiesApiResponse> call, Response<AddUtilitiesApiResponse> response)
                {
                    if (response.code() == 200)
                    {
                        assesmentvalue = response.body().toString();
                        assesmentvalue = response.body().toString();
                        Toast.makeText(AddAssesmentCodeActivity.this, "Assesment Added :" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        final Dialog dialog = new Dialog(AddAssesmentCodeActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.custom_dialog_box);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                        TextView mDialogTitile = dialog.findViewById(R.id.dialog_title);
                        TextView mDialogmsg = dialog.findViewById(R.id.dialog_message);

                        mDialogTitile.setText("Assesment Status");
                        mDialogmsg.setText("" + response.body().getMessage());

                        Button myes = dialog.findViewById(R.id.yes_txt);
                        Button mNo = dialog.findViewById(R.id.no_txt);
                        mNo.setVisibility(View.GONE);
                        myes.setText("  Ok  ");
                        myes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent mIntent = new Intent(AddAssesmentCodeActivity.this, MasterActivity.class);
                                Bundle mBundle = new Bundle();
                                mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                mBundle.putString("Value","addAssCode" );
                                mIntent.putExtras(mBundle);
                                startActivity(mIntent);
                                dialog.dismiss();
                            }
                        });

                        dialog.show();
                    }
                }

                @Override
                public void onFailure(Call<AddUtilitiesApiResponse> call, Throwable t)
                {

                }
            });

            return assesmentvalue;

        }

        ProgressDialog pd;
        android.app.AlertDialog.Builder d;
        @Override
        protected void onPreExecute() {
            {
                super.onPreExecute();
                Log.d("sucess", "Done");
                pd = new ProgressDialog(AddAssesmentCodeActivity.this);
                pd.setMessage("processing request");
                d = new android.app.AlertDialog.Builder(AddAssesmentCodeActivity.this);
                d.setTitle("Update Assessment Code");
                pd.show();
            }

        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();

        }
    }
    public void updateEntity(String entityId)
    {
        {
            new UpdateAssCodeAsyncTask().execute(
                    Authorization,
                    userType,
                    corporateId,
                    userId,
                    txtAssCode.getText().toString(),
                    txtAssCodeDesc.getText().toString(),
                    txtDateFrom.getText().toString(),
                    txtDateTo.getText().toString(),
                    txtServiceDateFrom.getText().toString(),
                    txtServiceDateTo.getText().toString(),
                    entityId

            );
        }
    }
}
