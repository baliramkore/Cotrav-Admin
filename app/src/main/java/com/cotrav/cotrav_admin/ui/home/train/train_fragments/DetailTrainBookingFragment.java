package com.cotrav.cotrav_admin.ui.home.train.train_fragments;


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
import com.cotrav.cotrav_admin.model.home_model.train_model.show_train.TrainBooking;
import com.cotrav.cotrav_admin.model.home_model.train_model.view_train.ViewTrainBooking;
import com.cotrav.cotrav_admin.model.login_model.UserViewModel;
import com.cotrav.cotrav_admin.ui.home.train.TrainBookingsViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailTrainBookingFragment extends Fragment {

    TextView bookingId, tourType, bookingStatus, bookingDate,
            assCode, assCity, bookingReason,
            pickupTime, pickupDate, pickupCity, pickupBoardingPoint, noOfEmployees,
            dropTime, dropDate, dropCity, dropEndPont, spocName, spocContact, spocEmail,
            driverName, driverContact, driverEmail,
            trainType, trainTicketNo, trainPnr, trainSeatNo, trainName;

    LinearLayout basicDetailLay, spocDetailLay, trainDetailLay;
    CardView passenger_lay;
    LinearLayout passengersListLay;

    ProgressBar progressBar;
    Bundle arg;
    TrainBookingsViewModel trainBookingsViewModel;
    UserViewModel userViewModel;
    ViewTrainBooking trainBookingDetails;
    SharedPreferences loginPref;
    String token, authorization;
    Bundle args;
    View view;
    DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
    DateFormat timeFormat = new SimpleDateFormat("HH:mm");
    private TrainBooking trainBooking;

    public DetailTrainBookingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_detail_train_booking, container, false);
        basicDetailLay = view.findViewById(R.id.basic_detail_lay);
        basicDetailLay.setVisibility(View.GONE);
        bookingId = view.findViewById(R.id.refrence_no);
        bookingDate = view.findViewById(R.id.booking_date);
        tourType = view.findViewById(R.id.tour_type);
        bookingStatus = view.findViewById(R.id.booking_status);
        assCity = view.findViewById(R.id.assesment_city);
        assCode = view.findViewById(R.id.assesment_code);
        bookingReason = view.findViewById(R.id.booking_reason);


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
        noOfEmployees = view.findViewById(R.id.no_of_emp);
        passenger_lay = view.findViewById(R.id.passenger_lay);
        passengersListLay = (LinearLayout) view.findViewById(R.id.passengersListLay);
        progressBar = view.findViewById(R.id.progressBar_bookingDetails);
        trainPnr = view.findViewById(R.id.train_pnr_no);
        trainType = view.findViewById(R.id.train_type);
        trainSeatNo = view.findViewById(R.id.train_seat_no);
        trainTicketNo = view.findViewById(R.id.train_ticket_no);
        trainDetailLay = view.findViewById(R.id.train_detail_lay);
        trainDetailLay.setVisibility(View.GONE);
        trainName = view.findViewById(R.id.train_name);


        trainBookingsViewModel = ViewModelProviders.of(this).get(TrainBookingsViewModel.class);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        loginPref = getActivity().getSharedPreferences("login_info", Context.MODE_PRIVATE);
        token = loginPref.getString("access_token", "n");
        authorization = "Token " + token;
        args = getArguments();
        progressBar.setVisibility(View.VISIBLE);
        if (!getArguments().getString("details", "n").equals("n")) {
            trainBooking = GsonStringConvertor.stringToGson(getArguments().getString("details", "n"), TrainBooking.class);
            setData();
        }
        if (args.getString("booking_status", "n").equals("search")) {
            trainBookingsViewModel.getTrainBookingDetails(authorization, "6",
                    getArguments().getString("bookingId")).observe(this, new Observer<ViewTrainBooking>() {
                @Override
                public void onChanged(ViewTrainBooking trainBookingDetail) {
                    progressBar.setVisibility(View.GONE);
                    if (trainBookingDetail != null) {
                        Log.d("TrainBookingDetails", GsonStringConvertor.gsonToString(trainBookingDetail));
                        trainBookingDetails = trainBookingDetail;
                        setOnlineData();
                    }
                }
            });


            trainBookingsViewModel.getTrainDetailsConnectionError().observe(this, new Observer<String>() {
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
            Date date1 = df.parse(trainBooking.getPickupFromDatetime());
            pickupDate.setText(dateFormat.format(date1));
            pickupTime.setText(timeFormat.format(date1));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            Date date1 = df.parse(trainBooking.getBookingDatetime());
            bookingDate.setText(dateFormat.format(date1) + " " + timeFormat.format(date1));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        bookingId.setText(trainBooking.getReferenceNo());
        bookingStatus.setText(trainBooking.getClientStatus());

        bookingReason.setText(trainBooking.getReasonBooking());
        assCode.setText(trainBooking.getAssessmentCode());
        assCity.setText(trainBooking.getAssessmentCityId());

        pickupBoardingPoint.setText(trainBooking.getPickupLocation());
        // pickupDate.setText(trainBookingDetails.getPickupDatetime());
        //pickupTime.setText();
        //pickupBoardingPoint.setText();

        dropEndPont.setText(trainBooking.getDropLocation());
        //dropDate.setText(trainBookingDetails.getdr);
        //droptime
        //droplocation
        basicDetailLay.setVisibility(View.VISIBLE);
        String pickupstr = trainBooking.getPickupLocation();
        String[] pickupSplit = pickupstr.split(" ");
        pickupCity.setText(pickupSplit[0]);
        String strMain = trainBooking.getDropLocation();
        String[] arrSplit = strMain.split(" ");
        dropCity.setText(arrSplit[0]);
        spocName.setText(trainBooking.getUserName());
        spocContact.setText(trainBooking.getUserContact());
        spocEmail.setText(trainBooking.getBookingEmail());
        //spocId.setText(trainBookingDetails.getId());
        spocDetailLay.setVisibility(View.VISIBLE);

        if (trainBooking.getPassangers().size() > 0) {
            passenger_lay.setVisibility(View.VISIBLE);
            List<Passanger> passangers = trainBooking.getPassangers();
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
                    passName.setText(trainBooking.getPassangers().get(i).getEmployeeName());
                    passEmail.setText(trainBooking.getPassangers().get(i).getEmployeeEmail());
                    passCon.setText(trainBooking.getPassangers().get(i).getEmployeeContact());
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
        if (trainBooking.getStatusCotrav() <= 1) {
            bookingStatus.setText(trainBooking.getClientStatus());
            dropDate.setText("Not Assigned");

        } else {
            if (trainBooking.getStatusCotrav() == 3 || trainBooking.getStatusCotrav() == 2)
                dropDate.setText("Not Assigned");

            bookingStatus.setText(trainBooking.getCotravStatus());
        }


        if (trainBooking.getStatusCotrav() >= 4) {

            try {
                Date date1 = df.parse(trainBooking.getBoardingDatetime());
                dropDate.setText(dateFormat.format(date1));
                dropTime.setText(timeFormat.format(date1));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            trainTicketNo.setText(trainBooking.getTicketNo());
            trainSeatNo.setText(trainBooking.getSeatNo());
            trainPnr.setText(trainBooking.getPnrNo());
            trainType.setText(trainBooking.getAssignBusTypeId());
            trainDetailLay.setVisibility(View.VISIBLE);
            trainName.setText(trainBooking.getOperatorName());
        }

        progressBar.setVisibility(View.GONE);
    }


    void setOnlineData(){
        try {
            Date date1 = df.parse(trainBookingDetails.getPickupFromDatetime());
            pickupDate.setText(dateFormat.format(date1));
            pickupTime.setText(timeFormat.format(date1));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            Date date1 = df.parse(trainBookingDetails.getBookingDatetime());
            bookingDate.setText(dateFormat.format(date1) + " " + timeFormat.format(date1));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        bookingId.setText(trainBookingDetails.getReferenceNo());
        bookingStatus.setText(trainBookingDetails.getClientStatus());

        bookingReason.setText(trainBookingDetails.getReasonBooking());
        assCode.setText(trainBookingDetails.getAssessmentCode());
        assCity.setText(trainBookingDetails.getAssessmentCityId());

        pickupBoardingPoint.setText(trainBookingDetails.getPickupLocation());
        // pickupDate.setText(trainBookingDetailsDetails.getPickupDatetime());
        //pickupTime.setText();
        //pickupBoardingPoint.setText();

        dropEndPont.setText(trainBookingDetails.getDropLocation());
        //dropDate.setText(trainBookingDetailsDetails.getdr);
        //droptime
        //droplocation
        basicDetailLay.setVisibility(View.VISIBLE);
        String pickupstr = trainBookingDetails.getPickupLocation();
        String[] pickupSplit = pickupstr.split(" ");
        pickupCity.setText(pickupSplit[0]);
        String strMain = trainBookingDetails.getDropLocation();
        String[] arrSplit = strMain.split(" ");
        dropCity.setText(arrSplit[0]);
        spocName.setText(trainBookingDetails.getUserName());
        spocContact.setText(trainBookingDetails.getUserContact());
        spocEmail.setText(trainBookingDetails.getBookingEmail());
        //spocId.setText(trainBookingDetailsDetails.getId());
        spocDetailLay.setVisibility(View.VISIBLE);

        if (trainBookingDetails.getPassangers().size() > 0) {
            passenger_lay.setVisibility(View.VISIBLE);
            List<Passanger> passangers = trainBookingDetails.getPassangers();
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
                    passName.setText(trainBookingDetails.getPassangers().get(i).getEmployeeName());
                    passEmail.setText(trainBookingDetails.getPassangers().get(i).getEmployeeEmail());
                    passCon.setText(trainBookingDetails.getPassangers().get(i).getEmployeeContact());
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
        if (trainBookingDetails.getStatusCotrav() <= 1) {
            bookingStatus.setText(trainBookingDetails.getClientStatus());
            dropDate.setText("Not Assigned");

        } else {
            if (trainBookingDetails.getStatusCotrav() == 3 || trainBookingDetails.getStatusCotrav() == 2)
                dropDate.setText("Not Assigned");

            bookingStatus.setText(trainBookingDetails.getCotravStatus());
        }


        if (trainBookingDetails.getStatusCotrav() >= 4) {

            try {
                Date date1 = df.parse(trainBookingDetails.getBoardingDatetime());
                dropDate.setText(dateFormat.format(date1));
                dropTime.setText(timeFormat.format(date1));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            trainTicketNo.setText(trainBookingDetails.getTicketNo());
            trainSeatNo.setText(trainBookingDetails.getSeatNo());
            trainPnr.setText(trainBookingDetails.getPnrNo());
            trainType.setText(trainBookingDetails.getAssignBusTypeId());
            trainDetailLay.setVisibility(View.VISIBLE);
            trainName.setText(trainBookingDetails.getTrainName());
        }

        progressBar.setVisibility(View.GONE);
    }

}
