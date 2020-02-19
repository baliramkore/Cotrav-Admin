package com.cotrav.cotrav_admin.ui.utilities.entities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cotrav.cotrav_admin.AllCityViewModel;
import com.cotrav.cotrav_admin.GsonStringConvertor;
import com.cotrav.cotrav_admin.MasterActivity;
import com.cotrav.cotrav_admin.R;
import com.cotrav.cotrav_admin.model.all_cities_model.City;
import com.cotrav.cotrav_admin.model.entities_model.AddUtilitiesApiResponse;
import com.cotrav.cotrav_admin.model.entities_model.Entities;
import com.cotrav.cotrav_admin.retrofit.AddUtilitiesApi;
import com.cotrav.cotrav_admin.retrofit.ConfigRetrofit;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEntityActvity extends AppCompatActivity
{
    EditText txtEntityName,txtContactName,txtContactNumber,txtContactEmail,txtGSTNo,txtPanNo,txtAddress1,txtAddress2,txtAddress3;
    SearchableSpinner txtSelectCity;
    private Toolbar mtoolbar;
    Button btnAdd;
    SharedPreferences loginPref;
    String corporateId = "";
    String token ="";
    String Authorization = "";
    String userType = "";
    String userId = "";
    String authorization = "";
    private List<City> citiesList;
    private AllCityViewModel citiesViewModel;
    ArrayList<String> pickup_CityList, pickup_CityIdList;
    ArrayAdapter<String> allcityadapter;
    Entities updateEntity;
    String venName;
    String entId;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entity_actvity);

        progressDialog = new ProgressDialog(AddEntityActvity.this);
        progressDialog.setMessage("Getting Cities");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        Bundle bundle = getIntent().getExtras();
        venName = bundle.getString("EntityTask");
        entId = bundle.getString("EntityId");
        loginPref=this.getSharedPreferences("login_info", Context.MODE_PRIVATE);
        token = loginPref.getString("access_token","n");
        authorization = "Token "+token;
        Authorization="Token "+token;
        userType = loginPref.getString("usertype", "n");
        userId=loginPref.getString("user_id", "n");
        corporateId = loginPref.getString("corporate_id", "n");
        citiesViewModel = ViewModelProviders.of(AddEntityActvity.this).get(AllCityViewModel.class);
        citiesViewModel.InitCityViewModel(authorization,userType,corporateId);
        txtEntityName=findViewById(R.id.text_entityName);
        txtContactName=findViewById(R.id.text_contactName);
        txtContactNumber=findViewById(R.id.text_contactNumber);
        txtContactEmail=findViewById(R.id.text_contactEmail);
        txtGSTNo=findViewById(R.id.text_GSTNo);
        txtPanNo=findViewById(R.id.text_PanNo);
       // txtSelectCity=findViewById(R.id.text_selectCity);
        txtSelectCity = (SearchableSpinner) findViewById(R.id.bus_assesmentCity);
        txtAddress1=findViewById(R.id.text_Address1);
        txtAddress2=findViewById(R.id.text_Address2);
        txtAddress3=findViewById(R.id.text_Address3);

        btnAdd=findViewById(R.id.btnSubmit);
        citiesList=new ArrayList<>();
        pickup_CityList=new ArrayList<>();
        pickup_CityIdList=new ArrayList<>();

        if(venName.equals("UpdateEntity"))
        {
            if (!bundle.getString("details","n").equals("n")){
                updateEntity = GsonStringConvertor.stringToGson(bundle.getString("details","n"), Entities.class);

            }
        }
        if(venName.equals("UpdateEntity"))
        {
            btnAdd.setText("Update");

                txtEntityName.setText(updateEntity.getEntityName());
                txtContactName.setText(updateEntity.getContactPersonName());
                txtContactNumber.setText(updateEntity.getContactPersonNo());
                txtContactEmail.setText(updateEntity.getContactPersonEmail());
                txtGSTNo.setText(updateEntity.getGstId());
                txtPanNo.setText(updateEntity.getPanNo());
                txtAddress1.setText(updateEntity.getAddressLine1());
                txtAddress2.setText(updateEntity.getAddressLine2());
                txtAddress3.setText(updateEntity.getAddressLine3());
        }

        citiesViewModel.getCityLiveData(Authorization, userType,corporateId);
        pickup_CityList.add(new String("Select City"));
        pickup_CityIdList.add(new String("Select City"));
        allcityadapter = new ArrayAdapter<String>(this, R.layout.spinner_item, pickup_CityList);
        txtSelectCity.setAdapter(allcityadapter);
        citiesViewModel.getCityLiveData(Authorization, userType,corporateId).
                observe(this, new Observer<List<City>>() {
                    @Override
                    public void onChanged(List<City> cities) {
                        if (cities != null && cities.size() > 0) {
                            pickup_CityList.clear();
                            pickup_CityIdList.clear();
                            pickup_CityList.add(new String("Select City"));
                            pickup_CityIdList.add(new String("Select City"));
                            for (int i = 0; i < cities.size(); i++) {
                                pickup_CityList.add(cities.get(i).getCityName());
                                pickup_CityIdList.add(String.valueOf(cities.get(i).getId()));
                            }


                            if (venName.equals("UpdateEntity")) {

                                for (int i = 0; i < cities.size(); i++) {
                                    if (cities.get(i).getId().equals(updateEntity.getBillingCityId())) {
                                        txtSelectCity.setSelection(i+1);
                                        break;
                                    }
                                }
                            }
                            allcityadapter.notifyDataSetChanged();
                            progressDialog.dismiss();
                        }
                    }
                });
        allcityadapter.notifyDataSetChanged();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (isInternetConnected(AddEntityActvity.this))
                {

                    if (txtEntityName.getText().toString().length() < 1
                        ||txtContactNumber.getText().toString().length() < 1
                        ||txtContactEmail.getText().toString().length() < 1
                        ||txtGSTNo.getText().toString().length() < 1
                        ||txtPanNo.getText().toString().length() < 1
                        ||txtSelectCity.getSelectedItem().toString().length() < 1
                        ||txtAddress1.getText().toString().length() < 1
                        ||txtAddress2.getText().toString().length() < 1
                    ){

                        if (txtEntityName.getText().toString().length() < 1)
                        {
                            txtEntityName.setError("field required");
                        }
                        if (txtContactName.getText().toString().length() < 1)
                        {
                            txtContactName.setError("field required");
                        }
                        if (txtContactNumber.getText().toString().length() < 1)
                        {
                            txtContactNumber.setError("field required");
                        }
                        if (txtContactEmail.getText().toString().length() < 1)
                        {
                            txtContactEmail.setError("field required");
                        }
                        if (txtGSTNo.getText().toString().length() < 1)
                        {
                            txtGSTNo.setError("field required");
                        }
                        if (txtPanNo.getText().toString().length() < 1)
                        {
                            txtPanNo.setError("field required");
                        }

                        if (txtAddress1.getText().toString().length() < 1)
                        {
                            txtAddress1.setError("field required");
                        }
                        if (txtSelectCity.getSelectedItem().toString().equals("Select City")) {
                            ((android.widget.TextView) txtSelectCity.getSelectedView()).setError("field required");
                        }

                        if (txtAddress2.getText().toString().length() < 1)
                        {
                            txtAddress2.setError("field required");
                        }
                        if (txtAddress3.getText().toString().length() < 1)
                        {
                            txtAddress3.setError("field required");
                        }
                    }else
                    {

                        if (venName.equals("AddEntity"))
                        {
                            final Dialog dialog = new Dialog(AddEntityActvity.this);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setCancelable(false);
                            dialog.setContentView(R.layout.custom_dialog_box);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            TextView mDialogTitile = dialog.findViewById(R.id.dialog_title);
                            TextView mDialogmsg = dialog.findViewById(R.id.dialog_message);
                            mDialogTitile.setText("Add Entity");
                            mDialogmsg.setText("Please Press Yes to Add New Entity");
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

                                        new AddEntitiesAsyncTask().execute(
                                                Authorization,
                                                userType,
                                                corporateId,
                                                userId,
                                                txtEntityName.getText().toString(),
                                                pickup_CityIdList.get(txtSelectCity.getSelectedItemPosition()),
                                                txtContactName.getText().toString(),
                                                txtContactEmail.getText().toString(),
                                                txtContactNumber.getText().toString(),
                                                txtAddress1.getText().toString(),
                                                txtAddress2.getText().toString(),
                                                txtAddress3.getText().toString(),
                                                txtGSTNo.getText().toString(),
                                                txtPanNo.getText().toString()

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
                            final Dialog dialog = new Dialog(AddEntityActvity.this);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setCancelable(false);
                            dialog.setContentView(R.layout.custom_dialog_box);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            TextView mDialogTitile = dialog.findViewById(R.id.dialog_title);
                            TextView mDialogmsg = dialog.findViewById(R.id.dialog_message);
                            mDialogTitile.setText("Update Entity");
                            mDialogmsg.setText("Please Press Yes to Update Entity");
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

                        Toast.makeText(AddEntityActvity.this,"Please Check Internet Connection",Toast.LENGTH_LONG).show();

                    }

            }
        });

        mtoolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);
        if (getSupportActionBar() != null) {
            if(venName.equals("UpdateEntity")) {
                getSupportActionBar().setTitle("Update Entities");
            }else {
                getSupportActionBar().setTitle("Add Entities");
            }
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            mtoolbar.setNavigationIcon(R.drawable.ic_back);
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



    public class AddEntitiesAsyncTask extends AsyncTask<String, Integer, String> {
        AddUtilitiesApi addUtilitiesApi;
        private String assesmentvalue;
        public AddEntitiesAsyncTask()
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
            addUtilitiesApi.addEntities(
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
                    params[13].toString()
            ).enqueue(new Callback<AddUtilitiesApiResponse>() {
                @Override
                public void onResponse(Call<AddUtilitiesApiResponse> call, Response<AddUtilitiesApiResponse> response) {
                    if (response.code() == 200) {
                        assesmentvalue = response.body().toString();

                        Toast.makeText(getApplicationContext(), "Entity Added :" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        final Dialog dialog = new Dialog(AddEntityActvity.this);
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

                                Intent mIntent = new Intent(AddEntityActvity.this, MasterActivity.class);
                                Bundle mBundle = new Bundle();
                                mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                mBundle.putString("Value","Entity" );
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
            pd = new ProgressDialog(AddEntityActvity.this);
            pd.setMessage("Processing");
            d = new android.app.AlertDialog.Builder(AddEntityActvity.this);
            d.setTitle("Adding New Entity");
            pd.show();
        }

        @Override
        protected void onPostExecute(String s)
        {
            pd.dismiss();
        }
    }

    public class UpdateEntitiesAsyncTask extends AsyncTask<String, Integer, String> {
        AddUtilitiesApi addUtilitiesApi;
        private String assesmentvalue;
        public UpdateEntitiesAsyncTask()
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

            addUtilitiesApi.updateEntities(
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
                    params[14].toString()
            ).enqueue(new Callback<AddUtilitiesApiResponse>() {
                @Override
                public void onResponse(Call<AddUtilitiesApiResponse> call, Response<AddUtilitiesApiResponse> response)
                {
                    if (response.code() == 200)
                    {
                        assesmentvalue = response.body().toString();

                        Toast.makeText(getApplicationContext(), "Entity Added :" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        final Dialog dialog = new Dialog(AddEntityActvity.this);
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

                                Intent mIntent = new Intent(AddEntityActvity.this, MasterActivity.class);
                                Bundle mBundle = new Bundle();
                                mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                mBundle.putString("Value","Entity" );
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
            super.onPreExecute();
        Log.d("sucess", "Done");
        pd = new ProgressDialog(AddEntityActvity.this);
        pd.setMessage("processing..");
        d = new android.app.AlertDialog.Builder(AddEntityActvity.this);
        d.setTitle("Add Entity");
        pd.show();
        }


        @Override
        protected void onPostExecute(String s) {

             pd.dismiss();

        }
    }

    public static boolean isInternetConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo[] = cm.getAllNetworkInfo();

        int i;
        for (i = 0; i < networkInfo.length; ++i) {
            if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                return true;
            }
        }
        return false;

    }

    public void updateEntity(String entityId)
    {
        new UpdateEntitiesAsyncTask().execute(
                Authorization,
                userType,
                corporateId,
                userId,
                txtEntityName.getText().toString(),
                pickup_CityIdList.get(txtSelectCity.getSelectedItemPosition()),
                txtContactName.getText().toString(),
                txtContactEmail.getText().toString(),
                txtContactNumber.getText().toString(),
                txtAddress1.getText().toString(),
                txtAddress2.getText().toString(),
                txtAddress3.getText().toString(),
                txtGSTNo.getText().toString(),
                txtPanNo.getText().toString(),entityId

        );
    }
}
