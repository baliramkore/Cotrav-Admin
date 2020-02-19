package com.cotrav.cotrav_admin.ui.utilities.admins;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cotrav.cotrav_admin.GsonStringConvertor;
import com.cotrav.cotrav_admin.MasterActivity;
import com.cotrav.cotrav_admin.R;
import com.cotrav.cotrav_admin.model.admin_model.Admins;
import com.cotrav.cotrav_admin.model.entities_model.AddUtilitiesApiResponse;
import com.cotrav.cotrav_admin.retrofit.AddUtilitiesApi;
import com.cotrav.cotrav_admin.retrofit.ConfigRetrofit;
import com.cotrav.cotrav_admin.ui.utilities.subgroups.AddSubGroupsActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAdminsActivity extends AppCompatActivity  implements View.OnClickListener {

    EditText textAdminName,textAdminContact,textAdminEmail;
    CheckBox checkLocal,checkRadio,checkOutstation,checkBus,checkTrain,checkFlight,checkHotel,checkMeal,checkWater,checkLogistic;
    Button btnSubmit;
    String strLocal="0",strRadio="0",strOutstation="0",strBus="0",strTrain="0",strFlight="0",strHotel="0",strMeal="0",strWater="0",strLogistic="0";
    SharedPreferences loginPref;
    String corporateId = "";
    String token ="";
    String Authorization = "";
    String userType = "";
    String userId = "";
    String venName = null;
    String entId;
    Admins updateAdmins;
    private Toolbar mtoolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_admins);
        Bundle bundle = getIntent().getExtras();
        entId = bundle.getString("EntityId");
        venName = bundle.getString("EntityTask");
        mtoolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);
        if (getSupportActionBar() != null) {
            if(venName.equals("UpdateEntity")) {
                getSupportActionBar().setTitle("Update Admin");
            }else {
                getSupportActionBar().setTitle("Add Admin");
            }
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            mtoolbar.setNavigationIcon(R.drawable.ic_back);
        }
        textAdminName=(EditText)findViewById(R.id.text_adminName);
        textAdminContact=(EditText)findViewById(R.id.text_adminContact);
        textAdminEmail=(EditText)findViewById(R.id.text_adminEmail);
        checkLocal=(CheckBox)findViewById(R.id.checkBox1);
        checkRadio=(CheckBox)findViewById(R.id.checkBox2);
        checkOutstation=(CheckBox)findViewById(R.id.checkBox3);
        checkBus=(CheckBox)findViewById(R.id.checkBox4);
        checkTrain=(CheckBox)findViewById(R.id.checkBox5);
        checkHotel=(CheckBox)findViewById(R.id.checkBox6);
        checkMeal=(CheckBox)findViewById(R.id.checkBox7);
        checkWater=(CheckBox)findViewById(R.id.checkBox8);
        checkFlight=(CheckBox)findViewById(R.id.checkBox9);
        checkLogistic=(CheckBox)findViewById(R.id.checkBox10);
        btnSubmit=findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
        loginPref = getSharedPreferences("login_info", MODE_PRIVATE);
        userType = loginPref.getString("usertype", "n");
        userId = loginPref.getString("user_id", "n");
        token = loginPref.getString("access_token", "n");
        Authorization = "Token " + loginPref.getString("access_token", "n");
        corporateId = loginPref.getString("corporate_id", "n");

        if(venName.equals("UpdateEntity")) {
            if (!bundle.getString("details","n").equals("n")){

                updateAdmins= GsonStringConvertor.stringToGson(bundle.getString("details","n"), Admins.class);
                textAdminName.setText(updateAdmins.getName());
                textAdminContact.setText(updateAdmins.getContactNo());
                textAdminEmail.setText(updateAdmins.getEmail());
                btnSubmit.setText("Update");
                if (updateAdmins.getIsLocal()==1)
                {
                    checkLocal.setChecked(true);
                }

                if (updateAdmins.getIsRadio()==1)
                {
                    checkRadio.setChecked(true);
                }
                if (updateAdmins.getIsOutstation()==1)
                {
                    checkOutstation.setChecked(true);
                }
                if (updateAdmins.getIsBus()==1)
                {
                    checkBus.setChecked(true);
                }
                if (updateAdmins.getIsTrain()==1)
                {
                    checkTrain.setChecked(true);
                }
                if (updateAdmins.getIsHotel()==1)
                {
                    checkHotel.setChecked(true);
                }
                if (updateAdmins.getIsMeal()==1)
                {
                    checkMeal.setChecked(true);
                }
                if (updateAdmins.getIsWaterBottles()==1)
                {
                    checkWater.setChecked(true);
                }
                if (updateAdmins.getIsFlight()==1)
                {
                    checkFlight.setChecked(true);
                }
                if (updateAdmins.getIsReverseLogistics()==1)
                {
                    checkLogistic.setChecked(true);
                }


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

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id)
        {
            case R.id.btnSubmit:
                 checkradiobutton();
                if (isInternetConnected(AddAdminsActivity.this)) {
                    String emailRegistration=textAdminEmail.getText().toString().trim();
                    if (            textAdminName.getText().toString().length() < 1
                                    ||textAdminContact.getText().toString().length()<1
                                    ||textAdminEmail.getText().toString().length()<1){
                        if (textAdminName.getText().toString().length() < 1)
                        {
                            textAdminName.setError("field required");
                        }
                        if (textAdminContact.getText().toString().length() < 1)
                        {
                            textAdminContact.setError("field required");
                        }

                        if (textAdminEmail.getText().toString().length() < 1)
                        {
                            textAdminEmail.setError("field required");
                        }

                    }else
                    {

                        if (venName.equals("AddEntity"))
                        {
                            final Dialog dialog = new Dialog(AddAdminsActivity.this);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setCancelable(false);
                            dialog.setContentView(R.layout.custom_dialog_box);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            TextView mDialogTitile = dialog.findViewById(R.id.dialog_title);
                            TextView mDialogmsg = dialog.findViewById(R.id.dialog_message);
                            mDialogTitile.setText("Add Admin");
                            mDialogmsg.setText("Please Press Yes to Add New Admin");
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

                                        new AddAdminsAsyncTask().execute
                                                (
                                                        Authorization,
                                                        userType,
                                                        corporateId,
                                                        userId,
                                                        textAdminName.getText().toString(),
                                                        textAdminEmail.getText().toString(),
                                                        textAdminContact.getText().toString(),
                                                        strRadio,
                                                        strLocal,
                                                        strOutstation,
                                                        strBus,
                                                        strTrain,
                                                        strHotel,
                                                        strMeal,
                                                        strFlight,
                                                        strWater,
                                                        strLogistic,
                                                        Authorization,
                                                        "taxi123"
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
                            final Dialog dialog = new Dialog(AddAdminsActivity.this);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setCancelable(false);
                            dialog.setContentView(R.layout.custom_dialog_box);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            TextView mDialogTitile = dialog.findViewById(R.id.dialog_title);
                            TextView mDialogmsg = dialog.findViewById(R.id.dialog_message);
                            mDialogTitile.setText("Update Admin");
                            mDialogmsg.setText("Please Press Yes to Update Admin");
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
                        /*{
                        final Dialog dialog = new Dialog(AddAdminsActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.custom_dialog_box);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        TextView mDialogTitile = dialog.findViewById(R.id.dialog_title);
                        TextView mDialogmsg = dialog.findViewById(R.id.dialog_message);
                        mDialogTitile.setText("Add Admin");
                        mDialogmsg.setText("Please Press Yes to Add Admin");
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

                                    new AddAdminsAsyncTask().execute
                                            (
                                                    Authorization,
                                                    userType,
                                                    corporateId,
                                                    userId,
                                                    textAdminName.getText().toString(),
                                                    textAdminEmail.getText().toString(),
                                                    textAdminContact.getText().toString(),
                                                    strRadio,
                                                    strLocal,
                                                    strOutstation,
                                                    strBus,
                                                    strTrain,
                                                    strHotel,
                                                    strMeal,
                                                    strFlight,
                                                    strWater,
                                                    strLogistic,
                                                    Authorization,
                                                    "taxi123"
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
                    }*/
                }else
                {

                    Toast.makeText(AddAdminsActivity.this,"Please Check Internet Connection",Toast.LENGTH_LONG).show();

                }
                 break;
        }
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

    private void checkradiobutton()

    {
        if(checkLocal.isChecked())
        {
            strLocal="1";
        }
        if(checkRadio.isChecked())
        {
            strRadio="1";
        }
        if(checkOutstation.isChecked())
        {
            strOutstation="1";
        }
        if(checkBus.isChecked())
        {
            strBus="1";
        }
        if(checkTrain.isChecked())
        {
            strTrain="1";
        }
        if(checkHotel.isChecked())
        {
            strHotel="1";
        }
        if(checkMeal.isChecked())
        {
            strMeal="1";
        }

        if(checkFlight.isChecked())
        {
            strFlight="1";
        }

        if(checkWater.isChecked())
        {
            strWater="1";
        }
        if(checkLogistic.isChecked())
        {
            strLogistic="1";
        }
    }

    public class AddAdminsAsyncTask extends AsyncTask<String, Integer, String> {
        AddUtilitiesApi addUtilitiesApi;
        private String assesmentvalue;
        public AddAdminsAsyncTask()
        {

        }

        @Override
        protected String doInBackground(String... params)
        {
            String Auth = params[0].toString();
            String usertype = params[1].toString();
            String corporateId=params[2].toString();
            String userId=params[3].toString();
            final AlertDialog.Builder[] d = new AlertDialog.Builder[1];
            Log.d("posting data", params.toString());
            addUtilitiesApi = ConfigRetrofit.configRetrofit(AddUtilitiesApi.class);
            addUtilitiesApi.addAdmins(

                    params[0].toString(),
                    params[1].toString(),
                    params[2].toString(),
                    params[3].toString(),
                    params[4].toString(),
                    params[5].toString(),
                    params[6].toString(),
                    params[7].toString(),
                    params[8].toString(),
                    params[9].toString(),
                    params[10].toString(),
                    params[11].toString(),
                    params[12].toString(),
                    params[13].toString(),
                    params[14].toString(),
                    params[15].toString(),
                    params[16].toString(),
                    params[17].toString(),
                    params[18].toString()
            ).enqueue(new Callback<AddUtilitiesApiResponse>() {
                @Override
                public void onResponse(Call<AddUtilitiesApiResponse> call, Response<AddUtilitiesApiResponse> response) {
                    if (response.code() == 200) {
                        assesmentvalue = response.body().toString();
                        final Dialog dialog = new Dialog(AddAdminsActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.custom_dialog_box);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        TextView mDialogTitile = dialog.findViewById(R.id.dialog_title);
                        TextView mDialogmsg = dialog.findViewById(R.id.dialog_message);
                        mDialogTitile.setText("Add Admin Status");
                        mDialogmsg.setText("" + response.body().getMessage());
                        Button myes = dialog.findViewById(R.id.yes_txt);
                        Button mNo = dialog.findViewById(R.id.no_txt);
                        mNo.setVisibility(View.GONE);
                        myes.setText("  Ok  ");
                        myes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent mIntent = new Intent(AddAdminsActivity.this, MasterActivity.class);
                                Bundle mBundle = new Bundle();
                                mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                mBundle.putString("Value","addAdmin" );
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
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("sucess", "Done");
            pd = new ProgressDialog(AddAdminsActivity.this);
            pd.setMessage("Processing");
            d = new android.app.AlertDialog.Builder(AddAdminsActivity.this);
            d.setTitle("Adding New Admin");
            pd.show();
        }
        @Override
        protected void onPostExecute(String s)
        {
            pd.dismiss();
        }
    }
    public class UpdateAdminsAsyncTask extends AsyncTask<String, Integer, String> {
        AddUtilitiesApi addUtilitiesApi;
        private String assesmentvalue;
        public UpdateAdminsAsyncTask()
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

            addUtilitiesApi.updateAdmins(
                    params[0].toString(),
                    params[1].toString(),
                    params[2].toString(),
                    params[3].toString(),
                    params[4].toString(),
                    params[5].toString(),
                    params[6].toString(),
                    params[7].toString(),
                    params[8].toString(),
                    params[9].toString(),
                    params[10].toString(),
                    params[11].toString(),
                    params[12].toString(),
                    params[13].toString(),
                    params[14].toString(),
                    params[15].toString(),
                    params[16].toString(),
                    params[17].toString()
            ).enqueue(new Callback<AddUtilitiesApiResponse>() {
                @Override
                public void onResponse(Call<AddUtilitiesApiResponse> call, Response<AddUtilitiesApiResponse> response)
                {
                    if (response.code() == 200)
                    {
                        assesmentvalue = response.body().toString();
                        final Dialog dialog = new Dialog(AddAdminsActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.custom_dialog_box);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        TextView mDialogTitile = dialog.findViewById(R.id.dialog_title);
                        TextView mDialogmsg = dialog.findViewById(R.id.dialog_message);
                        mDialogTitile.setText("Update Admin Status");
                        mDialogmsg.setText("" + response.body().getMessage());
                        Button myes = dialog.findViewById(R.id.yes_txt);
                        Button mNo = dialog.findViewById(R.id.no_txt);
                        mNo.setVisibility(View.GONE);
                        myes.setText("  Ok  ");
                        myes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent mIntent = new Intent(AddAdminsActivity.this, MasterActivity.class);
                                Bundle mBundle = new Bundle();
                                mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                mBundle.putString("Value","addAdmin" );
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
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("sucess", "Done");
            pd = new ProgressDialog(AddAdminsActivity.this);
            pd.setMessage("Processing");
            d = new android.app.AlertDialog.Builder(AddAdminsActivity.this);
            d.setTitle("Update Admin");
            pd.show();
        }
        @Override
        protected void onPostExecute(String s)
        {
            pd.dismiss();
        }
    }

    public void updateEntity(String entityId)
    {
        {
            new UpdateAdminsAsyncTask().execute(
                    Authorization,
                    userType,
                    corporateId,
                    userId,
                    textAdminName.getText().toString(),
                    textAdminEmail.getText().toString(),
                    textAdminContact.getText().toString(),
                    strRadio,
                    strLocal,
                    strOutstation,
                    strBus,
                    strTrain,
                    strHotel,
                    strMeal,
                    strFlight,
                    strWater,
                    strLogistic,
                    entityId

            );
        }
    }
}
