package com.cotrav.cotrav_admin.ui.utilities.entities;


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
import com.cotrav.cotrav_admin.adapter.EntitiesAdapter;
import com.cotrav.cotrav_admin.model.entities_model.Entities;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddEntityFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private EntitiesAdapter enitiesAdapter;
    private EntitiesViewModel entityViewModel;
    private List<Entities> EntitiesList;
    private LinearLayout connectionErrorLayout;
    TextView connectionErrorTextView;
    SharedPreferences loginPref;
    String corporate_id = "";
    String token ="";
    String authorization = "";
    String usertype = "";
    String userId = "";
    private FloatingActionButton fab;

    public AddEntityFragment()
    {

    }


    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent,
                             Bundle onSavedInstance) {
        super.onCreateView(layoutInflater, parent, onSavedInstance);
        final View view = layoutInflater.inflate(R.layout.fragment_add_entity, parent, false);
        entityViewModel = ViewModelProviders.of(getActivity()).get(EntitiesViewModel.class);
        loginPref=getActivity().getSharedPreferences("login_info", Context.MODE_PRIVATE);
        token = loginPref.getString("access_token","n");
        authorization = "Token "+token;
        usertype = loginPref.getString("usertype", "n");
        userId=loginPref.getString("user_id", "n");
        corporate_id = loginPref.getString("corporate_id", "n");
        entityViewModel.InitEntitiesViewModel(authorization,usertype,corporate_id);
        EntitiesList =new ArrayList<>();

        Log.d("Add Entity Fragment", getActivity().getClass().getSimpleName());
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_to_refresh);
        enitiesAdapter = new EntitiesAdapter(getActivity(), EntitiesList,authorization,userId);
        //EntitiesAdapter.notifyDataSetChanged();
        recyclerView = (RecyclerView) view.findViewById(R.id.entity_recycler_view);
        connectionErrorLayout=(LinearLayout) view.findViewById(R.id.error_empty_layout);
        connectionErrorTextView=(TextView)view.findViewById(R.id.no_booking_connection_error);
        swipeRefreshLayout.setRefreshing(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(enitiesAdapter);
        fab=(FloatingActionButton)view.findViewById(R.id.fab111);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getContext(), AddEntityActvity.class);
                Bundle bundle = new Bundle();
                bundle.putString("EntityTask", "AddEntity");
                intent.putExtras(bundle);
                getContext().startActivity(intent);
            }
        });


        entityViewModel.getEntitiesLiveData(authorization,usertype,corporate_id).observe(this, new Observer<List<Entities>>() {
            @Override
            public void onChanged(List<Entities> entitiesList) {

                if (swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }
                else if (entitiesList!=null && entitiesList.size()==0){
                    connectionErrorLayout.setVisibility(View.VISIBLE);
                    connectionErrorTextView.setText("No Entity Available");
                    EntitiesList.clear();

                }
                if (entitiesList!=null && entitiesList.size()>0){
                    connectionErrorLayout.setVisibility(View.GONE);
                    EntitiesList.clear();
                    EntitiesList.addAll(entitiesList);

                }

                enitiesAdapter.notifyDataSetChanged();
        }
    });

        entityViewModel.getEntitiesConnectionError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d("Entities Error",s);
                if (swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }
                if (s.equals("No Entity Available")) {
                    Snackbar.make(view, s, Snackbar.LENGTH_LONG).show();
                    connectionErrorTextView.setText(s);
                    connectionErrorLayout.setVisibility(View.VISIBLE);
                }
                else {
                    if (EntitiesList.size()==0){
                        connectionErrorTextView.setText(s);
                        connectionErrorLayout.setVisibility(View.VISIBLE);
                    }
                    Snackbar.make(view, s, Snackbar.LENGTH_LONG).setAction("try again", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            swipeRefreshLayout.setRefreshing(true);
                            entityViewModel.getEntitiesLiveData(authorization,usertype,corporate_id);
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
        entityViewModel.refreshEntities(authorization,usertype,corporate_id);
    }

    @Override
    public void onResume() {
        super.onResume();
        // swipeRefreshLayout.setRefreshing(true);
        entityViewModel.refreshEntities(authorization,usertype,corporate_id);
    }
}
