package com.cotrav.cotrav_admin.ui.home.flight.flight_fragments;


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
import com.cotrav.cotrav_admin.model.home_model.flight_model.show_flight.FlightBooking;
import com.cotrav.cotrav_admin.model.home_model.flight_model.view_flight.FlightBookingDetails;
import com.cotrav.cotrav_admin.model.login_model.UserViewModel;
import com.cotrav.cotrav_admin.ui.home.flight.FlightBookingsViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFlightBookingFragment extends Fragment {


    TextView bookingId, tourType, bookingStatus, bookingDate,
            assCode, assCity, bookingReason, dep_date,
            pickupTime, pickupCity, pickupBoardingPoint, empList, noOfEmployees,
            dropTime, dropCity, dropEndPont, spocName, spocId, spocContact, spocEmail,
            driverName, driverContact, driverEmail,
            flightType, flightTicketNo, flightPnr, flightSeatType;

    LinearLayout basicDetailLay, spocDetailLay, flightDetailLay;
    CardView passenger_lay;
    ProgressBar progressBar;
    Bundle arg;
    FlightBookingsViewModel flightBookingsViewModel;
    UserViewModel userViewModel;
    FlightBookingDetails flightBookingDetails;
    SharedPreferences loginPref;
    String token, authorization;
    Bundle args;
    View view;
    DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
    DateFormat timeFormat = new SimpleDateFormat("HH:mm");
    private FlightBooking flightBooking;
    LinearLayout passengersListLay;

    public DetailFlightBookingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_detail_flight_booking, container, false);
        basicDetailLay = view.findViewById(R.id.basic_detail_lay);
        basicDetailLay.setVisibility(View.GONE);
        bookingId = view.findViewById(R.id.refrence_no);
        tourType = view.findViewById(R.id.tour_type);
        bookingStatus = view.findViewById(R.id.booking_status);
        assCity = view.findViewById(R.id.assesment_city);
        assCode = view.findViewById(R.id.assesment_code);
        bookingReason = view.findViewById(R.id.booking_reason);
        bookingDate = view.findViewById(R.id.booking_date);
        dep_date = view.findViewById(R.id.dep_date);


        pickupCity = view.findViewById(R.id.pickup_city);
        pickupBoardingPoint = view.findViewById(R.id.pickup_boardingpoint);
        //pickupDate = view.findViewById(R.id.pickup_date);
        pickupTime = view.findViewById(R.id.pickup_time);

        dropCity = view.findViewById(R.id.drop_city);
        //dropDate = view.findViewById(R.id.drop_date);
        dropEndPont = view.findViewById(R.id.drop_droping_point);
        dropTime = view.findViewById(R.id.drop_time);

        spocDetailLay = view.findViewById(R.id.spoc_detail_lay);
        spocDetailLay.setVisibility(View.GONE);
        spocName = view.findViewById(R.id.spoc_name);
        spocEmail = view.findViewById(R.id.spoc_email);
        spocContact = view.findViewById(R.id.spoc_contact);
        noOfEmployees = view.findViewById(R.id.no_of_emp);
        passenger_lay = view.findViewById(R.id.passenger_lay);

        progressBar = view.findViewById(R.id.progressBar_bookingDetails);
        flightPnr = view.findViewById(R.id.flight_pnr_no);
        flightType = view.findViewById(R.id.flight_type);
        flightSeatType = view.findViewById(R.id.flight_seat_type);
        flightTicketNo = view.findViewById(R.id.flight_ticket_no);
        flightDetailLay = view.findViewById(R.id.flight_detail_lay);
        flightDetailLay.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        Bundle args = getArguments();
        if (!args.getString("details", "n").equals("n")) {
            flightBooking = GsonStringConvertor.stringToGson(args.getString("details", "n"), FlightBooking.class);

            setData();

        }


        loginPref = getActivity().getSharedPreferences("login_info", Context.MODE_PRIVATE);
        token = loginPref.getString("access_token", "n");
        authorization = "Token " + token;
        args = getArguments();


        // Toasty.normal(getActivity(),getArguments().getString("bookingId")).show();
        if (args.getString("booking_status", "n").equals("search")) {
            flightBookingsViewModel = ViewModelProviders.of(this).get(FlightBookingsViewModel.class);
            userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
            flightBookingsViewModel.getFlightBookingDetails(authorization, "1",
                    getArguments().getString("bookingId")).observe(this, new Observer<FlightBookingDetails>() {
                @Override
                public void onChanged(FlightBookingDetails flightBookingDetail) {
                    progressBar.setVisibility(View.GONE);
                    if (flightBookingDetail != null) {
                        Log.d("FlightBookingDetails", GsonStringConvertor.gsonToString(flightBookingDetail));
                        flightBookingDetails = flightBookingDetail;
                        setOnlineData();
                    }
                }
            });


            flightBookingsViewModel.getFlightdetailsConnectionError().observe(this, new Observer<String>() {
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

        dep_date.setText(flightBooking.getDepartureDatetime());

        try {
            Date date1 = df.parse(flightBooking.getBookingDatetime());
            bookingDate.setText(dateFormat.format(date1) + " " + timeFormat.format(date1));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        bookingId.setText(flightBooking.getReferenceNo());
        bookingStatus.setText(flightBooking.getClientStatus());

        bookingReason.setText(flightBooking.getReasonBooking());
        assCode.setText(flightBooking.getAssessmentCode());
        assCity.setText(flightBooking.getFromLocation());

        String strMain = flightBooking.getFromLocation();
        String[] arrSplit = strMain.split(", ");
        pickupCity.setText(arrSplit[0]);
        pickupBoardingPoint.setText(flightBooking.getFromLocation());
        // pickupDate.setText(flightBookingDetails.getPickupDatetime());
        //pickupTime.setText();
        //pickupBoardingPoint.setText();

        String strMain2 = flightBooking.getToLocation();
        String[] arrSplit2 = strMain2.split(", ");
        dropCity.setText(arrSplit2[0]);
        dropEndPont.setText(flightBooking.getToLocation());
        //  dropDate.setText(flightBookingDetails.getDepartureDatetime());
        //droptime
        //droplocation
        basicDetailLay.setVisibility(View.VISIBLE);

        spocName.setText(flightBooking.getUserName());
        spocContact.setText(flightBooking.getUserContact());
        spocEmail.setText(flightBooking.getBookingEmail());
        //spocId.setText(flightBookingDetails.getId());
        spocDetailLay.setVisibility(View.VISIBLE);
        passengersListLay = (LinearLayout) view.findViewById(R.id.passengersListLay);
        if (flightBooking.getPassangers().size() > 0) {
            passenger_lay.setVisibility(View.VISIBLE);
            List<Passanger> passangers = flightBooking.getPassangers();
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
                    passName.setText(flightBooking.getPassangers().get(i).getEmployeeName());
                    passEmail.setText(flightBooking.getPassangers().get(i).getEmployeeEmail());
                    passCon.setText(flightBooking.getPassangers().get(i).getEmployeeContact());
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
        if (flightBooking.getStatusCotrav() <= 1) {
            bookingStatus.setText(flightBooking.getClientStatus());
            dep_date.append(" Not Assigned");

        } else {
            if (flightBooking.getStatusCotrav() == 3 || flightBooking.getStatusCotrav() == 2)
                dep_date.append(" Not Assigned");

            bookingStatus.setText(flightBooking.getCotravStatus());
        }


        if (flightBooking.getStatusCotrav() >= 4) {

           /* try {
                Date date1 = df.parse(flightBookingDetails.getDepartureDatetime());
                dropDate.setText(dateFormat.format(date1));
                //dropTime.setText(timeFormat.format(date1));
            } catch (ParseException e) {
                e.printStackTrace();
            }*/
            // flightTicketNo.setText(flightBooking.getFlights().get(0).getFlightNo());
            // flightSeatType.setText(flightBooking.getSeatType());
            // flightPnr.setText(flightBooking.getFlights().get(0).getPnrNo());
            // flightType.setText(flightBooking.getFlightType());
            // flightDetailLay.setVisibility(View.VISIBLE);
        }
        progressBar.setVisibility(View.GONE);
    }

    void setOnlineData(){

        dep_date.setText(flightBookingDetails.getDepartureDatetime());

        try {
            Date date1 = df.parse(flightBookingDetails.getBookingDatetime());
            bookingDate.setText(dateFormat.format(date1) + " " + timeFormat.format(date1));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        bookingId.setText(flightBookingDetails.getReferenceNo());
        bookingStatus.setText(flightBookingDetails.getClientStatus());

        bookingReason.setText(flightBookingDetails.getReasonBooking());
        assCode.setText(flightBookingDetails.getAssessmentCode());
        assCity.setText(flightBookingDetails.getFromLocation());

        String strMain = flightBookingDetails.getFromLocation();
        String[] arrSplit = strMain.split(", ");
        pickupCity.setText(arrSplit[0]);
        pickupBoardingPoint.setText(flightBookingDetails.getFromLocation());
        // pickupDate.setText(flightBookingDetailsDetails.getPickupDatetime());
        //pickupTime.setText();
        //pickupBoardingPoint.setText();

        String strMain2 = flightBookingDetails.getToLocation();
        String[] arrSplit2 = strMain2.split(", ");
        dropCity.setText(arrSplit2[0]);
        dropEndPont.setText(flightBookingDetails.getToLocation());
        //  dropDate.setText(flightBookingDetailsDetails.getDepartureDatetime());
        //droptime
        //droplocation
        basicDetailLay.setVisibility(View.VISIBLE);

        spocName.setText(flightBookingDetails.getUserName());
        spocContact.setText(flightBookingDetails.getUserContact());
        spocEmail.setText(flightBookingDetails.getBookingEmail());
        //spocId.setText(flightBookingDetailsDetails.getId());
        spocDetailLay.setVisibility(View.VISIBLE);
        passengersListLay = (LinearLayout) view.findViewById(R.id.passengersListLay);
        if (flightBookingDetails.getPassangers().size() > 0) {
            passenger_lay.setVisibility(View.VISIBLE);
            List<Passanger> passangers = flightBookingDetails.getPassangers();
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
                    passName.setText(flightBookingDetails.getPassangers().get(i).getEmployeeName());
                    passEmail.setText(flightBookingDetails.getPassangers().get(i).getEmployeeEmail());
                    passCon.setText(flightBookingDetails.getPassangers().get(i).getEmployeeContact());
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
        if (flightBookingDetails.getStatusCotrav() <= 1) {
            bookingStatus.setText(flightBookingDetails.getClientStatus());
            dep_date.append(" Not Assigned");

        } else {
            if (flightBookingDetails.getStatusCotrav() == 3 || flightBookingDetails.getStatusCotrav() == 2)
                dep_date.append(" Not Assigned");

            bookingStatus.setText(flightBookingDetails.getCotravStatus());
        }


        if (flightBookingDetails.getStatusCotrav() >= 4) {

           /* try {
                Date date1 = df.parse(flightBookingDetails.getDepartureDatetime());
                dropDate.setText(dateFormat.format(date1));
                //dropTime.setText(timeFormat.format(date1));
            } catch (ParseException e) {
                e.printStackTrace();
            }*/
             flightTicketNo.setText(flightBookingDetails.getFlights().get(0).getFlightNo());
              flightSeatType.setText(flightBookingDetails.getSeatType());
              flightPnr.setText(flightBookingDetails.getFlights().get(0).getPnrNo());
              flightType.setText(flightBookingDetails.getFlightType());
              flightDetailLay.setVisibility(View.VISIBLE);
        }
        progressBar.setVisibility(View.GONE);
    }

}
