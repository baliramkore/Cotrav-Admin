package com.cotrav.cotrav_admin.ui.home.taxi.addTaxiFragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
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
import com.cotrav.cotrav_admin.asynctask.AddTaxiBookingAsyncTask;
import com.cotrav.cotrav_admin.model.all_cities_model.City;
import com.cotrav.cotrav_admin.model.assesmentcity_model.AssesmentCities;
import com.cotrav.cotrav_admin.model.assesmentcode_model.AssesmentCodes;
import com.cotrav.cotrav_admin.model.entities_model.Entities;
import com.cotrav.cotrav_admin.model.login_model.UserViewModel;
import com.cotrav.cotrav_admin.model.spoc_model.Spocs;
import com.cotrav.cotrav_admin.model.taxi_types_model.TaxiType;
import com.cotrav.cotrav_admin.retrofit.APIurls;
import com.cotrav.cotrav_admin.ui.home.employee_info.EmployeeInfo;
import com.cotrav.cotrav_admin.ui.home.taxi.TaxiTypeViewModel;
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
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.snackbar.Snackbar;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import es.dmoral.toasty.Toasty;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentLocalTaxi extends Fragment implements View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener {
    private static final int EMPLOYEE_INFO=100;
    Button submitBtn;
    Context context;
    Activity activity;
    //TextView  journeyDate,journeyTime;
    static TextView startdate, starttime, enddate, endtime;
    ArrayList<String> pacakagelist, pacakage_idlist;
    HashMap<String, String> packagehashMap;
    ProgressDialog pd;
    AlertDialog.Builder b;
    String pacakagevalue;
    static int clickCount= 0;
    static int total=1;
    static int max =4;
    String acSlected = "0", nonAcSelected = "0", sleeperSelected = "0", seaterSlected = "0";
    SearchableSpinner assessmentSpinner, assessmentCitySpinner, taxitypeSpinner, pickupCitySpinner, pacakageSpinner,
            billingSpinner,spocsSpinner;
    ArrayList<String> assessmentCityList, assessmentCityListId;
    ArrayList<String> assessmentList, assessmentListId;
    ArrayList<String> prefTaxiTypeList, prefTaxiTypeListId;
    ArrayList<String> cityList, cityListId;
    ArrayAdapter<String> pacakageadapter;
    ArrayAdapter<String> assessmentCityAdapter, assessmentAdapter, prefTaxiTypeAdapter, cityAdapter,billingEntityAdapter,spocsAdapter;
    AssesmentCityViewModel assessmentCitiesViewModel;
    AssesmentCodeViewModel assessmentCodeViewModel;
    TaxiTypeViewModel taxiTypeViewModel;
    AllCityViewModel citiesViewModel;
    EntitiesViewModel entityViewModel;
    SpocsViewModel spocsViewModel;
    RelativeLayout assessmentLayout;
    static ArrayList<String> assesmentlist;
    static ArrayAdapter<String> arrayAdapter, assesmentadapter;
    TextView dropLocation;
    TextView pickupLocation;
    static TextView localPassengers;
    SharedPreferences loginpref, allCitiesSharedPreferences, adminpref;
    List<City> allCities;
    static String start_date_send, end_date_send, end_time_send;
    EditText prefTaxiNO, reasomOfBooking;
    UserViewModel viewAdmin;
    String assCodeId = "0", assCityId = "0", prefTaxitypeId, cityId, pacakageId = "";
    RectangularBounds bounds = RectangularBounds.newInstance(
            new LatLng(-40, -168), new LatLng(71, 136));
    private static final int DROP_CODE_AUTOCOMPLETE = 0;
    private static final int PICKUP_CODE_AUTOCOMPLETE = 1;
    ArrayList<String> employee_idlist;
    ArrayList<String> getEmployee_idphno;
    ArrayList<String> employee_id_value;

    View view;
    ProgressDialog progressDialog;
    static String hasAssCode,access_token,userType,userId,spocId,groupId,subgroupId,mpickupCity,mdropCity,mtaxiType,mpickupLocation,mdropLocation,mpickupDate,
            corporateId,authorization,adminId,empId,booking_datetime,finalDateAndTime,entity_id,
    mpickupTime,mpessengers,mpackage,massCity,massCode,mbiilEntity,mreason,selectedDateTime;

    ArrayList<String> spocList, spocIdList,billingEntityList, billingEntityIdList;

    public FragmentLocalTaxi() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
/*        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_local_taxi, container, false);*/

        context = getContext();
        activity = getActivity();
        loginpref = context.getSharedPreferences("login_info", MODE_PRIVATE);
        if (loginpref.getString("is_local", "n").equals("1")) {


            view = inflater.inflate(R.layout.fragment_local_taxi, container, false);


            if (!Places.isInitialized()) {
                Places.initialize(context, getResources().getString(R.string.places_api_key));
            }
            final PlacesFieldSelector fieldSelector = new PlacesFieldSelector();

            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Getting Cities");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            allCitiesSharedPreferences = context.getSharedPreferences("cities_info", MODE_PRIVATE);
            //allCities = allCitiesSharedPreferences.getString("cities","n");
            adminpref = context.getSharedPreferences("admin_info", MODE_PRIVATE);
            corporateId = loginpref.getString("corporate_id", "n");
            access_token = loginpref.getString("access_token", "n");
            authorization = "Token " + access_token;
            adminId = loginpref.getString("admin_id", "n");
            //entity_id = loginpref.getString("entity_id", "n");
            empId = loginpref.getString("emp_id", "n");
           // groupId = loginpref.getString("group_id", "n");
            //subgroupId = loginpref.getString("subgroup_id", "n");
            Date c = Calendar.getInstance().getTime();
            System.out.println("Current time => " + c);

            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String formattedDate = df.format(c);
            booking_datetime = formattedDate;
            pacakagelist = new ArrayList<>();
            pacakage_idlist = new ArrayList<>();
            packagehashMap = new HashMap<>();
            assesmentlist = new ArrayList<>();
            assessmentCityList = new ArrayList<>();
            assessmentCityListId = new ArrayList<>();
            assessmentList = new ArrayList<>();
            assessmentListId = new ArrayList<>();
            cityList = new ArrayList<>();
            cityListId = new ArrayList<>();
            prefTaxiTypeList = new ArrayList<>();
            prefTaxiTypeListId = new ArrayList<>();

            spocList = new ArrayList<>();
            spocIdList = new ArrayList<>();
            billingEntityList = new ArrayList<>();
            billingEntityIdList = new ArrayList<>();

            employee_idlist=new ArrayList<>();
            getEmployee_idphno=new ArrayList<>();
            employee_id_value=new ArrayList<>();

            reasomOfBooking = view.findViewById(R.id.booking_reason);
            pacakageSpinner = view.findViewById(R.id.package_spinner);
            billingSpinner=view.findViewById(R.id.billing_entity);
            spocsSpinner=view.findViewById(R.id.spoc_spinner);
            taxitypeSpinner = view.findViewById(R.id.pref_taxi_type);
            assessmentCitySpinner = view.findViewById(R.id.assessment_city);
            assessmentSpinner = view.findViewById(R.id.assessnment_code);
            assessmentLayout = view.findViewById(R.id.assessment_layout);
            pickupCitySpinner = view.findViewById(R.id.city_spinner);
            dropLocation = (TextView) view.findViewById(R.id.drop_location);
            pickupLocation = (TextView) view.findViewById(R.id.pickup_location);
            localPassengers=view.findViewById(R.id.local_pessangers);
            submitBtn = view.findViewById(R.id.submit_btn);
            submitBtn.setOnClickListener(this);



            citiesViewModel = ViewModelProviders.of(this).get(AllCityViewModel.class);
            citiesViewModel.getCityLiveData(authorization, "1", corporateId);
            taxiTypeViewModel = ViewModelProviders.of(this).get(TaxiTypeViewModel.class);
            entityViewModel = ViewModelProviders.of(getActivity()).get(EntitiesViewModel.class);
            spocsViewModel = ViewModelProviders.of(getActivity()).get(SpocsViewModel.class);
            assessmentCitiesViewModel = ViewModelProviders.of(getActivity()).get(AssesmentCityViewModel.class);
            assessmentCitiesViewModel.InitCorporateCitiesViewModel(authorization,"1",corporateId);
            assessmentCodeViewModel = ViewModelProviders.of(this).get(AssesmentCodeViewModel.class);
            assessmentCodeViewModel.InitCorporateCodesViewModel(authorization, "1", corporateId);

            assessmentAdapter=new ArrayAdapter<>(context,R.layout.spinner_layout,assessmentList);
            assessmentCityAdapter=new ArrayAdapter<>(context,R.layout.spinner_layout,assessmentCityList);
            prefTaxiTypeAdapter = new ArrayAdapter<>(context,R.layout.spinner_layout,prefTaxiTypeList);
            billingEntityAdapter = new ArrayAdapter<>(context,R.layout.spinner_layout,billingEntityList);
            spocsAdapter = new ArrayAdapter<>(context,R.layout.spinner_layout,spocList);
            pacakageadapter=new ArrayAdapter<String>(context,R.layout.spinner_layout,pacakagelist);
            pacakagelist.add(new String("Select Pacakage"));
            pacakage_idlist.add("Select Package Id");
            pacakageSpinner.setAdapter(pacakageadapter);
            cityAdapter = new ArrayAdapter<>(context, R.layout.spinner_layout, cityList);

            cityList.add(new String("Select City"));
            cityListId.add(new String("Select City"));
            cityAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_layout, cityList);
            pickupCitySpinner.setAdapter(cityAdapter);
            citiesViewModel.getCityLiveData(authorization, "1", corporateId).
                    observe(this,
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
                                        cityAdapter.notifyDataSetChanged();
                                        progressDialog.dismiss();
                                    }

                                }
                            });
            pickupCitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (pickupCitySpinner.getSelectedItem().toString().equals("Select City")) {
                        // ((TextView) citySpinner.getSelectedView()).setError("field required");
                    } else {
                        cityId = cityListId.get(pickupCitySpinner.getSelectedItemPosition());
                        //gettingLatLng(taxiModelname);
                        taxitypeSpinner.setSelection(0);

                    }
                    cityId = cityListId.get(pickupCitySpinner.getSelectedItemPosition());
                    Log.e("cityId", cityId);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });

            localPassengers.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if(taxitypeSpinner.getSelectedItem().toString().equals("SUV")) {
                        Log.e("click", "SUV");
                        // noOfPassenger.add(new String("5"));
                        //noOfPassenger.add(new String("6"));
                        max = 6;
                    }else{
                        max=4;
                    }
                    DialogFragment passengerPicker = new NoOfPassengers();
                    passengerPicker.show(getChildFragmentManager(),"no of radio_passenger");

                }
            });


            assessmentCityList.add("Ass City");
            assessmentCityListId.add("Ass.City id");
            assessmentCitySpinner.setAdapter(assessmentCityAdapter);
            assessmentCitiesViewModel.getCorporateCitiesLiveData(authorization, "1", corporateId).observe(this,
                    new Observer<List<AssesmentCities>>() {
                @Override
                public void onChanged(List<AssesmentCities> cities) {
                    assessmentCityList.clear();
                    assessmentCityListId.clear();
                    assessmentCityList.add("As.City");
                    assessmentCityListId.add("Ass.City id");
                    if (cities != null) {
                        for (int i = 0; i < cities.size(); i++) {
                            assessmentCityList.add(cities.get(i).getCityName());
                            assessmentCityListId.add(cities.get(i).getId().toString());
                        }
                    }
                    assessmentCityAdapter.notifyDataSetChanged();
                }
            });
            assessmentCitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if(assessmentCitySpinner.getSelectedItem().toString().equals("As.City")){
                        //  ((TextView) assessmentCitySpinner.getSelectedView()).setError("field required");
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


            assessmentSpinner.setAdapter(assessmentAdapter);
            assessmentList.add("Assessment Code");
            assessmentListId.add("Assessment Code id");
            assessmentCodeViewModel.getCorporateCodesLiveData(authorization, "1", corporateId).observe(this,
                    new Observer<List<AssesmentCodes>>() {
                @Override
                public void onChanged(@Nullable List<AssesmentCodes> assCodes) {
                    Log.d("Assessment Codes:::", GsonStringConvertor.gsonToString(assCodes));
                    assessmentList.clear();
                    assessmentListId.clear();
                    assessmentList.add("Ass.Code");
                    assessmentListId.add("Ass.Code id");
                    if (assCodes != null) {
                        for (int i = 0; i < assCodes.size(); i++) {
                            assessmentList.add(assCodes.get(i).getAssessmentCode());
                            assessmentListId.add(assCodes.get(i).getId().toString());
                        }
                    }
                    assessmentAdapter.notifyDataSetChanged();
                }
            });
            assessmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if(assessmentSpinner.getSelectedItem().toString().equals("Assessment Code")){
                        //((TextView) assessmentSpinner.getSelectedView()).setError("field required");
                    }else
                    {
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

            pacakageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if(pacakageSpinner.getSelectedItem().toString().equals("Select Pacakage")){
//                    ((TextView) pacakageSpinner.getSelectedView()).setError("field required");
                    }else{
                        pacakageId=pacakage_idlist.get(pacakageSpinner.getSelectedItemPosition());
                        Log.e("pacakageId",pacakageId);

                    }
                    // pacakageId=pacakage_idlist.get(pacakageSpinner.getSelectedItemPosition());
                    Log.e("pacakageId",pacakageId);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


            prefTaxiTypeList.clear();
            prefTaxiTypeListId.clear();
            prefTaxiTypeList.add("Taxi Type");
            prefTaxiTypeListId.add("Taxi Type id");
            taxiTypeViewModel.initTaxiType(authorization,"1");
            taxitypeSpinner.setAdapter(prefTaxiTypeAdapter);
            taxiTypeViewModel.getTaxiTypeInfo(authorization,"1").observe(this,
                    new Observer<List<TaxiType>>() {
                        @Override
                        public void onChanged(List<TaxiType> taxiTypes) {

                            prefTaxiTypeList.clear();
                            prefTaxiTypeListId.clear();
                            prefTaxiTypeList.add("Taxi Type");
                            prefTaxiTypeListId.add("Taxi Type id");
                            if (taxiTypes != null) {
                                for (int i = 0; i < taxiTypes.size(); i++) {
                                    prefTaxiTypeList.add(taxiTypes.get(i).getName());
                                    prefTaxiTypeListId.add(String.valueOf(taxiTypes.get(i).getId()));
                                }
                            }
                            prefTaxiTypeAdapter.notifyDataSetChanged();
                        }
                    });


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
                                    groupId= String.valueOf(spocs.get(i).getGroupId());
                                    subgroupId=String.valueOf(spocs.get(i).getSubgroupId());
                                }
                            }
                            spocsAdapter.notifyDataSetChanged();
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

            taxitypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    if(taxitypeSpinner.getSelectedItem().toString().equals("Taxi Type")){
                        //((TextView) prefTaxiTypeSpinner.getSelectedView()).setError("field required");
                    }else{
                        prefTaxitypeId=prefTaxiTypeListId.get(taxitypeSpinner.getSelectedItemPosition());
                        //gettingLatLng(taxiModelname);
                        if(pickupCitySpinner.getSelectedItemPosition() != 0 && taxitypeSpinner.getSelectedItemPosition()!=0) {
                            pacakagelist.removeAll(pacakagelist);
                            pacakage_idlist.removeAll(pacakage_idlist);
                            packagehashMap.clear();
                            if (isInternetConnected(context)) {
                                if (prefTaxitypeId!=null) {
                                    Toasty.success(context, pickupCitySpinner.getSelectedItem().toString()).show();
                                    new getPacakages(context, access_token, corporateId, cityId, prefTaxitypeId).execute("");
                                }
                            }else {
                                Snackbar.make(view,"Internet Connection Unavailable",Snackbar.LENGTH_LONG).show();
                            }
                            //  pd.dismiss();
                        }
                    }
                    prefTaxitypeId=prefTaxiTypeListId.get(taxitypeSpinner.getSelectedItemPosition());
                    Log.e("prefTrainTypeID",prefTaxitypeId);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            dropLocation.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent autocompleteIntent = new Autocomplete.
                                    IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldSelector.getAllFields())
                                    .setLocationBias(bounds)
                                    .setCountry("IN")
                                    .build(context);
                            startActivityForResult(autocompleteIntent, DROP_CODE_AUTOCOMPLETE);
                        }
                    });
            pickupLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent autocompleteIntent = new Autocomplete.
                            IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldSelector.getAllFields())
                            .setLocationBias(bounds)
                            .setCountry("IN")
                            .build(context);
                    startActivityForResult(autocompleteIntent, PICKUP_CODE_AUTOCOMPLETE);
                }
            });

            startdate = view.findViewById(R.id.journey_date);
            startdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment datePickerFragment = new DatePickerFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("start", "start");
                    datePickerFragment.setArguments(bundle);
                    datePickerFragment.show(getFragmentManager(), "datePicker");
                }
            });
            starttime = view.findViewById(R.id.journey_time);
            starttime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment timePickerFragment = new FragmentLocalTaxi.TimePickerFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("start", "start");
                    timePickerFragment.setArguments(bundle);
                    timePickerFragment.show(getFragmentManager(), "starttime");
                }
            });

            return view;
        } else

            view = inflater.inflate(R.layout.fragment_access_denied, container, false);


        return view;
    }

    public static boolean isInternetConnected(Context activity) {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
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
    public void onClick(View v) {
        int id  = v.getId();
        switch (id) {
            case R.id.submit_btn:

                setData();
                if (isInternetConnected(context)) {
                    if (dropLocation.getText().toString().length() < 1
                            || pickupLocation.getText().toString().length() < 1
                            || starttime.getText().toString().length() < 1
                            || startdate.getText().toString().length() < 1
                            || reasomOfBooking.getText().toString().length() < 1
                            || assessmentSpinner.getSelectedItemPosition() == 0
                            || taxitypeSpinner.getSelectedItemPosition() == 0
                            || pickupCitySpinner.getSelectedItemPosition() == 0
                            || pacakageSpinner.getSelectedItemPosition() == 0
                            || spocsSpinner.getSelectedItemPosition() == 0
                            || billingSpinner.getSelectedItemPosition() == 0
                            || assessmentCitySpinner.getSelectedItemPosition() == 0
                            ||localPassengers.getText().equals("Passengers")
                    ) {
                        if (starttime.getText().toString().length() < 2
                                && startdate.getText().toString().length() < 2) {
                            starttime.setError("Not Slected");
                            startdate.setError("Not Slected");
                        }
                        if (localPassengers.getText().equals("Passengers"))
                        {
                            localPassengers.setError("Select Passengers");
                        }

                        if (dropLocation.getText().toString().length() < 1) {
                            dropLocation.setError("Select City");
                        }
                        if (pickupLocation.getText().toString().length() < 1) {
                            pickupLocation.setError("Select City");
                        }
                        if (assessmentSpinner.getSelectedItemPosition() == 0) {
                            ((android.widget.TextView) assessmentSpinner.getSelectedView()).setError("field required");
                        }
                        if (spocsSpinner.getSelectedItemPosition() == 0) {
                            ((android.widget.TextView) spocsSpinner.getSelectedView()).setError("field required");
                        }
                        if (billingSpinner.getSelectedItemPosition() == 0) {
                            ((android.widget.TextView) billingSpinner.getSelectedView()).setError("field required");
                        }
                        if (assessmentCitySpinner.getSelectedItemPosition() == 0) {
                            ((android.widget.TextView) assessmentCitySpinner.getSelectedView()).setError("field required");
                        }if (pickupCitySpinner.getSelectedItemPosition() == 0) {
                            ((android.widget.TextView) pickupCitySpinner.getSelectedView()).setError("field required");
                        }
                        if (taxitypeSpinner.getSelectedItemPosition() == 0) {
                            ((android.widget.TextView) taxitypeSpinner.getSelectedView()).setError("field required");
                        }
                        if (pacakageSpinner.getSelectedItemPosition() == 0) {
                            ((android.widget.TextView) pacakageSpinner.getSelectedView()).setError("field required");
                        }
                        if (reasomOfBooking.getText().toString().length() < 1) {
                            reasomOfBooking.setError("Reason is must");
                        }
                    }else
                        {

                            Toasty.normal(context, "Processing  ").show();
                            final Dialog dialog = new Dialog(context);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setCancelable(false);
                            dialog.setContentView(R.layout.custom_dialog_box);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                            TextView mDialogmsg = dialog.findViewById(R.id.dialog_message);
                            mDialogmsg.setText("Proceed To Complete Booking.");

                            TextView mDialogtitle = dialog.findViewById(R.id.dialog_title);
                            mDialogtitle.setText("COMPLETE TAXI BOOKING");

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

                                    new AddTaxiBookingAsyncTask(context,access_token,
                                            empId, corporateId,spocId, groupId, subgroupId,"2" ,
                                            cityId,
                                            pickupLocation.getText().toString(),
                                            dropLocation.getText().toString(),selectedDateTime,
                                            booking_datetime,
                                            entity_id,
                                            assCodeId,
                                            assCityId,
                                            prefTaxitypeId,
                                            reasomOfBooking.getText().toString(),
                                            pacakageId,employee_id_value,"").execute(activity);
                                    dialog.dismiss();
                                }
                            });

                            dialog.show();
                        }
                } else Toasty.error(context, "Check Network Connection").show();
                break;
        }

    }

    private void setData() {

        //Getting Login Time Spoc Details for Api Call
        authorization = "Token " + access_token;
        userType = loginpref.getString("usertype", "n");
        userId = loginpref.getString("user_id", "n");
        corporateId = loginpref.getString("corporate_id", "n");
        spocId =spocIdList.get(spocsSpinner.getSelectedItemPosition()) ;
        entity_id =billingEntityIdList.get(billingSpinner.getSelectedItemPosition()) ;
        mpickupCity=cityListId.get(pickupCitySpinner.getSelectedItemPosition());
        mtaxiType=prefTaxiTypeListId.get(taxitypeSpinner.getSelectedItemPosition());
        massCity=assessmentCityListId.get(assessmentCitySpinner.getSelectedItemPosition());
        massCode=assessmentListId.get(assessmentSpinner.getSelectedItemPosition());
        mbiilEntity=billingEntityIdList.get(billingSpinner.getSelectedItemPosition());
        mpickupLocation = pickupLocation.getText().toString();
        mdropLocation=dropLocation.getText().toString();
        mpickupDate = startdate.getText().toString();
        mpickupTime = starttime.getText().toString();
        finalDateAndTime = mpickupDate + " " + mpickupTime;
        mpessengers = localPassengers.getText().toString();
        mreason = reasomOfBooking.getText().toString();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    protected class getPacakages extends AsyncTask<String,Integer,String> {

        String apiURL = APIurls.getPackages;
        Response response;
        OkHttpClient client = new OkHttpClient();
        String token ,corporate_id ,city_id,taxiType;
        Context context;

        public  getPacakages (Context context,String token,String corporate_id,String city_id,String taxiType){
            this.context=context;
            this.token=token;
            this.corporate_id=corporate_id;
            this.taxiType=taxiType;
            this.city_id=city_id;
        }

        @Override
        protected String doInBackground(String... params) {


            RequestBody formBody = new FormBody.Builder()
                    .add("corporate_id", corporate_id)
                    .add("city_id", city_id)
                    .add("taxi_type",taxiType)
                    .add("tour_type","2")
                    .build();

            Request.Builder request = new Request.Builder();
            request.url(apiURL)
                    .post(formBody)
                    .addHeader("Authorization","Token "+token)
                    .addHeader("usertype","1")
                    .build();
            try {
                response = client.newCall(request.build()).execute();
                if (response.code()==200) {

                    pacakagevalue = response.body().string();
                    return pacakagevalue;
                }
                else {
                    return "Network Error";
                }

            } catch (IOException e) {
                e.getMessage();
                Log.d("error", e.getMessage());
            }

            return "Network Error";

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(context);
            pd.setMessage("Getting Packages");
            pd.show();
            b=new AlertDialog.Builder(context);
            b.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    pd.dismiss();
                }
            });
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            if (response!=null) {
                response.body().close();
            }
            if (s.equals("Network Error")) {
                b.setMessage("Unable To Load Packages");
                b.setTitle("CONNECTION ERROR");
                b.show();
            } else {
                Log.e("pacakages", s);

                JSONObject json = (JSONObject) JSONValue.parse(s);
                if (json.get("success").toString().equals("1")) {
                    JSONObject response = (JSONObject) json.get("response");
                    JSONArray pacakages = (JSONArray) json.get("Package");

                    if (pacakages.size()>0) {
                        Iterator i = pacakages.iterator();
                        // pacakage_idlist.removeAll(pacakage_idlist);
                        pacakagelist.removeAll(pacakagelist);
                        packagehashMap.clear();
                        pacakagelist.add("Select Package");
                        pacakage_idlist.add("Select Package id");
                        while (i.hasNext() && pacakages.size() > 0) {
                            JSONObject obj = (JSONObject) i.next();
                            pacakagelist.add(new String(obj.get("package_name").toString()));
                            pacakage_idlist.add(new String(obj.get("id").toString()));
                            packagehashMap.put(obj.get("package_name").toString(),obj.get("id").toString());

                        }
                        if (cityList != null) {

                            cityAdapter.setNotifyOnChange(true);
                            pacakageadapter.setNotifyOnChange(true);
                        }

                    }
                    else {
                        pacakagelist.removeAll(pacakagelist);
                        pacakage_idlist.removeAll(pacakage_idlist);
                        pacakagelist.add("Select Pacakage");
                        pacakage_idlist.add("Select Package Id");
                        Toast.makeText(context, "No Package found", Toast.LENGTH_SHORT).show();
                        b.setMessage("No Package found");
                        b.setTitle("Packages");
                        b.show();}
                }else {
                    pacakagelist.removeAll(pacakagelist);
                    pacakage_idlist.removeAll(pacakage_idlist);
                    pacakagelist.add("Select Pacakage");
                    pacakage_idlist.add("Select Package Id");
                    Toast.makeText(context, "No Package found", Toast.LENGTH_SHORT).show();
                    b.setMessage("No Package found");
                    b.setTitle("Packages");
                    b.show();
                }


            }pacakageSpinner.setSelection(0);
            pacakageadapter.setNotifyOnChange(true);
        }


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

            id = new String();
            id = getArguments().getString("start");

            // Create a new instance of DatePickerDialog and return it
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
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
            } else if (id.equals("end")) {

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

            id = new String();
            id = getArguments().getString("start");
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
            } else if (id.equals("end")) {

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("request code",requestCode+"");


        if (requestCode == PICKUP_CODE_AUTOCOMPLETE) {
            if (resultCode == RESULT_OK) {
                // Get the user's selected place from the Intent.
                String location=data.getStringExtra("LOCATION");
                //Place place = PlaceAutocomplete.getPlace(this, data);
                Log.i(TAG, "Place Selected: " + location);
                Place place = Autocomplete.getPlaceFromIntent(data);

                // Format the place's details and display them in the TextView.
                pickupLocation.setError(null);
                pickupLocation.setText(place.getAddress());

            }
        } else if (requestCode == DROP_CODE_AUTOCOMPLETE) {
            if (resultCode == RESULT_OK) {
                String location=data.getStringExtra("LOCATION");
                Log.i(TAG, "Place Selected: " + location);
                Place place = Autocomplete.getPlaceFromIntent(data);

                // Format the place's details and display them in the TextView.

                dropLocation.setError(null);
                dropLocation.setText(place.getAddress());

            }
        }

        else if (requestCode ==EMPLOYEE_INFO){
            if(resultCode == RESULT_OK){
                //passenger.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.checkbox_on_background , 0);
                employee_idlist=data.getStringArrayListExtra("employee_id");
                getEmployee_idphno=data.getStringArrayListExtra("employee_phno");
                employee_id_value = data.getStringArrayListExtra("employeeIdValue");
            }
        }
    }
}
