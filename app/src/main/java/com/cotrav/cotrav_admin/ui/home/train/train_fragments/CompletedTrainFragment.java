package com.cotrav.cotrav_admin.ui.home.train.train_fragments;


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
import com.cotrav.cotrav_admin.adapter.train_adapter.CompletedTrainBookingAdapter;
import com.cotrav.cotrav_admin.model.home_model.train_model.show_train.TrainBooking;
import com.cotrav.cotrav_admin.ui.home.train.TrainBookingsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompletedTrainFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private SwipeRefreshLayout swipeRefreshLayout;
    private CompletedTrainBookingAdapter completedTrainBookingAdapter;
    private TrainBookingsViewModel trainBookingsViewModel;
    private List<TrainBooking> completedBookings;
    private LinearLayout connectionErrorLayout;
    TextView connectionErrorTextView;
    SharedPreferences loginPref;
    String spoc_id = "";
    String token ="";
    String authorization = "";
    Context context;
    String usertype = "";

    public CompletedTrainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent,
                             Bundle onSavedInstance) {
        super.onCreateView(layoutInflater, parent, onSavedInstance);
        context=getActivity();

        trainBookingsViewModel = ViewModelProviders.of(getActivity()).get(TrainBookingsViewModel.class);
        loginPref=getActivity().getSharedPreferences("login_info", Context.MODE_PRIVATE);
        //token ="FQSC5OV68K3WG083FMXKWOWGO2IP8Z0S87G5HBHGIKLPESSC599ZVJU9XGIC";
        token = loginPref.getString("access_token","n");
        authorization = "Token "+token;
        usertype = loginPref.getString("usertype", "n");
        spoc_id = loginPref.getString("corporate_id","n");
        //trainBookingsViewModel.InitTrainBookingViewModel(authorization,usertype,spoc_id);
        completedBookings =new ArrayList<>();
        final View view = layoutInflater.inflate(R.layout.fragment_completed_train, parent, false);
        Log.d("train Booking Fragment", getActivity().getClass().getSimpleName());
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_to_refresh);
        completedTrainBookingAdapter = new CompletedTrainBookingAdapter(getActivity(), completedBookings,"completed");
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        connectionErrorLayout=(LinearLayout) view.findViewById(R.id.error_empty_layout);
        connectionErrorTextView=(TextView)view.findViewById(R.id.no_booking_connection_error);
        swipeRefreshLayout.setRefreshing(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        //Log.d("Archived", "Running archived fragment");
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(completedTrainBookingAdapter);

/*
        fab=(FloatingActionButton)view.findViewById(R.id.fab_add_Train);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), AddTrainBookingActivity.class);
                startActivity(intent);

            }
        });
*/

        trainBookingsViewModel.getPastLiveData(authorization,usertype,spoc_id).observe(this, new Observer<List<TrainBooking>>() {
            @Override
            public void onChanged(List<TrainBooking> trainBookingList) {
                if (swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }
                if (trainBookingList!=null && trainBookingList.size()>0){
                    connectionErrorLayout.setVisibility(View.GONE);
                    completedBookings.clear();
                    completedBookings.addAll(trainBookingList);
                }
                else if (trainBookingList!=null && trainBookingList.size()==0){

                    completedBookings.clear();
                    connectionErrorLayout.setVisibility(View.VISIBLE);
                }
                completedTrainBookingAdapter.notifyDataSetChanged();
            }
        });
        trainBookingsViewModel.getPastConnectionError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d("train Booking",s);
                if (swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }
                if (s.equals("No Bookings Available")) {
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
                            trainBookingsViewModel.refreshPastBooking(authorization,usertype,spoc_id);
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
        trainBookingsViewModel.refreshPastBooking(authorization,usertype,spoc_id);
    }
    @Override
    public void onResume() {
        super.onResume();
        // swipeRefreshLayout.setRefreshing(true);
        trainBookingsViewModel.refreshUpcomingBooking(authorization,usertype,spoc_id);
    }
}
