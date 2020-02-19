package com.cotrav.cotrav_admin.ui.utilities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.cotrav.cotrav_admin.MasterActivity;
import com.cotrav.cotrav_admin.R;

public class ManagementFragment extends Fragment  {

    private ManagementViewModel managementViewModel;
    Context context;
    CardView addEntity,addRates,addAssCity,addAssCode,addGroup,addSubGroup,addAdmin,addSpoc,addEmployees;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        managementViewModel = ViewModelProviders.of(this).get(ManagementViewModel.class);
        View root = inflater.inflate(R.layout.fragment_management, container, false);
        addEntity=root.findViewById(R.id.cardEntity);
        addRates=root.findViewById(R.id.cardRates);
        addAssCity=root.findViewById(R.id.cardAssCity);
        addAssCode=root.findViewById(R.id.cardAssCode);
        addGroup=root.findViewById(R.id.cardGroup);
        addSubGroup=root.findViewById(R.id.cardSubGroup);
        addAdmin=root.findViewById(R.id.cardAdmin);
        addSpoc=root.findViewById(R.id.cardSpoc);
        addEmployees=root.findViewById(R.id.cardEmployees);

        addEntity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(),"Add Entity Clicked",Toast.LENGTH_LONG).show();
                //startActivity(new Intent(getActivity(), MasterActivity.class));
                Intent mIntent = new Intent(getActivity(), MasterActivity.class);
                Bundle mBundle = new Bundle();
                mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mBundle.putString("Value","Entity" );
                mIntent.putExtras(mBundle);
                startActivity(mIntent);
            }
        });
        addRates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(),"Add Rates Clicked",Toast.LENGTH_LONG).show();
                Intent mIntent = new Intent(getActivity(), MasterActivity.class);
                Bundle mBundle = new Bundle();
                mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mBundle.putString("Value","Rates" );
                mIntent.putExtras(mBundle);
                startActivity(mIntent);
            }
        });

        addAssCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(),"Add addAssCity Clicked",Toast.LENGTH_LONG).show();
                Intent mIntent = new Intent(getActivity(), MasterActivity.class);
                Bundle mBundle = new Bundle();
                mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mBundle.putString("Value","addAssCity" );
                mIntent.putExtras(mBundle);
                startActivity(mIntent);
            }
        });

        addAssCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(),"Add addAssCode Clicked",Toast.LENGTH_LONG).show();
                Intent mIntent = new Intent(getActivity(), MasterActivity.class);
                Bundle mBundle = new Bundle();
                mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mBundle.putString("Value","addAssCode" );
                mIntent.putExtras(mBundle);
                startActivity(mIntent);
            }
        });
        addGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(),"Add addGroup Clicked",Toast.LENGTH_LONG).show();
                Intent mIntent = new Intent(getActivity(), MasterActivity.class);
                Bundle mBundle = new Bundle();
                mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mBundle.putString("Value","addGroup" );
                mIntent.putExtras(mBundle);
                startActivity(mIntent);
            }
        });

        addSubGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(),"Add addSubGroup Clicked",Toast.LENGTH_LONG).show();

                Intent mIntent = new Intent(getActivity(), MasterActivity.class);
                Bundle mBundle = new Bundle();
                mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mBundle.putString("Value","addSubGroup" );
                mIntent.putExtras(mBundle);
                startActivity(mIntent);
            }
        });
        addAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(),"Add addAdmin Clicked",Toast.LENGTH_LONG).show();
                Intent mIntent = new Intent(getActivity(), MasterActivity.class);
                Bundle mBundle = new Bundle();
                mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mBundle.putString("Value","addAdmin" );
                mIntent.putExtras(mBundle);
                startActivity(mIntent);
            }
        });
        addSpoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(),"Add addSpoc Clicked",Toast.LENGTH_LONG).show();
                Intent mIntent = new Intent(getActivity(), MasterActivity.class);
                Bundle mBundle = new Bundle();
                mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mBundle.putString("Value","addSpoc" );
                mIntent.putExtras(mBundle);
                startActivity(mIntent);
            }
        });

        addEmployees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(),"Add addEmployees Clicked",Toast.LENGTH_LONG).show();
                Intent mIntent = new Intent(getActivity(), MasterActivity.class);
                Bundle mBundle = new Bundle();
                mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mBundle.putString("Value","addEmployees" );
                mIntent.putExtras(mBundle);
                startActivity(mIntent);
            }
        });
        return root;
    }



}