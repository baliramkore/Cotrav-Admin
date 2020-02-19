package com.cotrav.cotrav_admin.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.cotrav.cotrav_admin.GsonStringConvertor;
import com.cotrav.cotrav_admin.R;
import com.cotrav.cotrav_admin.model.assesmentcode_model.AssesmentCodes;
import com.cotrav.cotrav_admin.model.entities_model.DeleteEntityResponse;
import com.cotrav.cotrav_admin.retrofit.ConfigRetrofit;
import com.cotrav.cotrav_admin.retrofit.DeleteUtilitiesApi;
import com.cotrav.cotrav_admin.ui.utilities.addcode.AddAssCodeFragment;
import com.cotrav.cotrav_admin.ui.utilities.addcode.AddAssesmentCodeActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssesmentCodeAdapter extends RecyclerView.Adapter<AssesmentCodeAdapter.MyViewHolder>
{
    private final String userId;
    private final String authorization;
    private FragmentActivity activity;
    private List<AssesmentCodes> codeList;
    SharedPreferences loginpref;
    Context context;
    private DeleteUtilitiesApi deleteUtilitiesApi;
    public AssesmentCodeAdapter(FragmentActivity activity, List<AssesmentCodes> codeList, String authorization,String userId)
    {
        this.activity = activity;
        this.codeList = codeList;
        this.userId=userId;
        this.authorization=authorization;
        deleteUtilitiesApi= ConfigRetrofit.configRetrofit(DeleteUtilitiesApi.class);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.show_assesmentcode_item, null);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        //holder.textOperatorName.setText(codeList.get(position).getCorporateName().toString());
        holder.textAssCode.setText(codeList.get(position).getAssessmentCode().toString());
        holder.textAssCodeDesc.setText(codeList.get(position).getCodeDesc());
        holder.textActiveFrom.setText(codeList.get(position).getFromDate().toString());
        holder.textActiveTo.setText(codeList.get(position).getToDate().toString());
        holder.textServiceFrom.setText(codeList.get(position).getServiceFrom().toString());
        holder.textServiceTo.setText(codeList.get(position).getServiceTo().toString());
    }

    @Override
    public int getItemCount() {
        return codeList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textAssCodeDesc,textAssCode,textActiveFrom,textActiveTo,textServiceFrom,textServiceTo;
        LinearLayout entity_more;
        ImageView show_details,edit_entity,delete_entity;
        String entityId;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
          //  textOperatorName= itemView.findViewById(R.id.tv_companyName);

            textAssCode=itemView.findViewById(R.id.tv_assCode);
            textAssCodeDesc=itemView.findViewById(R.id.tv_assDesc);
            textActiveFrom=itemView.findViewById(R.id.tv_activeFrom);
            textActiveTo=itemView.findViewById(R.id.tv_activeTo);
            textServiceFrom=itemView.findViewById(R.id.tv_serviceFrom);
            textServiceTo=itemView.findViewById(R.id.tv_serviceTo);
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
            entityId= codeList.get(position).getId().toString();

            switch (id) {
                case R.id.btn_showDetails:
                    Toast.makeText(activity,"Details Button Clicked "+ position,Toast.LENGTH_LONG).show();

                    break;
                case R.id.btn_editEntity:
                    Toast.makeText(activity,"Edit Function will be Called "+entityId,Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(activity, AddAssesmentCodeActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("EntityTask","UpdateEntity");
                    bundle.putString("EntityId", entityId);
                    bundle.putString("details", GsonStringConvertor.gsonToString(codeList.get(position)));
                    //intent.putArrayListExtra("employee_id",entitiesList);
                    intent.putExtras(bundle);
                    activity.startActivity(intent);
                    break;
                case R.id.btn_deleteEntity:


                    final Dialog dialog = new Dialog(activity);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.custom_dialog_box);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                    TextView mDialogmsg = dialog.findViewById(R.id.dialog_message);
                    mDialogmsg.setText("Proceed To Delete Ass.Code");

                    TextView mDialogtitle = dialog.findViewById(R.id.dialog_title);
                    mDialogtitle.setText("Delete Ass.Code");

                    Button myes = dialog.findViewById(R.id.yes_txt);
                    myes.setText("Okey");
                    Button mNo = dialog.findViewById(R.id.no_txt);
                    mNo.setText("No");
                    mNo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    myes.setText("Yes");
                    myes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            deleteAssesmentCode();
                            dialog.dismiss();
                        }
                    });

                    dialog.show();

                    break;
            }

        }

        private void deleteAssesmentCode()
        {
                    //Toast.makeText(activity,"Clicked Deleted Id :"+entityId,Toast.LENGTH_LONG).show();
                    deleteUtilitiesApi.deleteAssesmentCode(authorization,"1",entityId,userId).enqueue(new Callback<DeleteEntityResponse>() {
                        @Override
                        public void onResponse(Call<DeleteEntityResponse> call, Response<DeleteEntityResponse> response)
                        {

                            if (response.code() == 200)
                            {

                                final Dialog dialog = new Dialog(activity);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setCancelable(false);
                                dialog.setContentView(R.layout.custom_dialog_box);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                                TextView mDialogTitile = dialog.findViewById(R.id.dialog_title);
                                TextView mDialogmsg = dialog.findViewById(R.id.dialog_message);

                                mDialogTitile.setText("Cancel Status");
                                mDialogmsg.setText("" + response.body().getMessage());

                                Button myes = dialog.findViewById(R.id.yes_txt);
                                Button mNo = dialog.findViewById(R.id.no_txt);
                                mNo.setVisibility(View.GONE);
                                myes.setText("  Ok  ");
                                myes.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                                new AddAssCodeFragment()).commit();
                                        dialog.dismiss();
                                    }
                                });


                                dialog.show();

                            }

                        }

                        @Override
                        public void onFailure(Call<DeleteEntityResponse> call, Throwable t) {

                            Toast.makeText(activity,"Response "+t.toString(),Toast.LENGTH_LONG).show();

                        }
                    });
        }
    }

}
