package com.cotrav.cotrav_admin.ui.utilities.asscity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cotrav.cotrav_admin.GsonStringConvertor;
import com.cotrav.cotrav_admin.MasterActivity;
import com.cotrav.cotrav_admin.R;
import com.cotrav.cotrav_admin.model.assesmentcity_model.AssesmentCities;
import com.cotrav.cotrav_admin.model.entities_model.AddUtilitiesApiResponse;
import com.cotrav.cotrav_admin.retrofit.AddUtilitiesApi;
import com.cotrav.cotrav_admin.retrofit.ConfigRetrofit;
import com.cotrav.cotrav_admin.ui.utilities.addcode.AddAssesmentCodeActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAssesmentCityActivity extends AppCompatActivity implements View.OnClickListener {

    EditText textCityName;
    Button buttonSubmit;
    private Toolbar mtoolbar;
    SharedPreferences loginPref;
    String corporateId = "";
    String token ="";
    String Authorization = "";
    String userType = "";
    String userId = "";
    String venName = null;
    String entId;
    AssesmentCities assesmentCities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assesment_city);
        textCityName=(EditText)findViewById(R.id.text_cityName);
        buttonSubmit=(Button)findViewById(R.id.btnSubmit);
        buttonSubmit.setOnClickListener(this);
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
                getSupportActionBar().setTitle("Update Assesment City");
            }else {
                getSupportActionBar().setTitle("Add Assesment City");
            }
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            mtoolbar.setNavigationIcon(R.drawable.ic_back);
        }
        if(venName.equals("UpdateEntity")) {
            if (!bundle.getString("details","n").equals("n"))
            {
                buttonSubmit.setText("Update");
                assesmentCities = GsonStringConvertor.stringToGson(bundle.getString("details", "n"), AssesmentCities.class);
                textCityName.setText(assesmentCities.getCityName());
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

                if (isInternetConnected(AddAssesmentCityActivity.this)) {

                    if (
                            textCityName.getText().toString().length() < 1

                    ){

                        if (textCityName.getText().toString().length() < 1)
                        {
                            textCityName.setError("field required");
                        }
                    }else
                    {
                        final Dialog dialog = new Dialog(AddAssesmentCityActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.custom_dialog_box);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        TextView mDialogTitile = dialog.findViewById(R.id.dialog_title);
                        TextView mDialogmsg = dialog.findViewById(R.id.dialog_message);
                        mDialogTitile.setText("Add New City");
                        mDialogmsg.setText("Please Press Yes to Add New City");
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

                                    new AddAssCityAsyncTask().execute
                                            (
                                                    Authorization,
                                                    userType,
                                                    corporateId,
                                                    userId,
                                                    textCityName.getText().toString()
                                            );
                                }
                                if(venName.equals("UpdateEntity"))
                                {
                                    updateEntity(entId);

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
                }else
                {

                    Toast.makeText(AddAssesmentCityActivity.this,"Please Check Internet Connection",Toast.LENGTH_LONG).show();

                }
                break;

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
                    textCityName.getText().toString(),
                    entityId

            );
        }
    }
    public class AddAssCityAsyncTask extends AsyncTask<String, Integer, String> {
        AddUtilitiesApi addUtilitiesApi;
        private String assesmentvalue;
        public AddAssCityAsyncTask()
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
            addUtilitiesApi.addAssCity(
                    params[0].toString(),
                    params[1].toString(),
                    params[2].toString(),
                    params[3].toString(),
                    params[4].toString()
            ).enqueue(new Callback<AddUtilitiesApiResponse>() {
                @Override
                public void onResponse(Call<AddUtilitiesApiResponse> call, Response<AddUtilitiesApiResponse> response) {
                    if (response.code() == 200) {
                        assesmentvalue = response.body().toString();
                        final Dialog dialog = new Dialog(AddAssesmentCityActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.custom_dialog_box);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        TextView mDialogTitile = dialog.findViewById(R.id.dialog_title);
                        TextView mDialogmsg = dialog.findViewById(R.id.dialog_message);
                        mDialogTitile.setText("Add Entity Status");
                        mDialogmsg.setText("" + response.body().getMessage());
                        Button myes = dialog.findViewById(R.id.yes_txt);
                        Button mNo = dialog.findViewById(R.id.no_txt);
                        mNo.setVisibility(View.GONE);
                        myes.setText("  Ok  ");
                        myes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                Intent mIntent = new Intent(AddAssesmentCityActivity.this, MasterActivity.class);
                                Bundle mBundle = new Bundle();
                                mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                mBundle.putString("Value","addAssCity" );
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
            super.onPreExecute();
        }


        @Override
        protected void onPostExecute(String s)
        {
            // pd.dismiss();
        }
    }
    public class UpdateAssCodeAsyncTask extends AsyncTask<String, Integer, String> {
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

            addUtilitiesApi.updateAssCity(
                    params[0].toString(),
                    params[1].toString(),
                    params[2].toString(),
                    params[3].toString(),
                    params[4].toString(),
                    params[5].toString()

            ).enqueue(new Callback<AddUtilitiesApiResponse>() {
                @Override
                public void onResponse(Call<AddUtilitiesApiResponse> call, Response<AddUtilitiesApiResponse> response)
                {
                    if (response.code() == 200)
                    {
                        assesmentvalue = response.body().toString();
                        Toast.makeText(getApplicationContext(), "Entity Updated :" + response.body().getMessage(), Toast.LENGTH_SHORT).show();


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
                pd = new ProgressDialog(AddAssesmentCityActivity.this);
                pd.setMessage("processing request");
                d = new android.app.AlertDialog.Builder(AddAssesmentCityActivity.this);
                d.setTitle("Add Assessment City");
                pd.show();
            }

        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();

        }
    }

}
