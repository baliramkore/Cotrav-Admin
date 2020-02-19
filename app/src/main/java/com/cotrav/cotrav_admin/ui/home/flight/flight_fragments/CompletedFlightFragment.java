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
import com.cotrav.cotrav_admin.adapter.flight_adapter.CompletedFlightBookingAdapter;
import com.cotrav.cotrav_admin.model.home_model.flight_model.show_flight.FlightBooking;
import com.cotrav.cotrav_admin.ui.home.flight.FlightBookingsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompletedFlightFragment extends Fragment  implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private SwipeRefreshLayout swipeRefreshLayout;
    private CompletedFlightBookingAdapter completedFlightBookingAdapter;
    private FlightBookingsViewModel flightBookingsViewModel;
    private List<FlightBooking> completedBookings;
    private LinearLayout connectionErrorLayout;
    TextView connectionErrorTextView;
    SharedPreferences loginPref;
    String corporate_id = "";
    String token ="";
    String authorization = "";
    Context context;
    String usertype = "";

    public CompletedFlightFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent,
                             Bundle onSavedInstance) {
        super.onCreateView(layoutInflater, parent, onSavedInstance);
        context=getActivity();

        flightBookingsViewModel = ViewModelProviders.of(getActivity()).get(FlightBookingsViewModel.class);
        loginPref=getActivity().getSharedPreferences("login_info", Context.MODE_PRIVATE);
        //token ="FQSC5OV68K3WG083FMXKWOWGO2IP8Z0S87G5HBHGIKLPESSC599ZVJU9XGIC";
        token = loginPref.getString("access_token","n");
        authorization = "Token "+token;
        usertype = "4";
        corporate_id = loginPref.getString("corporate_id","n");
        //flightBookingsViewModel.InitFlightBookingViewModel(authorization,usertype,corporate_id);
        completedBookings =new ArrayList<>();
        final View view = layoutInflater.inflate(R.layout.fragment_completed_flight, parent, false);
        Log.d("Taxi Booking Fragment", getActivity().getClass().getSimpleName());
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_to_refresh);
        completedFlightBookingAdapter = new CompletedFlightBookingAdapter(getActivity(), completedBookings,"completed");
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        connectionErrorLayout=(LinearLayout) view.findViewById(R.id.error_empty_layout);
        connectionErrorTextView=(TextView)view.findViewById(R.id.no_booking_connection_error);
        swipeRefreshLayout.setRefreshing(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        //Log.d("Archived", "Running archived fragment");
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(completedFlightBookingAdapter);

        flightBookingsViewModel.getPastLiveData(authorization,usertype,corporate_id).observe(this, new Observer<List<FlightBooking>>() {
            @Override
            public void onChanged(List<FlightBooking> flightBookingList) {

                if (swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }
                if (flightBookingList!=null && flightBookingList.size()>0){
                    connectionErrorLayout.setVisibility(View.GONE);
                    completedBookings.clear();
                    completedBookings.addAll(flightBookingList);
                }
                else if (flightBookingList!=null && flightBookingList.size()==0){
                    connectionErrorTextView.setText("No Flight Booking Available");
                    completedBookings.clear();
                    connectionErrorLayout.setVisibility(View.VISIBLE);
                }
                completedFlightBookingAdapter.notifyDataSetChanged();

            }/*{
                if (swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }
                if (FlightBookingList!=null){
                    connectionErrorLayout.setVisibility(View.GONE);
                    completedBookings.clear();
                    completedBookings.addAll(FlightBookingList);


                }
                completedFlightBookingAdapter.notifyDataSetChanged();
            }*/
        });
        flightBookingsViewModel.getPastConnectionError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d("Flight Booking",s);
                if (swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }
                if (s.equals("No Flight Booking Available")) {
                    Snackbar.make(view, s, Snackbar.LENGTH_LONG).show();
                    connectionErrorTextView.setText(s);
                    connectionErrorLayout.setVisibility(View.VISIBLE);
                }
                else {
                    if (completedBookings.size()==0){
                        connectionErrorTextView.setText(s);
                        connectionErrorLayout.setVisibility(View.VISIBLE);
                    }
                    Snackbar.make(view, s, Snackbar.LENGTH_LONG).setAction("try again", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            swipeRefreshLayout.setRefreshing(true);
                            flightBookingsViewModel.refreshPastBooking(authorization,usertype,corporate_id);
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
        flightBookingsViewModel.refreshPastBooking(authorization,usertype,corporate_id);
    }

    @Override
    public void onResume() {
        super.onResume();
        // swipeRefreshLayout.setRefreshing(true);
        flightBookingsViewModel.refreshUpcomingBooking(authorization,usertype,corporate_id);
    }

}
