package com.cotrav.cotrav_admin.ui.home.bus;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.cotrav.cotrav_admin.GsonStringConvertor;
import com.cotrav.cotrav_admin.PlacesFieldSelector;
import com.cotrav.cotrav_admin.R;
import com.cotrav.cotrav_admin.asynctask.AddBusBookingAsyncTask;
import com.cotrav.cotrav_admin.model.assesmentcity_model.AssesmentCities;
import com.cotrav.cotrav_admin.model.assesmentcode_model.AssesmentCodes;
import com.cotrav.cotrav_admin.model.entities_model.Entities;
import com.cotrav.cotrav_admin.model.login_model.UserViewModel;
import com.cotrav.cotrav_admin.model.spoc_model.Spocs;
import com.cotrav.cotrav_admin.ui.home.employee_info.EmployeeInfo;
import com.cotrav.cotrav_admin.ui.home.taxi.addTaxiFragment.FragmentLocalTaxi;
import com.cotrav.cotrav_admin.ui.utilities.addcode.AssesmentCodeViewModel;
import com.cotrav.cotrav_admin.ui.utilities.asscity.AssesmentCityViewModel;
import com.cotrav.cotrav_admin.ui.utilities.entities.EntitiesViewModel;
import com.cotrav.cotrav_admin.ui.utilities.spocs.SpocsViewModel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import es.dmoral.toasty.Toasty;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class AddBusBookingActivity extends AppCompatActivity implements View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener
{

    private static final int EMPLOYEE_INFO=100;
    private Toolbar toolbar;
    BusBookingsViewModel busBookingsViewModel;
    Button submitBtn;
    Activity activity = this;
    static TextView startdate,starttime,enddate,endtime;
    ImageView acimg,nonacimg,sleepimg,seaterimg;
    String acSlected="0",nonAcSelected="0",sleeperSelected="0",seaterSlected="0",bus_type="0";
    SearchableSpinner assessmentSpinner,assessmentCitySpinner,
    billingSpinner,spocsSpinner
            ;
    ArrayList<String> assessmentCityList, assessmentCityListId;
    ArrayList<String> assessmentList, assessmentListId;
    ArrayAdapter<String> assessmentCityAdapter,assessmentAdapter,billingEntityAdapter,spocsAdapter;
    AssesmentCityViewModel assessmentCitiesViewModel;
    AssesmentCodeViewModel assessmentCodeViewModel;
    EntitiesViewModel entityViewModel;
    SpocsViewModel spocsViewModel;

    RelativeLayout assessmentLayout;
    static ArrayAdapter<String> arrayAdapter,assesmentadapter;
    TextView dropCity,pickupCity;
    static TextView localPassengers;

    SharedPreferences loginpref,spocpref;
    static  String start_date_send,end_date_send,end_time_send;
    static String authorization,token,corporateId,spocId,empId,groupId,userType,userId,
            subgroupId,selectedDateTime,selectedDateTimeTo,entity_id,
            booking_datetime="";
    EditText prefBus,reasomOfBooking,dropLocation;
    UserViewModel viewAdmin;
    String assCodeId="",assCityId="";
    Context context=this;
    RectangularBounds bounds = RectangularBounds.newInstance(
            new LatLng(-40,-168),new LatLng(71,136));
    TextView seatertxt,sleeprtxt;
    private static final int DROP_CODE_AUTOCOMPLETE = 0;
    private static final int PICKUP_CODE_AUTOCOMPLETE = 1;
    ArrayList<String> spocList, spocIdList,billingEntityList, billingEntityIdList;
    static int total=1;
    static int max =4;
    private static ArrayList<String> employee_idlist;
    private static ArrayList<String> getEmployee_idphno;
    private static ArrayList<String> employee_id_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bus_booking);
        overridePendingTransition(R.anim.slidein, R.anim.slideout);
        toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setTitle("Add Bus Booking");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar.setNavigationIcon(R.drawable.ic_back);
        }



        if (!Places.isInitialized()) {
            Places.initialize(context, getResources().getString(R.string.places_api_key));
        }
        final PlacesFieldSelector fieldSelector = new PlacesFieldSelector();

        loginpref = getSharedPreferences("login_info", MODE_PRIVATE);
        spocpref = getSharedPreferences("admin_info", MODE_PRIVATE);
        corporateId = loginpref.getString("corporate_id", "n");
        token = loginpref.getString("access_token", "n");
        empId = loginpref.getString("emp_id", "n");
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String formattedDate = df.format(c);
        booking_datetime = formattedDate;
        authorization = "Token " + token;
        reasomOfBooking = findViewById(R.id.booking_reason);
        prefBus = findViewById(R.id.prefered_bus);
        assessmentCitySpinner = findViewById(R.id.assessment_city);
        assessmentSpinner = findViewById(R.id.assessnment_code);
        assessmentLayout =  findViewById(R.id.assessment_layout);
        billingSpinner=findViewById(R.id.billing_entity);
        spocsSpinner=findViewById(R.id.spoc_spinner);
        dropCity = findViewById(R.id.drop_city);
        localPassengers=findViewById(R.id.local_pessangers);
        pickupCity = findViewById(R.id.pickup_city);
        dropLocation=findViewById(R.id.drop_location);
        submitBtn = findViewById(R.id.submit_btn);
        acimg= findViewById(R.id.ac_img);
        acimg.setBackground(ContextCompat.getDrawable(context,R.drawable.round_corner_lightwhite_background));
        acimg.setPadding(30,30,30,30);
        nonacimg = findViewById(R.id.nonac_img);
        nonacimg.setBackground(ContextCompat.getDrawable(context,R.drawable.round_corner_lightwhite_background));
        nonacimg.setPadding(30,30,30,30);
        sleepimg=findViewById(R.id.sleep_img);
        sleepimg.setBackground(ContextCompat.getDrawable(context,R.drawable.round_corner_lightwhite_background));
        sleepimg.setPadding(30,30,30,30);
        seaterimg=findViewById(R.id.seater_img);
        seaterimg.setBackground(ContextCompat.getDrawable(context,R.drawable.round_corner_lightwhite_background));
        seaterimg.setPadding(30,30,30,30);
        acimg.setOnClickListener(this);
        nonacimg.setOnClickListener(this);
        sleepimg.setOnClickListener(this);
        seaterimg.setOnClickListener(this);
        submitBtn.setOnClickListener(this);

        sleeprtxt = findViewById(R.id.seater_txt);
        seatertxt = findViewById(R.id.sleep_txt);
        assessmentCityList = new ArrayList<>();
        assessmentCityListId = new ArrayList<>();
        assessmentList = new ArrayList<>();
        assessmentListId = new ArrayList<>();

        employee_idlist = new ArrayList<String>();
        getEmployee_idphno = new ArrayList<String>();
        employee_id_value = new ArrayList<>();

        spocList = new ArrayList<>();
        spocIdList = new ArrayList<>();
        billingEntityList = new ArrayList<>();
        billingEntityIdList = new ArrayList<>();
        spocsViewModel = ViewModelProviders.of(this).get(SpocsViewModel.class);
        spocsAdapter = new ArrayAdapter<>(context,R.layout.spinner_layout,spocList);
        entityViewModel = ViewModelProviders.of(this).get(EntitiesViewModel.class);
        billingEntityAdapter = new ArrayAdapter<>(context,R.layout.spinner_layout,billingEntityList);

        assessmentCitiesViewModel = ViewModelProviders.of(this).get(AssesmentCityViewModel.class);
        assessmentCodeViewModel = ViewModelProviders.of(this).get(AssesmentCodeViewModel.class);
        busBookingsViewModel = ViewModelProviders.of(this).get(BusBookingsViewModel.class);
        assessmentCityAdapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, assessmentCityList);
        assessmentAdapter = new ArrayAdapter<String>(AddBusBookingActivity.this, R.layout.spinner_layout, assessmentList);

        if (loginpref.getString("has_assessment_codes", "n").equals("0")) {
            assessmentLayout.setVisibility(View.GONE);
        } else {
            assessmentCodeViewModel.InitCorporateCodesViewModel(authorization, "1", corporateId);
            assessmentCitiesViewModel.InitCorporateCitiesViewModel(authorization, "1", corporateId);

            assessmentCitySpinner.setAdapter(assessmentCityAdapter);
            assessmentCitiesViewModel.getCorporateCitiesLiveData(authorization, "1", corporateId).observe(this,
                    new Observer<List<AssesmentCities>>() {
                @Override
                public void onChanged(List<AssesmentCities> cities) {
                    assessmentCityList.clear();
                    assessmentCityListId.clear();
                    assessmentCityList.add("Ass.City");
                    assessmentCityListId.add("Ass.City id");
                    if (cities != null) {
                        for (int i = 0; i < cities.size(); i++) {
                            assessmentCityList.add(cities.get(i).getCityName());
                            assessmentCityListId.add(String.valueOf(cities.get(i).getId()));
                        }
                    }
                    assessmentCityAdapter.notifyDataSetChanged();
                }
            });
            assessmentSpinner.setAdapter(assessmentAdapter);
            assessmentCodeViewModel.getCorporateCodesLiveData(authorization, "1", corporateId).observe(this,
                    new Observer<List<AssesmentCodes>>() {
                @Override
                public void onChanged(@Nullable List<AssesmentCodes> assCodes) {
                    Log.d("Assessment Codes:::", GsonStringConvertor.gsonToString(assCodes));
                    assessmentList.clear();
                    assessmentListId.clear();
                    assessmentList.add("Ass. Code");
                    assessmentListId.add("Ass. Code id");
                    if (assCodes != null) {
                        for (int i = 0; i < assCodes.size(); i++) {
                            assessmentList.add(assCodes.get(i).getAssessmentCode());
                            assessmentListId.add(String.valueOf(assCodes.get(i).getId()));
                        }
                    }
                    assessmentAdapter.notifyDataSetChanged();
                }
            });

            assessmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if(assessmentSpinner.getSelectedItem().toString().equals("Ass. Code")){

                        //((TextView) assessmentSpinner.getSelectedView()).setError("field required");
                    }else{
                        assCodeId=assessmentListId.get(assessmentSpinner.getSelectedItemPosition());
                        //gettingLatLng(taxiModelname);
                    }
                    assCodeId=assessmentListId.get(assessmentSpinner.getSelectedItemPosition());
                    Log.e("assCodeId",assCodeId);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            assessmentCitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if(assessmentCitySpinner.getSelectedItem().toString().equals("Ass.City")){
                        // ((TextView) assessmentCitySpinner.getSelectedView()).setError("field required");
                    }else{
                        assCityId=assessmentCityListId.get(assessmentCitySpinner.getSelectedItemPosition());
                        //gettingLatLng(taxiModelname);
                    }
                    assCityId=assessmentCityListId.get(assessmentCitySpinner.getSelectedItemPosition());
                    Log.e("assCityId",assCityId);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }

        starttime = findViewById(R.id.journey_time);
        starttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePickerFragment = new TimePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putString("start", "start");
                timePickerFragment.setArguments(bundle);
                timePickerFragment.show(getSupportFragmentManager(), "starttime");
            }
        });
        startdate = findViewById(R.id.journey_date);
        startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePickerFragment = new DatePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putString("start", "start");
                datePickerFragment.setArguments(bundle);
                datePickerFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
        enddate = findViewById(R.id.journey_date_to);
        enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePickerFragment = new DatePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putString("start", "end");
                datePickerFragment.setArguments(bundle);
                datePickerFragment.show(getSupportFragmentManager(), "enddate");
            }
        });

        endtime = findViewById(R.id.journey_time_to);
        endtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePickerFragment = new TimePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putString("start", "end");
                timePickerFragment.setArguments(bundle);
                timePickerFragment.show(getSupportFragmentManager(), "endtime");
            }
        });


        dropCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent autocompleteIntent = new Autocomplete.
                        IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldSelector.getAllFields())
                        .setLocationBias(bounds)
                        .setCountry("IN")
                        .setTypeFilter(TypeFilter.CITIES)
                        .build(context);
                startActivityForResult(autocompleteIntent, DROP_CODE_AUTOCOMPLETE);
            }
        });
        pickupCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent autocompleteIntent = new Autocomplete.
                        IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldSelector.getAllFields())
                        .setLocationBias(bounds)
                        .setCountry("IN")
                        .setTypeFilter(TypeFilter.CITIES)
                        .build(context);
                startActivityForResult(autocompleteIntent, PICKUP_CODE_AUTOCOMPLETE);
            }
        });

        localPassengers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment passengerPicker = new NoOfPassengers();
                passengerPicker.show(getSupportFragmentManager(), "no of radio_passenger");
            }
        });



        spocList.clear();
        spocIdList.clear();
        spocList.add("Select Spoc");
        spocIdList.add("Select Spoc");
        spocsViewModel.InitSpocsViewModel(authorization,"1",corporateId);
        spocsSpinner.setAdapter(spocsAdapter);
        spocsViewModel.getCorporateSpocsLiveData(authorization,"1",corporateId).observe(this,

                new Observer<List<Spocs>>() {
                    @Override
                    public void onChanged(List<Spocs> spocs) {
                        spocList.clear();
                        spocIdList.clear();
                        spocList.add("Select Spoc");
                        spocIdList.add("Select Spoc");
                        if (spocs != null) {
                            for (int i = 0; i < spocs.size(); i++) {
                                spocList.add(spocs.get(i).getUserName());
                                spocIdList.add(String.valueOf(spocs.get(i).getId()));
                            }
                        }
                        spocsAdapter.notifyDataSetChanged();
                    }
                }
        );


        billingEntityList.clear();
        billingEntityIdList.clear();
        billingEntityList.add("Billing Entity");
        billingEntityIdList.add("Billing Entity");
        entityViewModel.InitEntitiesViewModel(authorization,"1",corporateId);
        billingSpinner.setAdapter(billingEntityAdapter);
        entityViewModel.getEntitiesLiveData(authorization,"1",corporateId).observe(this,

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
                        billingEntityAdapter.notifyDataSetChanged();
                    }
                }
        );

        spocsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(spocsSpinner.getSelectedItem().toString().equals("Select Spoc")){
                    //((TextView) assessmentSpinner.getSelectedView()).setError("field required");
                }else{
                    spocId=spocIdList.get(spocsSpinner.getSelectedItemPosition());
                    //gettingLatLng(taxiModelname);
                }
                spocId=spocIdList.get(spocsSpinner.getSelectedItemPosition());
                Log.e("assCodeId",assCodeId);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    @Override
    public void onClick(View view)
    {
        int id  = view.getId();
        switch (id) {
             case R.id.submit_btn:
                 setData();

                 bus_type="0";
                if (acSlected.equals("1") && seaterSlected.equals("1"))
                    bus_type = "1";
                if (acSlected.equals("1") && sleeperSelected.equals("1"))
                    bus_type = "2";
                if (nonAcSelected.equals("1") && seaterSlected.equals("1"))
                    bus_type = "3";
                if (nonAcSelected.equals("1") && sleeperSelected.equals("1"))
                    bus_type = "4";

                if (isInternetConnected(activity)){

                    if (dropCity.getText().toString().length()<1
                            ||pickupCity.getText().toString().length()<1
                            ||starttime.getText().toString().length()<1
                            ||startdate.getText().toString().length()<1
                            ||enddate.getText().toString().length()<1
                            ||endtime.getText().toString().length()<1
                            ||bus_type.equals("0")
                            || spocsSpinner.getSelectedItemPosition() == 0
                            || billingSpinner.getSelectedItemPosition() == 0
                            ||localPassengers.getText().equals("Passengers")
                            ||reasomOfBooking.getText().toString().length()<1
                            ||assessmentSpinner.getSelectedItemPosition()==0
                            ||prefBus.getText().toString().length()<1
                            ||dropLocation.getText().toString().length()<1
                            ||assessmentCitySpinner.getSelectedItemPosition()==0){

                        if (billingSpinner.getSelectedItemPosition() == 0) {
                            ((android.widget.TextView) billingSpinner.getSelectedView()).setError("field required");
                        }
                        if (spocsSpinner.getSelectedItemPosition() == 0) {
                            ((android.widget.TextView) spocsSpinner.getSelectedView()).setError("field required");
                        }
                        if (localPassengers.getText().equals("Passengers"))
                        {
                            localPassengers.setError("Select Passengers");
                        }

                        //Toasty.error(this, "Error   ").show();
                        if (starttime.getText().toString().length()<2)
                            starttime.setError("Not Slected");

                        if (startdate.getText().toString().length()<2){

                            startdate.setError("Not Slected");
                        }
                        if (endtime.getText().toString().length()<2)
                            endtime.setError("Not Slected");

                        if (enddate.getText().toString().length()<2){

                            enddate.setError("Not Slected");
                        }
                        if (bus_type.equals("0")){
                            Toasty.error(this,"Bus Type Not Selected").show();}
                        if (dropCity.getText().toString().length()<1){
                            dropCity.setError("Select City");
                        }
                        if (pickupCity.getText().toString().length()<1){
                            pickupCity.setError("Select City");
                        }

                        if (dropLocation.getText().toString().length()<1){
                            dropLocation.setError("Drop Location");
                        }
                        if (assessmentSpinner.getSelectedItemPosition() == 0) {
                            ((android.widget.TextView) assessmentSpinner.getSelectedView()).setError("field required");
                        }
                        if (assessmentCitySpinner.getSelectedItemPosition() == 0) {
                            ((android.widget.TextView) assessmentCitySpinner.getSelectedView()).setError("field required");
                        }
                        if (reasomOfBooking.getText().toString().length()<1){
                            reasomOfBooking.setError("Reason is must");
                        }
                        if (prefBus.getText().toString().length()<1){
                            prefBus.setError("preffred Bus");
                        }

                    }
                    else {
                        Toasty.normal(this, "Processing").show();
                        final Dialog dialog = new Dialog(AddBusBookingActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.custom_dialog_box);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                        TextView mDialogmsg = dialog.findViewById(R.id.dialog_message);
                        mDialogmsg.setText("Proceed To Complete Booking.");

                        TextView mDialogtitle = dialog.findViewById(R.id.dialog_title);
                        mDialogtitle.setText("COMPLETE BUS BOOKING");

                        Button myes = dialog.findViewById(R.id.yes_txt);
                        myes.setText("Okey");
                        Button mNo = dialog.findViewById(R.id.no_txt);
                        mNo.setText("Cancel");
                        mNo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        myes.setText("Proceed");
                        myes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                selectedDateTime = startdate.getText().toString() + " " + starttime.getText().toString();
                                selectedDateTimeTo = enddate.getText().toString() + " " + endtime.getText().toString();

                                new AddBusBookingAsyncTask(activity,
                                        token,
                                        empId,
                                        corporateId,
                                        spocId,
                                        groupId,
                                        subgroupId,
                                        selectedDateTime,
                                        selectedDateTimeTo,
                                        pickupCity.getText().toString(),
                                        dropCity.getText().toString(),
                                        booking_datetime,
                                        entity_id,
                                        assCodeId,
                                        assCityId,
                                        bus_type,
                                        reasomOfBooking.getText().toString(),
                                        employee_id_value,
                                        prefBus.getText().toString()).execute(activity);

                                dialog.dismiss();
                            }
                        });

                        dialog.show();




                    }}
                else Toasty.error(this,"Check Network Connection").show();
                break;
            case R.id.ac_img:
                acSlected = "1";
                nonAcSelected="0";
                sleepimg.setVisibility(View.VISIBLE);
                seaterimg.setVisibility(View.VISIBLE);
                seatertxt.setVisibility(View.VISIBLE);
                sleeprtxt.setVisibility(View.VISIBLE);
                acimg.setBackground(ContextCompat.getDrawable(context,R.drawable.round_corner));
                acimg.setPadding(20,20,20,20);
                nonacimg.setBackground(ContextCompat.getDrawable(context,R.drawable.round_corner_lightwhite_background));
                nonacimg.setPadding(30,30,30,30);
                break;
            case R.id.nonac_img:
                acSlected = "0";
                sleepimg.setVisibility(View.VISIBLE);
                seaterimg.setVisibility(View.VISIBLE);
                seatertxt.setVisibility(View.VISIBLE);
                sleeprtxt.setVisibility(View.VISIBLE);
                nonAcSelected="1";
                nonacimg.setBackgroundResource(R.drawable.round_corner);
                // nonacimg.setBackground(ContextCompat.getDrawable(context,R.drawable.round_corner));
                nonacimg.setPadding(20,20,20,20);
                acimg.setBackgroundResource(R.drawable.round_corner_lightwhite_background);
                //acimg.setBackground(ContextCompat.getDrawable(context,R.drawable.round_corner_lightwhite_background));
                acimg.setPadding(30,30,30,30);

                break;
            case R.id.sleep_img:
                sleeperSelected = "1";
                seaterSlected = "0";
                sleepimg.setBackground(getResources().getDrawable(R.drawable.round_corner));
                sleepimg.setPadding(20,20,20,20);
                seaterimg.setBackground(ContextCompat.getDrawable(context,R.drawable.round_corner_lightwhite_background));
                seaterimg.setPadding(30,30,30,30);
                break;
            case R.id.seater_img:
                sleeperSelected = "0";
                seaterSlected = "1";
                seaterimg.setBackground(ContextCompat.getDrawable(context,R.drawable.round_corner));
                seaterimg.setPadding(20,20,20,20);
                sleepimg.setBackground(ContextCompat.getDrawable(context,R.drawable.round_corner_lightwhite_background));
                sleepimg.setPadding(30,30,30,30);
                break;



        }

    }



    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Log.d("request code",requestCode+"");
        if (requestCode == PICKUP_CODE_AUTOCOMPLETE) {
            if (resultCode == RESULT_OK) {
                // Get the user's selected place from the Intent.
                String location=intent.getStringExtra("LOCATION");
                //Place place = PlaceAutocomplete.getPlace(this, data);
                Log.i(TAG, "Place Selected: " + location);
                Place place = Autocomplete.getPlaceFromIntent(intent);

                // Format the place's details and display them in the TextView.
                pickupCity.setError(null);
                pickupCity.setText(place.getName());
                pickupCity.append(", "+place.getAddress());


            }
        }

        else if (requestCode == DROP_CODE_AUTOCOMPLETE) {
            if (resultCode == RESULT_OK) {
                String location=intent.getStringExtra("LOCATION");
                Log.i(TAG, "Place Selected: " + location);
                Place place = Autocomplete.getPlaceFromIntent(intent);
                dropCity.setError(null);
                dropCity.setText(place.getName());
                dropCity.append(", "+place.getAddress());


            } else if (resultCode == RESULT_CANCELED) {
                // Indicates that the activity closed before a selection was made. For example if
                // the user pressed the back button.
            }
        }
        else if (requestCode ==EMPLOYEE_INFO){

            if(resultCode == RESULT_OK){
                employee_idlist = intent.getStringArrayListExtra("employee_id");
                getEmployee_idphno = intent.getStringArrayListExtra("employee_phno");
                employee_id_value = intent.getStringArrayListExtra("employeeIdValue");
            }
        }
    }


    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        String id;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            id=new String();
            id=getArguments().getString("start");

            // Create a new instance of DatePickerDialog and return it
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month , day);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            return datePickerDialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            final Calendar c = Calendar.getInstance();
            c.set(year, month, day);
            if (id.equals("start")) {

                if (c.compareTo(Calendar.getInstance()) >= 0) {
                    month = month + 1;
                    start_date_send = day + "-" + month + "-" + year;
                    if (day < 10) {
                        String d = Integer.toString(day);
                        d = 0 + "" + d;
                        startdate.setText(d + "-" + month + "-" + year);
                    } else {
                        startdate.setText(day + "-" + month + "-" + year);
                    }
                    startdate.setError(null);

                } else
                    {

                    Toasty.error(getActivity(), "past dates are not allowed", Toast.LENGTH_SHORT).show();

                    }
            }else if (id.equals("end")) {

                if (c.compareTo(Calendar.getInstance()) >= 0) {
                    month = month + 1;
                    end_date_send = day + "-" + month + "-" + year;
                    if (day < 10) {
                        String d = Integer.toString(day);
                        d = 0 + "" + d;
                        enddate.setText(d + "-" + month + "-" + year);
                    } else {
                        enddate.setText(day + "-" + month + "-" + year);
                    }
                    enddate.setError(null);

                } else {
                    Toasty.error(getActivity(), "past dates are not allowed", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }
    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {
        String id;
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            id=new String();
            id=getArguments().getString("start");
            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute, true);
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            if (id.equals("start")) {

                if (minute < 10 && hourOfDay < 10) {
                    starttime.setText("0" + Integer.toString(hourOfDay) + ":" + "0" + Integer.toString(minute) + ":00");
                } else if (minute < 10) {
                    starttime.setText(Integer.toString(hourOfDay) + ":" + "0" + Integer.toString(minute) + ":00");
                } else if (hourOfDay < 10) {
                    starttime.setText("0" + Integer.toString(hourOfDay) + ":" + Integer.toString(minute) + ":00");
                } else {
                    starttime.setText(hourOfDay + ":" + minute + ":00");
                }
                starttime.setError(null);
            }        else  if (id.equals("end")) {

                if (minute < 10 && hourOfDay < 10) {
                    endtime.setText("0" + Integer.toString(hourOfDay) + ":" + "0" + Integer.toString(minute) + ":00");
                } else if (minute < 10) {
                    endtime.setText(Integer.toString(hourOfDay) + ":" + "0" + Integer.toString(minute) + ":00");
                } else if (hourOfDay < 10) {
                    endtime.setText("0" + Integer.toString(hourOfDay) + ":" + Integer.toString(minute) + ":00");
                } else {
                    endtime.setText(hourOfDay + ":" + minute + ":00");
                }
                endtime.setError(null);
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

    public  static boolean isInternetConnected(Activity activity){
        ConnectivityManager cm=(ConnectivityManager)activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo[]=cm.getAllNetworkInfo();

        int i;
        for(i=0;i<networkInfo.length;++i){
            if(networkInfo[i].getState()== NetworkInfo.State.CONNECTED){
                return true;
            }
        }
        return false;

    }
    public static class NoOfPassengers extends  DialogFragment{
        private ImageView plus,minus;
        private ImageView passenger1,passenger2,passenger3,passenger4,passenger5,passenger6;
        private TextView total_text;
        Button ok;
        RelativeLayout relativeLayout;
        public NoOfPassengers(){

        }

        public static FragmentLocalTaxi.NoOfPassengers newInstance(int no){
            FragmentLocalTaxi.NoOfPassengers frag = new FragmentLocalTaxi.NoOfPassengers();
            Bundle args = new Bundle();
            args.putInt("details",no);
            frag.setArguments(args);
            return  frag;
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
            return inflater.inflate(R.layout.passenger_dialog_fragment,container);
        }

        @Override

        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

            super.onViewCreated(view, savedInstanceState);
            plus = (ImageView) view.findViewById(R.id.plus_one);
            minus = (ImageView) view.findViewById(R.id.negative_one);
            relativeLayout =(RelativeLayout) view.findViewById(R.id.text_relative);
            passenger1 = (ImageView) view.findViewById(R.id.passenger_1);
            passenger2 = (ImageView) view.findViewById(R.id.passenger_2);
            passenger2.setVisibility(View.GONE);
            passenger3 = (ImageView) view.findViewById(R.id.passenger_3);
            passenger3.setVisibility(View.GONE);
            passenger4 = (ImageView) view.findViewById(R.id.passenger_4);
            passenger4.setVisibility(View.GONE);
            passenger5 = (ImageView) view.findViewById(R.id.passenger_5);
            passenger5.setVisibility(View.GONE);
            passenger6 = (ImageView) view.findViewById(R.id.passenger_6);
            passenger6.setVisibility(View.GONE);
            total_text = (TextView) view.findViewById(R.id.total_text);
            if(total==2){
                passenger2.setVisibility(View.VISIBLE);
            }else if(total ==3){
                passenger2.setVisibility(View.VISIBLE);
                passenger3.setVisibility(View.VISIBLE);
            }else if(total ==4){
                passenger2.setVisibility(View.VISIBLE);
                passenger3.setVisibility(View.VISIBLE);
                passenger4.setVisibility(View.VISIBLE);
            }else if(total == 5){
                passenger2.setVisibility(View.VISIBLE);
                passenger3.setVisibility(View.VISIBLE);
                passenger4.setVisibility(View.VISIBLE);
                passenger5.setVisibility(View.VISIBLE);
            }else if(total ==6){
                passenger2.setVisibility(View.VISIBLE);
                passenger3.setVisibility(View.VISIBLE);
                passenger4.setVisibility(View.VISIBLE);
                passenger5.setVisibility(View.VISIBLE);
                passenger6.setVisibility(View.VISIBLE);
            }

            total_text.setText(total+"");
            ok = (Button) view.findViewById(R.id.ok);
            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (total==max){
                        Toast.makeText(getActivity(),"Number of Local Taxi Passenger should be less then "+max,Toast.LENGTH_SHORT).show();

                    }else {
                        total++;
                        total_text.setText(total + "");
                        if(total==2) {
                            passenger2.setVisibility(View.VISIBLE);
                        }if(total==3){
                            passenger3.setVisibility(View.VISIBLE);
                        }if (total==4){
                            passenger4.setVisibility(View.VISIBLE);
                        }if(total==5){
                            passenger5.setVisibility(View.VISIBLE);
                        }if(total==6){
                            passenger6.setVisibility(View.VISIBLE);
                        }
                    }

                }
            });
            minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (total==1){
                        Toast.makeText(getActivity(),"Number of Local Taxi Passenger should be more then "+ 0,Toast.LENGTH_SHORT).show();
                    }else {
                        total--;
                        total_text.setText(total + "");
                        if(total==2) {
                            passenger3.setVisibility(View.GONE);
                        }if(total==3){
                            passenger4.setVisibility(View.GONE);
                        }if (total==1){
                            passenger2.setVisibility(View.GONE);
                        }if(total==4){
                            passenger5.setVisibility(View.GONE);
                        }if(total == 5){
                            passenger6.setVisibility(View.GONE);
                        }
                    }

                }
            });

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    localPassengers.setError(null);
                    localPassengers.setText(total+"");
                    getDialog().dismiss();
                    Intent intent=new Intent(getActivity(), EmployeeInfo.class);
                    intent.putExtra("no_of_employee",total+"");
                    getActivity().startActivityForResult(intent,EMPLOYEE_INFO);

                }
            });




        }
    }

    private void setData() {

        //Getting Login Time Spoc Details for Api Call
        authorization = "Token " + token;
        userType = loginpref.getString("usertype", "n");
        userId = loginpref.getString("user_id", "n");
        entity_id =billingEntityIdList.get(billingSpinner.getSelectedItemPosition()) ;
        groupId = loginpref.getString("group_id", "n");
        subgroupId = loginpref.getString("subgroup_id", "n");


    }
    public class Responce {

        @SerializedName("success")
        @Expose
        private Integer success;
        @SerializedName("message")
        @Expose
        private String message;

        public Integer getSuccess() {
            return success;
        }

        public void setSuccess(Integer success) {
            this.success = success;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}