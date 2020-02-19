package com.cotrav.cotrav_admin.ui.utilities.subgroups;


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
import com.cotrav.cotrav_admin.adapter.SubGroupsAdapter;
import com.cotrav.cotrav_admin.model.subgroup_model.Subgroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddSubGroupFragment extends Fragment  implements SwipeRefreshLayout.OnRefreshListener  {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SubGroupsAdapter subgroupAdapter;
    private SubGroupsViewModel subgroupViewModel;
    private List<Subgroup> subgroupList;
    private LinearLayout connectionErrorLayout;
    TextView connectionErrorTextView;
    SharedPreferences loginPref;
    String corporate_id = "";
    String token ="";
    String authorization = "";
    String usertype = "";
    String userId = "";
    private FloatingActionButton fab;

    public AddSubGroupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent,
                             Bundle onSavedInstance) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_add_sub_group, container, false);
        super.onCreateView(layoutInflater, parent, onSavedInstance);
        final View view = layoutInflater.inflate(R.layout.fragment_add_entity, parent, false);
        subgroupViewModel = ViewModelProviders.of(getActivity()).get(SubGroupsViewModel.class);
        loginPref=getActivity().getSharedPreferences("login_info", Context.MODE_PRIVATE);
        token = loginPref.getString("access_token","n");
        authorization = "Token "+token;
        usertype = loginPref.getString("usertype", "n");
        userId=loginPref.getString("user_id", "n");
        corporate_id = loginPref.getString("corporate_id", "n");
        subgroupViewModel.InitAdminSubGroupsViewModel(authorization,usertype,corporate_id);
        subgroupList =new ArrayList<>();

        Log.d("Add Entity Fragment", getActivity().getClass().getSimpleName());
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_to_refresh);
        subgroupAdapter = new SubGroupsAdapter(getActivity(), subgroupList,authorization,userId);
        //EntitiesAdapter.notifyDataSetChanged();
        recyclerView = (RecyclerView) view.findViewById(R.id.entity_recycler_view);
        connectionErrorLayout=(LinearLayout) view.findViewById(R.id.error_empty_layout);
        connectionErrorTextView=(TextView)view.findViewById(R.id.no_booking_connection_error);
        swipeRefreshLayout.setRefreshing(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        //Log.d("Archived", "Running archived fragment");
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(subgroupAdapter);
        fab=(FloatingActionButton)view.findViewById(R.id.fab111);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
//                Intent intent = new Intent(getContext(), AddEntityActvity.class);
//                startActivity(intent);
                Intent intent = new Intent(getContext(), AddSubGroupsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("EntityTask", "AddEntity");
                intent.putExtras(bundle);
                getContext().startActivity(intent);
            }
        });


        subgroupViewModel.getAdminSubGroupsLiveData(authorization,usertype,corporate_id).observe(this,
                new Observer<List<Subgroup>>() {
                    @Override
                    public void onChanged(List<Subgroup> groupsList) {

                        if (swipeRefreshLayout.isRefreshing()){
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        if (groupsList!=null && groupsList.size()>0){
                            connectionErrorLayout.setVisibility(View.GONE);
                            subgroupList.clear();
                            subgroupList.addAll(groupsList);

                        }
                        else if (groupsList!=null && groupsList.size()==0){
                            connectionErrorTextView.setText("No Entity Booking Available");
                            groupsList.clear();
                            connectionErrorLayout.setVisibility(View.VISIBLE);
                        }
                        subgroupAdapter.notifyDataSetChanged();

                    }
                });
        subgroupViewModel.getRatesConnectionError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d("Entities Error",s);
                if (swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }
                if (s.equals("No Sub Group Available")) {
                    Snackbar.make(view, s, Snackbar.LENGTH_LONG).show();
                    connectionErrorTextView.setText(s);
                    connectionErrorLayout.setVisibility(View.VISIBLE);
                }
                else {
                    if (subgroupList.size()==0){
                        connectionErrorTextView.setText(s);
                        connectionErrorLayout.setVisibility(View.VISIBLE);
                    }
                    Snackbar.make(view, s, Snackbar.LENGTH_LONG).setAction("try again", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            swipeRefreshLayout.setRefreshing(true);
                            subgroupViewModel.getAdminSubGroupsLiveData(authorization,usertype,corporate_id);
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
        subgroupViewModel.refreshAdminSubGroups(authorization,usertype,corporate_id);
    }

    @Override
    public void onResume() {
        super.onResume();
        // swipeRefreshLayout.setRefreshing(true);
        subgroupViewModel.refreshAdminSubGroups(authorization,usertype,corporate_id);
    }
}
