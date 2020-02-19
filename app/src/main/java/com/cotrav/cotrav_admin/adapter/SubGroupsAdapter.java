package com.cotrav.cotrav_admin.adapter;

import android.app.Dialog;
import android.content.Intent;
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
import com.cotrav.cotrav_admin.model.entities_model.DeleteEntityResponse;
import com.cotrav.cotrav_admin.model.subgroup_model.Subgroup;
import com.cotrav.cotrav_admin.retrofit.ConfigRetrofit;
import com.cotrav.cotrav_admin.retrofit.DeleteUtilitiesApi;
import com.cotrav.cotrav_admin.ui.utilities.groups.AddGroupsActivity;
import com.cotrav.cotrav_admin.ui.utilities.subgroups.AddSubGroupFragment;
import com.cotrav.cotrav_admin.ui.utilities.subgroups.AddSubGroupsActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubGroupsAdapter extends RecyclerView.Adapter<SubGroupsAdapter.MyViewHolder> {
    private FragmentActivity activity;
    private List<Subgroup> subgroupList;
    String userId;
    String authorization="";
    private DeleteUtilitiesApi deleteUtilitiesApi;
    AddGroupsActivity addGroupsActvity;



    public SubGroupsAdapter(FragmentActivity activity, List<Subgroup> subgroupList,String authorization, String userId) {
        this.activity = activity;
        this.subgroupList = subgroupList;
        this.userId=userId;
        this.authorization=authorization;
        deleteUtilitiesApi= ConfigRetrofit.configRetrofit(DeleteUtilitiesApi.class);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.show_subgroup_item, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textZoneName.setText(subgroupList.get(position).getGroupName());

    }

    @Override
    public int getItemCount() {
        return subgroupList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
      TextView textZoneName;
        LinearLayout entity_more;
        ImageView show_details,edit_entity,delete_entity;
        String entityId;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textZoneName=itemView.findViewById(R.id.tv_billingCity);
            show_details=itemView.findViewById(R.id.btn_showDetails);
            entity_more=itemView.findViewById(R.id.entity_more);
            show_details.setOnClickListener(this);
            delete_entity=itemView.findViewById(R.id.btn_deleteEntity);
            delete_entity.setOnClickListener(this);
            edit_entity=itemView.findViewById(R.id.btn_editEntity);
            edit_entity.setOnClickListener(this);
            //hide_details=itemView.findViewById()*/
        }

        @Override
        public void onClick(View view)
        {
            int id=view.getId();
            final int position=getAdapterPosition();
            entityId= subgroupList.get(position).getId().toString();
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

                case R.id.btn_editEntity:
                    Toast.makeText(activity,"Edit Function will be Called "+entityId,Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(activity, AddSubGroupsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("EntityTask", "UpdateEntity");
                    bundle.putString("EntityId", entityId);
                    bundle.putString("details", GsonStringConvertor.gsonToString(subgroupList.get(position)));
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
                    mDialogmsg.setText("Proceed To Delete Entity");

                    TextView mDialogtitle = dialog.findViewById(R.id.dialog_title);
                    mDialogtitle.setText("Delete Entity");

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
                            deleteSubGroups();
                            dialog.dismiss();
                        }
                    });

                    dialog.show();

                    break;
            }
        }

        private void deleteSubGroups()
        {

                    deleteUtilitiesApi.deleteSubGroups(authorization,"1",userId,entityId).enqueue(new Callback<DeleteEntityResponse>() {
                        @Override
                        public void onResponse(Call<DeleteEntityResponse> call, Response<DeleteEntityResponse> response) {
                            if (response.code() == 200)
                            {
                                Toast.makeText(activity, "Entity Cancelled :" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                final Dialog dialog = new Dialog(activity);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setCancelable(false);
                                dialog.setContentView(R.layout.custom_dialog_box);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                                TextView mDialogTitile = dialog.findViewById(R.id.dialog_title);
                                TextView mDialogmsg = dialog.findViewById(R.id.dialog_message);

                                mDialogTitile.setText("Cancel Entity Status");
                                mDialogmsg.setText("" + response.body().getMessage());

                                Button myes = dialog.findViewById(R.id.yes_txt);
                                Button mNo = dialog.findViewById(R.id.no_txt);
                                mNo.setVisibility(View.GONE);
                                myes.setText("  Ok  ");
                                myes.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                                new AddSubGroupFragment()).commit();
                                        dialog.dismiss();
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
