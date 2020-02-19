package com.cotrav.cotrav_admin.ui.home.taxi.addTaxiFragment;


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
import com.cotrav.cotrav_admin.model.spoc_model.Spocs;
import com.cotrav.cotrav_admin.ui.home.employee_info.EmployeeInfo;
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
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import es.dmoral.toasty.Toasty;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentRadioTaxi extends Fragment implements View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener{
    Button submitBtn;
    Context context ;
    Activity activity;
    private static final int EMPLOYEE_INFO=100;
    static TextView startdate,starttime,enddate,endtime,localPassenger;
    String acSlected="0",nonAcSelected="0",sleeperSelected="0",seaterSlected="0";
    SearchableSpinner assessmentSpinner,assessmentCitySpinner,citySpinner,spocsSpinner,billingSpinner;
    ArrayList<String> assessmentCityList, assessmentCityListId;
    ArrayList<String> assessmentList, assessmentListId;
    ArrayList<String> cityList, cityListId;
    ArrayList<String> spocList, spocIdList,billingEntityList,billingEntityIdList;
    ArrayAdapter<String> assessmentCityAdapter,assessmentAdapter,cityAdapter,spocsAdapter,billingEntityAdapter;
    AssesmentCityViewModel assessmentCitiesViewModel;
    AssesmentCodeViewModel assessmentCodeViewModel;
    EntitiesViewModel entityViewModel;
    AllCityViewModel citiesViewModel;
    RelativeLayout assessmentLayout;
    static ArrayList<String> assesmentlist;
    SpocsViewModel spocsViewModel;
    static ArrayAdapter<String> arrayAdapter,assesmentadapter;
    TextView dropLocation,pickUpLocation;
    SharedPreferences loginpref,spocpref;
    static  String start_date_send,end_date_send,end_time_send;
    String authorization,token,corporateId,spocId,empId,groupId,
            subgroupId,selectedDateTime,entity_id,
            booking_datetime="";
    EditText prefTaxiNO,reasomOfBooking;
    String assCodeId="0",assCityId="0",cityId;
    ProgressDialog pd;

    RectangularBounds bounds = RectangularBounds.newInstance(
            new LatLng(-40,-168),new LatLng(71,136));
    TextView txt_radiodropLocation,txt_radiopickupLocation;
    static  String city;
    static int total=1,max;
    double lat;
    double lng;
    private static final int DROP_CODE_AUTOCOMPLETE = 0;
    private static final int PICKUP_CODE_AUTOCOMPLETE = 1;
    View view;
    ArrayList<String> employee_idlist;
    ArrayList<String> getEmployee_idphno;
    ArrayList<String> employee_id_value;

    public FragmentRadioTaxi() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    /*    // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_radio_taxi, container, false);*/
        context = getContext();
        activity = getActivity();

        loginpref = context.getSharedPreferences("login_info", MODE_PRIVATE);

        if (loginpref.getString("is_radio", "n").equals("1")) {
            view = inflater.inflate(R.layout.fragment_radio_taxi, container, false);
            localPassenger=view.findViewById(R.id.local_pessangers);


            if (!Places.isInitialized()) {
                Places.initialize(context, getResources().getString(R.string.places_api_key));
            }
            final PlacesFieldSelector fieldSelector = new PlacesFieldSelector();

            pd = new ProgressDialog(context);
            pd.setCanceledOnTouchOutside(false);
            pd.setMessage("Getting Cities");
            pd.show();


            spocpref = context.getSharedPreferences("spoc_info", MODE_PRIVATE);
            corporateId = loginpref.getString("corporate_id", "n");
            token = loginpref.getString("access_token", "n");
            //spocId = loginpref.getString("spoc_id", "n");
            //entity_id = loginpref.getString("entity_id", "n");
            empId = loginpref.getString("emp_id", "n");
            groupId = spocpref.getString("group_id", "n");
            subgroupId = spocpref.getString("subgroup_id", "n");

            Date c = Calendar.getInstance().getTime();
            System.out.println("Current time => " + c);

            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String formattedDate = df.format(c);
            booking_datetime = formattedDate;
            authorization = "Token " + token;

            reasomOfBooking = view.findViewById(R.id.booking_reason);


            assessmentCitySpinner = view.findViewById(R.id.assessment_city);
            assessmentSpinner = view.findViewById(R.id.assessnment_code);
            assessmentLayout = view.findViewById(R.id.assessment_layout);
            citySpinner = view.findViewById(R.id.city_spinner);
            billingSpinner=view.findViewById(R.id.billing_entity);
            // dropLocation = view.findViewById(R.id.drop_location);
            // dropLocation.setAdapter(placeAutocompleteAdapter);
            spocsSpinner=view.findViewById(R.id.spoc_spinner);
            txt_radiodropLocation = (TextView) view.findViewById(R.id.drop_location);
            txt_radiopickupLocation = (TextView) view.findViewById(R.id.pickup_location);
            // pickUpLocation = view.findViewById(R.id.pickup_location);
            // pickUpLocation.setAdapter(placeAutocompleteAdapter);
            submitBtn = view.findViewById(R.id.submit_btn);

            submitBtn.setOnClickListener(this);

            spocList = new ArrayList<>();
            spocIdList = new ArrayList<>();
            assesmentlist = new ArrayList<>();
            assessmentCityList = new ArrayList<>();
            assessmentCityListId = new ArrayList<>();
            assessmentList = new ArrayList<>();
            assessmentListId = new ArrayList<>();
            cityList = new ArrayList<>();
            cityListId = new ArrayList<>();
            billingEntityList = new ArrayList<>();
            billingEntityIdList = new ArrayList<>();

            employee_idlist=new ArrayList<>();
            getEmployee_idphno=new ArrayList<>();

            citiesViewModel = ViewModelProviders.of(this).get(AllCityViewModel.class);
            assessmentCitiesViewModel = ViewModelProviders.of(this).get(AssesmentCityViewModel.class);
            assessmentCodeViewModel = ViewModelProviders.of(this).get(AssesmentCodeViewModel.class);
            spocsViewModel = ViewModelProviders.of(getActivity()).get(SpocsViewModel.class);
            entityViewModel = ViewModelProviders.of(getActivity()).get(EntitiesViewModel.class);
            assessmentCityAdapter = new ArrayAdapter<String>(context, R.layout.spinner_layout, assessmentCityList);
            assessmentAdapter = new ArrayAdapter<String>(context, R.layout.spinner_layout, assessmentList);
            cityAdapter = new ArrayAdapter<>(context, R.layout.city_spinner_layout, cityList);
            spocsAdapter = new ArrayAdapter<>(context,R.layout.spinner_layout,spocList);
            billingEntityAdapter = new ArrayAdapter<>(context,R.layout.spinner_layout,billingEntityList);
            localPassenger.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment passengerPicker = new NoOfPassengers();
                    passengerPicker.show(getChildFragmentManager(),"no of radio_passenger");
                }
            });
            txt_radiodropLocation.setOnClickListener(new View.OnClickListener() {
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
            txt_radiopickupLocation.setOnClickListener(new View.OnClickListener() {
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


            citiesViewModel.InitCityViewModel(authorization, "1",corporateId);
            citySpinner.setAdapter(cityAdapter);
            citiesViewModel.getCityLiveData(authorization, "1",corporateId).observe(this,
                    new Observer<List<City>>() {
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
                    cityAdapter.notifyDataSetChanged();
                    pd.dismiss();
                }
            });


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
                        assessmentCityList.add("As.City");
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
                        assessmentList.add("Assessment Code");
                        assessmentListId.add("Assessment Code id");
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
                        if (assessmentSpinner.getSelectedItem().toString().equals("Assessment Code")) {
                            //  ((TextView) assessmentSpinner.getSelectedView()).setError("field required");
                        } else {
                            assCodeId = assessmentListId.get(assessmentSpinner.getSelectedItemPosition());
                            //gettingLatLng(taxiModelname);
                        }
                        assCodeId = assessmentListId.get(assessmentSpinner.getSelectedItemPosition());
                        Log.e("assCodeId", assCodeId);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                assessmentCitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (assessmentCitySpinner.getSelectedItem().toString().equals("As.City")) {
                            // ((TextView) assessmentCitySpinner.getSelectedView()).setError("field required");
                        } else {
                            assCityId = assessmentCityListId.get(assessmentCitySpinner.getSelectedItemPosition());
                            //gettingLatLng(taxiModelname);
                        }
                        assCityId = assessmentCityListId.get(assessmentCitySpinner.getSelectedItemPosition());
                        Log.e("assCityId", assCityId);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (citySpinner.getSelectedItem().toString().equals("Select City")) {
                        // ((TextView) citySpinner.getSelectedView()).setError("field required");
                    } else {
                        cityId = cityListId.get(citySpinner.getSelectedItemPosition());
                        //gettingLatLng(taxiModelname);
                    }
                    cityId = cityListId.get(citySpinner.getSelectedItemPosition());
                    Log.e("assCodeId", cityId);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });

            starttime = view.findViewById(R.id.journey_time);
            starttime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment timePickerFragment = new TimePickerFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("start", "start");
                    timePickerFragment.setArguments(bundle);
                    timePickerFragment.show(getFragmentManager(), "starttime");
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



            spocsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
            {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                {

                    if(spocsSpinner.getSelectedItem().toString().equals("Select Spoc"))
                    {
                        //((TextView) assessmentSpinner.getSelectedView()).setError("field required");
                    }else
                    {
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

            billingEntityList.clear();
            billingEntityIdList.clear();
            billingEntityList.add("Billing Entity");
            billingEntityIdList.add("Billing Entity");
            entityViewModel.InitEntitiesViewModel(authorization,"1",corporateId);
            billingSpinner.setAdapter(billingEntityAdapter);
            entityViewModel.getEntitiesLiveData(authorization,"1",corporateId).observe(this, new Observer<List<Entities>>() {
                        @Override
                        public void onChanged(List<Entities> entitiesList)
                        {
                            billingEntityList.clear();
                            billingEntityIdList.clear();
                            billingEntityList.add("Billing Entity");
                            billingEntityIdList.add("Billing Entity");
                            if (entitiesList != null)
                            {
                                for (int i = 0; i < entitiesList.size(); i++)
                                {
                                    billingEntityList.add(entitiesList.get(i).getEntityName());
                                    billingEntityIdList.add(String.valueOf(entitiesList.get(i).getId()));
                                }
                            }
                            billingEntityAdapter.notifyDataSetChanged();
                        }
                    }
            );


            return view;
        } else

            view = inflater.inflate(R.layout.fragment_access_denied, container, false);


        return view;


    }


    @Override
    public void onClick(View v) {
        int id  = v.getId();

        switch (id) {
            case R.id.submit_btn:

                entity_id =billingEntityIdList.get(billingSpinner.getSelectedItemPosition());
                if (isInternetConnected(context))
                {
                    if (    txt_radiodropLocation.getText().toString().length() < 1
                            || txt_radiopickupLocation.getText().toString().length() < 1
                            || starttime.getText().toString().length() < 1
                            || startdate.getText().toString().length() < 1
                            || reasomOfBooking.getText().toString().length() < 1
                            || assessmentSpinner.getSelectedItemPosition() == 0
                            || citySpinner.getSelectedItemPosition() == 0
                            || spocsSpinner.getSelectedItemPosition() == 0
                            || billingSpinner.getSelectedItemPosition() == 0
                            ||localPassenger.getText().equals("Passengers")
                            || assessmentCitySpinner.getSelectedItemPosition() == 0) {
                        if (starttime.getText().toString().length() < 2
                                && startdate.getText().toString().length() < 2)
                        {
                            starttime.setError("Not Slected");
                            startdate.setError("Not Slected");
                        }

                        if (localPassenger.getText().equals("Passengers"))
                        {
                            localPassenger.setError("Select Passengers");
                        }


                        if (spocsSpinner.getSelectedItemPosition() == 0) {
                            ((android.widget.TextView) spocsSpinner.getSelectedView()).setError("field required");
                        }
                        if (billingSpinner.getSelectedItemPosition() == 0) {
                            ((android.widget.TextView) billingSpinner.getSelectedView()).setError("field required");
                        }
                        if (txt_radiodropLocation.getText().toString().length() < 1)
                        {
                            txt_radiodropLocation.setError("Select City");
                        }
                        if (txt_radiopickupLocation.getText().toString().length() < 1)
                        {
                            txt_radiopickupLocation.setError("Select City");
                        }
                        if (assessmentSpinner.getSelectedItemPosition() == 0)
                        {
                            ((android.widget.TextView) assessmentSpinner.getSelectedView()).setError("field required");
                        }
                        if (assessmentCitySpinner.getSelectedItemPosition() == 0)
                        {
                            ((android.widget.TextView) assessmentCitySpinner.getSelectedView()).setError("field required");
                        }if (citySpinner.getSelectedItemPosition() == 0)
                        {
                            ((android.widget.TextView) citySpinner.getSelectedView()).setError("field required");
                        }
                        if (reasomOfBooking.getText().toString().length() < 1)
                        {
                            reasomOfBooking.setError("Reason is must");
                        }
                    }else {
                        Toasty.normal(context, "Processing  ").show();

                        final Dialog dialog = new Dialog(context);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.custom_dialog_box);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                        TextView mDialogmsg = dialog.findViewById(R.id.dialog_message);
                        mDialogmsg.setText("Proceed To Complete Booking.");

                        TextView mDialogtitle = dialog.findViewById(R.id.dialog_title);
                        mDialogtitle.setText("COMPLETE Taxi BOOKING");

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
                        myes.setOnClickListener(new View.OnClickListener()
                            {
                            @Override
                            public void onClick(View v)
                            {
                                selectedDateTime = startdate.getText().toString() + " " + starttime.getText().toString();
                                String assCode =""+assessmentSpinner.getSelectedItemPosition();
                                String assCity =""+assessmentCitySpinner.getSelectedItemPosition();
                                new AddTaxiBookingAsyncTask(context,token,
                                        empId, corporateId,spocId, groupId, subgroupId,"1" ,
                                        cityId,
                                        txt_radiopickupLocation.getText().toString(),
                                        txt_radiodropLocation.getText().toString(),selectedDateTime,
                                        booking_datetime,
                                        entity_id,
                                        assCodeId,
                                        assCityId,
                                        "",
                                        reasomOfBooking.getText().toString(),
                                        "",employee_id_value,"1").execute(activity);

                                dialog.dismiss();
                            }
                        });

                        dialog.show();
                    }
                } else Toasty.error(context, "Check Network Connection").show();
                break;
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("request code",requestCode+"");
        // Log.d("employeee", Arrays.toString(employeeIdList.toArray()));

        // Check that the result was from the autocomplete widget.
        if (requestCode == PICKUP_CODE_AUTOCOMPLETE) {
            if (resultCode == RESULT_OK) {
                // Get the user's selected place from the Intent.
                String location=data.getStringExtra("LOCATION");
                //Place place = PlaceAutocomplete.getPlace(this, data);
                Log.i(TAG, "Place Selected: " + location);
                Place place = Autocomplete.getPlaceFromIntent(data);

                // Format the place's details and display them in the TextView.
                txt_radiopickupLocation.setError(null);
                txt_radiopickupLocation.setText(place.getName());
                txt_radiopickupLocation.append(", "+place.getAddress());

            }
        } else if (requestCode == DROP_CODE_AUTOCOMPLETE) {
            if (resultCode == RESULT_OK) {
                String location=data.getStringExtra("LOCATION");
                Log.i(TAG, "Place Selected: " + location);
                Place place = Autocomplete.getPlaceFromIntent(data);

                // Format the place's details and display them in the TextView.

                txt_radiodropLocation.setError(null);
                txt_radiodropLocation.setText(place.getAddress());

                txt_radiodropLocation.setText(place.getName());
                txt_radiodropLocation.append(", "+place.getAddress());

            }
        }else if (requestCode ==EMPLOYEE_INFO){
            if(resultCode == RESULT_OK){
                //passenger.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.checkbox_on_background , 0);
                employee_idlist=data.getStringArrayListExtra("employee_id");
                getEmployee_idphno=data.getStringArrayListExtra("employee_phno");
                employee_id_value= data.getStringArrayListExtra("employeeIdValue");

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
    public  static boolean isInternetConnected(Context activity){
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
                    localPassenger.setError(null);
                    localPassenger.setText(total+"");
                    getDialog().dismiss();
                    Intent intent=new Intent(getActivity(), EmployeeInfo.class);
                    intent.putExtra("no_of_employee",total+"");
                    getActivity().startActivityForResult(intent,EMPLOYEE_INFO);

                }
            });




        }
    }
}
