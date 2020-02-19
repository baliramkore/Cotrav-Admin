package com.cotrav.cotrav_admin.ui.home.bus.bus_fragments;


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
import com.cotrav.cotrav_admin.adapter.bus_adapter.CancelledBusBookingAdapter;
import com.cotrav.cotrav_admin.model.home_model.bus_model.show_bus.BusBooking;
import com.cotrav.cotrav_admin.ui.home.bus.BusBookingsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CancelledBusFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private SwipeRefreshLayout swipeRefreshLayout;
    private CancelledBusBookingAdapter cancelledBusBookingAdapter;
    private BusBookingsViewModel busBookingsViewModel;
    private List<BusBooking> cancelledBookings;
    private LinearLayout connectionErrorLayout;
    TextView connectionErrorTextView;
    SharedPreferences loginPref;
    String corporate_id = "";
    String token ="";
    String authorization = "";

    String usertype = "";

    public CancelledBusFragment() {
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
        busBookingsViewModel = ViewModelProviders.of(getActivity()).get(BusBookingsViewModel.class);
        //busBookingsViewModel.InitBusBookingViewModel(authorization,usertype,corporate_id);
        cancelledBookings =new ArrayList<>();
        final View view = layoutInflater.inflate(R.layout.fragment_cancelled_bus, parent, false);
        Log.d("Bus Booking Fragment", getActivity().getClass().getSimpleName());
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_to_refresh);
        cancelledBusBookingAdapter = new CancelledBusBookingAdapter(getActivity(), cancelledBookings,"cancelled");
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        connectionErrorLayout=(LinearLayout) view.findViewById(R.id.error_empty_layout);
        connectionErrorTextView=(TextView)view.findViewById(R.id.no_booking_connection_error);
        swipeRefreshLayout.setRefreshing(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        //Log.d("Archived", "Running archived fragment");
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cancelledBusBookingAdapter);


        busBookingsViewModel.getCancelledLiveData(authorization,usertype,corporate_id).observe(this,

                new Observer<List<BusBooking>>() {
                    @Override
                    public void onChanged(List<BusBooking> busBookings) {

                        if (swipeRefreshLayout.isRefreshing()){
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        if (busBookings!=null && busBookings.size()>0){
                            connectionErrorLayout.setVisibility(View.GONE);
                            cancelledBookings.clear();
                            cancelledBookings.addAll(busBookings);
                        }
                        else if (busBookings!=null && busBookings.size()==0){
                            connectionErrorTextView.setText("No Bus Booking Available");
                            cancelledBookings.clear();
                            connectionErrorLayout.setVisibility(View.VISIBLE);
                        }
                        cancelledBusBookingAdapter.notifyDataSetChanged();

                    }
                });

                /* new Observer<List<BusBooking>>() {
            @Override
            public void onChanged(List<BusBooking> busBookingList) {

                if (swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }
                if (busBookingList!=null && busBookingList.size()>0){
                    connectionErrorLayout.setVisibility(View.GONE);
                    cancelledBookings.clear();
                    cancelledBookings.addAll(busBookingList);
                }
                else if (busBookingList!=null && busBookingList.size()==0){
                    connectionErrorTextView.setText("No Bus Booking Available");
                    cancelledBookings.clear();
                    connectionErrorLayout.setVisibility(View.VISIBLE);
                }
                cancelledBusBookingAdapter.notifyDataSetChanged();

            }
        });*/
                busBookingsViewModel.getCancelledConnectionError().observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        Log.d("Bus Booking", s);
                        if (swipeRefreshLayout.isRefreshing()) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        if (s.equals("No Bookings Available")) {
                            Snackbar.make(view, s, Snackbar.LENGTH_LONG).show();
                            connectionErrorTextView.setText(s);
                            connectionErrorLayout.setVisibility(View.VISIBLE);
                        } else {
                            if (cancelledBookings.size() == 0) {
                                connectionErrorTextView.setText(s);
                                connectionErrorLayout.setVisibility(View.VISIBLE);
                            }
                            Snackbar.make(view, s, Snackbar.LENGTH_LONG).setAction("try again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    swipeRefreshLayout.setRefreshing(true);
                                    busBookingsViewModel.refreshCancelledBooking(authorization, usertype, corporate_id);
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
        busBookingsViewModel.refreshCancelledBooking(authorization,usertype,corporate_id);
    }

    @Override
    public void onResume() {
        super.onResume();
        // swipeRefreshLayout.setRefreshing(true);
        busBookingsViewModel.refreshUpcomingBooking(authorization,usertype,corporate_id);
    }
}
