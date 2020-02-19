package com.cotrav.cotrav_admin.ui.utilities.subgroups;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cotrav.cotrav_admin.GsonStringConvertor;
import com.cotrav.cotrav_admin.MasterActivity;
import com.cotrav.cotrav_admin.R;
import com.cotrav.cotrav_admin.adapter.GroupsAuthAdapter;
import com.cotrav.cotrav_admin.adapter.SubGroupsAdapter;
import com.cotrav.cotrav_admin.model.entities_model.AddUtilitiesApiResponse;
import com.cotrav.cotrav_admin.model.group_model.Group;
import com.cotrav.cotrav_admin.model.group_model.GroupResponse;
import com.cotrav.cotrav_admin.model.subgroup_model.Subgroup;
import com.cotrav.cotrav_admin.retrofit.AddUtilitiesApi;
import com.cotrav.cotrav_admin.retrofit.ConfigRetrofit;
import com.cotrav.cotrav_admin.ui.utilities.groups.AddGroupsActivity;
import com.cotrav.cotrav_admin.ui.utilities.groups.GroupsViewModel;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSubGroupsActivity extends AppCompatActivity implements View.OnClickListener {

    EditText textGroupName,textAuthName,textEmail,textCompanyId,textAuthContact;
    String strLocal="0",strRadio="0",strOutstation="0",strBus="0",strTrain="0",strFlight="0",strHotel="0",strMeal="0",strWater="0",strLogistic="0";
    CheckBox checkLocal,checkRadio,checkOutstation,checkBus,checkTrain,checkFlight,checkHotel,checkMeal,checkWater,checkLogistic;
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
    GroupResponse groupResponse;
    ArrayList<String> groupList, groupIdList;
    SearchableSpinner groupSpinner;
    GroupsViewModel groupViewModel;
    SubGroupsViewModel subGroupsViewModel;
    private LinearLayout connectionErrorLayout;
    ArrayAdapter<String> groupAdapter;
    private SubGroupsAdapter groupAuthAdapter;
    String groupId;
    TextView connectionErrorTextView;
    private List<Subgroup> groupAuthList;
    LinearLayout permissionLay;
    private SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerview;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sub_groups);

        progressDialog = new ProgressDialog(AddSubGroupsActivity.this);
        progressDialog.setMessage("Getting Groups");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        recyclerview=findViewById(R.id.recycler_view);
        connectionErrorLayout=(LinearLayout) findViewById(R.id.error_empty_layout);
        connectionErrorTextView=(TextView)findViewById(R.id.no_booking_connection_error);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_to_refresh);
        groupSpinner=findViewById(R.id.spinnerSelectGroup);
        textGroupName=findViewById(R.id.text_groupName);
        textAuthName=findViewById(R.id.text_authName);
        textEmail=findViewById(R.id.text_authEmail);
        textCompanyId=findViewById(R.id.text_authCompanyId);
        textAuthContact=findViewById(R.id.text_authContact);
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
        permissionLay=findViewById(R.id.permissions_lay);
        buttonSubmit=(Button)findViewById(R.id.btnSubmit);
        buttonSubmit.setOnClickListener(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(groupAuthAdapter);


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

        subGroupsViewModel = ViewModelProviders.of(this).get(SubGroupsViewModel.class);
        subGroupsViewModel.InitAdminSubGroupsViewModel(Authorization,userType,entId);
        groupList = new ArrayList<>();
        groupIdList = new ArrayList<>();
        groupAuthList=new ArrayList<>();

        groupAdapter = new ArrayAdapter<>(this,R.layout.spinner_layout,groupList);
        if (getSupportActionBar() != null) {
            if(venName.equals("UpdateEntity")) {
                permissionLay.setVisibility(View.GONE);
                buttonSubmit.setText("Update");
                getSupportActionBar().setTitle("Update Sub Group");

            }else {
                getSupportActionBar().setTitle("Add Sub Group");
            }
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            mtoolbar.setNavigationIcon(R.drawable.ic_back);
        }
        if(venName.equals("UpdateEntity")) {
            if (!bundle.getString("details","n").equals("n"))
            {
                swipeRefreshLayout.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setRefreshing(true);
                recyclerview.setVisibility(View.VISIBLE);
                groupResponse = GsonStringConvertor.stringToGson(bundle.getString("details", "n"), GroupResponse.class);
                textGroupName.setText(groupResponse.getGroupName());
                textAuthName.setText(groupResponse.getCorporateName());
            }
        }


        groupList.clear();
        groupIdList.clear();
        groupList.add("Select Group");
        groupIdList.add("Select Group");
        groupViewModel=ViewModelProviders.of(this).get(GroupsViewModel.class);
        groupViewModel.InitAdminGroupsViewModel(Authorization,userType,corporateId);
        groupSpinner.setAdapter(groupAdapter);
        groupViewModel.getAdminGroupsLiveData(Authorization,userType,corporateId).observe(this,

                new Observer<List<GroupResponse>>() {
                    @Override
                    public void onChanged(List<GroupResponse> groups) {
                        groupList.clear();
                        groupIdList.clear();
                        groupList.add("Select Group");
                        groupIdList.add("Select Group");
                        if (groups != null) {
                            for (int i = 0; i < groups.size(); i++) {
                                groupList.add(groups.get(i).getGroupName());
                                groupIdList.add(String.valueOf(groups.get(i).getId()));
                            }
                        }
                        if (venName.equals("UpdateEntity")) {

                            for (int i = 0; i < groups.size(); i++) {
                                if (groups.get(i).getId().equals(groupResponse.getId())) {
                                    groupSpinner.setSelection(i+1);
                                    break;
                                }
                            }
                        }
                        groupAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }
                }
        );

        groupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(groupSpinner.getSelectedItem().toString().equals("Select Spoc")){
                    //((TextView) assessmentSpinner.getSelectedView()).setError("field required");
                }else{
                    groupId=groupIdList.get(groupSpinner.getSelectedItemPosition());
                    //gettingLatLng(taxiModelname);
                }
                groupId=groupIdList.get(groupSpinner.getSelectedItemPosition());
                Log.e("assCodeId",groupId);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });


        subGroupsViewModel.getAdminSubGroupsLiveData(Authorization,userType,entId).observe(this,
                new Observer<List<Subgroup>>() {
                    @Override
                    public void onChanged(List<Subgroup> groupsList) {


                        if (swipeRefreshLayout.isRefreshing()){
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        if (groupsList!=null && groupsList.size()>0){
                            connectionErrorLayout.setVisibility(View.GONE);
                            groupAuthList.clear();
                            groupAuthList.addAll(groupsList);


                        }
                        else if (groupsList!=null && groupsList.size()==0){
                            connectionErrorTextView.setText("No Authenticators Available");
                            groupsList.clear();
                            connectionErrorLayout.setVisibility(View.VISIBLE);
                        }
                        groupAdapter.notifyDataSetChanged();

                    }
                });

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
                checkradiobutton();
                if (isInternetConnected(AddSubGroupsActivity.this)) {

                    if (
                            textGroupName.getText().toString().length() < 1
                                    ||textAuthName.getText().toString().length()<1
                                    || textEmail.getText().toString().length()<1
                                    ||textCompanyId.getText().toString().length()<1
                                    ||textAuthContact.getText().toString().length()<1
                                    ||groupSpinner.getSelectedItem().toString().length() < 1

                    ){


                        if (textGroupName.getText().toString().length() < 1)
                        {
                            textGroupName.setError("field required");
                        }
                        if (textAuthName.getText().toString().length() < 1)
                        {
                            textAuthName.setError("field required");
                        }

                        if (textEmail.getText().toString().length() < 1)
                        {
                            textEmail.setError("field required");
                        }
                        if (textCompanyId.getText().toString().length() < 1)
                        {
                            textCompanyId.setError("field required");
                        }
                        if (textAuthContact.getText().toString().length() < 1)
                        {
                            textAuthContact.setError("field required");
                        }
                        if (groupSpinner.getSelectedItem().toString().equals("Select Group")) {
                            ((android.widget.TextView) groupSpinner.getSelectedView()).setError("field required");
                        }
                    }else
                    {
                        final Dialog dialog = new Dialog(AddSubGroupsActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.custom_dialog_box);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        TextView mDialogTitile = dialog.findViewById(R.id.dialog_title);
                        TextView mDialogmsg = dialog.findViewById(R.id.dialog_message);
                        mDialogTitile.setText("Add Sub Group");
                        mDialogmsg.setText("Please Press Yes to Add New Group");
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


                                    new AddSubGroupsAsyncTask().execute
                                            (
                                                    Authorization,
                                                    userType,
                                                    corporateId,
                                                    userId,
                                                    textGroupName.getText().toString(),
                                                    groupId,textAuthName.getText().toString(),
                                                    textEmail.getText().toString(),
                                                    textCompanyId.getText().toString(),
                                                    textAuthContact.getText().toString(),
                                                    strRadio,
                                                    strLocal,
                                                    strOutstation,
                                                    strBus,
                                                    strTrain,
                                                    strHotel,
                                                    strMeal,
                                                    strFlight,
                                                    strWater,
                                                    strLogistic
                                            );
                                }
                                if(venName.equals("UpdateEntity"))
                                {
                                    //updateEntity(entId);

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

                    Toast.makeText(AddSubGroupsActivity.this,"Please Check Internet Connection",Toast.LENGTH_LONG).show();

                }

                break;

        }
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

    public class AddSubGroupsAsyncTask extends AsyncTask<String, Integer, String> {
        AddUtilitiesApi addUtilitiesApi;
        private String assesmentvalue;
        public AddSubGroupsAsyncTask()
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
            addUtilitiesApi.addAdminSubGroup(
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
                    params[18].toString()
                    ,Auth,"taxi123"

            ).enqueue(new Callback<AddUtilitiesApiResponse>() {
                @Override
                public void onResponse(Call<AddUtilitiesApiResponse> call, Response<AddUtilitiesApiResponse> response) {
                    if (response.code() == 200) {
                        assesmentvalue = response.body().toString();
                        final Dialog dialog = new Dialog(AddSubGroupsActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.custom_dialog_box);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        TextView mDialogTitile = dialog.findViewById(R.id.dialog_title);
                        TextView mDialogmsg = dialog.findViewById(R.id.dialog_message);
                        mDialogTitile.setText("Add SubGroup Status");
                        mDialogmsg.setText("" + response.body().getMessage());
                        Button myes = dialog.findViewById(R.id.yes_txt);
                        Button mNo = dialog.findViewById(R.id.no_txt);
                        mNo.setVisibility(View.GONE);
                        myes.setText("  Ok  ");
                        myes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent mIntent = new Intent(AddSubGroupsActivity.this, MasterActivity.class);
                                Bundle mBundle = new Bundle();
                                mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                mBundle.putString("Value","addSubGroup" );
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
            pd = new ProgressDialog(AddSubGroupsActivity.this);
            pd.setMessage("Processing");
            d = new android.app.AlertDialog.Builder(AddSubGroupsActivity.this);
            d.setTitle("Adding New SubGroup");
            pd.show();
        }

        @Override
        protected void onPostExecute(String s)
        {
            pd.dismiss();
        }    }


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
                        Toast.makeText(getApplicationContext(), "Sub Group Updated :" + response.body().getMessage(), Toast.LENGTH_SHORT).show();


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

        }


        @Override
        protected void onPostExecute(String s) {

            //pd.dismiss();

        }
    }

}
