package com.cotrav.cotrav_admin.ui.utilities.addcode;


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
import com.cotrav.cotrav_admin.adapter.AssesmentCodeAdapter;
import com.cotrav.cotrav_admin.model.assesmentcode_model.AssesmentCodes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddAssCodeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private AssesmentCodeAdapter assesmentCodeAdapter;
    private AssesmentCodeViewModel codeViewModel;
    private List<AssesmentCodes> assesmentCodesList;
    private LinearLayout connectionErrorLayout;
    TextView connectionErrorTextView;
    SharedPreferences loginPref;
    String corporate_id = "";
    String token ="";
    String authorization = "";
    String usertype = "";
    String userId="";
    private FloatingActionButton fab;

    public AddAssCodeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.fragment_add_ass_code, container, false);

        codeViewModel = ViewModelProviders.of(getActivity()).get(AssesmentCodeViewModel.class);
        loginPref=getActivity().getSharedPreferences("login_info", Context.MODE_PRIVATE);
        //token ="FQSC5OV68K3WG083FMXKWOWGO2IP8Z0S87G5HBHGIKLPESSC599ZVJU9XGIC";
        token = loginPref.getString("access_token","n");
        authorization = "Token "+token;
        usertype = loginPref.getString("usertype", "n");
        corporate_id = loginPref.getString("corporate_id", "n");
        userId=loginPref.getString("user_id","n");
        codeViewModel.InitCorporateCodesViewModel(authorization,usertype,corporate_id);
        assesmentCodesList =new ArrayList<>();
        Log.d("Add AssCity Fragment", getActivity().getClass().getSimpleName());
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_to_refresh);
        assesmentCodeAdapter = new AssesmentCodeAdapter(getActivity(), assesmentCodesList,authorization,corporate_id);
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
        recyclerView.setAdapter(assesmentCodeAdapter);

        fab=(FloatingActionButton)view.findViewById(R.id.fab111);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

/*                Intent intent = new Intent(getContext(), AddAssesmentCodeActivity.class);
                startActivity(intent);*/
                Intent intent = new Intent(getContext(), AddAssesmentCodeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("EntityTask","AddEntity");
                intent.putExtras(bundle);
                getContext().startActivity(intent);

            }
        });
        codeViewModel.getCorporateCodesLiveData(authorization,usertype,corporate_id).observe(this, new Observer<List<AssesmentCodes>>() {
            @Override
            public void onChanged(List<AssesmentCodes> codeList) {

                if (swipeRefreshLayout.isRefreshing())
                {
                    swipeRefreshLayout.setRefreshing(false);
                }
                if (codeList!=null && codeList.size()>0)
                {
                    connectionErrorLayout.setVisibility(View.GONE);
                    assesmentCodesList.clear();
                    assesmentCodesList.addAll(codeList);
                    assesmentCodeAdapter.notifyDataSetChanged();
                }
                else if (codeList!=null && codeList.size()==0){
                    connectionErrorTextView.setText("No Assessment Code Available");
                    codeList.clear();
                    assesmentCodesList.clear();
                    connectionErrorLayout.setVisibility(View.VISIBLE);
                }
                if (assesmentCodesList.size()==0){
                    connectionErrorTextView.setText("No Assessment Code Available");
                    connectionErrorLayout.setVisibility(View.VISIBLE);
                    assesmentCodesList.clear();

                }


            }
        }

        );

        codeViewModel.getAssesmentCodeConnectionError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d("Assessment Code",s);
                if (swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }
                if (s.equals("No Assessment Code Available")) {
                    Snackbar.make(view, s, Snackbar.LENGTH_LONG).show();
                    connectionErrorTextView.setText(s);
                    connectionErrorLayout.setVisibility(View.VISIBLE);
                }
                else {
                    if (assesmentCodesList.size()==0){
                        connectionErrorTextView.setText(s);
                        connectionErrorLayout.setVisibility(View.VISIBLE);
                    }
                    Snackbar.make(view, s, Snackbar.LENGTH_LONG).setAction("try again", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            swipeRefreshLayout.setRefreshing(true);
                            codeViewModel.refreshAssesmentCode(authorization,usertype,corporate_id);
                        }
                    }).show();
                }
            }
        });
        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        codeViewModel.refreshAssesmentCode(authorization,usertype,corporate_id);
    }
    @Override
    public void onResume() {
        super.onResume();
        // swipeRefreshLayout.setRefreshing(true);
        codeViewModel.refreshAssesmentCode(authorization,usertype,corporate_id);
    }
}
