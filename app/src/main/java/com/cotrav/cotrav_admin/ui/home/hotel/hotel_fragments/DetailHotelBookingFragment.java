package com.cotrav.cotrav_admin.ui.home.hotel.hotel_fragments;


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
import com.cotrav.cotrav_admin.model.home_model.hotel_model.show_hotel.HotelBooking;
import com.cotrav.cotrav_admin.model.home_model.hotel_model.view_hotel.ViewHotelBooking;
import com.cotrav.cotrav_admin.model.login_model.UserViewModel;
import com.cotrav.cotrav_admin.ui.home.hotel.HotelBookingsViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailHotelBookingFragment extends Fragment {


    TextView bookingId , tourType,bookingStatus,bookingDate,
            assCode,assCity,bookingReason,
            pickupTime,pickupDate,pickupCity,pickupBoardingPoint,noOfEmployees,
            dropTime,dropDate,dropCity,dropEndPont,spocName,spocId,spocContact,spocEmail,
            driverName,driverContact,driverEmail,
            hotelType,roomtype,hotelName;

    LinearLayout basicDetailLay,spocDetailLay,hotelDetailLay;
    CardView passenger_lay ;
    ProgressBar progressBar;
    Bundle arg;
    HotelBookingsViewModel hotelBookingsViewModel;
    UserViewModel userViewModel;
    ViewHotelBooking hotelBookingDetails;
    SharedPreferences loginPref;
    String token,authorization;
    Bundle args;
    LinearLayout passengersListLay;
    View view;
    DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
    DateFormat timeFormat = new SimpleDateFormat("HH:mm");
    HotelBooking hotelBooking;
    public DetailHotelBookingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_detail_hotel_booking, container, false);
        basicDetailLay = view .findViewById(R.id.basic_detail_lay);
        basicDetailLay.setVisibility(View.GONE);
        bookingId = view.findViewById(R.id.refrence_no);
        tourType = view.findViewById(R.id.tour_type);
        bookingStatus= view.findViewById(R.id.booking_status);
        assCity = view.findViewById(R.id.assesment_city);
        assCode = view.findViewById(R.id.assesment_code);
        bookingReason = view.findViewById(R.id.booking_reason);
        bookingDate = view.findViewById(R.id.booking_date);
        hotelType=view.findViewById(R.id.hotel_type);
        roomtype=view.findViewById(R.id.room_type);
        hotelName=view.findViewById(R.id.hotel_name);


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

        hotelDetailLay=view.findViewById(R.id.hotel_detail_lay);
        hotelDetailLay.setVisibility(View.GONE);
        progressBar = view.findViewById(R.id.progressBar_bookingDetails);

        noOfEmployees = view.findViewById(R.id.no_of_emp);
        passenger_lay = view.findViewById(R.id.passenger_lay);



        hotelBookingsViewModel= ViewModelProviders.of(this).get(HotelBookingsViewModel.class);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        loginPref=getActivity().getSharedPreferences("login_info", Context.MODE_PRIVATE);
        token = loginPref.getString("access_token","n");
        authorization = "Token "+token;
        args=getArguments();
        progressBar.setVisibility(View.VISIBLE);

        if (!getArguments().getString("details","n").equals("n")){
            hotelBooking= GsonStringConvertor.stringToGson(getArguments().getString("details","n"),HotelBooking.class);
            setData();
        }
        if (args.getString("booking_status","n").equals("search")){

            hotelBookingsViewModel.getHotelBookingDetails(authorization,"6",
                    getArguments().getString("bookingId")).observe(this, new Observer<ViewHotelBooking>() {
                @Override
                public void onChanged(ViewHotelBooking hotelBookingDetail) {
                    progressBar.setVisibility(View.GONE);
                    if (hotelBookingDetail!=null){
                        Log.d("HotelBookingDetails", GsonStringConvertor.gsonToString(hotelBookingDetail));
                        hotelBookingDetails=hotelBookingDetail;
                        setOnlineData();
                    }
                }
            });

            hotelBookingsViewModel.getHoteldetailsConnectionError().observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    progressBar.setVisibility(View.GONE);
                    Snackbar.make(view,"Connection Error",Snackbar.LENGTH_LONG).show();
                }
            });

        }








        return view;

    }
    void setData(){
        bookingId.setText(hotelBooking.getReferenceNo());
        try {
            Date date1 = df.parse(hotelBooking.getCheckinDatetime());
            pickupDate.setText(dateFormat.format(date1));
            pickupTime.setText(timeFormat.format(date1));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            Date date1 = df.parse(hotelBooking.getCheckoutDatetime());
            dropDate.setText(dateFormat.format(date1));
            dropTime.setText(timeFormat.format(date1));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            Date date1 = df.parse(hotelBooking.getBookingDatetime());
            bookingDate.setText(dateFormat.format(date1)+" "+timeFormat.format(date1));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        bookingStatus.setText(hotelBooking.getClientStatus());

        bookingReason.setText(hotelBooking.getReasonBooking());
        assCode.setText(hotelBooking.getAssessmentCode());
       // assCity.setText(hotelBooking.getAssessmentCityId());

        pickupCity.setText(hotelBooking.getFromCityName());
        // pickupDate.setText(hotelBooking.getPickupDatetime());
        //pickupTime.setText();
        pickupBoardingPoint.setText(hotelBooking.getPreferredArea());

        //dropCity.setText(hotelBookingDetails.getDropLocation());
        //dropDate.setText(hotelBookingDetails.getdr);
        //droptime
        //droplocation
        basicDetailLay.setVisibility(View.VISIBLE);

        spocName.setText(hotelBooking.getUserName());
        spocContact.setText(hotelBooking.getUserContact());
        // spocEmail.setText(hotelBookingDetails.getEmail());
        //spocId.setText(hotelBookingDetails.getId());
        spocDetailLay.setVisibility(View.VISIBLE);

        passengersListLay = (LinearLayout)view.findViewById(R.id.passengersListLay);
        if (hotelBooking.getPassangers().size()>0)
        {
            passenger_lay.setVisibility(View.VISIBLE);
            List<Passanger> passangers = hotelBooking.getPassangers();
            String size = String.valueOf(passangers.size());
            noOfEmployees.setText(size);
            for (int i = 0;i<passangers.size();i++){
                int j = i+1;
                String no = String.valueOf(j);
                if (passangers.get(i).getEmployeeEmail()!=null&&
                        passangers.get(i).getEmployeeName()!=null &&
                        passangers.get(i).getEmployeeContact()!=null){

                    LayoutInflater layoutInflater =
                            (LayoutInflater) getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View addView = layoutInflater.inflate(R.layout.passengers_list, null);
                    final TextView passName = (TextView)addView.findViewById(R.id.passenger_name);
                    final TextView passEmail = (TextView)addView.findViewById(R.id.passemail);
                    final TextView passCon = (TextView)addView.findViewById(R.id.passcon);
                    passName.setText(hotelBooking.getPassangers().get(i).getEmployeeName());
                    passEmail.setText(hotelBooking.getPassangers().get(i).getEmployeeEmail());
                    passCon.setText(hotelBooking.getPassangers().get(i).getEmployeeContact());
                    passengersListLay.addView(addView, 0);
                    LayoutTransition transition = new LayoutTransition();
                    passengersListLay.setLayoutTransition(transition);




                }
                else
                {
                    LayoutInflater layoutInflater =
                            (LayoutInflater) getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View addView = layoutInflater.inflate(R.layout.passengers_list, null);
                    final TextView passName = (TextView)addView.findViewById(R.id.passenger_name);
                    passName.setText("Passenger Details Not Found");
                    passengersListLay.addView(addView, 0);
                    LayoutTransition transition = new LayoutTransition();
                    passengersListLay.setLayoutTransition(transition);

                }



            }

        }

        if (hotelBooking.getStatusCotrav()<=1){
            bookingStatus.setText(hotelBooking.getClientStatus());
            dropDate.setText("Not Assigned");

        }else{
            if (hotelBooking.getStatusCotrav()==3||hotelBooking.getStatusCotrav() == 2)
                dropDate.setText("Not Assigned");

            bookingStatus.setText(hotelBooking.getCotravStatus());
        }


        if (hotelBooking.getStatusCotrav()>=4){


            hotelType.setText(hotelBooking.getHotelTypeName());
            roomtype.setText(hotelBooking.getRoomTypeName());
            hotelName.setText(hotelBooking.getOperatorName());

            hotelDetailLay.setVisibility(View.VISIBLE);
        }progressBar.setVisibility(View.GONE);
    }

    void setOnlineData(){
        bookingId.setText(hotelBookingDetails.getReferenceNo());
        try {
            Date date1 = df.parse(hotelBookingDetails.getCheckinDatetime());
            pickupDate.setText(dateFormat.format(date1));
            pickupTime.setText(timeFormat.format(date1));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            Date date1 = df.parse(hotelBookingDetails.getCheckoutDatetime());
            dropDate.setText(dateFormat.format(date1));
            dropTime.setText(timeFormat.format(date1));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            Date date1 = df.parse(hotelBookingDetails.getBookingDatetime());
            bookingDate.setText(dateFormat.format(date1)+" "+timeFormat.format(date1));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        bookingStatus.setText(hotelBookingDetails.getClientStatus());

        bookingReason.setText(hotelBookingDetails.getReasonBooking());
        assCode.setText(hotelBookingDetails.getAssessmentCode());
        assCity.setText(hotelBookingDetails.getAssessmentCityId());

        pickupCity.setText(hotelBookingDetails.getFromCityName());
        // pickupDate.setText(hotelBookingDetails.getPickupDatetime());
        //pickupTime.setText();
        pickupBoardingPoint.setText(hotelBookingDetails.getPreferredArea());

        //dropCity.setText(hotelBookingDetailsDetails.getDropLocation());
        //dropDate.setText(hotelBookingDetailsDetails.getdr);
        //droptime
        //droplocation
        basicDetailLay.setVisibility(View.VISIBLE);

        spocName.setText(hotelBookingDetails.getUserName());
        spocContact.setText(hotelBookingDetails.getUserContact());
        // spocEmail.setText(hotelBookingDetailsDetails.getEmail());
        //spocId.setText(hotelBookingDetailsDetails.getId());
        spocDetailLay.setVisibility(View.VISIBLE);

        passengersListLay = (LinearLayout)view.findViewById(R.id.passengersListLay);
        if (hotelBookingDetails.getPassangers().size()>0)
        {
            passenger_lay.setVisibility(View.VISIBLE);
            List<Passanger> passangers = hotelBookingDetails.getPassangers();
            String size = String.valueOf(passangers.size());
            noOfEmployees.setText(size);
            for (int i = 0;i<passangers.size();i++){
                int j = i+1;
                String no = String.valueOf(j);
                if (passangers.get(i).getEmployeeEmail()!=null&&
                        passangers.get(i).getEmployeeName()!=null &&
                        passangers.get(i).getEmployeeContact()!=null){

                    LayoutInflater layoutInflater =
                            (LayoutInflater) getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View addView = layoutInflater.inflate(R.layout.passengers_list, null);
                    final TextView passName = (TextView)addView.findViewById(R.id.passenger_name);
                    final TextView passEmail = (TextView)addView.findViewById(R.id.passemail);
                    final TextView passCon = (TextView)addView.findViewById(R.id.passcon);
                    passName.setText(hotelBookingDetails.getPassangers().get(i).getEmployeeName());
                    passEmail.setText(hotelBookingDetails.getPassangers().get(i).getEmployeeEmail());
                    passCon.setText(hotelBookingDetails.getPassangers().get(i).getEmployeeContact());
                    passengersListLay.addView(addView, 0);
                    LayoutTransition transition = new LayoutTransition();
                    passengersListLay.setLayoutTransition(transition);


                }
                else
                {
                    LayoutInflater layoutInflater =
                            (LayoutInflater) getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View addView = layoutInflater.inflate(R.layout.passengers_list, null);
                    final TextView passName = (TextView)addView.findViewById(R.id.passenger_name);
                    passName.setText("Passenger Details Not Found");
                    passengersListLay.addView(addView, 0);
                    LayoutTransition transition = new LayoutTransition();
                    passengersListLay.setLayoutTransition(transition);

                }



            }

        }

        if (hotelBookingDetails.getStatusCotrav()<=1){
            bookingStatus.setText(hotelBookingDetails.getClientStatus());
            dropDate.setText("Not Assigned");

        }else{
            if (hotelBookingDetails.getStatusCotrav()==3||hotelBookingDetails.getStatusCotrav() == 2)
                dropDate.setText("Not Assigned");

            bookingStatus.setText(hotelBookingDetails.getCotravStatus());
        }


        if (hotelBookingDetails.getStatusCotrav()>=4){
            hotelType.setText(hotelBookingDetails.getHotelTypeName());
            roomtype.setText(hotelBookingDetails.getRoomTypeName());
            hotelName.setText(hotelBookingDetails.getOperatorName());

            hotelDetailLay.setVisibility(View.VISIBLE);
        }progressBar.setVisibility(View.GONE);
    }

}
