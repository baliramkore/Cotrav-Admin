package com.cotrav.cotrav_admin.ui.utilities.rates;


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
import com.cotrav.cotrav_admin.adapter.RatesAdapter;
import com.cotrav.cotrav_admin.model.rates_model.CorporateRates;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddRateFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RatesAdapter ratesAdapter;
    private RatesViewModel ratesViewModel;
    private List<CorporateRates> corporateRatesList;
    private LinearLayout connectionErrorLayout;
    TextView connectionErrorTextView;
    SharedPreferences loginPref;
    String corporate_id = "";
    String token ="";
    String authorization = "";
    String usertype = "";

    public AddRateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        super.onCreateView(layoutInflater, parent, savedInstanceState);
        final View view = layoutInflater.inflate(R.layout.fragment_add_rate, parent, false);

        ratesViewModel = ViewModelProviders.of(getActivity()).get(RatesViewModel.class);
        loginPref=getActivity().getSharedPreferences("login_info", Context.MODE_PRIVATE);
        //token ="FQSC5OV68K3WG083FMXKWOWGO2IP8Z0S87G5HBHGIKLPESSC599ZVJU9XGIC";
        token = loginPref.getString("access_token","n");
        authorization = "Token "+token;
        usertype = loginPref.getString("usertype", "n");
        corporate_id = loginPref.getString("corporate_id", "n");
        ratesViewModel.InitCorporateRatesViewModel(authorization,usertype,corporate_id);
        corporateRatesList =new ArrayList<>();

        Log.d("Add Rates Fragment", getActivity().getClass().getSimpleName());
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_to_refresh);
        ratesAdapter = new RatesAdapter(getActivity(), corporateRatesList,"add_rates");
        //EntitiesAdapter.notifyDataSetChanged();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        connectionErrorLayout=(LinearLayout) view.findViewById(R.id.error_empty_layout);
        connectionErrorTextView=(TextView)view.findViewById(R.id.no_booking_connection_error);
        swipeRefreshLayout.setRefreshing(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        //Log.d("Archived", "Running archived fragment");
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(ratesAdapter);

        ratesViewModel.getCorporateRatesLiveData(authorization,usertype,corporate_id).observe(this, new Observer<List<CorporateRates>>() {
            @Override
            public void onChanged(List<CorporateRates> ratesList) {

                if (swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }
                if (ratesList!=null && ratesList.size()>0){
                    connectionErrorLayout.setVisibility(View.GONE);
                    corporateRatesList.clear();
                    corporateRatesList.addAll(ratesList);

                }
                else if (ratesList!=null && ratesList.size()==0){
                    connectionErrorTextView.setText("No Rates Booking Available");
                    ratesList.clear();
                    connectionErrorLayout.setVisibility(View.VISIBLE);
                }
                ratesAdapter.notifyDataSetChanged();

            }
        });

        ratesViewModel.getRatesConnectionError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d("Entities Error",s);
                if (swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }
                if (s.equals("No Rates Available")) {
                    Snackbar.make(view, s, Snackbar.LENGTH_LONG).show();
                    connectionErrorTextView.setText(s);
                    connectionErrorLayout.setVisibility(View.VISIBLE);
                }
                else {
                    if (corporateRatesList.size()==0){
                        connectionErrorTextView.setText(s);
                        connectionErrorLayout.setVisibility(View.VISIBLE);
                    }
                    Snackbar.make(view, s, Snackbar.LENGTH_LONG).setAction("try again", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            swipeRefreshLayout.setRefreshing(true);
                            ratesViewModel.getCorporateRatesLiveData(authorization,usertype,corporate_id);
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
        ratesViewModel.refreshTodaysBooking(authorization,usertype,corporate_id);
    }

    @Override
    public void onResume() {
        super.onResume();
        // swipeRefreshLayout.setRefreshing(true);
        ratesViewModel.refreshTodaysBooking(authorization,usertype,corporate_id);
    }
}
