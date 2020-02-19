package com.cotrav.cotrav_admin.ui.utilities.groups;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cotrav.cotrav_admin.GsonStringConvertor;
import com.cotrav.cotrav_admin.MasterActivity;
import com.cotrav.cotrav_admin.R;
import com.cotrav.cotrav_admin.adapter.GroupsAuthAdapter;
import com.cotrav.cotrav_admin.model.entities_model.AddUtilitiesApiResponse;
import com.cotrav.cotrav_admin.model.group_model.Group;
import com.cotrav.cotrav_admin.model.group_model.GroupResponse;
import com.cotrav.cotrav_admin.retrofit.AddUtilitiesApi;
import com.cotrav.cotrav_admin.retrofit.ConfigRetrofit;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddGroupsActivity extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    EditText textGroupName,textZoneName,textAuthName,textEmail,textCompanyId,textAuthContact;
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
    static String venName = null;
    String entId;
    GroupResponse groupResponse;
    RecyclerView recyclerview;
    LinearLayout permissionLay;
    private GroupsViewModel groupViewModel;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Group> groupAuthList;
    private GroupsAuthAdapter groupAdapter;
    private LinearLayout connectionErrorLayout;
    TextView connectionErrorTextView;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_groups);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_to_refresh);
        textGroupName=findViewById(R.id.text_groupName);
        textZoneName=findViewById(R.id.text_zoneName);
        textAuthName=findViewById(R.id.text_authName);
        textEmail=findViewById(R.id.text_authEmail);
        recyclerview=findViewById(R.id.recycler_view);
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

        connectionErrorLayout=(LinearLayout) findViewById(R.id.error_empty_layout);
        connectionErrorTextView=(TextView)findViewById(R.id.no_booking_connection_error);
        permissionLay=findViewById(R.id.permissions_lay);
        buttonSubmit=(Button)findViewById(R.id.btnSubmit);
        buttonSubmit.setOnClickListener(this);
        groupAuthList =new ArrayList<>();
        loginPref = getSharedPreferences("login_info", MODE_PRIVATE);
        userType = loginPref.getString("usertype", "n");
        userId = loginPref.getString("user_id", "n");
        token = loginPref.getString("access_token", "n");
        Authorization = "Token " + loginPref.getString("access_token", "n");
        corporateId = loginPref.getString("corporate_id", "n");
        Bundle bundle = getIntent().getExtras();
        entId = bundle.getString("GroupId");
        venName = bundle.getString("EntityTask");
        mtoolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);
        groupViewModel = ViewModelProviders.of(this).get(GroupsViewModel.class);
        groupViewModel.InitAdminAuthViewModel(Authorization,userType,entId);
        groupAdapter = new GroupsAuthAdapter(AddGroupsActivity.this, groupAuthList,Authorization,entId);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(groupAdapter);
        if (getSupportActionBar() != null) {
            if(venName.equals("UpdateEntity")) {
                getSupportActionBar().setTitle("Update Group");
            }else {
                getSupportActionBar().setTitle("Add Group");
            }
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            mtoolbar.setNavigationIcon(R.drawable.ic_back);
        }
        if(venName.equals("UpdateEntity")) {
            if (!bundle.getString("details","n").equals("n"))
            {
                buttonSubmit.setText("Update");
                swipeRefreshLayout.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setRefreshing(true);
                recyclerview.setVisibility(View.VISIBLE);
                permissionLay.setVisibility(View.GONE);
                groupResponse = GsonStringConvertor.stringToGson(bundle.getString("details", "n"), GroupResponse.class);
                textGroupName.setText(groupResponse.getGroupName());
                textZoneName.setText(groupResponse.getZoneName());


            }
        }

        groupViewModel.getAdminAuthLiveData(Authorization,userType,entId).observe(this,
                new Observer<List<Group>>() {
                    @Override
                    public void onChanged(List<Group> groupsList) {


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


                if (isInternetConnected(AddGroupsActivity.this)) {


                    if(venName.equals("UpdateEntity"))
                    {
                        if (
                                        textGroupName.getText().toString().length() < 1
                                        ||textZoneName.getText().toString().length()<1)
                        {
                            if (textGroupName.getText().toString().length() < 1)
                            {
                                textGroupName.setError("field required");
                            }
                            if (textZoneName.getText().toString().length() < 1)
                            {
                                textZoneName.setError("field required");
                            }




                        }
                        updateEntity(entId);


                    }else if(venName.equals("AddEntity"))
                        {
                            if (
                                    textGroupName.getText().toString().length() < 1
                                            ||textZoneName.getText().toString().length()<1
                                            ||textAuthName.getText().toString().length()<1
                                            || textEmail.getText().toString().length()<1
                                            ||textCompanyId.getText().toString().length()<1
                                            ||textAuthContact.getText().toString().length()<1

                            ){

                                checkradiobutton();

                                if (textGroupName.getText().toString().length() < 1)
                                {
                                    textGroupName.setError("field required");
                                }
                                if (textZoneName.getText().toString().length() < 1)
                                {
                                    textZoneName.setError("field required");
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
                            }else
                            {
                                final Dialog dialog = new Dialog(AddGroupsActivity.this);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setCancelable(false);
                                dialog.setContentView(R.layout.custom_dialog_box);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                TextView mDialogTitile = dialog.findViewById(R.id.dialog_title);
                                TextView mDialogmsg = dialog.findViewById(R.id.dialog_message);
                                mDialogTitile.setText("Add New Group");
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


                                            new AddGroupsAsyncTask().execute
                                                    (
                                                            Authorization,
                                                            userType,
                                                            corporateId,
                                                            userId,
                                                            textGroupName.getText().toString(),
                                                            textZoneName.getText().toString(),
                                                            textAuthName.getText().toString(),
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

                    Toast.makeText(AddGroupsActivity.this,"Please Check Internet Connection",Toast.LENGTH_LONG).show();

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


    public class AddGroupsAsyncTask extends AsyncTask<String, Integer, String> {
        AddUtilitiesApi addUtilitiesApi;
        private String assesmentvalue;
        public AddGroupsAsyncTask()
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
            addUtilitiesApi.addAdminGroup(
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
                    params[19].toString()

            ).enqueue(new Callback<AddUtilitiesApiResponse>() {
                @Override
                public void onResponse(Call<AddUtilitiesApiResponse> call, Response<AddUtilitiesApiResponse> response) {
                    if (response.code() == 200) {
                        assesmentvalue = response.body().toString();
                        final Dialog dialog = new Dialog(AddGroupsActivity.this);
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

                                Intent mIntent = new Intent(AddGroupsActivity.this, MasterActivity.class);
                                Bundle mBundle = new Bundle();
                                mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                mBundle.putString("Value","addGroup" );
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
            pd = new ProgressDialog(AddGroupsActivity.this);
            pd.setMessage("Processing");
            d = new android.app.AlertDialog.Builder(AddGroupsActivity.this);
            d.setTitle("Adding New Entity");
            pd.show();
        }

        @Override
        protected void onPostExecute(String s)
        {
            pd.dismiss();
        }
    }
    public class UpdateGroupAsyncTask extends AsyncTask<String, Integer, String> {
        AddUtilitiesApi addUtilitiesApi;
        private String assesmentvalue;
        public UpdateGroupAsyncTask()
        {

        }


        @Override
        protected String doInBackground(String... params) {
            String Auth = params[0].toString();
            String usertype = params[1].toString();
            String corporateId=params[2].toString();
            String userId=params[3].toString();
            final androidx.appcompat.app.AlertDialog.Builder[] d = new androidx.appcompat.app.AlertDialog.Builder[1];
            Log.d("posting data", params.toString());
            //System Date Added From Mobile

            addUtilitiesApi = ConfigRetrofit.configRetrofit(AddUtilitiesApi.class);
            addUtilitiesApi.updateAdminGroup(
                    params[0].toString(),
                    params[1].toString(),
                    params[2].toString(),
                    params[3].toString(),
                    params[4].toString(),
                    params[5].toString(),
                    entId
            ).enqueue(new Callback<AddUtilitiesApiResponse>() {
                @Override
                public void onResponse(Call<AddUtilitiesApiResponse> call, Response<AddUtilitiesApiResponse> response)
                {
                    if (response.code() == 200) {
                        assesmentvalue = response.body().toString();
                        final Dialog dialog = new Dialog(AddGroupsActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.custom_dialog_box);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        TextView mDialogTitile = dialog.findViewById(R.id.dialog_title);
                        TextView mDialogmsg = dialog.findViewById(R.id.dialog_message);

                        mDialogTitile.setText("Update Group Status");
                        mDialogmsg.setText("" + response.body().getMessage());

                        Button myes = dialog.findViewById(R.id.yes_txt);
                        Button mNo = dialog.findViewById(R.id.no_txt);
                        mNo.setVisibility(View.GONE);
                        myes.setText("  Ok  ");
                        myes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent mIntent = new Intent(AddGroupsActivity.this, MasterActivity.class);
                                Bundle mBundle = new Bundle();
                                mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                mBundle.putString("Value","addGroup" );
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
            pd = new ProgressDialog(AddGroupsActivity.this);
            pd.setMessage("Processing");
            d = new android.app.AlertDialog.Builder(AddGroupsActivity.this);
            d.setTitle("Adding New Entity");
            pd.show();
        }

        @Override
        protected void onPostExecute(String s)
        {
            pd.dismiss();
        }
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        groupViewModel.refreshAdminAuthGroups(Authorization,userType,entId);
    }

    @Override
    public void onResume() {
        super.onResume();
        // swipeRefreshLayout.setRefreshing(true);
        groupViewModel.refreshAdminAuthGroups(Authorization,userType,entId);
    }

    public void updateEntity(String entityId)
    {
        new UpdateGroupAsyncTask().execute(
                Authorization,
                userType,
                corporateId,
                userId,
                textGroupName.getText().toString(),
                textZoneName.getText().toString());
    }

}
