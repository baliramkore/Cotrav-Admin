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
import com.cotrav.cotrav_admin.model.group_model.GroupResponse;
import com.cotrav.cotrav_admin.retrofit.ConfigRetrofit;
import com.cotrav.cotrav_admin.retrofit.DeleteUtilitiesApi;
import com.cotrav.cotrav_admin.ui.utilities.groups.AddGroupFragment;
import com.cotrav.cotrav_admin.ui.utilities.groups.AddGroupsActivity;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.MyViewHolder>
{
    private FragmentActivity activity;
    private List<GroupResponse> groupList;
    String userId;
    String authorization="";
    private DeleteUtilitiesApi deleteUtilitiesApi;


    public GroupsAdapter(FragmentActivity activity, List<GroupResponse> groupList,String authorization, String userId) {
        this.activity = activity;
        this.groupList = groupList;
        this.userId=userId;
        this.authorization=authorization;
        deleteUtilitiesApi= ConfigRetrofit.configRetrofit(DeleteUtilitiesApi.class);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.show_group_item, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        holder.textEntityName.setText(groupList.get(position).getGroupName().toString());
        holder.textBiilingCity.setText(groupList.get(position).getZoneName());
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textBiilingCity,textGSTNo,textPanNo,textEntityName,textContactNo,textContactPerson,textAddress;
        LinearLayout entity_more;
        String entityId;
        ImageView show_details,edit_entity,delete_entity;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textEntityName=itemView.findViewById(R.id.tv_entityName);
            textBiilingCity=itemView.findViewById(R.id.tv_billingCity);
            textGSTNo= itemView.findViewById(R.id.tv_gstNo);
            textPanNo=itemView.findViewById(R.id.tv_panNo);
            textContactNo=itemView.findViewById(R.id.tv_contactNumber);
            textContactPerson=itemView.findViewById(R.id.tv_contactName);
            textAddress=itemView.findViewById(R.id.tv_address);
            show_details=itemView.findViewById(R.id.btn_showDetails);
            entity_more=itemView.findViewById(R.id.entity_more);
            show_details.setOnClickListener(this);
            delete_entity=itemView.findViewById(R.id.btn_deleteEntity);
            delete_entity.setOnClickListener(this);
            edit_entity=itemView.findViewById(R.id.btn_editEntity);
            edit_entity.setOnClickListener(this);
            //hide_details=itemView.findViewById()
        }

        @Override
        public void onClick(View view)
        {
            int id=view.getId();
            final int position=getAdapterPosition();
            entityId= groupList.get(position).getId().toString();
            switch (id) {
                case R.id.btn_showDetails:

                    break;

                case R.id.btn_editEntity:
                    Toast.makeText(activity,"Edit Function will be Called "+entityId,Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(activity,AddGroupsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("EntityTask", "UpdateEntity");
                    bundle.putString("GroupId", entityId);
                    bundle.putString("details", GsonStringConvertor.gsonToString(groupList.get(position)));
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
                    mDialogmsg.setText("Proceed To Delete Employee");

                    TextView mDialogtitle = dialog.findViewById(R.id.dialog_title);
                    mDialogtitle.setText("Delete Employee");

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
                            deleteGroups();
                            dialog.dismiss();
                        }
                    });

                    dialog.show();



                    break;
            }
        }

        private void deleteGroups()
        {
                                deleteUtilitiesApi.deleteGroups(authorization,"1",userId,entityId).enqueue(new Callback<DeleteEntityResponse>() {
                        @Override
                        public void onResponse(Call<DeleteEntityResponse> call, Response<DeleteEntityResponse> response) {
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
                                                new AddGroupFragment()).commit();
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
