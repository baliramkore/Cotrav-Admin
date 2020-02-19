package com.cotrav.cotrav_admin.ui.home.flight;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import static androidx.constraintlayout.widget.Constraints.TAG;
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
import com.cotrav.cotrav_admin.asynctask.AddFlightBookingAsyncTask;
import com.cotrav.cotrav_admin.model.assesmentcity_model.AssesmentCities;
import com.cotrav.cotrav_admin.model.assesmentcode_model.AssesmentCodes;
import com.cotrav.cotrav_admin.model.entities_model.Entities;
import com.cotrav.cotrav_admin.model.login_model.UserViewModel;
import com.cotrav.cotrav_admin.model.spoc_model.Spocs;
import com.cotrav.cotrav_admin.ui.home.employee_info.EmployeeInfo;
import com.cotrav.cotrav_admin.ui.utilities.addcode.AssesmentCodeViewModel;
import com.cotrav.cotrav_admin.ui.utilities.asscity.AssesmentCityViewModel;
import com.cotrav.cotrav_admin.ui.utilities.entities.EntitiesViewModel;
import com.cotrav.cotrav_admin.ui.utilities.spocs.SpocsViewModel;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class AddFlightBookingActivity extends AppCompatActivity  implements View.OnClickListener {

    private static final int EMPLOYEE_INFO=100;
    static TextView startdate,starttime,enddate,endtime;
    static  String start_date_send,end_date_send,end_time_send;
    SearchableSpinner assessmentSpinner,assessmentCitySpinner,
    billingSpinner,spocsSpinner;
    static int total=1;
    static int max =4;
    ArrayList<String> assessmentList, assessmentListId;
    ArrayList<String> assessmentCityList, assessmentCityListId;
    ArrayList<String> spocList, spocIdList,billingEntityList, billingEntityIdList;
    ArrayAdapter<String> assessmentAdapter,assessmentCityAdapter,billingEntityAdapter,spocsAdapter;
    AssesmentCodeViewModel assessmentCodeViewModel;
    AssesmentCityViewModel assessmentCitiesViewModel;
    EntitiesViewModel entityViewModel;
    SpocsViewModel spocsViewModel;
    RelativeLayout assessmentLayout;
    SharedPreferences loginpref,spocpref;
    ArrayList<String> usageTypeList;
    ArrayAdapter<String> usageAdapter;
    String authorization,token,corporateId,spocId,empId,groupId,
            subgroupId,selectedDateTime,entity_id,
            booking_datetime="";
    EditText prefFlight,reasomOfBooking;
    UserViewModel viewSpoc;
    String assCodeId="0",assCityId="0";
    TextView fromCity,toCity;
    Button oneWay,roundTrip,submitbtn;
    TextView economyClass,premEconomyClass,businessClass;
    ImageView tripTypeImg;
    RelativeLayout returnLay;
    Toolbar toolbar;
    String classType="";
    String journeyType="One Way";
    SearchableSpinner usageSpinner;
    Activity activity=this;
    static TextView localPassengers;
    Context context=this;
    RectangularBounds bounds = RectangularBounds.newInstance(
            new LatLng(-40,-168),new LatLng(71,136));
    TextView seatertxt,sleeprtxt;
    private static final int DROP_CODE_AUTOCOMPLETE = 0;
    private static final int PICKUP_CODE_AUTOCOMPLETE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flight_booking);
        overridePendingTransition(R.anim.slidein, R.anim.slideout);
        toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setTitle("Add Flight Booking");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar.setNavigationIcon(R.drawable.ic_back);
        }


        if (!Places.isInitialized()) {
            Places.initialize(context, getResources().getString(R.string.places_api_key));
        }
        final PlacesFieldSelector fieldSelector = new PlacesFieldSelector();


        loginpref = getSharedPreferences("login_info", MODE_PRIVATE);
        spocpref = getSharedPreferences("spoc_info", MODE_PRIVATE);
        corporateId = loginpref.getString("corporate_id", "n");
        token = loginpref.getString("access_token", "n");
        spocId = loginpref.getString("spoc_id", "n");
        entity_id = loginpref.getString("entity_id", "n");
        empId = loginpref.getString("emp_id", "n");
        groupId = spocpref.getString("group_id", "n");
        subgroupId = spocpref.getString("subgroup_id", "n");


        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String formattedDate = df.format(c);
        booking_datetime = formattedDate;
        authorization = "Token " + token;


        usageTypeList = new ArrayList<>();
        usageSpinner = findViewById(R.id.usage_type);
        usageTypeList.add("Usage");
        usageTypeList.add("Flight");
        usageTypeList.add("Web Checkin");
        usageAdapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, usageTypeList);
        usageSpinner.setAdapter(usageAdapter);
        fromCity=findViewById(R.id.from_city);
        // fromCity.setAdapter(placeAutocompleteAdapter);
        toCity=findViewById(R.id.to_city);
        localPassengers=findViewById(R.id.local_pessangers);
        spocsSpinner=findViewById(R.id.spoc_spinner);
        billingSpinner=findViewById(R.id.billing_entity);
        //toCity.setAdapter(placeAutocompleteAdapter);
        reasomOfBooking = findViewById(R.id.booking_reason);
        prefFlight = findViewById(R.id.prefered_flight);
        returnLay = findViewById(R.id.return_datetime_lay);
        returnLay.setVisibility(View.GONE);
        tripTypeImg= findViewById(R.id.trip_type_img);
        tripTypeImg.setOnClickListener(this);
        oneWay=findViewById(R.id.one_way);
        oneWay.setOnClickListener(this);
        oneWay.setBackground(ContextCompat.getDrawable(context,R.drawable.round_corner));
        oneWay.setTextColor(ContextCompat.getColor(context,R.color.colorPrimary));
        roundTrip=findViewById(R.id.round_trip);
        roundTrip.setOnClickListener(this);
        economyClass=findViewById(R.id.economy_calss);
        premEconomyClass = findViewById(R.id.prem_economy_class) ;
        businessClass= findViewById(R.id.business_class);
        economyClass.setOnClickListener(this);
        premEconomyClass.setOnClickListener(this);
        businessClass.setOnClickListener(this);
        submitbtn = findViewById(R.id.submit_btn);
        submitbtn.setOnClickListener(this);
        assessmentCitySpinner = findViewById(R.id.assessment_city);
        assessmentSpinner = findViewById(R.id.assessment_code);
        assessmentLayout =  findViewById(R.id.assessment_layout);
        assessmentCityList = new ArrayList<>();
        assessmentCityListId = new ArrayList<>();
        assessmentList = new ArrayList<>();
        assessmentListId = new ArrayList<>();
        spocList = new ArrayList<>();
        spocIdList = new ArrayList<>();
        billingEntityList = new ArrayList<>();
        billingEntityIdList = new ArrayList<>();
        spocsViewModel = ViewModelProviders.of(this).get(SpocsViewModel.class);
        spocsAdapter = new ArrayAdapter<>(context,R.layout.spinner_layout,spocList);
        entityViewModel = ViewModelProviders.of(this).get(EntitiesViewModel.class);
        billingEntityAdapter = new ArrayAdapter<>(context,R.layout.spinner_layout,billingEntityList);
        assessmentCodeViewModel = ViewModelProviders.of(this).get(AssesmentCodeViewModel.class);
        assessmentCitiesViewModel = ViewModelProviders.of(this).get(AssesmentCityViewModel.class);
        assessmentAdapter = new ArrayAdapter<String>(AddFlightBookingActivity.this, R.layout.spinner_layout, assessmentList);
        assessmentCityAdapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, assessmentCityList);
        if (loginpref.getString("has_assessment_codes", "n").equals("0")) {
            assessmentLayout.setVisibility(View.GONE);
        } else {

            assessmentCodeViewModel.InitCorporateCodesViewModel(authorization, "1", corporateId);
            assessmentCitiesViewModel.InitCorporateCitiesViewModel(authorization, "1", corporateId);

            assessmentCitySpinner.setAdapter(assessmentCityAdapter);
            assessmentCitySpinner.setTitle("Select Assessment City");

            assessmentCitiesViewModel.getCorporateCitiesLiveData(authorization, "1", corporateId).observe(this, new Observer<List<AssesmentCities>>() {
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

            assessmentCitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if(assessmentCitySpinner.getSelectedItem().toString().equals("Ass.City")){
                        //((TextView) assessmentCitySpinner.getSelectedView()).setError("field required");
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

            assessmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if(assessmentSpinner.getSelectedItem().toString().equals("Ass. Code")){
                        // ((TextView) assessmentSpinner.getSelectedView()).setError("field required");
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

        }




        starttime = findViewById(R.id.journey_time);
        starttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePickerFragment = new AddFlightBookingActivity.TimePickerFragment();
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
                DialogFragment datePickerFragment = new AddFlightBookingActivity.DatePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putString("start", "start");
                datePickerFragment.setArguments(bundle);
                datePickerFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
        enddate = findViewById(R.id.return_date);
        enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePickerFragment = new AddFlightBookingActivity.DatePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putString("start", "end");
                datePickerFragment.setArguments(bundle);
                datePickerFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
        endtime = findViewById(R.id.return_time);
        endtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePickerFragment = new AddFlightBookingActivity.TimePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putString("start", "end");
                timePickerFragment.setArguments(bundle);
                timePickerFragment.show(getSupportFragmentManager(), "starttime");
            }
        });


        toCity.setOnClickListener(new View.OnClickListener() {
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

        fromCity.setOnClickListener(new View.OnClickListener() {
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
    public void onClick(View view) {
        int id  = view.getId();
        switch (id) {
            case R.id.submit_btn:
                if (isInternetConnected(activity)){

                    if (fromCity.getText().toString().length()<1
                            ||toCity.getText().toString().length()<1
                            ||starttime.getText().toString().length()<1
                            ||startdate.getText().toString().length()<1
                            ||classType.length()<1
                            || spocsSpinner.getSelectedItemPosition() == 0
                            || billingSpinner.getSelectedItemPosition() == 0
                            ||localPassengers.getText().equals("Passengers")
                            ||reasomOfBooking.getText().toString().length()<1
                            ||prefFlight.getText().toString().length()<1
                            ||assessmentSpinner.getSelectedItemPosition()==0
                            ||usageSpinner.getSelectedItemPosition()==0
                            ||assessmentCitySpinner.getSelectedItemPosition()==0
                    ){
                        //Toasty.error(this, "Error   ").show();
                        if (starttime.getText().toString().length()<2)
                            starttime.setError("Not Slected");
                        if(startdate.getText().toString().length()<2){

                            startdate.setError("Not Slected");
                        }
                        if (fromCity.getText().toString().length()<1){
                            fromCity.setError("Select City");
                        }
                        if (classType.length() < 1) {

                            Toasty.error(this, "Select Flight Class").show();
                        }
                        if (toCity.getText().toString().length()<1){
                            toCity.setError("Select City");
                        }
                        if (assessmentSpinner.getSelectedItemPosition() == 0) {
                            ((android.widget.TextView) assessmentSpinner.getSelectedView()).setError("field required");
                        }
                        if (assessmentCitySpinner.getSelectedItemPosition() == 0) {
                            ((android.widget.TextView) assessmentCitySpinner.getSelectedView()).setError("field required");
                        }
                        if (usageSpinner.getSelectedItemPosition() == 0) {
                            ((android.widget.TextView) usageSpinner.getSelectedView()).setError("field required");
                        }

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
                        if (reasomOfBooking.getText().toString().length()<1){
                            reasomOfBooking.setError("Reason is must");
                        }
                        if (prefFlight.getText().toString().length()<1){
                            prefFlight.setError("preffred Bus");
                        }
                    }
                    else {
                        Toasty.normal(this, "Processing  ").show();

                        final Dialog dialog = new Dialog(AddFlightBookingActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.custom_dialog_box);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                        TextView mDialogmsg = dialog.findViewById(R.id.dialog_message);
                        mDialogmsg.setText("Proceed To Complete Booking.");

                        TextView mDialogtitle = dialog.findViewById(R.id.dialog_title);
                        mDialogtitle.setText("COMPLETE Flight BOOKING");

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

                                selectedDateTime = startdate.getText().toString() ;
                                new AddFlightBookingAsyncTask(activity,token,
                                        empId, corporateId, spocId, groupId, subgroupId, selectedDateTime,
                                        booking_datetime,
                                        fromCity.getText().toString(),
                                        toCity.getText().toString()
                                        ,entity_id,
                                        assCodeId,assCityId,
                                        usageSpinner.getSelectedItem().toString(),
                                        classType,
                                        reasomOfBooking.getText().toString(),
                                        prefFlight.getText().toString(),
                                        journeyType).execute(activity);

                                dialog.dismiss();
                            }
                        });

                        dialog.show();



                   /* AlertDialog.Builder ad=new AlertDialog.Builder(AddBusBookingActivity.this);
                    ad.setTitle("COMPLETE BUS BOOKING");
                    ad.setMessage("Proceed To Complete Booking");
                    ad.setCancelable(false);
                    ad.setPositiveButton("proceed", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                    selectedDateTime = startdate.getText().toString() + " " + starttime.getText().toString();
                    String assCode =""+assessmentSpinner.getSelectedItemPosition();
                    String assCity =""+assessmentCitySpinner.getSelectedItemPosition();
                    new AddBusBookingAsyncTask(activity,token,
                            empId, corporateId, spocId, groupId, subgroupId, selectedDateTime,
                            pickupCity.getText().toString(),
                            dropCity.getText().toString(),
                            booking_datetime,entity_id,
                            assCodeId,
                            assCityId,
                            flight_type,
                            reasomOfBooking.getText().toString(),
                            prefBus.getText().toString()).execute(activity);}
                    });
                    ad.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    ad.show();*/
                    }}
                else Toasty.error(this,"Check Network Connection").show();
                break;
            case R.id.one_way:
                journeyType="One Way";
                oneWay.setBackground(ContextCompat.getDrawable(context,R.drawable.round_corner));
                oneWay.setTextColor(ContextCompat.getColor(context,R.color.colorPrimary));
                roundTrip.setBackground(ContextCompat.getDrawable(context,R.drawable.round_corner_transparent));
                roundTrip.setTextColor(ContextCompat.getColor(context,R.color.white));
                tripTypeImg.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_one_way));
                returnLay.setVisibility(View.GONE);


                break;

            case R.id.round_trip:
                journeyType="Round Trip";
                roundTrip.setBackground(ContextCompat.getDrawable(context,R.drawable.round_corner));
                roundTrip.setTextColor(ContextCompat.getColor(context,R.color.colorPrimary));
                oneWay.setBackground(ContextCompat.getDrawable(context,R.drawable.round_corner_transparent));
                oneWay.setTextColor(ContextCompat.getColor(context,R.color.white));

                tripTypeImg.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_round_trip));
                returnLay.setVisibility(View.VISIBLE);
                //YoYo.with(Techniques.SlideInDown).duration(200).repeat(0).playOn(returnLay);

                break;

            case R.id.economy_calss:
                classType ="Economy";
                economyClass.setBackground(ContextCompat.getDrawable(context,R.drawable.round_corner));
                premEconomyClass.setBackground(ContextCompat.getDrawable(context,R.drawable.round_corner_lightwhite_background));
                businessClass.setBackground(ContextCompat.getDrawable(context,R.drawable.round_corner_lightwhite_background));

                break;

            case R.id.prem_economy_class:
                classType="Premium Economy";
                premEconomyClass.setBackground(ContextCompat.getDrawable(context,R.drawable.round_corner));
                economyClass.setBackground(ContextCompat.getDrawable(context,R.drawable.round_corner_lightwhite_background));
                businessClass.setBackground(ContextCompat.getDrawable(context,R.drawable.round_corner_lightwhite_background));
                break;

            case R.id.business_class:
                classType="Business";
                businessClass.setBackground(ContextCompat.getDrawable(context,R.drawable.round_corner));
                economyClass.setBackground(ContextCompat.getDrawable(context,R.drawable.round_corner_lightwhite_background));
                premEconomyClass.setBackground(ContextCompat.getDrawable(context,R.drawable.round_corner_lightwhite_background));
                break;



        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Log.d("request code",requestCode+"");
        // Log.d("employeee", Arrays.toString(employeeIdList.toArray()));

        // Check that the result was from the autocomplete widget.
        if (requestCode == PICKUP_CODE_AUTOCOMPLETE) {
            if (resultCode == RESULT_OK) {
                // Get the user's selected place from the Intent.
                String location=intent.getStringExtra("LOCATION");
                //Place place = PlaceAutocomplete.getPlace(this, data);
                Log.i(TAG, "Place Selected: " + location);
                Place place = Autocomplete.getPlaceFromIntent(intent);

                // Format the place's details and display them in the TextView.
                fromCity.setError(null);

                fromCity.setText(place.getName());
                fromCity.append(", "+place.getAddress());

            }
        } else if (requestCode == DROP_CODE_AUTOCOMPLETE) {
            if (resultCode == RESULT_OK) {
                String location=intent.getStringExtra("LOCATION");
                Log.i(TAG, "Place Selected: " + location);
                Place place = Autocomplete.getPlaceFromIntent(intent);
                toCity.setError(null);
                toCity.setText(place.getName());
                toCity.append(", "+place.getAddress());


            } else if (resultCode == RESULT_CANCELED) {
                // Indicates that the activity closed before a selection was made. For example if
                // the user pressed the back button.
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

                } else {
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

        public static NoOfPassengers newInstance(int no){
            NoOfPassengers frag = new NoOfPassengers();
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
}
