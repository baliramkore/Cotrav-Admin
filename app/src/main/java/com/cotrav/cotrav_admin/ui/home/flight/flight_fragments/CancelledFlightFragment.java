package com.cotrav.cotrav_admin.ui.home.flight.flight_fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cotrav.cotrav_admin.R;
import com.cotrav.cotrav_admin.adapter.flight_adapter.CancelledFlightBookingAdapter;
import com.cotrav.cotrav_admin.model.home_model.flight_model.show_flight.FlightBooking;
import com.cotrav.cotrav_admin.ui.home.flight.FlightBookingsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CancelledFlightFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private SwipeRefreshLayout swipeRefreshLayout;
    private CancelledFlightBookingAdapter cancelledFlightBookingAdapter;
    private FlightBookingsViewModel flightBookingsViewModel;
    private List<FlightBooking> cancelledBookings;
    private LinearLayout connectionErrorLayout;
    TextView connectionErrorTextView;
    SharedPreferences loginPref;
    String corporate_id = "";
    String token ="";
    String authorization = "";
    String usertype = "";

    public CancelledFlightFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent,
                             Bundle onSavedInstance) {
        super.onCreateView(layoutInflater, parent, onSavedInstance);

        loginPref=getActivity().getSharedPreferences("login_info", Context.MODE_PRIVATE);
        //token ="FQSC5OV68K3WG083FMXKWOWGO2IP8Z0S87G5HBHGIKLPESSC599ZVJU9XGIC";
        token = loginPref.getString("access_token","n");
        authorization = "Token "+token;
        usertype = loginPref.getString("usertype", "n");
        corporate_id = loginPref.getString("corporate_id", "n");
        flightBookingsViewModel = ViewModelProviders.of(getActivity()).get(FlightBookingsViewModel.class);
        //flightBookingsViewModel.InitFlightBookingViewModel(authorization,usertype,corporate_id);
        cancelledBookings =new ArrayList<>();
        final View view = layoutInflater.inflate(R.layout.fragment_cancelled_flight, parent, false);
        Log.d("Flight Booking Fragment", getActivity().getClass().getSimpleName());
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_to_refresh);
        cancelledFlightBookingAdapter = new CancelledFlightBookingAdapter(getActivity(), cancelledBookings,"cancelled");
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        connectionErrorLayout=(LinearLayout) view.findViewById(R.id.error_empty_layout);
        connectionErrorTextView=(TextView)view.findViewById(R.id.no_booking_connection_error);
        swipeRefreshLayout.setRefreshing(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        //Log.d("Archived", "Running archived fragment");
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cancelledFlightBookingAdapter);


        flightBookingsViewModel.getCancelledLiveData(authorization,usertype,corporate_id).observe(this, new Observer<List<FlightBooking>>() {
            @Override
            public void onChanged(List<FlightBooking> flightBookingList) {

                if (swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }
                if (flightBookingList!=null && flightBookingList.size()>0){
                    connectionErrorLayout.setVisibility(View.GONE);
                    cancelledBookings.clear();
                    cancelledBookings.addAll(flightBookingList);
                }
                else if (flightBookingList!=null && flightBookingList.size()==0){
                    connectionErrorTextView.setText("No Flight Booking Available");
                    cancelledBookings.clear();
                    connectionErrorLayout.setVisibility(View.VISIBLE);
                }
                cancelledFlightBookingAdapter.notifyDataSetChanged();

            }
        });
        flightBookingsViewModel.getCancelledConnectionError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d("Flight Booking",s);
                if (swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }
                if (s.equals("No Bookings Available")) {
                    Snackbar.make(view, s, Snackbar.LENGTH_LONG).show();
                    connectionErrorTextView.setText(s);
                    connectionErrorLayout.setVisibility(View.VISIBLE);
                }
                else {
                    if (cancelledBookings.size()==0){
                        connectionErrorTextView.setText(s);
                        connectionErrorLayout.setVisibility(View.VISIBLE);
                    }
                    Snackbar.make(view, s, Snackbar.LENGTH_LONG).setAction("try again", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            swipeRefreshLayout.setRefreshing(true);
                            flightBookingsViewModel.refreshCancelledBooking(authorization,usertype,corporate_id);
                        }
                    }).show();
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(this);
        return view;
    }
    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        flightBookingsViewModel.refreshCancelledBooking(authorization,usertype,corporate_id);
    }

    @Override
    public void onResume() {
        super.onResume();
        // swipeRefreshLayout.setRefreshing(true);
        flightBookingsViewModel.refreshUpcomingBooking(authorization,usertype,corporate_id);
    }
}
