package com.cotrav.cotrav_admin.ui.home.hotel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
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
import com.cotrav.cotrav_admin.AllCityViewModel;
import com.cotrav.cotrav_admin.GsonStringConvertor;
import com.cotrav.cotrav_admin.PlacesFieldSelector;
import com.cotrav.cotrav_admin.R;
import com.cotrav.cotrav_admin.asynctask.AddHotelBookingAsyncTask;
import com.cotrav.cotrav_admin.model.all_cities_model.City;
import com.cotrav.cotrav_admin.model.assesmentcity_model.AssesmentCities;
import com.cotrav.cotrav_admin.model.assesmentcode_model.AssesmentCodes;
import com.cotrav.cotrav_admin.model.entities_model.Entities;
import com.cotrav.cotrav_admin.model.home_model.hotel_model.add_hotel.hotel_type.HotelType;
import com.cotrav.cotrav_admin.model.home_model.hotel_model.add_hotel.room_type.RoomType;
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
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import es.dmoral.toasty.Toasty;
import static androidx.constraintlayout.widget.Constraints.TAG;
public class AddHotelBookingActivity extends AppCompatActivity implements View.OnClickListener
{
    private static final int EMPLOYEE_INFO=100;
    private Toolbar toolbar;
    Button submitBtn;
    Activity activity = this;
    //TextView  journeyDate,journeyTime;
    static TextView startdate,starttime,enddate,endtime;
    static TextView localPassengers;
    SearchableSpinner assessmentSpinner,assessmentCitySpinner,
            billingSpinner,spocsSpinner,
    citySpinner,roomTypeSpinner,roomTypeSpinner2,hotelTypeSpinner;
    ArrayList<String> assessmentCityList, assessmentCityListId;
    ArrayList<String> assessmentList, assessmentListId;
    ArrayList<String> cityList, cityListId;
    ArrayList<String> roomTypeList, roomTypeListId;
    ArrayList<String> roomTypeList2, roomTypeListId2;
    ArrayList<String> hotelTypeList, hotelTypeListId;
    ArrayList<String> employee_idlist;
    ArrayList<String> getEmployee_idphno;
    ArrayList<String> employee_id_value;

    ArrayAdapter<String> assessmentCityAdapter,assessmentAdapter,
            cityAdapter,roomTypeAdapter,roomTypeAdapter2,hotelTypeAdapter,spocsAdapter,billingEntityAdapter;
    HotelBookingsViewModel hotelBookingsViewModel;
    AssesmentCityViewModel assessmentCitiesViewModel;
    AssesmentCodeViewModel assessmentCodeViewModel;
    AllCityViewModel citiesViewModel;
    EntitiesViewModel entityViewModel;
    RelativeLayout assessmentLayout;
    static ArrayList<String> assesmentlist;
    static ArrayAdapter<String> arrayAdapter,assesmentadapter;
    SharedPreferences loginpref,spocpref;
    static int total=1;
    static int max =4;
    static  String start_date_send,end_date_send,end_time_send;
    String authorization,token,corporateId,spocId,empId,groupId,
            subgroupId,checkInDateTime,checkOutDateTime,entity_id,
            booking_datetime="";
    ArrayList<String> spocList, spocIdList,billingEntityList, billingEntityIdList;
    TextView prefArea;
    EditText reasomOfBooking,prefHotel;
    String assCodeId="0",assCityId="0",cityId,roomTypeId1,roomTypeId2,hotelTypeId;
    Context context=this;
    RectangularBounds bounds = RectangularBounds.newInstance(
            new LatLng(-40,-168),new LatLng(71,136));
    private static final int PREFAREA = 1;
    ProgressDialog progressDialog;
    SpocsViewModel spocsViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hotel_booking);
        overridePendingTransition(R.anim.slidein, R.anim.slideout);
        toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setTitle("Add Hotel Booking");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar.setNavigationIcon(R.drawable.ic_back);
        }

        if (!Places.isInitialized()) {
            Places.initialize(context, getResources().getString(R.string.places_api_key));
        }
        final PlacesFieldSelector fieldSelector = new PlacesFieldSelector();

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Getting Cities");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        loginpref = getSharedPreferences("login_info", MODE_PRIVATE);
        spocpref = getSharedPreferences("spoc_info", MODE_PRIVATE);
        corporateId = loginpref.getString("corporate_id", "n");
        token = loginpref.getString("access_token", "n");
        entity_id = loginpref.getString("entity_id", "n");
        empId = loginpref.getString("emp_id", "n");
        groupId = spocpref.getString("group_id", "n");
        subgroupId = spocpref.getString("subgroup_id", "n");
        billingSpinner=findViewById(R.id.billing_entity);
        spocsSpinner=findViewById(R.id.spoc_spinner);
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String formattedDate = df.format(c);
        booking_datetime = formattedDate;
        authorization = "Token " + token;
        reasomOfBooking = findViewById(R.id.booking_reason);
        prefHotel = findViewById(R.id.prefered_hotel);

        assessmentCitySpinner = findViewById(R.id.assessment_city);
        assessmentSpinner = findViewById(R.id.assessnment_code);
        assessmentLayout =  findViewById(R.id.assessment_layout);
        citySpinner = findViewById(R.id.city_spinner);
        roomTypeSpinner = findViewById(R.id.room_type);
        roomTypeSpinner2 = findViewById(R.id.room_type2);
        hotelTypeSpinner = findViewById(R.id.hotel_type);
        prefArea = findViewById(R.id.pref_area);
        localPassengers=findViewById(R.id.local_pessangers);
        submitBtn = findViewById(R.id.submit_btn);

        submitBtn.setOnClickListener(this);
        assesmentlist = new ArrayList<>();

        assessmentCityList = new ArrayList<>();
        assessmentCityListId = new ArrayList<>();
        assessmentList = new ArrayList<>();
        assessmentListId = new ArrayList<>();
        cityList = new ArrayList<>();cityListId = new ArrayList<>();
        roomTypeList = new ArrayList<>();roomTypeListId = new ArrayList<>();
        roomTypeList2 = new ArrayList<>();roomTypeListId2 = new ArrayList<>();
        hotelTypeList = new ArrayList<>();hotelTypeListId = new ArrayList<>();

        spocList = new ArrayList<>();
        spocIdList = new ArrayList<>();
        billingEntityList = new ArrayList<>();
        billingEntityIdList = new ArrayList<>();

        employee_idlist = new ArrayList<String>();
        getEmployee_idphno = new ArrayList<String>();
        employee_id_value = new ArrayList<>();

        hotelBookingsViewModel = ViewModelProviders.of(this).get(HotelBookingsViewModel.class);
        assessmentCitiesViewModel = ViewModelProviders.of(this).get(AssesmentCityViewModel.class);
        assessmentCodeViewModel = ViewModelProviders.of(this).get(AssesmentCodeViewModel.class);
        entityViewModel = ViewModelProviders.of(this).get(EntitiesViewModel.class);
        citiesViewModel = ViewModelProviders.of(this).get(AllCityViewModel.class);

        assessmentCityAdapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, assessmentCityList);
        assessmentAdapter = new ArrayAdapter<String>(AddHotelBookingActivity.this, R.layout.spinner_layout, assessmentList);
        cityAdapter = new ArrayAdapter<>(AddHotelBookingActivity.this,R.layout.city_spinner_layout,cityList);
        roomTypeAdapter = new ArrayAdapter<>(AddHotelBookingActivity.this,R.layout.spinner_layout,roomTypeList);
        hotelTypeAdapter = new ArrayAdapter<>(AddHotelBookingActivity.this,R.layout.spinner_layout,hotelTypeList);
        roomTypeAdapter2 = new ArrayAdapter<>(AddHotelBookingActivity.this,R.layout.spinner_layout,roomTypeList2);
        billingEntityAdapter = new ArrayAdapter<>(context,R.layout.spinner_layout,billingEntityList);


        spocsViewModel = ViewModelProviders.of(this).get(SpocsViewModel.class);
        spocsAdapter = new ArrayAdapter<>(context,R.layout.spinner_layout,spocList);

        citiesViewModel.InitCityViewModel(authorization,"1",corporateId);
        citySpinner.setAdapter(cityAdapter);
        citySpinner.setTitle("Select City");
        citiesViewModel.getCityLiveData(authorization,"1",corporateId).observe(this, new Observer<List<City>>() {
            @Override
            public void onChanged(List<City> cities) {
                cityList.clear();
                cityListId.clear();
                cityList.add("Select City");
                cityListId.add("Select City id");
                if (cities != null) {
                    for (int i = 0; i < cities.size(); i++) {
                        cityList.add(cities.get(i).getCityName());
                        cityListId.add(String.valueOf(cities.get(i).getId()));
                    }
                }
                progressDialog.dismiss();
                cityAdapter.notifyDataSetChanged();
            }
        });


        hotelBookingsViewModel.initHotelData(authorization,"1");
        hotelTypeSpinner.setAdapter(hotelTypeAdapter);
        hotelTypeSpinner.setTitle("Select Room Occupancy");

        hotelBookingsViewModel.getHotelTypeInfo(authorization,"1").observe(this, new Observer<List<HotelType>>() {
            @Override
            public void onChanged(List<HotelType> hotelTypes) {

                hotelTypeList.clear();
                hotelTypeListId.clear();
                hotelTypeList.add("Room Occupancy ");
                hotelTypeListId.add("Hotel Type id");
                if (hotelTypes != null) {
                    for (int i = 0; i < hotelTypes.size(); i++) {
                        hotelTypeList.add(hotelTypes.get(i).getName());
                        hotelTypeListId.add(hotelTypes.get(i).getId());
                    }
                }
                hotelTypeAdapter.notifyDataSetChanged();
            }
        });



        roomTypeSpinner.setTitle("Select Room Type");
        roomTypeSpinner2.setTitle("Select Room Type");
        roomTypeSpinner.setAdapter(roomTypeAdapter);
        roomTypeSpinner2.setAdapter(roomTypeAdapter2);
        hotelBookingsViewModel.getRoomTypeList(authorization,"1").observe(this, new Observer<List<RoomType>>() {
            @Override
            public void onChanged(List<RoomType> roomTypes) {
                roomTypeList.clear();
                roomTypeListId.clear();
                roomTypeList.add("Room Type ");
                roomTypeListId.add("Room Type id");
                if (roomTypes != null) {
                    for (int i = 0; i < roomTypes.size(); i++) {
                        roomTypeList.add(roomTypes.get(i).getName());
                        roomTypeListId.add(roomTypes.get(i).getId());
                    }
                }
                roomTypeAdapter.notifyDataSetChanged();

                roomTypeList2.clear();
                roomTypeListId2.clear();
                roomTypeList2.add("Room Type ");
                roomTypeListId2.add("Room Type id");
                if (roomTypes != null) {
                    for (int i = 0; i < roomTypes.size(); i++) {
                        roomTypeList2.add(roomTypes.get(i).getName());
                        roomTypeListId2.add(roomTypes.get(i).getId());
                    }
                }
                roomTypeAdapter2.notifyDataSetChanged();
            }

        });

        if (loginpref.getString("has_assessment_codes", "n").equals("0")) {
            assessmentLayout.setVisibility(View.GONE);
        } else {
            assessmentCodeViewModel.InitCorporateCodesViewModel(authorization, "1", corporateId);
            assessmentCitiesViewModel.InitCorporateCitiesViewModel(authorization, "1", corporateId);

            assessmentCitySpinner.setAdapter(assessmentCityAdapter);
            assessmentCitySpinner.setTitle("Select Assessment City");

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
            assessmentSpinner.setTitle("Select Assessment Code");
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
        }

        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(citySpinner.getSelectedItem().toString().equals("Room Type ")){
                    // ((TextView) citySpinner.getSelectedView()).setError("field required");
                }else{
                    cityId=cityListId.get(citySpinner.getSelectedItemPosition());
                    //gettingLatLng(taxiModelname);
                }
                cityId=cityListId.get(citySpinner.getSelectedItemPosition());
                Log.e("assCodeId",cityId);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        roomTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(roomTypeSpinner.getSelectedItem().toString().equals("Room Type ")){
                    // ((TextView) roomTypeSpinner.getSelectedView()).setError("field required");
                }else{
                    roomTypeId1=roomTypeListId.get(roomTypeSpinner.getSelectedItemPosition());
                    //gettingLatLng(taxiModelname);
                }
                roomTypeId1=roomTypeListId.get(roomTypeSpinner.getSelectedItemPosition());
                Log.e("assCodeId",roomTypeId1);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        roomTypeSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(roomTypeSpinner2.getSelectedItem().toString().equals("Room Type ")){
                    // ((TextView) roomTypeSpinner2.getSelectedView()).setError("field required");
                }else{
                    roomTypeId2=roomTypeListId2.get(roomTypeSpinner2.getSelectedItemPosition());
                    //gettingLatLng(taxiModelname);
                }
                roomTypeId2=roomTypeListId2.get(roomTypeSpinner2.getSelectedItemPosition());
                Log.e("assCodeId",roomTypeId2);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        hotelTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(hotelTypeSpinner.getSelectedItem().toString().equals("Room Occupancy ")){
                    //((TextView) hotelTypeSpinner.getSelectedView()).setError("field required");
                }else{
                    hotelTypeId=hotelTypeListId.get(hotelTypeSpinner.getSelectedItemPosition());
                    //gettingLatLng(taxiModelname);
                }
                hotelTypeId=hotelTypeListId.get(hotelTypeSpinner.getSelectedItemPosition());
                Log.e("assCodeId",hotelTypeId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        starttime = findViewById(R.id.checkin_time);
        starttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePickerFragment = new AddHotelBookingActivity.TimePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putString("start", "start");
                timePickerFragment.setArguments(bundle);
                timePickerFragment.show(getSupportFragmentManager(), "starttime");
            }
        });
        startdate = findViewById(R.id.checkin_date);
        startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePickerFragment = new AddHotelBookingActivity.DatePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putString("start", "start");
                datePickerFragment.setArguments(bundle);
                datePickerFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
        endtime = findViewById(R.id.checkout_time);
        endtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePickerFragment = new AddHotelBookingActivity.TimePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putString("start", "end");
                timePickerFragment.setArguments(bundle);
                timePickerFragment.show(getSupportFragmentManager(), "endtime");
            }
        });
        enddate = findViewById(R.id.checkout_date);
        enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePickerFragment = new AddHotelBookingActivity.DatePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putString("start", "end");
                datePickerFragment.setArguments(bundle);
                datePickerFragment.show(getSupportFragmentManager(), "enddate");
            }
        });


        prefArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent autocompleteIntent = new Autocomplete.
                        IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldSelector.getAllFields())
                        .setLocationBias(bounds)
                        .setCountry("IN")
                        .build(context);
                startActivityForResult(autocompleteIntent, PREFAREA);
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
        localPassengers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment passengerPicker = new NoOfPassengers();
                passengerPicker.show(getSupportFragmentManager(), "no of radio_passenger");
            }
        });


    }

    @Override
    public void onClick(View view) {
        int id  = view.getId();

        switch (id) {
            case R.id.submit_btn:


                if (isInternetConnected(activity)){

                    if (
                            citySpinner.getSelectedItemPosition()==0
                                    ||starttime.getText().toString().length()<1
                                    ||startdate.getText().toString().length()<1
                                    ||enddate.getText().toString().length()<1
                                    ||endtime.getText().toString().length()<1
                                    ||reasomOfBooking.getText().toString().length()<1
                                    ||assessmentSpinner.getSelectedItemPosition()==0
                                    ||roomTypeSpinner.getSelectedItemPosition()==0
                                    ||roomTypeSpinner2.getSelectedItemPosition()==0
                                    ||hotelTypeSpinner.getSelectedItemPosition()==0
                                    ||prefArea.getText().toString().length()<1
                                    || spocsSpinner.getSelectedItemPosition() == 0
                                    || billingSpinner.getSelectedItemPosition() == 0
                                    ||localPassengers.getText().equals("Passengers")
                                    ||prefHotel.getText().toString().length()<1
                                    || assessmentCitySpinner.getSelectedItemPosition()==0)
                    {
                        //Toasty.error(this, "Error   ").show();

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



                        if (citySpinner.getSelectedItemPosition() == 0) {
                            ((android.widget.TextView) citySpinner.getSelectedView()).setError("Select City");
                        }
                        if (starttime.getText().toString().length()<1){
                            starttime.setError("Not Slected");
                        }
                        if(startdate.getText().toString().length()<2){
                            startdate.setError("Not Slected");
                        }
                        if (endtime.getText().toString().length()<2){
                            endtime.setError("Not Slected");
                        }
                        if (enddate.getText().toString().length()<2){
                            enddate.setError("Not Slected");

                        }

                        if (prefHotel.getText().toString().length()<1){
                            prefHotel.setError("Select Pref Hotel");
                        }
                        if (prefArea.getText().toString().length()<1){
                            prefArea.setError("Select Pref area");
                        }

                        if (roomTypeSpinner.getSelectedItemPosition() == 0) {
                            ((android.widget.TextView) roomTypeSpinner.getSelectedView()).setError("field required");
                        }
                        if (roomTypeSpinner2.getSelectedItemPosition() == 0) {
                            ((android.widget.TextView) roomTypeSpinner2.getSelectedView()).setError("field required");
                            Toasty.error(this,"Prefer Two Room Type").show();
                        }
                        if (hotelTypeSpinner.getSelectedItemPosition() == 0) {
                            ((android.widget.TextView) hotelTypeSpinner.getSelectedView()).setError("field required");
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
                    }
                    else {
                        Toasty.normal(this, "Processing  ").show();

                        final Dialog dialog = new Dialog(AddHotelBookingActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.custom_dialog_box);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                        TextView mDialogmsg = dialog.findViewById(R.id.dialog_message);
                        mDialogmsg.setText("Proceed To Complete Booking.");

                        TextView mDialogtitle = dialog.findViewById(R.id.dialog_title);
                        mDialogtitle.setText("COMPLETE Hotel BOOKING");

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
                                checkInDateTime = startdate.getText().toString() + " " + starttime.getText().toString();
                                checkOutDateTime = enddate.getText().toString() + " " + endtime.getText().toString();
                                new AddHotelBookingAsyncTask(activity,
                                        token,
                                        empId, corporateId, spocId, groupId, subgroupId,
                                        cityId,cityId,
                                        checkInDateTime,checkOutDateTime,
                                        assCityId,assCodeId,
                                        roomTypeId1,roomTypeId2,
                                        hotelTypeId,
                                        prefArea.getText().toString(),
                                        booking_datetime,
                                        entity_id,
                                        reasomOfBooking.getText().toString(),
                                        prefHotel.getText().toString(),employee_id_value).execute(activity);
                                dialog.dismiss();
                                /*
                                .add("user_id",empId)
                                        .add("corporate_id", corporateId)
                                        .add("spoc_id",spocId)
                                        .add("group_id",groupId)
                                        .add("subgroup_id",subgroupId)
                                        .add("booking_datetime",booking_datetime)
                                        .add("checkin_datetime",checkin_datetime)
                                        .add("checkout_datetime",checkout_datetime)
                                        .add("billing_entity_id",entity_id)
                                        .add("preferred_hotel",prefHotel)
                                        .add("assessment_code",assessment_code)
                                        .add("assessment_city_id",assessment_city_id)
                                        .add("reason_booking",bookingReason)
                                        .add("no_of_seats","1")
                                        .add("employees",empId)
                                        .add("room_type_id",room_type_id)
                                        .add("bucket_priority_1",bucket_priority_1)
                                        .add("bucket_priority_2",bucket_priority_2)
                                        .add("preferred_area",preferred_area)
                                        .add("from_area_id",from_area_id)
                                        .add("from_city_id",from_city_id)
                                        .build();*/




                            }
                        });

                        dialog.show();



                    }}
                else Toasty.error(this,"Check Network Connection").show();
                break;

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
        hotelBookingsViewModel.refreshUpcomingBooking(authorization,"6",empId);
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

            ok.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Log.d("request code",requestCode+"");

   if (requestCode==PREFAREA)
   {
       if (resultCode == RESULT_OK)
       {
           String location=intent.getStringExtra("LOCATION");
           Log.i(TAG, "Place Selected: " + location);
           Place place = Autocomplete.getPlaceFromIntent(intent);
           // Format the place's details and display them in the TextView.
           prefArea.setError(null);
           prefArea.setText(place.getName());
           prefArea.append("\n"+place.getAddress());

       }
   }
        if (requestCode ==EMPLOYEE_INFO){

            if(resultCode == RESULT_OK){
                employee_idlist = intent.getStringArrayListExtra("employee_id");
                getEmployee_idphno = intent.getStringArrayListExtra("employee_phno");
                employee_id_value = intent.getStringArrayListExtra("employeeIdValue");
            }
        }
    }
}
