package com.cotrav.cotrav_admin.ui.utilities.spocs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cotrav.cotrav_admin.GsonStringConvertor;
import com.cotrav.cotrav_admin.MasterActivity;
import com.cotrav.cotrav_admin.R;
import com.cotrav.cotrav_admin.model.entities_model.AddUtilitiesApiResponse;
import com.cotrav.cotrav_admin.model.group_model.GroupResponse;
import com.cotrav.cotrav_admin.model.spoc_model.Spocs;
import com.cotrav.cotrav_admin.model.subgroup_model.Subgroup;
import com.cotrav.cotrav_admin.retrofit.AddUtilitiesApi;
import com.cotrav.cotrav_admin.retrofit.ConfigRetrofit;
import com.cotrav.cotrav_admin.ui.utilities.groups.GroupsViewModel;
import com.cotrav.cotrav_admin.ui.utilities.subgroups.SubGroupsViewModel;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSpocsActivity extends AppCompatActivity implements View.OnClickListener
{
    EditText txt_userName,txt_userEmail,txt_userContact,txt_userCID;
    SearchableSpinner textSelectGroup,textSelectSubGroup;
    String strLocal="0",strRadio="0",strOutstation="0",strBus="0",strTrain="0",strFlight="0",strHotel="0",strMeal="0",strWater="0",strLogistic="0";
    CheckBox checkLocal,checkRadio,checkOutstation,checkBus,checkTrain,checkFlight,checkHotel,checkMeal,checkWater,checkLogistic;
    private GroupsViewModel groupViewModel;
    private SubGroupsViewModel subgroupViewModel;
    Button btnSubmit;
    private Toolbar mtoolbar;
    SharedPreferences loginPref;
    String corporate_id = "";
    String token ="";
    String authorization = "";
    String usertype = "";
    String userId = "";
    private List<String> adminGroupsList;
    private List<String> adminGroupsIdList;
    private List<String> adminSubGroupsList;
    private List<String> adminSubGroupsIdList;
    ArrayAdapter<String> allcityadapter;
    String venName = null;
    String entId;
    ProgressDialog progressDialog;
    Spocs spocsResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_spocs);

        progressDialog = new ProgressDialog(AddSpocsActivity.this);
        progressDialog.setMessage("Getting Cities");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        groupViewModel = ViewModelProviders.of(this).get(GroupsViewModel.class);
        subgroupViewModel=ViewModelProviders.of(this).get(SubGroupsViewModel.class);
        loginPref=getSharedPreferences("login_info", Context.MODE_PRIVATE);
        token = loginPref.getString("access_token","n");
        authorization = "Token "+token;
        usertype = loginPref.getString("usertype", "n");
        userId=loginPref.getString("user_id", "n");
        corporate_id = loginPref.getString("corporate_id", "n");
        Bundle bundle = getIntent().getExtras();
        entId = bundle.getString("EntityId");
        venName = bundle.getString("EntityTask");
        mtoolbar =  findViewById(R.id.toolbar);
        groupViewModel.InitAdminGroupsViewModel(authorization,usertype,corporate_id);
        subgroupViewModel.InitAdminSubGroupsViewModel(authorization,usertype,corporate_id);
        adminGroupsList =new ArrayList<>();
        adminSubGroupsList =new ArrayList<>();
        adminGroupsIdList =new ArrayList<>();
        adminSubGroupsIdList =new ArrayList<>();

        txt_userName=findViewById(R.id.text_userName);
        txt_userEmail=findViewById(R.id.text_contactEmail);
        txt_userContact=findViewById(R.id.text_ContactNo);
        txt_userCID=findViewById(R.id.text_userCID);
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
        if(venName.equals("UpdateEntity"))
        {
            if (!bundle.getString("details","n").equals("n")){
                spocsResponse = GsonStringConvertor.stringToGson(bundle.getString("details","n"), Spocs.class);

            }
        }

        if(venName.equals("UpdateEntity")) {
            btnSubmit.setText("Update");
            txt_userName.setText(spocsResponse.getUserName());
            txt_userEmail.setText(spocsResponse.getEmail());
            txt_userContact.setText(spocsResponse.getUserContact());
            txt_userCID.setText(spocsResponse.getUserCid());
          /*  textSelectGroup.setSelection(spocsResponse.getGroupId());
            textSelectSubGroup.setSelection(spocsResponse.getSubgroupId());*/
            if (spocsResponse.getIsLocal()==1)
                checkLocal.setChecked(true);
            if (spocsResponse.getIsLocal()==1)
                checkLocal.setChecked(true);
            if (spocsResponse.getIsRadio()==1)
                checkRadio.setChecked(true);
            if (spocsResponse.getIsOutstation()==1)
                checkOutstation.setChecked(true);
            if (spocsResponse.getIsBus()==1)
                checkBus.setChecked(true);
            if (spocsResponse.getIsTrain()==1)
                checkTrain.setChecked(true);
            if (spocsResponse.getIsHotel()==1)
                checkHotel.setChecked(true);
            if (spocsResponse.getIsFlight()==1)
                checkFlight.setChecked(true);
            if (spocsResponse.getIsMeal()==1)
                checkMeal.setChecked(true);
            if (spocsResponse.getIsWaterBottles()==1)
                checkWater.setChecked(true);
            if (spocsResponse.getIsReverseLogistics()==1)
                checkLogistic.setChecked(true);
        }

        setSupportActionBar(mtoolbar);
        if (getSupportActionBar() != null) {
            if(venName.equals("UpdateEntity")) {
                getSupportActionBar().setTitle("Update Spocs");
            }else {
                getSupportActionBar().setTitle("Add Spocs");
            }
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            mtoolbar.setNavigationIcon(R.drawable.ic_back);
        }



        textSelectGroup = (SearchableSpinner) findViewById(R.id.spinner_groups);
        adminGroupsList.add(new String("Select Group"));
        adminGroupsIdList.add(new String("Select Group"));
        allcityadapter = new ArrayAdapter<String>(this, R.layout.spinner_item, adminGroupsList);
        textSelectGroup.setAdapter(allcityadapter);
        groupViewModel.getAdminGroupsLiveData(authorization,usertype,corporate_id).observe(this,
                new Observer<List<GroupResponse>>()
                {
                    @Override
                    public void onChanged(List<GroupResponse> groupsList)
                    {
                        if (groupsList != null && groupsList.size() > 0)
                        {
                            adminGroupsList.clear();
                            adminGroupsIdList.clear();
                            adminGroupsList.add(new String("Select Group"));
                            adminGroupsIdList.add(new String("Select Group"));
                            for (int i = 0; i < groupsList.size(); i++) {
                                adminGroupsList.add(groupsList.get(i).getGroupName().toString());
                                adminGroupsIdList.add(String.valueOf(groupsList.get(i).getId()));
                            }

                            if (venName.equals("UpdateEntity")) {

                                for (int i = 0; i < groupsList.size(); i++) {
                                    if (groupsList.get(i).getId().equals(spocsResponse.getGroupId())) {
                                        textSelectGroup.setSelection(i+1);
                                        break;
                                    }
                                }
                            }
                            allcityadapter.notifyDataSetChanged();
                            progressDialog.dismiss();

                        }
                        else if (groupsList!=null && groupsList.size()==0)
                        {
                            adminGroupsList.clear();
                            adminGroupsIdList.clear();
                            adminGroupsList.add(new String("No  Group "));
                            adminGroupsIdList.add(new String("Select Group"));

                        }
                    }
                });

        textSelectSubGroup = (SearchableSpinner) findViewById(R.id.spinner_subgrops);
        adminSubGroupsList.add(new String("Select SubGroup"));
        adminSubGroupsIdList.add(new String("Select SubGroup"));
        allcityadapter = new ArrayAdapter<String>(this, R.layout.spinner_item, adminSubGroupsList);
        textSelectSubGroup.setAdapter(allcityadapter);
        subgroupViewModel.getAdminSubGroupsLiveData(authorization,usertype,corporate_id).observe(this,
                new Observer<List<Subgroup>>() {
                    @Override
                    public void onChanged(List<Subgroup> subgroupsList) {

                        if (subgroupsList != null && subgroupsList.size() > 0) {
                            adminSubGroupsList.clear();
                            adminSubGroupsIdList.clear();
                            adminSubGroupsList.add(new String("Select SubGroup"));
                            adminSubGroupsIdList.add(new String("Select SubGroup"));
                            for (int i = 0; i < subgroupsList.size(); i++) {
                                adminSubGroupsList.add(subgroupsList.get(i).getSubgroupName().toString());
                                adminSubGroupsIdList.add(String.valueOf(subgroupsList.get(i).getGroupId()));
                            }
                            allcityadapter.notifyDataSetChanged();
                            if (venName.equals("UpdateEntity")) {

                                for (int i = 0; i < subgroupsList.size(); i++) {
                                    if (subgroupsList.get(i).getId().equals(spocsResponse.getSubgroupId())) {
                                        textSelectSubGroup.setSelection(i+1);
                                        break;
                                    }
                                }

                            }

                        }
                        else if (subgroupsList!=null && subgroupsList.size()==0)
                        {
                            adminSubGroupsList.clear();
                            adminSubGroupsIdList.clear();
                            adminSubGroupsList.add(new String("No SubGroup"));
                            adminSubGroupsIdList.add(new String("No SubGroup"));

                        }
                    }
                });
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
                if (isInternetConnected(AddSpocsActivity.this)) {

                    if (txt_userName.getText().toString().length() < 1
                                    ||txt_userEmail.getText().toString().length()<1
                                    ||txt_userContact.getText().toString().length()<1
                                    || txt_userCID.getText().toString().length()<1
                                    ||textSelectGroup.getSelectedItem().toString().length() < 1
                                    ||textSelectSubGroup.getSelectedItem().toString().length() < 1){

                        if (textSelectGroup.getSelectedItem().toString().equals("Select Group")) {
                            ((android.widget.TextView) textSelectGroup.getSelectedView()).setError("field required");
                        }
                        if (textSelectSubGroup.getSelectedItem().toString().equals("Select SubGroup")) {
                            ((android.widget.TextView) textSelectSubGroup.getSelectedView()).setError("field required");
                        }
                        if (txt_userName.getText().toString().length() < 1)
                        {
                            txt_userName.setError("field required");
                        }
                        if (txt_userEmail.getText().toString().length() < 1)
                        {
                            txt_userEmail.setError("field required");
                        }
                        if (txt_userContact.getText().toString().length() < 1)
                        {
                            txt_userContact.setError("field required");
                        }

                        if (txt_userCID.getText().toString().length() < 1)
                        {
                            txt_userCID.setError("field required");
                        }
                    }else
                    {

                        if (venName.equals("AddEntity"))
                        {
                            final Dialog dialog = new Dialog(AddSpocsActivity.this);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setCancelable(false);
                            dialog.setContentView(R.layout.custom_dialog_box);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            TextView mDialogTitile = dialog.findViewById(R.id.dialog_title);
                            TextView mDialogmsg = dialog.findViewById(R.id.dialog_message);
                            mDialogTitile.setText("Add Spoc");
                            mDialogmsg.setText("Please Press Yes to Add New Spoc");
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

                                        new AddSpocsAsyncTask().execute
                                                (
                                                        authorization,
                                                        usertype,
                                                        corporate_id,
                                                        userId,
                                                        adminGroupsIdList.get(textSelectGroup.getSelectedItemPosition()),
                                                        adminSubGroupsIdList.get(textSelectSubGroup.getSelectedItemPosition()),
                                                        txt_userCID.getText().toString(),
                                                        txt_userName.getText().toString(),
                                                        txt_userContact.getText().toString(),
                                                        txt_userEmail.getText().toString(),
                                                        txt_userName.getText().toString(),
                                                        "budjet",
                                                        "expence",
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
                                                        token,
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
                            final Dialog dialog = new Dialog(AddSpocsActivity.this);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setCancelable(false);
                            dialog.setContentView(R.layout.custom_dialog_box);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            TextView mDialogTitile = dialog.findViewById(R.id.dialog_title);
                            TextView mDialogmsg = dialog.findViewById(R.id.dialog_message);
                            mDialogTitile.setText("Update Spoc");
                            mDialogmsg.setText("Please Press Yes to Update Spoc");
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

                    Toast.makeText(AddSpocsActivity.this,"Please Check Internet Connection",Toast.LENGTH_LONG).show();

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


    public class AddSpocsAsyncTask extends AsyncTask<String, Integer, String> {
        AddUtilitiesApi addUtilitiesApi;
        private String assesmentvalue;
        public AddSpocsAsyncTask()
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
            addUtilitiesApi.addSpocsByAdmin(
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
                    params[19].toString(),
                    params[19].toString(),
                    params[19].toString(),
                    params[19].toString(),
                    params[19].toString()

            ).enqueue(new Callback<AddUtilitiesApiResponse>() {
                @Override
                public void onResponse(Call<AddUtilitiesApiResponse> call, Response<AddUtilitiesApiResponse> response) {
                    if (response.code() == 200) {
                        assesmentvalue = response.body().toString();
                        final Dialog dialog = new Dialog(AddSpocsActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.custom_dialog_box);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        TextView mDialogTitile = dialog.findViewById(R.id.dialog_title);
                        TextView mDialogmsg = dialog.findViewById(R.id.dialog_message);
                        mDialogTitile.setText("Add Spoc Status");
                        mDialogmsg.setText("" + response.body().getMessage());
                        Button myes = dialog.findViewById(R.id.yes_txt);
                        Button mNo = dialog.findViewById(R.id.no_txt);
                        mNo.setVisibility(View.GONE);
                        myes.setText("  Ok  ");
                        myes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent mIntent = new Intent(AddSpocsActivity.this, MasterActivity.class);
                                Bundle mBundle = new Bundle();
                                mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                mBundle.putString("Value","addSpoc" );
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
            pd = new ProgressDialog(AddSpocsActivity.this);
            pd.setMessage("Processing");
            d = new android.app.AlertDialog.Builder(AddSpocsActivity.this);
            d.setTitle("Adding New Spoc");
            pd.show();
        }
        @Override
        protected void onPostExecute(String s)
        {
            pd.dismiss();
        }
    }


    public class UpdateSpocAsyncTask extends AsyncTask<String, Integer, String> {
        AddUtilitiesApi addUtilitiesApi;
        private String assesmentvalue;
        public UpdateSpocAsyncTask()
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
            addUtilitiesApi.updateSpoc(
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
                    params[22].toString(),params[23].toString(),params[24].toString(),params[25].toString()
            ).enqueue(new Callback<AddUtilitiesApiResponse>() {
                @Override
                public void onResponse(Call<AddUtilitiesApiResponse> call, Response<AddUtilitiesApiResponse> response)
                {
                    if (response.code() == 200)
                    {
                        assesmentvalue = response.body().toString();
                        final Dialog dialog = new Dialog(AddSpocsActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.custom_dialog_box);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        TextView mDialogTitile = dialog.findViewById(R.id.dialog_title);
                        TextView mDialogmsg = dialog.findViewById(R.id.dialog_message);
                        mDialogTitile.setText("Update Spoc Status");
                        mDialogmsg.setText("" + response.body().getMessage());
                        Button myes = dialog.findViewById(R.id.yes_txt);
                        Button mNo = dialog.findViewById(R.id.no_txt);
                        mNo.setVisibility(View.GONE);
                        myes.setText("  Ok  ");
                        myes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent mIntent = new Intent(AddSpocsActivity.this, MasterActivity.class);
                                Bundle mBundle = new Bundle();
                                mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                mBundle.putString("Value","addSpoc" );
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
            pd = new ProgressDialog(AddSpocsActivity.this);
            pd.setMessage("Processing");
            d = new android.app.AlertDialog.Builder(AddSpocsActivity.this);
            d.setTitle("Update Spoc");
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


            new UpdateSpocAsyncTask().execute
                    (
                            authorization,
                            usertype,
                            corporate_id,
                            userId,
                            adminGroupsIdList.get(textSelectGroup.getSelectedItemPosition()),
                            adminSubGroupsIdList.get(textSelectSubGroup.getSelectedItemPosition()),
                            txt_userCID.getText().toString(),
                            txt_userName.getText().toString(),
                            txt_userContact.getText().toString(),
                            txt_userEmail.getText().toString(),
                            txt_userName.getText().toString(),
                            "budjet",
                            "expence",
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
                            token,
                            "taxi123",
                            entityId
                    );
        }
    }
}
