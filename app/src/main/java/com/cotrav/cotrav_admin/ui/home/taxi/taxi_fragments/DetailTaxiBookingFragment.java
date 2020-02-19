package com.cotrav.cotrav_admin.ui.home.taxi.taxi_fragments;


import android.animation.LayoutTransition;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cotrav.cotrav_admin.GsonStringConvertor;
import com.cotrav.cotrav_admin.R;
import com.cotrav.cotrav_admin.model.home_model.Passanger;
import com.cotrav.cotrav_admin.model.home_model.taxi_model.show_taxi.TaxiBooking;
import com.cotrav.cotrav_admin.model.home_model.taxi_model.view_taxi.ViewTaxiBooking;
import com.cotrav.cotrav_admin.model.login_model.UserViewModel;
import com.cotrav.cotrav_admin.ui.home.taxi.TaxiBookingsViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailTaxiBookingFragment extends Fragment {
    private TextView bookingId, tourType, bookingStatus,
            assCode, assCity, bookingReason, bookingDate, noOfEmployees,
            pickupTime, pickupDate, pickupCity, pickupBoardingPoint,
            dropTime, dropDate, dropCity, dropEndPont, spocName, spocContact, spocEmail,
            driverName, driverContact, driverEmail,
            taxiType, taxiModel, taxiRegNo;

    LinearLayout basicDetailLay, spocDetailLay, driverDetailLay, taxiDetailLay;
    CardView passenger_lay;
    LinearLayout passengersListLay;
    ProgressBar progressBar;
    Bundle arg;
    TaxiBookingsViewModel taxiBookingsViewModel;
    UserViewModel userViewModel;
    ViewTaxiBooking taxiBookingDetails;
    SharedPreferences loginPref;
    String token, authorization,empId;
    Bundle args;
    View view;
    DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
    DateFormat timeFormat = new SimpleDateFormat("HH:mm");
    private TaxiBooking taxiBooking;

    public DetailTaxiBookingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_detail_taxi_booking, container, false);
        basicDetailLay = view.findViewById(R.id.basic_detail_lay);
        basicDetailLay.setVisibility(View.GONE);
        bookingId = view.findViewById(R.id.refrence_no);
        tourType = view.findViewById(R.id.tour_type);
        bookingStatus = view.findViewById(R.id.booking_status);
        assCity = view.findViewById(R.id.assesment_city);
        assCode = view.findViewById(R.id.assesment_code);
        bookingReason = view.findViewById(R.id.booking_reason);
        bookingDate = view.findViewById(R.id.booking_date);

        pickupCity = view.findViewById(R.id.pickup_city);
        pickupBoardingPoint = view.findViewById(R.id.pickup_boardingpoint);
        pickupDate = view.findViewById(R.id.pickup_date);
        pickupTime = view.findViewById(R.id.pickup_time);

        dropCity = view.findViewById(R.id.drop_city);
        dropDate = view.findViewById(R.id.drop_date);
        dropEndPont = view.findViewById(R.id.drop_city_endpoint);
        dropTime = view.findViewById(R.id.drop_time);

        spocDetailLay = view.findViewById(R.id.spoc_detail_lay);
        spocDetailLay.setVisibility(View.GONE);
        spocName = view.findViewById(R.id.spoc_name);
        spocEmail = view.findViewById(R.id.spoc_email);
        spocContact = view.findViewById(R.id.spoc_contact);


        progressBar = view.findViewById(R.id.progressBar_bookingDetails);

        driverDetailLay = view.findViewById(R.id.driver_detail_lay);
        driverEmail = view.findViewById(R.id.driver_email);
        driverContact = view.findViewById(R.id.driver_contact);
        driverName = view.findViewById(R.id.driver_name);
        driverDetailLay.setVisibility(View.GONE);
        taxiDetailLay = view.findViewById(R.id.taxi_detail_lay);
        taxiDetailLay.setVisibility(View.GONE);
        noOfEmployees = view.findViewById(R.id.no_of_emp);
        passenger_lay = view.findViewById(R.id.passenger_lay);
        taxiModel = view.findViewById(R.id.taxi_model);
        taxiType = view.findViewById(R.id.taxi_type);
        taxiRegNo = view.findViewById(R.id.taxi_reg_no);
        passengersListLay = (LinearLayout) view.findViewById(R.id.passengersListLay);

        taxiBookingsViewModel = ViewModelProviders.of(this).get(TaxiBookingsViewModel.class);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        loginPref = getActivity().getSharedPreferences("login_info", Context.MODE_PRIVATE);
        token = loginPref.getString("access_token", "n");
        empId = loginPref.getString("emp_id", "n");
        authorization = "Token " + token;
        args = getArguments();
        progressBar.setVisibility(View.VISIBLE);
        if (!getArguments().getString("details", "n").equals("n")) {
            taxiBooking = GsonStringConvertor.stringToGson(getArguments().
                    getString("details", "n"), TaxiBooking.class);
            setData();
        }
        if (args.getString("booking_status", "n").equals("search")) {

            taxiBookingsViewModel.getTaxiBookingDetails(authorization, "6",
                    getArguments().getString("bookingId"),empId).observe(this, new Observer<ViewTaxiBooking>() {
                @Override
                public void onChanged(ViewTaxiBooking taxiBookingDetail) {
                    progressBar.setVisibility(View.GONE);
                    if (taxiBookingDetail != null) {
                        Log.d("TaxiBookingDetails", GsonStringConvertor.gsonToString(taxiBookingDetail));
                        taxiBookingDetails = taxiBookingDetail;
                        setOnlineData();
                    }
                }
            });


            taxiBookingsViewModel.getTaxiDetailsConnectionError().observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    progressBar.setVisibility(View.GONE);
                    Snackbar.make(view, "Connection Error", Snackbar.LENGTH_LONG).show();
                }
            });


        }


        return view;

    }

    void setData() {


        try {
            Date date1 = df.parse(taxiBooking.getPickupDatetime());
            pickupDate.setText(dateFormat.format(date1));
            pickupTime.setText(timeFormat.format(date1));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            Date date1 = df.parse(taxiBooking.getBookingDate());
            bookingDate.setText(dateFormat.format(date1) + " " + timeFormat.format(date1));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        bookingId.setText(taxiBooking.getReferenceNo());


        // bookingDate.setText(taxiBookingDetails.getBookingDate());
        bookingReason.setText(taxiBooking.getReasonBooking());
        assCode.setText(taxiBooking.getAssessmentCode());
        assCity.setText(taxiBooking.getAssessmentCityId());
        if (taxiBooking.getTourType().equals("1")) {
            tourType.setText("RADIO");
            pickupCity.setText(taxiBooking.getCityName());
            dropCity.setText(taxiBooking.getCityName());
        }
        if (taxiBooking.getTourType().equals("2")) {
            tourType.setText("LOCAL");
            pickupCity.setText(taxiBooking.getCityName());
            dropCity.setText(taxiBooking.getCityName());
        }
        if (taxiBooking.getTourType().equals("3")) {
            tourType.setText("OUTSTATION");
//            pickupCity.setText(taxiBooking.getCityName());
//            String strMain = taxiBookingDetails.getDropLocation();
  //          String[] arrSplit = strMain.split(", ");
    //        dropCity.setText(arrSplit[0]);
        }

        //pickupBoardingPoint.setText(taxiBooking.getPickupLocation());
        //pickupDate.setText(taxiBookingDetails.getPickupDatetime());
        //pickupTime.setText();
        //pickupBoardingPoint.setText();

        dropEndPont.setText(taxiBooking.getDropLocation());
        //dropDate.setText(taxiBookingDetails.getdr);
        //droptime
        //droplocation
        basicDetailLay.setVisibility(View.VISIBLE);

        spocName.setText(taxiBooking.getUserName());
        spocContact.setText(taxiBooking.getUserContact());
        spocEmail.setText(taxiBooking.getBookingEmail());
        //spocId.setText(taxiBookingDetails.getId());


        spocDetailLay.setVisibility(View.VISIBLE);
        if (taxiBooking.getPassangers().size() > 0) {
            passenger_lay.setVisibility(View.VISIBLE);
            List<Passanger> passangers = taxiBooking.getPassangers();
            String size = String.valueOf(passangers.size());
            noOfEmployees.setText(size);
            for (int i = 0; i < passangers.size(); i++) {
                int j = i + 1;
                String no = String.valueOf(j);
                if (passangers.get(i).getEmployeeEmail() != null &&
                        passangers.get(i).getEmployeeName() != null &&
                        passangers.get(i).getEmployeeContact() != null) {

                    LayoutInflater layoutInflater =
                            (LayoutInflater) getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View addView = layoutInflater.inflate(R.layout.passengers_list, null);
                    final TextView passName = (TextView) addView.findViewById(R.id.passenger_name);
                    final TextView passEmail = (TextView) addView.findViewById(R.id.passemail);
                    final TextView passCon = (TextView) addView.findViewById(R.id.passcon);
                    passName.setText(taxiBooking.getPassangers().get(i).getEmployeeName());
                    passEmail.setText(taxiBooking.getPassangers().get(i).getEmployeeEmail());
                    passCon.setText(taxiBooking.getPassangers().get(i).getEmployeeContact());
                    passengersListLay.addView(addView, 0);
                    LayoutTransition transition = new LayoutTransition();
                    passengersListLay.setLayoutTransition(transition);


                } else {
                    LayoutInflater layoutInflater =
                            (LayoutInflater) getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View addView = layoutInflater.inflate(R.layout.passengers_list, null);
                    final TextView passName = (TextView) addView.findViewById(R.id.passenger_name);
                    passName.setText("Passenger Details Not Found");
                    passengersListLay.addView(addView, 0);
                    LayoutTransition transition = new LayoutTransition();
                    passengersListLay.setLayoutTransition(transition);

                }


            }

        }

        if (taxiBooking.getStatusCotrav() <= 1) {
            bookingStatus.setText(taxiBooking.getClientStatus());
            dropDate.setText("Not Assigned");


        } else {
            if (taxiBooking.getStatusCotrav() == 2 || taxiBooking.getStatusCotrav() == 3)
                dropDate.setText("Not Assigned");

            bookingStatus.setText(taxiBooking.getCotravStatus());
        }

        if (taxiBooking.getStatusCotrav() == 4) {
            driverName.setText(taxiBooking.getDriverId());
            driverContact.setText(taxiBooking.getDriverId());
            driverEmail.setText(taxiBookingDetails.getDriverEmail());
            driverDetailLay.setVisibility(View.VISIBLE);

            taxiRegNo.setText(taxiBooking.getTaxiId());
            taxiType.setText(taxiBooking.getTaxiTypeName());
            taxiModel.setText(taxiBooking.getTaxiTypeName() + " " + taxiBooking.getTaxiTypeName());
            taxiDetailLay.setVisibility(View.VISIBLE);

        }
        progressBar.setVisibility(View.GONE);
    }

    void setOnlineData() {


        try {
            Date date1 = df.parse(taxiBookingDetails.getPickupDatetime());
            pickupDate.setText(dateFormat.format(date1));
            pickupTime.setText(timeFormat.format(date1));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            Date date1 = df.parse(taxiBookingDetails.getBookingDate());
            bookingDate.setText(dateFormat.format(date1) + " " + timeFormat.format(date1));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        bookingId.setText(taxiBookingDetails.getReferenceNo());


        // bookingDate.setText(taxiBookingDetailsDetails.getBookingDate());
        bookingReason.setText(taxiBookingDetails.getReasonBooking());
        assCode.setText(taxiBookingDetails.getAssessmentCode());
        assCity.setText(taxiBookingDetails.getAssessmentCityId());
        if (taxiBookingDetails.getTourType().equals("1")) {
            tourType.setText("RADIO");
            pickupCity.setText(taxiBookingDetails.getCityName());
            dropCity.setText(taxiBookingDetails.getCityName());
        }
        if (taxiBookingDetails.getTourType().equals("2")) {
            tourType.setText("LOCAL");
            pickupCity.setText(taxiBookingDetails.getCityName());
            dropCity.setText(taxiBookingDetails.getCityName());
        }
        if (taxiBookingDetails.getTourType().equals("3")) {
            tourType.setText("OUTSTATION");
            pickupCity.setText(taxiBookingDetails.getCityName());
            String strMain = taxiBookingDetails.getDropLocation();
            String[] arrSplit = strMain.split(", ");
            dropCity.setText(arrSplit[0]);
        }


        pickupBoardingPoint.setText(taxiBookingDetails.getPickupLocation());
        //pickupDate.setText(taxiBookingDetailsDetails.getPickupDatetime());
        //pickupTime.setText();
        //pickupBoardingPoint.setText();

        dropEndPont.setText(taxiBookingDetails.getDropLocation());
        //dropDate.setText(taxiBookingDetailsDetails.getdr);
        //droptime
        //droplocation
        basicDetailLay.setVisibility(View.VISIBLE);

        spocName.setText(taxiBookingDetails.getUserName());
        spocContact.setText(taxiBookingDetails.getUserContact());
        spocEmail.setText(taxiBookingDetails.getEmail());
        //spocId.setText(taxiBookingDetailsDetails.getId());


        spocDetailLay.setVisibility(View.VISIBLE);
        if (taxiBookingDetails.getPassangers().size() > 0) {
            passenger_lay.setVisibility(View.VISIBLE);
            List<Passanger> passangers = taxiBookingDetails.getPassangers();
            String size = String.valueOf(passangers.size());
            noOfEmployees.setText(size);
            for (int i = 0; i < passangers.size(); i++) {
                int j = i + 1;
                String no = String.valueOf(j);
                if (passangers.get(i).getEmployeeEmail() != null &&
                        passangers.get(i).getEmployeeName() != null &&
                        passangers.get(i).getEmployeeContact() != null) {

                    LayoutInflater layoutInflater =
                            (LayoutInflater) getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View addView = layoutInflater.inflate(R.layout.passengers_list, null);
                    final TextView passName = (TextView) addView.findViewById(R.id.passenger_name);
                    final TextView passEmail = (TextView) addView.findViewById(R.id.passemail);
                    final TextView passCon = (TextView) addView.findViewById(R.id.passcon);
                    passName.setText(taxiBookingDetails.getPassangers().get(i).getEmployeeName());
                    passEmail.setText(taxiBookingDetails.getPassangers().get(i).getEmployeeEmail());
                    passCon.setText(taxiBookingDetails.getPassangers().get(i).getEmployeeContact());
                    passengersListLay.addView(addView, 0);
                    LayoutTransition transition = new LayoutTransition();
                    passengersListLay.setLayoutTransition(transition);


                } else {
                    LayoutInflater layoutInflater =
                            (LayoutInflater) getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View addView = layoutInflater.inflate(R.layout.passengers_list, null);
                    final TextView passName = (TextView) addView.findViewById(R.id.passenger_name);
                    passName.setText("Passenger Details Not Found");
                    passengersListLay.addView(addView, 0);
                    LayoutTransition transition = new LayoutTransition();
                    passengersListLay.setLayoutTransition(transition);

                }


            }

        }

        if (taxiBookingDetails.getStatusCotrav() <= 1) {
            bookingStatus.setText(taxiBookingDetails.getClientStatus());
            dropDate.setText("Not Assigned");


        } else {
            if (taxiBookingDetails.getStatusCotrav() == 2 || taxiBookingDetails.getStatusCotrav() == 3)
                dropDate.setText("Not Assigned");

            bookingStatus.setText(taxiBookingDetails.getCotravStatus());
        }

        if (taxiBookingDetails.getStatusCotrav() == 4) {
            driverName.setText(taxiBookingDetails.getDriverName());
            driverContact.setText(taxiBookingDetails.getDriverContact());
            driverEmail.setText(taxiBookingDetails.getDriverEmail());
            driverDetailLay.setVisibility(View.VISIBLE);

            taxiRegNo.setText(taxiBookingDetails.getTaxiRegNo());
            taxiType.setText(taxiBookingDetails.getTaxiTypeName());
            taxiModel.setText(taxiBookingDetails.getModelName() + " " + taxiBookingDetails.getModelName());
            taxiDetailLay.setVisibility(View.VISIBLE);

        }
        progressBar.setVisibility(View.GONE);
    }

}
