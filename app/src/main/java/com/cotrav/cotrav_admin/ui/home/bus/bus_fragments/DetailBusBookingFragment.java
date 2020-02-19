package com.cotrav.cotrav_admin.ui.home.bus.bus_fragments;


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
import com.cotrav.cotrav_admin.model.home_model.bus_model.show_bus.BusBooking;
import com.cotrav.cotrav_admin.model.home_model.bus_model.view_bus.ViewBusBooking;
import com.cotrav.cotrav_admin.model.login_model.UserViewModel;
import com.cotrav.cotrav_admin.ui.home.bus.BusBookingsViewModel;
import com.google.android.material.snackbar.Snackbar;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailBusBookingFragment extends Fragment {
    private TextView bookingId ,bookingStatus,
            assCode,assCity,bookingReason,bookingDate,
            pickupTime,pickupDate,pickupCity,pickupBoardingPoint,noOfEmployees,
            dropTime,dropDate,dropCity,dropEndPont,spocName,spocContact,spocEmail,
            driverName,driverContact,driverEmail,
            busType,busTicketNo,busPnr,busSeatNo;

    LinearLayout basicDetailLay,spocDetailLay,busDetailLay;
    CardView passenger_lay ;
    ProgressBar progressBar;
    Bundle arg;
    BusBookingsViewModel busBookingsViewModel;
    UserViewModel userViewModel;
    ViewBusBooking busBookingDetails;
    SharedPreferences loginPref;
    String token,authorization;
    Bundle args;
    View view;
    DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
    DateFormat timeFormat = new SimpleDateFormat("HH:mm");

    LinearLayout    passengersListLay;
    private BusBooking busBooking;
    public DetailBusBookingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_detail_bus_booking, container, false);
        basicDetailLay = view .findViewById(R.id.basic_detail_lay);
        basicDetailLay.setVisibility(View.GONE);
        bookingId = view.findViewById(R.id.refrence_no);
        bookingStatus= view.findViewById(R.id.booking_status);
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


        busPnr=view.findViewById(R.id.bus_pnr_no);
        busType=view.findViewById(R.id.bus_type);
        busSeatNo=view.findViewById(R.id.bus_seat_no);
        busTicketNo=view.findViewById(R.id.bus_ticket_no);
        busDetailLay = view.findViewById(R.id.bus_detail_lay);
        busDetailLay.setVisibility(View.GONE);

        progressBar = view.findViewById(R.id.progressBar_bookingDetails);

        //empList = view.findViewById(R.id.passengers_list);
        noOfEmployees = view.findViewById(R.id.no_of_emp);
        passenger_lay = view.findViewById(R.id.passenger_lay);


        loginPref=getActivity().getSharedPreferences("login_info", Context.MODE_PRIVATE);
        token = loginPref.getString("access_token","n");
        authorization = "Token "+token;
        args=getArguments();
        progressBar.setVisibility(View.VISIBLE);

        Bundle args=getArguments();
        if (!args.getString("details","n").equals("n")){
            busBooking= GsonStringConvertor.stringToGson(args.getString("details","n"),BusBooking.class);
            setData();
        }
        if (args.getString("booking_status","n").equals("search")){

            busBookingsViewModel= ViewModelProviders.of(this).get(BusBookingsViewModel.class);
            userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
            busBookingsViewModel.getBusBookingDetails(authorization,"1",
                    getArguments().getString("bookingId"),"").observe(this, new Observer<ViewBusBooking>() {
                @Override
                public void onChanged(ViewBusBooking busBookingDetail) {
                    progressBar.setVisibility(View.GONE);
                    if (busBookingDetail!=null){
                        Log.d("BusBookingDetails", GsonStringConvertor.gsonToString(busBookingDetail));
                        busBookingDetails=busBookingDetail;
                        setOnlineData();

                    }
                }
            });

            busBookingsViewModel.getBusdetailsConnectionError().observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    progressBar.setVisibility(View.GONE);
                    Snackbar.make(view,"No Booking Availabel",Snackbar.LENGTH_LONG).show();
                }
            });

        }








        return view;

    }
    void setData(){

        try {
            Date date1 = df.parse(busBooking.getPickupFromDatetime());
            pickupDate.setText(dateFormat.format(date1));
            pickupTime.setText(timeFormat.format(date1));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            Date date1 = df.parse(busBooking.getBookingDatetime());
            bookingDate.setText(dateFormat.format(date1)+" "+timeFormat.format(date1));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        bookingId.setText(busBooking.getReferenceNo());
        bookingStatus.setText(busBooking.getClientStatus());

        bookingReason.setText(busBooking.getReasonBooking());
        assCode.setText(busBooking.getAssessmentCode());
        assCity.setText(busBooking.getAssessmentCityId());


        String strMain = busBooking.getPickupLocation();
        String[] arrSplit = strMain.split(", ");
        pickupCity.setText(arrSplit[0]);
        pickupBoardingPoint.setText(busBooking.getPickupLocation());
        // pickupDate.setText(busBooking.getPickupDatetime());
        //pickupTime.setText();
        //pickupBoardingPoint.setText();


        String strMain2 = busBooking.getDropLocation();
        String[] arrSplit2 = strMain2.split(", ");
        dropCity.setText(arrSplit2[0]);
        dropEndPont.setText(busBooking.getDropLocation());
        //dropDate.setText(busBookingDetails.getdr);
        //droptime
        //droplocation
        basicDetailLay.setVisibility(View.VISIBLE);

        spocName.setText(busBooking.getUserName());
        spocContact.setText(busBooking.getUserContact());
        // spocEmail.setText(busBooking.getEmail());
        //spocId.setText(busBookingDetails.getId());
        spocDetailLay.setVisibility(View.VISIBLE);


        passengersListLay = (LinearLayout)view.findViewById(R.id.passengersListLay);

        if (busBooking.getPassangers().size()>0)
        {

            passenger_lay.setVisibility(View.VISIBLE);
            List<Passanger> passangers = busBooking.getPassangers();
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
                    passName.setText(busBooking.getPassangers().get(i).getEmployeeName());
                    passEmail.setText(busBooking.getPassangers().get(i).getEmployeeEmail());
                    passCon.setText(busBooking.getPassangers().get(i).getEmployeeContact());
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
        if (busBooking.getStatusCotrav()<=1){
            bookingStatus.setText(busBooking.getClientStatus());
            dropDate.setText("Not Assigned");

        }else{
            if (busBooking.getStatusCotrav()==3||busBooking.getStatusCotrav() == 2)
                dropDate.setText("Not Assigned");

            bookingStatus.setText(busBooking.getCotravStatus());
        }


        if (busBooking.getStatusCotrav()>=4){

            try {
                Date date1 = df.parse(busBooking.getBoardingDatetime());
                pickupDate.setText(dateFormat.format(date1));
                pickupTime.setText(timeFormat.format(date1));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            busTicketNo.setText(busBooking.getTicketNo());
            busSeatNo.setText(busBooking.getSeatNo());
            busPnr.setText(busBooking.getPnrNo());
            busType.setText(busBooking.getAssignBusTypeId());
            busDetailLay.setVisibility(View.VISIBLE);
        }
        progressBar.setVisibility(View.GONE);
    }


    void setOnlineData(){

        try {
            Date date1 = df.parse(busBookingDetails.getPickupFromDatetime());
            pickupDate.setText(dateFormat.format(date1));
            pickupTime.setText(timeFormat.format(date1));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            Date date1 = df.parse(busBookingDetails.getBookingDatetime());
            bookingDate.setText(dateFormat.format(date1)+" "+timeFormat.format(date1));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        bookingId.setText(busBookingDetails.getReferenceNo());
        bookingStatus.setText(busBookingDetails.getClientStatus());

        bookingReason.setText(busBookingDetails.getReasonBooking());
        assCode.setText(busBookingDetails.getAssessmentCode());
        assCity.setText(busBookingDetails.getAssessmentCityId());


        String strMain = busBookingDetails.getPickupLocation();
        String[] arrSplit = strMain.split(", ");
        pickupCity.setText(arrSplit[0]);
        pickupBoardingPoint.setText(busBookingDetails.getPickupLocation());
        // pickupDate.setText(busBookingDetails.getPickupDatetime());
        //pickupTime.setText();
        //pickupBoardingPoint.setText();


        String strMain2 = busBookingDetails.getDropLocation();
        String[] arrSplit2 = strMain2.split(", ");
        dropCity.setText(arrSplit2[0]);
        dropEndPont.setText(busBookingDetails.getDropLocation());
        //dropDate.setText(busBookingDetailsDetails.getdr);
        //droptime
        //droplocation
        basicDetailLay.setVisibility(View.VISIBLE);

        spocName.setText(busBookingDetails.getUserName());
        spocContact.setText(busBookingDetails.getUserContact());
        // spocEmail.setText(busBookingDetails.getEmail());
        //spocId.setText(busBookingDetailsDetails.getId());
        spocDetailLay.setVisibility(View.VISIBLE);


        passengersListLay = (LinearLayout)view.findViewById(R.id.passengersListLay);

        if (busBookingDetails.getPassangers().size()>0)
        {

            passenger_lay.setVisibility(View.VISIBLE);
            List<Passanger> passangers = busBookingDetails.getPassangers();
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
                    passName.setText(busBookingDetails.getPassangers().get(i).getEmployeeName());
                    passEmail.setText(busBookingDetails.getPassangers().get(i).getEmployeeEmail());
                    passCon.setText(busBookingDetails.getPassangers().get(i).getEmployeeContact());
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
        if (busBookingDetails.getStatusCotrav()<=1){
            bookingStatus.setText(busBookingDetails.getClientStatus());
            dropDate.setText("Not Assigned");

        }else{
            if (busBookingDetails.getStatusCotrav()==3||busBookingDetails.getStatusCotrav() == 2)
                dropDate.setText("Not Assigned");

            bookingStatus.setText(busBookingDetails.getCotravStatus());
        }


        if (busBookingDetails.getStatusCotrav()>=4){

            try {
                Date date1 = df.parse(busBookingDetails.getBoardingDatetime());
                pickupDate.setText(dateFormat.format(date1));
                pickupTime.setText(timeFormat.format(date1));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            busTicketNo.setText(busBookingDetails.getTicketNo());
            busSeatNo.setText(busBookingDetails.getSeatNo());
            busPnr.setText(busBookingDetails.getPnrNo());
            busType.setText(busBookingDetails.getAssignBusTypeId());
            busDetailLay.setVisibility(View.VISIBLE);
        }
        progressBar.setVisibility(View.GONE);
    }

}
