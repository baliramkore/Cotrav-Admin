package com.cotrav.cotrav_admin.ui.home.hotel.hotel_fragments;


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
import com.cotrav.cotrav_admin.adapter.hotel_adapter.TodaysHotelBookingAdapter;
import com.cotrav.cotrav_admin.model.home_model.hotel_model.show_hotel.HotelBooking;
import com.cotrav.cotrav_admin.ui.home.hotel.HotelBookingsViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TodaysHotelFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TodaysHotelBookingAdapter todaysHotelBookingAdapter;
    private HotelBookingsViewModel hotelBookingsViewModel;
    private List<HotelBooking> todaysBookings;
    private LinearLayout connectionErrorLayout;
    TextView connectionErrorTextView;
    SharedPreferences loginPref;
    String corporate_id = "";
    String token ="";
    String authorization = "";

    String usertype = "";

    public TodaysHotelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent,
                             Bundle onSavedInstance) {
        super.onCreateView(layoutInflater, parent, onSavedInstance);
        hotelBookingsViewModel = ViewModelProviders.of(getActivity()).get(HotelBookingsViewModel.class);
        loginPref=getActivity().getSharedPreferences("login_info", Context.MODE_PRIVATE);
        //token ="FQSC5OV68K3WG083FMXKWOWGO2IP8Z0S87G5HBHGIKLPESSC599ZVJU9XGIC";
        token = loginPref.getString("access_token","n");
        authorization = "Token "+token;
        usertype = loginPref.getString("usertype", "n");
        corporate_id = loginPref.getString("corporate_id","n");
        hotelBookingsViewModel.InitHotelBookingViewModel(authorization,usertype,corporate_id);
        todaysBookings =new ArrayList<>();
        final View view = layoutInflater.inflate(R.layout.fragment_todays_hotel, parent, false);
        Log.d("Hotel Booking Fragment", getActivity().getClass().getSimpleName());
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_to_refresh);
        todaysHotelBookingAdapter = new TodaysHotelBookingAdapter(getActivity(), todaysBookings,"todays");
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        connectionErrorLayout=(LinearLayout) view.findViewById(R.id.error_empty_layout);
        connectionErrorTextView=(TextView)view.findViewById(R.id.no_booking_connection_error);

        swipeRefreshLayout.setRefreshing(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        //Log.d("Archived", "Running archived fragment");
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(todaysHotelBookingAdapter);



        hotelBookingsViewModel.getTodaysLiveData(authorization,usertype,corporate_id).observe(this, new Observer<List<HotelBooking>>() {
            @Override
            public void onChanged(List<HotelBooking> hotelBookingList) {
                if (swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }
                if (hotelBookingList!=null && hotelBookingList.size()>0){
                    connectionErrorLayout.setVisibility(View.GONE);
                    todaysBookings.clear();
                    todaysBookings.addAll(hotelBookingList);
                }
                else if (hotelBookingList!=null && hotelBookingList.size()==0){

                    todaysBookings.clear();
                    connectionErrorLayout.setVisibility(View.VISIBLE);
                }
                todaysHotelBookingAdapter.notifyDataSetChanged();
            }
        });
        hotelBookingsViewModel.getTodaysConnectionError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d("Hotel Booking",s);
                if (swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }
                if (s.equals("No Hotel Booking Available")) {
                    Snackbar.make(view, s, Snackbar.LENGTH_LONG).show();
                    connectionErrorTextView.setText(s);
                    connectionErrorLayout.setVisibility(View.VISIBLE);
                }
                else {
                    if (todaysBookings.size()==0){
                        connectionErrorTextView.setText(s);
                        connectionErrorLayout.setVisibility(View.VISIBLE);
                    }
                    Snackbar.make(view, s, Snackbar.LENGTH_LONG).setAction("try again", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            swipeRefreshLayout.setRefreshing(true);
                            hotelBookingsViewModel.refreshTodaysBooking(authorization,usertype,corporate_id);
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
        hotelBookingsViewModel.refreshTodaysBooking(authorization,usertype,corporate_id);
    }
    @Override
    public void onResume() {
        super.onResume();
        // swipeRefreshLayout.setRefreshing(true);
        hotelBookingsViewModel.refreshUpcomingBooking(authorization,usertype,corporate_id);
    }
}
