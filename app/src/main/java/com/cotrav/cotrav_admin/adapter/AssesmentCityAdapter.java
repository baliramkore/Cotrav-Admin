package com.cotrav.cotrav_admin.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.cotrav.cotrav_admin.GsonStringConvertor;
import com.cotrav.cotrav_admin.R;
import com.cotrav.cotrav_admin.model.assesmentcity_model.AssesmentCities;
import com.cotrav.cotrav_admin.model.assesmentcity_model.DeleteAssementCityResponse;
import com.cotrav.cotrav_admin.retrofit.ConfigRetrofit;
import com.cotrav.cotrav_admin.retrofit.DeleteUtilitiesApi;
import com.cotrav.cotrav_admin.ui.utilities.asscity.AddAssesmentCityActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AssesmentCityAdapter extends RecyclerView.Adapter<AssesmentCityAdapter.MyViewHolder>
{
    private final String userId;
    private final String authorization;
    private FragmentActivity activity;
    private List<AssesmentCities> cityList;
    SharedPreferences loginpref;
    Context context;
    private DeleteUtilitiesApi deleteUtilitiesApi;
    public AssesmentCityAdapter(FragmentActivity activity, List<AssesmentCities> cityList, String authorization,String userId)
    {
        this.activity = activity;
        this.cityList = cityList;
        this.userId=userId;
        this.authorization=authorization;
        deleteUtilitiesApi= ConfigRetrofit.configRetrofit(DeleteUtilitiesApi.class);


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.show_city_item, null);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        //holder.textOperatorName.setText(cityList.get(position).getCorporateName().toString());
        holder.textCityName.setText(cityList.get(position).getCityName().toString());

    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textOperatorName,textPackage,textCityName,textTaxiType,textTourType,textBaseRate,textKmIncluded,textNightRates;
        LinearLayout entity_more;
        ImageView show_details,edit_entity,delete_entity;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //textOperatorName= itemView.findViewById(R.id.tv_companyName);
            textCityName=itemView.findViewById(R.id.tv_cityName);
            show_details=itemView.findViewById(R.id.btn_showDetails);
            entity_more=itemView.findViewById(R.id.entity_more);
            show_details.setOnClickListener(this);
            //hide_details=itemView.findViewById()
            delete_entity=itemView.findViewById(R.id.btn_deleteEntity);
            delete_entity.setOnClickListener(this);
            edit_entity=itemView.findViewById(R.id.btn_editEntity);
            edit_entity.setOnClickListener(this);

        }



        @Override
        public void onClick(View view)
        {
            int id=view.getId();
            final int position=getAdapterPosition();
            String entityId= cityList.get(position).getId().toString();

            switch (id) {
                case R.id.btn_showDetails:
                    Toast.makeText(activity,"Details Button Clicked "+ position,Toast.LENGTH_LONG).show();

                    break;
                case R.id.btn_editEntity:
                    Toast.makeText(activity,"Edit Function will be Called "+entityId,Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(activity, AddAssesmentCityActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("EntityTask", "UpdateEntity");
                    bundle.putString("EntityId", entityId);
                    bundle.putString("details", GsonStringConvertor.gsonToString(cityList.get(position)));

                    //intent.putArrayListExtra("employee_id",entitiesList);
                    intent.putExtras(bundle);
                    activity.startActivity(intent);
                    break;
                case R.id.btn_deleteEntity:
                    Toast.makeText(activity,"Clicked Deleted Id :"+entityId,Toast.LENGTH_LONG).show();
                    deleteUtilitiesApi.deleteAssesmentCity(authorization,"1",userId,entityId).enqueue(new Callback<DeleteAssementCityResponse>() {
                        @Override
                        public void onResponse(Call<DeleteAssementCityResponse> call, Response<DeleteAssementCityResponse> response)
                        {
                            Toast.makeText(activity,"Response "+response.body().getMessage().toString(),Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Call<DeleteAssementCityResponse> call, Throwable t) {

                            Toast.makeText(activity,"Response "+t.toString(),Toast.LENGTH_LONG).show();

                        }
                    });
                    break;
            }

        }
    }
}
