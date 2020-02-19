package com.cotrav.cotrav_admin.ui.utilities.spocs;


import android.content.Context;
import android.content.Intent;
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
import com.cotrav.cotrav_admin.adapter.SpocsAdapter;
import com.cotrav.cotrav_admin.model.spoc_model.Spocs;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddSpocFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SpocsAdapter spocsAdapter;
    private SpocsViewModel spocsViewModel;
    private List<Spocs> spocsList;
    private LinearLayout connectionErrorLayout;
    TextView connectionErrorTextView;
    SharedPreferences loginPref;
    String corporate_id = "";
    String token ="";
    String authorization = "";
    String usertype = "";
    String userId = "";
    private FloatingActionButton fab;

    public AddSpocFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.fragment_add_spoc, container, false);
        spocsViewModel = ViewModelProviders.of(getActivity()).get(SpocsViewModel.class);
        loginPref=getActivity().getSharedPreferences("login_info", Context.MODE_PRIVATE);
        //token ="FQSC5OV68K3WG083FMXKWOWGO2IP8Z0S87G5HBHGIKLPESSC599ZVJU9XGIC";
        token = loginPref.getString("access_token","n");
        authorization = "Token "+token;
        userId=loginPref.getString("corporate_id", "n");
        usertype = loginPref.getString("usertype", "n");
        corporate_id = loginPref.getString("corporate_id", "n");
        spocsViewModel.InitSpocsViewModel(authorization,usertype,corporate_id);
        spocsList =new ArrayList<>();

        Log.d("Add AssCity Fragment", getActivity().getClass().getSimpleName());
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_to_refresh);
        spocsAdapter = new SpocsAdapter(getActivity(), spocsList,authorization,userId);
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
        recyclerView.setAdapter(spocsAdapter);
        fab=(FloatingActionButton)view.findViewById(R.id.fab111);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

/*                Intent intent = new Intent(getContext(), AddAssesmentCityActivity.class);
                startActivity(intent);*/
                Intent intent = new Intent(getContext(), AddSpocsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("EntityTask", "AddEntity");
                intent.putExtras(bundle);
                getContext().startActivity(intent);

            }
        });

        spocsViewModel.getCorporateSpocsLiveData(authorization,usertype,corporate_id).observe(this, new Observer<List<Spocs>>() {
            @Override
            public void onChanged(List<Spocs> cityList) {

                if (swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }
                if (cityList!=null && cityList.size()>0){
                    connectionErrorLayout.setVisibility(View.GONE);
                    spocsList.clear();
                    spocsList.addAll(cityList);

                }
                else if (cityList!=null && cityList.size()==0){
                    connectionErrorTextView.setText("No Spocs Available");
                    cityList.clear();
                    connectionErrorLayout.setVisibility(View.VISIBLE);
                }
                spocsAdapter.notifyDataSetChanged();

            }
        });
        spocsViewModel.getRatesConnectionError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d("Entities Error",s);
                if (swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }
                if (s.equals("No Spoc Available")) {
                    Snackbar.make(view, s, Snackbar.LENGTH_LONG).show();
                    connectionErrorTextView.setText(s);
                    connectionErrorLayout.setVisibility(View.VISIBLE);
                }
                else {
                    if (spocsList.size()==0){
                        connectionErrorTextView.setText(s);
                        connectionErrorLayout.setVisibility(View.VISIBLE);
                    }
                    Snackbar.make(view, s, Snackbar.LENGTH_LONG).setAction("try again", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            swipeRefreshLayout.setRefreshing(true);
                            spocsViewModel.getCorporateSpocsLiveData(authorization,usertype,corporate_id);
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
        spocsViewModel.refreshTodaysBooking(authorization,usertype,corporate_id);
    }
    @Override
    public void onResume() {
        super.onResume();
        // swipeRefreshLayout.setRefreshing(true);
        spocsViewModel.refreshTodaysBooking(authorization,usertype,corporate_id);
    }
}
