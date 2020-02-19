package com.cotrav.cotrav_admin.ui.utilities.employees;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cotrav.cotrav_admin.AllCityViewModel;
import com.cotrav.cotrav_admin.GsonStringConvertor;
import com.cotrav.cotrav_admin.MasterActivity;
import com.cotrav.cotrav_admin.R;
import com.cotrav.cotrav_admin.model.admin_model.Admins;
import com.cotrav.cotrav_admin.model.all_cities_model.City;
import com.cotrav.cotrav_admin.model.employees_model.Employee;
import com.cotrav.cotrav_admin.model.entities_model.AddUtilitiesApiResponse;
import com.cotrav.cotrav_admin.model.entities_model.Entities;
import com.cotrav.cotrav_admin.model.spoc_model.Spocs;
import com.cotrav.cotrav_admin.retrofit.AddUtilitiesApi;
import com.cotrav.cotrav_admin.retrofit.ConfigRetrofit;
import com.cotrav.cotrav_admin.ui.utilities.entities.EntitiesViewModel;
import com.cotrav.cotrav_admin.ui.utilities.spocs.SpocsViewModel;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.Header;

public class AddEmployeesActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<String> cityList, cityListId, spocList, spocIdList, billingEntityList, billingEntityIdList;
    ArrayAdapter<String> cityAdapter, billingEntityAdapter, spocsAdapter;
    SearchableSpinner citySpinner, spocsSpinner, billingSpinner;
    EditText coreEmployeeId, employeeId, companyId, empName, empEmail, employeeContact, empAddress, empDesign, idproofType, idproofNo;
    static TextView birthDate;
    Button buttonSubmit;
    AllCityViewModel citiesViewModel;
    EntitiesViewModel entityViewModel;
    SpocsViewModel spocsViewModel;
    private Toolbar mtoolbar;
    RadioGroup radioGroup;
    String spocId, billingId, access_token, authorization, corporateId, usertype, userId, cityId;
    SharedPreferences loginpref;
    static String venName = null;
    String entId;
    RadioButton radioMale, radioFemale;
    static Employee updatEmployee;
    CheckBox isActive, isCXO;
    ProgressDialog progressDialog;
    String strIsActive = "0", strIsCXO = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employees);
        progressDialog = new ProgressDialog(AddEmployeesActivity.this);
        progressDialog.setMessage("Getting Cities");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        Bundle bundle = getIntent().getExtras();
        entId = bundle.getString("EntityId");
        venName = bundle.getString("EntityTask");
        loginpref = getSharedPreferences("login_info", MODE_PRIVATE);
        corporateId = loginpref.getString("corporate_id", "n");
        access_token = loginpref.getString("access_token", "n");
        authorization = "Token " + access_token;
        usertype = loginpref.getString("usertype", "n");
        userId = loginpref.getString("user_id", "n");
        citySpinner = findViewById(R.id.spinner_city);
        spocsSpinner = findViewById(R.id.spinner_spoc);
        billingSpinner = findViewById(R.id.spinner_billing);
        coreEmployeeId = findViewById(R.id.textCoreEmployeeId);
        employeeId = findViewById(R.id.text_employeeId);
        companyId = findViewById(R.id.text_companyId);
        empName = findViewById(R.id.text_empName);
        empEmail = findViewById(R.id.text_empEmail);
        employeeContact = findViewById(R.id.textEmployeeContact);
        birthDate = findViewById(R.id.text_birthDate);
        birthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePickerFragment = new DatePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putString("start", "start");
                datePickerFragment.setArguments(bundle);
                datePickerFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        empAddress = findViewById(R.id.text_empAddress);

        isActive = findViewById(R.id.checkBox1);
        isCXO = findViewById(R.id.checkBox2);

        empDesign = findViewById(R.id.text_empDesign);
        idproofType = findViewById(R.id.text_idproofType);
        idproofNo = findViewById(R.id.text_idproofNo);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
        buttonSubmit = findViewById(R.id.btnSubmit);
        buttonSubmit.setOnClickListener(this);

        cityList = new ArrayList<>();
        cityListId = new ArrayList<>();
        spocList = new ArrayList<>();
        spocIdList = new ArrayList<>();
        billingEntityList = new ArrayList<>();
        billingEntityIdList = new ArrayList<>();
        citiesViewModel = ViewModelProviders.of(this).get(AllCityViewModel.class);
        entityViewModel = ViewModelProviders.of(this).get(EntitiesViewModel.class);
        spocsViewModel = ViewModelProviders.of(this).get(SpocsViewModel.class);

        mtoolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);
        if (getSupportActionBar() != null) {
            if (venName.equals("UpdateEntity")) {
                getSupportActionBar().setTitle("Update Employee");
                buttonSubmit.setText("Update");
                if (!bundle.getString("details", "n").equals("n")) {

                    updatEmployee = GsonStringConvertor.stringToGson(bundle.getString("details", "n"), Employee.class);
                    // spocsSpinner.setSelection(updatEmployee.getSpocId());
                    //citySpinner.setSelection(updatEmployee.getHomeCity());
                    //billingSpinner.setSelection(updatEmployee.getBillingEntityId());
                    employeeId.setText(updatEmployee.getCoreEmployeeId());
                    coreEmployeeId.setText(updatEmployee.getCoreEmployeeId());
                    companyId.setText(updatEmployee.getEmployeeCid());
                    empName.setText(updatEmployee.getEmployeeName());
                    employeeContact.setText(updatEmployee.getEmployeeContact());
                    empEmail.setText(updatEmployee.getEmployeeEmail());
                    empDesign.setText(updatEmployee.getDesignation());
                    idproofType.setText(updatEmployee.getIdProofType());
                    idproofNo.setText(updatEmployee.getIdProofNo());
                    empAddress.setText(updatEmployee.getHomeAddress());
                    birthDate.setText(updatEmployee.getDateOfBirth());

                }

            } else {
                getSupportActionBar().setTitle("Add Employee");
            }
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            mtoolbar.setNavigationIcon(R.drawable.ic_back);
        }

        cityList.clear();
        cityListId.clear();
        cityList.add(new String("Select City"));
        cityListId.add(new String("Select City"));
        citiesViewModel.InitCityViewModel(authorization, "1", corporateId);
        cityAdapter = new ArrayAdapter<String>(AddEmployeesActivity.this, R.layout.spinner_layout, cityList);
        citySpinner.setAdapter(cityAdapter);
        citiesViewModel.getCityLiveData(authorization, "1", corporateId).
                observe(AddEmployeesActivity.this,
                        new Observer<List<City>>() {
                            @Override
                            public void onChanged(List<City> cities) {

                                if (cities != null && cities.size() > 0) {
                                    cityList.clear();
                                    cityListId.clear();
                                    cityList.add(new String("Select City"));
                                    cityListId.add(new String("Select City"));
                                    for (int i = 0; i < cities.size(); i++) {
                                        cityList.add(cities.get(i).getCityName());
                                        cityListId.add(String.valueOf(cities.get(i).getId()));
                                    }


                                    if (venName.equals("UpdateEntity")) {

                                        for (int i = 0; i < cities.size(); i++) {
                                            if (cities.get(i).getId().equals(updatEmployee.getHomeCity())) {
                                                citySpinner.setSelection(i + 1);
                                                break;
                                            }
                                        }

                                    }
                                    cityAdapter.notifyDataSetChanged();
                                    progressDialog.dismiss();
                                }

                            }
                        });


        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (citySpinner.getSelectedItem().toString().equals("Select City")) {
                    //((TextView) assessmentSpinner.getSelectedView()).setError("field required");
                } else {
                    cityId = cityListId.get(citySpinner.getSelectedItemPosition());
                    //gettingLatLng(taxiModelname);
                }
                cityId = cityListId.get(citySpinner.getSelectedItemPosition());
                Log.e("cityId", cityId);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spocList.clear();
        spocIdList.clear();
        spocList.add("Select Spoc");
        spocIdList.add("Select Spoc");
        spocsViewModel.InitSpocsViewModel(authorization, "1", corporateId);
        spocsAdapter = new ArrayAdapter<String>(AddEmployeesActivity.this, R.layout.spinner_layout, spocList);
        spocsSpinner.setAdapter(spocsAdapter);
        spocsViewModel.getCorporateSpocsLiveData(authorization, "1", corporateId).observe(AddEmployeesActivity.this,

                new Observer<List<Spocs>>()
                {
                    @Override
                    public void onChanged(List<Spocs> spocs)
                    {

                        if (spocs != null && spocs.size() > 0) {
                            spocList.clear();
                            spocIdList.clear();
                            spocList.add(new String("Select Spoc"));
                            spocIdList.add(new String("Select Spoc"));
                            for (int i = 0; i < spocs.size(); i++) {
                                spocList.add(spocs.get(i).getUserName());
                                spocIdList.add(String.valueOf(spocs.get(i).getId()));
                            }


                            if (venName.equals("UpdateEntity")) {

                                for (int i = 0; i < spocs.size(); i++) {
                                    if (spocs.get(i).getId().equals(updatEmployee.getSpocId())) {
                                        spocsSpinner.setSelection(i + 1);
                                        break;
                                    }
                                }

                            }
                            spocsAdapter.notifyDataSetChanged();
                            progressDialog.dismiss();
                        }

                    }

                    /*{
                        spocList.clear();
                        spocIdList.clear();
                        spocList.add("Select Spoc");
                        spocIdList.add("Select Spoc");
                        if (spocs != null)
                        {
                            for (int i = 0; i < spocs.size(); i++)
                            {
                                spocList.add(spocs.get(i).getUserName());
                                spocIdList.add(String.valueOf(spocs.get(i).getId()));
                            }
                        }
                        if (venName.equals("UpdateEntity"))
                        {

                            for (int i = 0; i < spocs.size(); i++)
                            {
                                if (spocs.get(i).getId().equals(updatEmployee.getSpocId()))
                                {
                                    spocsSpinner.setSelection(i + 1);
                                    break;
                                }
                            }
                        }
                        spocsAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }*/
                }
        );

        spocsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (spocsSpinner.getSelectedItem().toString().equals("Select Spoc")) {
                    //((TextView) assessmentSpinner.getSelectedView()).setError("field required");
                } else {
                    spocId = spocIdList.get(spocsSpinner.getSelectedItemPosition());
                    //gettingLatLng(taxiModelname);
                }
                spocId = spocIdList.get(spocsSpinner.getSelectedItemPosition());
                Log.e("spocId", spocId);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        billingEntityList.clear();
        billingEntityIdList.clear();
        billingEntityList.add("Billing Entity");
        billingEntityIdList.add("Billing Entity");
        entityViewModel.InitEntitiesViewModel(authorization, "1", corporateId);
        billingEntityAdapter = new ArrayAdapter<String>(AddEmployeesActivity.this, R.layout.spinner_layout, billingEntityList);
        billingSpinner.setAdapter(billingEntityAdapter);
        entityViewModel.getEntitiesLiveData(authorization, "1", corporateId).observe(this,

                new Observer<List<Entities>>() {
                    @Override
                    public void onChanged(List<Entities> entitiesList) {
                        billingEntityList.clear();
                        billingEntityIdList.clear();
                        billingEntityList.add("Billing Entity");
                        billingEntityIdList.add("Billing Entity");
                        if (entitiesList != null) {
                            for (int i = 0; i < entitiesList.size(); i++) {
                                billingEntityList.add(entitiesList.get(i).getEntityName());
                                billingEntityIdList.add(String.valueOf(entitiesList.get(i).getId()));
                            }
                        }

                        if (venName.equals("UpdateEntity")) {

                            for (int i = 0; i < entitiesList.size(); i++) {
                                if (entitiesList.get(i).getId().equals(updatEmployee.getBillingEntityId())) {
                                    billingSpinner.setSelection(i + 1);
                                    break;
                                }
                            }
                        }
                        billingEntityAdapter.notifyDataSetChanged();

                    }
                }
        );

        billingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (billingSpinner.getSelectedItem().toString().equals("Billing Entity")) {
                    // ((TextView) billingSpinner.getSelectedView()).setError("field required");
                } else {
                    billingId = spocIdList.get(billingSpinner.getSelectedItemPosition());
                    //gettingLatLng(taxiModelname);
                }
                billingId = billingEntityIdList.get(billingSpinner.getSelectedItemPosition());
                Log.e("billingId", billingId);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



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

    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id) {
            case R.id.btnSubmit:

                if (isInternetConnected(AddEmployeesActivity.this)) {

                    if (
                            employeeId.getText().toString().length() < 1
                                    || companyId.getText().toString().length() < 1
                                    || empName.getText().toString().length() < 1
                                    || empEmail.getText().toString().length() < 1
                                    || birthDate.getText().toString().length() < 1
                                    || empAddress.getText().toString().length() < 1
                                    || empDesign.getText().toString().length() < 1
                                    || idproofType.getText().toString().length() < 1
                                    || idproofNo.getText().toString().length() < 1
                                    || spocsSpinner.getSelectedItem().toString().length() < 1
                                    || citySpinner.getSelectedItem().toString().length() < 1
                                    || billingSpinner.getSelectedItem().toString().length() < 1

                    ) {

                        if (spocsSpinner.getSelectedItem().toString().equals("Select Spoc")) {
                            ((android.widget.TextView) spocsSpinner.getSelectedView()).setError("field required");
                        }
                        if (citySpinner.getSelectedItem().toString().equals("Select City")) {
                            ((android.widget.TextView) citySpinner.getSelectedView()).setError("field required");
                        }
                        if (billingSpinner.getSelectedItem().toString().equals("Billing Entity")) {
                            ((android.widget.TextView) billingSpinner.getSelectedView()).setError("field required");
                        }

                        if (companyId.getText().toString().length() < 1) {
                            companyId.setError("field required");
                        }
                        if (empName.getText().toString().length() < 1) {
                            empName.setError("field required");
                        }
                        if (empEmail.getText().toString().length() < 1) {
                            empEmail.setError("field required");
                        }
                        if (birthDate.getText().toString().length() < 1) {
                            birthDate.setError("field required");
                        }
                        if (empAddress.getText().toString().length() < 1) {
                            empAddress.setError("field required");
                        }
                        if (empDesign.getText().toString().length() < 1) {
                            empDesign.setError("field required");
                        }
                        if (idproofType.getText().toString().length() < 1) {
                            idproofType.setError("field required");
                        }
                        if (idproofNo.getText().toString().length() < 1) {
                            idproofNo.setError("field required");
                        }
                    }else
                        {

                        int selected = radioGroup.getCheckedRadioButtonId();
                        final RadioButton gender = (RadioButton) findViewById(selected);
                        //Toast.makeText(this," "+gender.getText(),Toast.LENGTH_LONG).show();
                        final Dialog dialog = new Dialog(AddEmployeesActivity.this);
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
                        myes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (venName.equals("AddEntity")) {
                                    Toast.makeText(AddEmployeesActivity.this, "clicked submit employee", Toast.LENGTH_LONG).show();

                                    if (isActive.isChecked()) {
                                        strIsActive = "1";
                                    }
                                    if (isCXO.isChecked()) {
                                        strIsCXO = "1";
                                    }


                                    new AddEmployeeAsyncTask().execute
                                            (
                                                    authorization,
                                                    usertype,
                                                    corporateId,
                                                    userId,
                                                    spocId,
                                                    billingId,
                                                    coreEmployeeId.getText().toString(),
                                                    companyId.getText().toString(),
                                                    empName.getText().toString(),
                                                    empEmail.getText().toString(),
                                                    employeeContact.getText().toString(),
                                                    "empAge",
                                                    gender.getText().toString(),
                                                    idproofType.getText().toString(),
                                                    idproofNo.getText().toString(),
                                                    strIsActive,
                                                    "fcmRegId",
                                                    strIsCXO,
                                                    empDesign.getText().toString(),
                                                    cityId,
                                                    empAddress.getText().toString(),
                                                    "assistentId",
                                                    birthDate.getText().toString(),
                                                    "taxi123",
                                                    empEmail.getText().toString()
                                            );

                                }
                                if (venName.equals("UpdateEntity")) {

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
                } else {

                    Toast.makeText(AddEmployeesActivity.this, "Please Check Internet Connection", Toast.LENGTH_LONG).show();

                }


                break;
        }
    }


    public class AddEmployeeAsyncTask extends AsyncTask<String, Integer, String> {
        AddUtilitiesApi addUtilitiesApi;
        private String assesmentvalue;

        public AddEmployeeAsyncTask() {

        }

        @Override
        protected String doInBackground(String... params) {
            final AlertDialog.Builder[] d = new AlertDialog.Builder[1];
            Log.d("posting data", params.toString());
            addUtilitiesApi = ConfigRetrofit.configRetrofit(AddUtilitiesApi.class);
            addUtilitiesApi.addEmployee(
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
                    params[18].toString(),
                    params[19].toString(),
                    params[20].toString(),
                    params[21].toString(),
                    params[22].toString(),
                    params[22].toString(),
                    params[9].toString()
            ).enqueue(new Callback<AddUtilitiesApiResponse>() {
                @Override
                public void onResponse(Call<AddUtilitiesApiResponse> call, Response<AddUtilitiesApiResponse> response) {
                    if (response.code() == 200) {
                        assesmentvalue = response.body().toString();
                        final Dialog dialog = new Dialog(AddEmployeesActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.custom_dialog_box);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        TextView mDialogTitile = dialog.findViewById(R.id.dialog_title);
                        TextView mDialogmsg = dialog.findViewById(R.id.dialog_message);
                        mDialogTitile.setText("Add Employees Status");
                        mDialogmsg.setText("" + response.body().getMessage());
                        Button myes = dialog.findViewById(R.id.yes_txt);
                        Button mNo = dialog.findViewById(R.id.no_txt);
                        mNo.setVisibility(View.GONE);
                        myes.setText("  Ok  ");
                        myes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent mIntent = new Intent(AddEmployeesActivity.this, MasterActivity.class);
                                Bundle mBundle = new Bundle();
                                mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                mBundle.putString("Value", "addEmployees");
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
            pd = new ProgressDialog(AddEmployeesActivity.this);
            pd.setMessage("Processing");
            d = new android.app.AlertDialog.Builder(AddEmployeesActivity.this);
            d.setTitle("Adding New Spoc");
            pd.show();
        }

        @Override
        protected void onPostExecute(String s) {
            pd.dismiss();
        }
    }

    public void updateEntity(String employeeId) {
        {

            //authorization,userType,corporateId,userId,spocId,billEntityId
            //coreEmpId,empCId,empName,empEmail,empContact,empAge,empGender,idProofType,idProofNo,isActive,fcmRegId
            //isCXO,isCXO,empDesigntn,homeCity,homeAddress,assistentId,dateOfBirth,password,username
            Toast.makeText(this, "Update Employee Clicked", Toast.LENGTH_LONG).show();
            int selected = radioGroup.getCheckedRadioButtonId();
            final RadioButton gender = (RadioButton) findViewById(selected);
            new UpdateEmployeeAsyncTask().execute
                    (
                            authorization,
                            usertype,
                            corporateId,
                            userId,
                            spocId,
                            billingId,
                            coreEmployeeId.getText().toString(),
                            companyId.getText().toString(),
                            empName.getText().toString(),
                            empEmail.getText().toString(),
                            employeeContact.getText().toString(),
                            "empAge",
                            gender.getText().toString(),
                            idproofType.getText().toString(),
                            idproofNo.getText().toString(),
                            strIsActive,
                            "fcmRegId",
                            strIsCXO,
                            empDesign.getText().toString(),
                            cityId,
                            empAddress.getText().toString(),
                            "assistentId",
                            birthDate.getText().toString(),
                            "taxi123",
                            empEmail.getText().toString(),
                            employeeId
                    );
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

    public class UpdateEmployeeAsyncTask extends AsyncTask<String, Integer, String> {
        AddUtilitiesApi addUtilitiesApi;
        private String assesmentvalue;

        public UpdateEmployeeAsyncTask()
        {

        }


        @Override
        protected String doInBackground(String... params) {
            String Auth = params[0].toString();
            String usertype = params[1].toString();
            String corporateId = params[2].toString();
            String userId = params[3].toString();
            final AlertDialog.Builder[] d = new AlertDialog.Builder[1];
            Log.d("posting data", params.toString());

            addUtilitiesApi = ConfigRetrofit.configRetrofit(AddUtilitiesApi.class);
            addUtilitiesApi.updateEmployee(
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
                    params[18].toString(),
                    params[19].toString(),
                    params[20].toString(),
                    params[21].toString(),
                    params[22].toString(),
                    params[23].toString(),
                    params[24].toString(),
                    params[25].toString()
            ).enqueue(new Callback<AddUtilitiesApiResponse>() {
                @Override
                public void onResponse(Call<AddUtilitiesApiResponse> call, Response<AddUtilitiesApiResponse> response) {
                    if (response.code() == 200) {
                        assesmentvalue = response.body().toString();
                        final Dialog dialog = new Dialog(AddEmployeesActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.custom_dialog_box);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        TextView mDialogTitile = dialog.findViewById(R.id.dialog_title);
                        TextView mDialogmsg = dialog.findViewById(R.id.dialog_message);
                        mDialogTitile.setText("Update Employees Status");
                        mDialogmsg.setText("" + response.body().getMessage());
                        Button myes = dialog.findViewById(R.id.yes_txt);
                        Button mNo = dialog.findViewById(R.id.no_txt);
                        mNo.setVisibility(View.GONE);
                        myes.setText("  Ok  ");
                        myes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent mIntent = new Intent(AddEmployeesActivity.this, MasterActivity.class);
                                Bundle mBundle = new Bundle();
                                mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                mBundle.putString("Value", "addEmployees");
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
            pd = new ProgressDialog(AddEmployeesActivity.this);
            pd.setMessage("Adding Employee");
            d = new android.app.AlertDialog.Builder(AddEmployeesActivity.this);
            d.setTitle("Add Employee");
            pd.show();
        }


        @Override
        protected void onPostExecute(String s) {
            pd.dismiss();
        }
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        String id;
        private String start_date_send;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = 0;
            int month = 0;
            int day = 0;

            id=new String();
            id=getArguments().getString("start");

            if(venName.equals("UpdateEntity"))
            {
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                String birth_date = updatEmployee.getDateOfBirth();
                if (id.equals("start"))
                {
                    String [] dateParts = birth_date.split("-");
                    day= Integer.parseInt(dateParts[0]);
                    month= Integer.parseInt(dateParts[1]);
                    month=month-1;
                    year= Integer.parseInt(dateParts[2]);
                }

            }
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month , day);
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            return datePickerDialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            final Calendar c = Calendar.getInstance();
            c.set(year, month, day);
            if (id.equals("start")) {

                if (c.compareTo(Calendar.getInstance()) <= 0) {

                    birthDate.setText(day + "-" + month + "-" + year);

                } else
                {

                    Toasty.error(getActivity(), "future dates are not allowed", Toast.LENGTH_SHORT).show();

                }
            }

        }
    }
}
