package com.cotrav.cotrav_admin.ui.home.employee_info;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cotrav.cotrav_admin.R;
import com.cotrav.cotrav_admin.retrofit.APIurls;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class EmployeeInfo extends AppCompatActivity {
    EditText emphno1,emphno2,emphno3,emphno4,emphno5,emphno6;
    SearchableSpinner empid1,empid2,empid3,empid4,empid5,empid6;
    ArrayAdapter<String> arrayAdapter;
    SharedPreferences employeepref;
    ArrayList<String> idlist,phnolist;

    private DrawerLayout mDrawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_info);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Enter Employee Details");




        LinearLayout l2,l3,l4,l5,l6;
        l2=(LinearLayout)findViewById(R.id.employee_layout2);
        l3=(LinearLayout)findViewById(R.id.employee_layout3);
        l4=(LinearLayout)findViewById(R.id.employee_layout4);
        l5=(LinearLayout)findViewById(R.id.employee_layout5);
        l6=(LinearLayout)findViewById(R.id.employee_layout6);
        empid1=(SearchableSpinner)findViewById(R.id.employee_spinner);
        empid2=(SearchableSpinner)findViewById(R.id.employee_spinner2);
        empid3=(SearchableSpinner)findViewById(R.id.employee_spinner3);
        empid4=(SearchableSpinner)findViewById(R.id.employee_spinner4);
        empid5=(SearchableSpinner)findViewById(R.id.employee_spinner5);
        empid6=(SearchableSpinner)findViewById(R.id.employee_spinner6);

        emphno1=(EditText)findViewById(R.id.phone_no1);
        emphno2=(EditText)findViewById(R.id.phone_no2);
        emphno3=(EditText)findViewById(R.id.phone_no3);
        emphno4=(EditText)findViewById(R.id.phone_no4);
        emphno5=(EditText)findViewById(R.id.phone_no5);
        emphno6=(EditText)findViewById(R.id.phone_no6);
        empid1.setTitle("Select employee");
        empid1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                emphno1.setText(phnolist.get(empid1.getSelectedItemPosition()));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        empid2.setTitle("Select employee");
        empid2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                emphno2.setText(phnolist.get(empid2.getSelectedItemPosition()));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        empid3.setTitle("Select employee");
        empid3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                emphno3.setText(phnolist.get(empid3.getSelectedItemPosition()));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        empid4.setTitle("Select employee");
        empid4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                emphno4.setText(phnolist.get(empid4.getSelectedItemPosition()));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        empid5.setTitle("Select employee");
        empid5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                emphno5.setText(phnolist.get(empid5.getSelectedItemPosition()));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        empid6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                emphno6.setText(phnolist.get(empid6.getSelectedItemPosition()));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        SharedPreferences loginpref=getSharedPreferences("login_info",MODE_PRIVATE);
        final Intent intent=getIntent();

        final String noofpassenger=intent.getStringExtra("no_of_employee");
        Log.e("no",noofpassenger);
        switch (noofpassenger){
            case "1":  l2.setVisibility(View.GONE);
                l3.setVisibility(View.GONE);
                l4.setVisibility(View.GONE);
                l5.setVisibility(View.GONE);
                l6.setVisibility(View.GONE);
                break;
            case "2":   l3.setVisibility(View.GONE);
                l4.setVisibility(View.GONE);
                l5.setVisibility(View.GONE);
                l6.setVisibility(View.GONE);
                break;
            case "3":       l4.setVisibility(View.GONE);
                l5.setVisibility(View.GONE);
                l6.setVisibility(View.GONE);
                break;
            case "4":       l5.setVisibility(View.GONE);
                l6.setVisibility(View.GONE);
                break;
            case "5":       l6.setVisibility(View.GONE);
                break;
        }

        idlist=new ArrayList<>();
        phnolist=new ArrayList<>();
        employeepref=getSharedPreferences("employeeinfo",MODE_PRIVATE);


        if(isInternetConnected()) {
            String Auth="Token "+loginpref.getString("access_token", "n");
            String usertype=loginpref.getString("usertype", "n");
            String corporatreId=loginpref.getString("corporate_id", "n");


            new getAllEmployee().execute(Auth,usertype,corporatreId);

            list1.add(new String("Employee ID"));
            idlist.add(new String("Employee ID"));
            phnolist.add(new String(""));
            arrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, list1);
            empid1.setAdapter(arrayAdapter);
            empid2.setAdapter(arrayAdapter);
            empid3.setAdapter(arrayAdapter);
            empid4.setAdapter(arrayAdapter);
            empid5.setAdapter(arrayAdapter);
            empid6.setAdapter(arrayAdapter);

            //new getAllEmployee().execute(Auth,usertype,corporatreId);
        }
        else {
            dialog=new AlertDialog.Builder(EmployeeInfo.this);
            dialog.setMessage("INTERNET CONNECTION UNAVAILABLE");
            dialog.show();
            finish();

        }


        Button employeebutton=(Button)findViewById(R.id.employeesubmit);
        employeebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> employeeid,employeephno,employeeidvalue;
                employeeid=new ArrayList<String>();
                employeeidvalue=new ArrayList<String>();
                employeephno=new ArrayList<String>();

                switch(noofpassenger){
                    case "1":   if(emphno1.getText().toString().length()==10&&!empid1.getSelectedItem().equals("Employee ID")){
                        employeeid.add(new String(empid1.getSelectedItem().toString()));
                        employeephno.add(new String(emphno1.getText().toString()));
                        employeeidvalue.add(new String(idlist.get(empid1.getSelectedItemPosition())));
                        Log.e("idvalue",idlist.get(empid1.getSelectedItemPosition()));
                        intent.putStringArrayListExtra("employee_id",employeeid);
                        intent.putStringArrayListExtra("employee_phno",employeephno);
                        intent.putStringArrayListExtra("employeeIdValue",employeeidvalue);
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                    else{
                        Toast.makeText(EmployeeInfo.this,"Either a Phone Number or a Employee filed is incorrect",Toast.LENGTH_SHORT).show();
                    }
                        break;
                    case "2":   if(emphno1.getText().toString().length()==10 && emphno2.getText().toString().length()==10
                            &&!empid1.getSelectedItem().equals("Employee ID") &&!empid2.getSelectedItem().equals("Employee ID")){
                        employeeid.add(new String(empid1.getSelectedItem().toString()));
                        employeephno.add(new String(emphno1.getText().toString()));
                        employeeidvalue.add(new String(idlist.get(empid1.getSelectedItemPosition())));
                        employeeidvalue.add(new String(idlist.get(empid2.getSelectedItemPosition())));
                        employeeid.add(new String(empid2.getSelectedItem().toString()));
                        employeephno.add(new String(emphno2.getText().toString()));
                        Log.e("id1",employeeidvalue.get(0));
                        Log.e("id2",employeeidvalue.get(1));
                        intent.putStringArrayListExtra("employee_id",employeeid);
                        intent.putStringArrayListExtra("employee_phno",employeephno);
                        intent.putStringArrayListExtra("employeeIdValue",employeeidvalue);
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                    else{
                        Toast.makeText(EmployeeInfo.this,"Either a Phone Number or a Employee filed is incorrect",Toast.LENGTH_SHORT).show();
                    }
                        break;
                    case "3":   if(emphno1.getText().toString().length()==10 &&
                            emphno2.getText().toString().length()==10 &&
                            emphno3.getText().toString().length()==10
                            &&!empid1.getSelectedItem().equals("Employee ID")
                            &&!empid2.getSelectedItem().equals("Employee ID")
                            && !empid3.getSelectedItem().equals("Employee ID")){
                        employeeid.add(new String(empid1.getSelectedItem().toString()));
                        employeephno.add(new String(emphno1.getText().toString()));
                        employeeid.add(new String(empid2.getSelectedItem().toString()));
                        employeephno.add(new String(emphno2.getText().toString()));
                        employeeid.add(new String(empid3.getSelectedItem().toString()));
                        employeephno.add(new String(emphno3.getText().toString()));
                        employeeidvalue.add(new String(idlist.get(empid1.getSelectedItemPosition())));
                        employeeidvalue.add(new String(idlist.get(empid2.getSelectedItemPosition())));
                        employeeidvalue.add(new String(idlist.get(empid3.getSelectedItemPosition())));
                        Log.e("id1",employeeidvalue.get(0));
                        Log.e("id2",employeeidvalue.get(1));
                        Log.e("id2",employeeidvalue.get(2));

                        intent.putStringArrayListExtra("employee_id",employeeid);
                        intent.putStringArrayListExtra("employee_phno",employeephno);
                        intent.putStringArrayListExtra("employeeIdValue",employeeidvalue);
                        setResult(RESULT_OK,intent);
                        finish();}
                    else{
                        Toast.makeText(EmployeeInfo.this,"Either a Phone Number or a Employee filed is incorrect",Toast.LENGTH_SHORT).show();
                    }
                        break;
                    case "4":   if(emphno1.getText().toString().length()==10 &&
                            emphno2.getText().toString().length()==10 &&
                            emphno3.getText().toString().length()==10 &&
                            emphno4.getText().toString().length()==10
                            &&!empid1.getSelectedItem().equals("Employee ID")
                            &&!empid2.getSelectedItem().equals("Employee ID")
                            &&!empid3.getSelectedItem().equals("Employee ID")
                            &&!empid4.getSelectedItem().equals("Employee ID")){
                        employeeid.add(new String(empid1.getSelectedItem().toString()));
                        employeephno.add(new String(emphno1.getText().toString()));
                        employeeid.add(new String(empid2.getSelectedItem().toString()));
                        employeephno.add(new String(emphno2.getText().toString()));
                        employeeid.add(new String(empid3.getSelectedItem().toString()));
                        employeephno.add(new String(emphno3.getText().toString()));
                        employeeid.add(new String(empid4.getSelectedItem().toString()));
                        employeephno.add(new String(emphno4.getText().toString()));
                        employeeidvalue.add(new String(idlist.get(empid1.getSelectedItemPosition())));
                        employeeidvalue.add(new String(idlist.get(empid2.getSelectedItemPosition())));
                        employeeidvalue.add(new String(idlist.get(empid3.getSelectedItemPosition())));
                        employeeidvalue.add(new String(idlist.get(empid4.getSelectedItemPosition())));
                        intent.putStringArrayListExtra("employee_id",employeeid);
                        intent.putStringArrayListExtra("employee_phno",employeephno);
                        intent.putStringArrayListExtra("employeeIdValue",employeeidvalue);
                        setResult(RESULT_OK,intent);
                        finish();}
                    else{
                        Toast.makeText(EmployeeInfo.this,"Either a Phone Number or a Employee filed is incorrect",Toast.LENGTH_SHORT).show();
                    }
                        break;
                    case "5":   if(emphno1.getText().toString().length()==10 && emphno2.getText().toString().length()==10 &&
                            emphno3.getText().toString().length()==10 &&
                            emphno4.getText().toString().length()==10 &&
                            emphno5.getText().toString().length()==10 &&!empid1.getSelectedItem().equals("Employee ID") &&
                            !empid2.getSelectedItem().equals("Employee ID")
                            &&!empid3.getSelectedItem().equals("Employee ID")
                            &&!empid4.getSelectedItem().equals("Employee ID")
                            &&!empid5.getSelectedItem().equals("Employee ID")){
                        employeeid.add(new String(empid1.getSelectedItem().toString()));
                        employeephno.add(new String(emphno1.getText().toString()));
                        employeeid.add(new String(empid2.getSelectedItem().toString()));
                        employeephno.add(new String(emphno2.getText().toString()));
                        employeeid.add(new String(empid3.getSelectedItem().toString()));
                        employeephno.add(new String(emphno3.getText().toString()));
                        employeeid.add(new String(empid4.getSelectedItem().toString()));
                        employeephno.add(new String(emphno4.getText().toString()));
                        employeeid.add(new String(empid5.getSelectedItem().toString()));
                        employeephno.add(new String(emphno5.getText().toString()));
                        employeeidvalue.add(new String(idlist.get(empid1.getSelectedItemPosition())));
                        employeeidvalue.add(new String(idlist.get(empid2.getSelectedItemPosition())));
                        employeeidvalue.add(new String(idlist.get(empid3.getSelectedItemPosition())));
                        employeeidvalue.add(new String(idlist.get(empid4.getSelectedItemPosition())));
                        employeeidvalue.add(new String(idlist.get(empid5.getSelectedItemPosition())));
                        intent.putStringArrayListExtra("employee_id",employeeid);
                        intent.putStringArrayListExtra("employee_phno",employeephno);
                        intent.putStringArrayListExtra("employeeIdValue",employeeidvalue);
                        setResult(RESULT_OK,intent);
                        finish();}
                    else{
                        Toast.makeText(EmployeeInfo.this,"Either a Phone Number or a Employee filed is incorrect",Toast.LENGTH_SHORT).show();
                    }
                        break;
                    case "6":   if(emphno1.getText().toString().length()==10 && emphno2.getText().toString().length()==10 &&
                            emphno3.getText().toString().length()==10 && emphno4.getText().toString().length()==10 &&
                            emphno5.getText().toString().length()==10 &&
                            emphno6.getText().toString().length()==10
                            &&!empid1.getSelectedItem().equals("Employee ID") &&!empid2.getSelectedItem().equals("Employee ID")
                            &&!empid3.getSelectedItem().equals("Employee ID") &&!empid4.getSelectedItem().equals("Employee ID")
                            &&!empid5.getSelectedItem().equals("Employee ID")&&!empid6.getSelectedItem().equals("Employee ID")){
                        employeeid.add(new String(empid1.getSelectedItem().toString()));
                        employeephno.add(new String(emphno1.getText().toString()));
                        employeeid.add(new String(empid2.getSelectedItem().toString()));
                        employeephno.add(new String(emphno2.getText().toString()));
                        employeeid.add(new String(empid3.getSelectedItem().toString()));
                        employeephno.add(new String(emphno3.getText().toString()));
                        employeeid.add(new String(empid4.getSelectedItem().toString()));
                        employeephno.add(new String(emphno4.getText().toString()));
                        employeeid.add(new String(empid5.getSelectedItem().toString()));
                        employeephno.add(new String(emphno5.getText().toString()));
                        employeeid.add(new String(empid6.getSelectedItem().toString()));
                        employeephno.add(new String(emphno6.getText().toString()));
                        employeeidvalue.add(new String(idlist.get(empid1.getSelectedItemPosition())));
                        employeeidvalue.add(new String(idlist.get(empid2.getSelectedItemPosition())));
                        employeeidvalue.add(new String(idlist.get(empid3.getSelectedItemPosition())));
                        employeeidvalue.add(new String(idlist.get(empid4.getSelectedItemPosition())));
                        employeeidvalue.add(new String(idlist.get(empid5.getSelectedItemPosition())));
                        employeeidvalue.add(new String(idlist.get(empid6.getSelectedItemPosition())));
                        intent.putStringArrayListExtra("employee_id",employeeid);
                        intent.putStringArrayListExtra("employee_phno",employeephno);
                        intent.putStringArrayListExtra("employeeIdValue",employeeidvalue);
                        setResult(RESULT_OK,intent);
                        finish();}
                    else{
                        Toast.makeText(EmployeeInfo.this,"Either a Phone Number or a Employee filed is incorrect",Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });

    }


    AlertDialog.Builder dialog;
    public boolean isInternetConnected(){
        ConnectivityManager cm=(ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo[]=cm.getAllNetworkInfo();

        int i;
        for(i=0;i<networkInfo.length;++i){
            if(networkInfo[i].getState()== NetworkInfo.State.CONNECTED){
                return true;
            }
        }
        return false;


    }



    ArrayList<String> list1=new ArrayList<>();
    String valu;
    protected class getAllEmployee extends AsyncTask<String,Integer,String> {
        String apiURL = APIurls.getEmployees;
        Integer id = 2;
        Response response;
        OkHttpClient client = new OkHttpClient();

        @Override
        protected String doInBackground(String... params) {

            RequestBody formBody = new FormBody.Builder()
                    .add("corporate_id", params[2])
                    .build();
            Request.Builder request = new Request.Builder();

            request.url(apiURL)
                    .addHeader("Authorization", params[0])
                    .addHeader("usertype", params[1])
                    .post(formBody)
                    .build();

            try {
                response = client.newCall(request.build()).execute();
                valu = response.body().string();
                return valu;

            } catch (IOException e) {
                e.getMessage();
                Log.d("error", e.getMessage());
            }
            return valu;

        }

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(EmployeeInfo.this);
            pd.setMessage("loading");
            pd.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            response.body().close();
            if (s == null) {
            } else {
                Log.e("employee", s);

                SharedPreferences.Editor editor = employeepref.edit();
                editor.putString("employeeinfo", s)
                        .commit();



                JSONObject json = (JSONObject) JSONValue.parse(s);
                JSONArray array = (JSONArray) json.get("Employees");
                Iterator i = array.iterator();
                while (i.hasNext()) {
                    JSONObject obj = (JSONObject) i.next();
                    list1.add(new String(obj.get("employee_name").toString()+"/"+obj.get("id").toString()));
                    idlist.add(new String(obj.get("id").toString()));
                    phnolist.add(new String(obj.get("employee_contact").toString()));

                }
                if (list1.isEmpty()) {

                    arrayAdapter.setNotifyOnChange(true);
                }

            }
            pd.dismiss();
        }


    }

}
