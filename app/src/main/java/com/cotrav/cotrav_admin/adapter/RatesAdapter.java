package com.cotrav.cotrav_admin.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.cotrav.cotrav_admin.R;
import com.cotrav.cotrav_admin.model.entities_model.Entities;
import com.cotrav.cotrav_admin.model.rates_model.CorporateRates;

import java.util.List;

public class RatesAdapter extends RecyclerView.Adapter<RatesAdapter.MyViewHolder> {
    private FragmentActivity activity;
    private List<CorporateRates> ratesList;
    SharedPreferences loginpref;
    Context context;
    public RatesAdapter(FragmentActivity activity, List<CorporateRates> ratesList, String addRates)
    {
        this.activity = activity;
        this.ratesList = ratesList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.show_rates_item, null);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.textPackage.setText(ratesList.get(position).getPackageName().toString());
        holder.textCityName.setText(ratesList.get(position).getCityName().toString());
        holder.textTaxiType.setText(ratesList.get(position).getTaxiType().toString());
        holder.textTourType.setText(ratesList.get(position).getTourType().toString());
        holder.textBaseRate.setText(ratesList.get(position).getBaseRate().toString());
        holder.textKmIncluded.setText(ratesList.get(position).getKmRate().toString());
        holder.textNightRates.setText(ratesList.get(position).getNightRate().toString());
    }

    @Override
    public int getItemCount() {
        return ratesList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textPackage,textCityName,textTaxiType,textTourType,textBaseRate,textKmIncluded,textNightRates;
        LinearLayout entity_more;
        ImageView show_details,edit_entity,delete_entity;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textPackage=itemView.findViewById(R.id.tv_packageName);
            textCityName=itemView.findViewById(R.id.tv_cityName);
            textTaxiType= itemView.findViewById(R.id.tv_taxiType);
            textTourType=itemView.findViewById(R.id.tv_tourType);
            textBaseRate=itemView.findViewById(R.id.tv_baseRate);
            textKmIncluded=itemView.findViewById(R.id.tv_kmsIncluded);
            textNightRates=itemView.findViewById(R.id.tv_nightRate);

            show_details=itemView.findViewById(R.id.btn_showDetails);
            entity_more=itemView.findViewById(R.id.entity_more);
            show_details.setOnClickListener(this);
            //hide_details=itemView.findViewById()


        }

        @Override
        public void onClick(View view)
        {
            int id=view.getId();
            final int position=getAdapterPosition();
            switch (id) {
                case R.id.btn_showDetails:
                    if (entity_more.getVisibility()==View.GONE)
                    {
                        entity_more.setVisibility(View.VISIBLE);

                    }
                    else
                    {
                        entity_more.setVisibility(View.GONE);
                    }


                    break;
            }

        }
    }
}
