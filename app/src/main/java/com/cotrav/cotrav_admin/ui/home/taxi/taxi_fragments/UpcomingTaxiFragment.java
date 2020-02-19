package com.cotrav.cotrav_admin.ui.home.taxi.taxi_fragments;


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
import com.cotrav.cotrav_admin.adapter.taxi_adapter.UpcomingTaxiBookingAdapter;
import com.cotrav.cotrav_admin.local_database.AdminRoomDatabase;
import com.cotrav.cotrav_admin.model.home_model.taxi_model.show_taxi.TaxiBooking;
import com.cotrav.cotrav_admin.ui.home.taxi.TaxiBookingsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpcomingTaxiFragment extends Fragment  implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private SwipeRefreshLayout swipeRefreshLayout;
    private UpcomingTaxiBookingAdapter upcomingTaxiBookingAdapter;
    private TaxiBookingsViewModel taxiBookingsViewModel;
    private List<TaxiBooking> upcomingBookings;
    List<TaxiBooking> bookingsdata;
    private LinearLayout connectionErrorLayout;
    TextView connectionErrorTextView;
    SharedPreferences loginPref;
    String corporate_id = "";
    String token ="";
    String authorization = "";
    String userId="";
    String usertype = "";
    AdminRoomDatabase adminRoomDatabase;

    public UpcomingTaxiFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent,
                             Bundle onSavedInstance) {
        super.onCreateView(layoutInflater, parent, onSavedInstance);
        adminRoomDatabase=AdminRoomDatabase.getDatabase(getContext());

        taxiBookingsViewModel = ViewModelProviders.of(getActivity()).get(TaxiBookingsViewModel.class);
        loginPref=getActivity().getSharedPreferences("login_info", Context.MODE_PRIVATE);
        //token ="FQSC5OV68K3WG083FMXKWOWGO2IP8Z0S87G5HBHGIKLPESSC599ZVJU9XGIC";
        token = loginPref.getString("access_token","n");
        authorization = "Token "+token;
        usertype = loginPref.getString("usertype", "n");
        corporate_id = loginPref.getString("corporate_id", "n");
        userId=loginPref.getString("corporate_id","n");
        //taxiBookingsViewModel.InitPlaneBookingViewModel(authorization,usertype,corporate_id);
        upcomingBookings =new ArrayList<>();
        bookingsdata=new ArrayList<>();
        final View view = layoutInflater.inflate(R.layout.fragment_upcoming_taxi, parent, false);
        Log.d("Taxi Booking Fragment", getActivity().getClass().getSimpleName());
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_to_refresh);
        upcomingTaxiBookingAdapter = new UpcomingTaxiBookingAdapter(getActivity(), upcomingBookings,
                "todays",authorization,usertype,userId);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        connectionErrorLayout=(LinearLayout) view.findViewById(R.id.error_empty_layout);
        connectionErrorTextView=(TextView)view.findViewById(R.id.no_booking_connection_error);
        swipeRefreshLayout.setRefreshing(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        //Log.d("Archived", "Running archived fragment");
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(upcomingTaxiBookingAdapter);



        taxiBookingsViewModel.getUpcomingLiveData(authorization,usertype,userId).observe(this, new Observer<List<TaxiBooking>>() {
            @Override
            public void onChanged(final List<TaxiBooking> taxiBookingList) {

                if (swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }
                if (taxiBookingList!=null && taxiBookingList.size()>0){
                    connectionErrorLayout.setVisibility(View.GONE);
                    upcomingBookings.clear();
                    upcomingBookings.addAll(taxiBookingList);
                }
                else if (taxiBookingList!=null && taxiBookingList.size()==0){
                    connectionErrorTextView.setText("No Taxi Booking Available");
                    upcomingBookings.clear();
                    connectionErrorLayout.setVisibility(View.VISIBLE);
                }
                upcomingTaxiBookingAdapter.notifyDataSetChanged();

            }/*{
                if (swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }
                if (taxiBookingList!=null){
                    connectionErrorLayout.setVisibility(View.GONE);
                    upcomingBookings.clear();
                    bookingsdata.clear();
                    upcomingBookings.addAll(taxiBookingList);
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            // Insert Data into Local Database
                            //spocDb.daoAccess().nukeTable();
                            spocDb.daoAccess().addTaxiBookings(taxiBookingList);
                           //bookingsdata= spocDb.daoAccess().getLocalTaxiBookings();
                            //upcomingBookings.addAll(bookingsdata);

                        }
                    });
                }
                upcomingTaxiBookingAdapter.notifyDataSetChanged();
            }*/
        });
        taxiBookingsViewModel.getUpcomingConnectionError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d("Taxi Booking",s);
                if (swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }
                if (s.equals("No Taxi Booking Available")) {
                    Snackbar.make(view, s, Snackbar.LENGTH_LONG).show();
                    connectionErrorTextView.setText(s);
                    connectionErrorLayout.setVisibility(View.VISIBLE);
                }
                else {
                    if (upcomingBookings.size()==0){
                        connectionErrorTextView.setText(s);
                        connectionErrorLayout.setVisibility(View.VISIBLE);
                    }
                    Snackbar.make(view, s, Snackbar.LENGTH_LONG).setAction("try again", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            swipeRefreshLayout.setRefreshing(true);
                            taxiBookingsViewModel.refreshUpcomingBooking(authorization,usertype,corporate_id);
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
        taxiBookingsViewModel.refreshUpcomingBooking(authorization,usertype,corporate_id);
    }

    @Override
    public void onResume() {
        super.onResume();
        // swipeRefreshLayout.setRefreshing(true);
        taxiBookingsViewModel.refreshUpcomingBooking(authorization,usertype,corporate_id);
    }
}
