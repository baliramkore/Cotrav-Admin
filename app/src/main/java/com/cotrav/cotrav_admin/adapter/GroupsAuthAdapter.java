package com.cotrav.cotrav_admin.adapter;

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

import com.cotrav.cotrav_admin.R;
import com.cotrav.cotrav_admin.model.group_model.Group;
import com.cotrav.cotrav_admin.retrofit.ConfigRetrofit;
import com.cotrav.cotrav_admin.retrofit.DeleteUtilitiesApi;

import java.util.List;

public class GroupsAuthAdapter extends RecyclerView.Adapter<GroupsAuthAdapter.MyViewHolder>
{
    private FragmentActivity activity;
    private List<Group> groupAuthList;
    String userId;
    String authorization="";
    private DeleteUtilitiesApi deleteUtilitiesApi;


    public GroupsAuthAdapter(FragmentActivity activity, List<Group> groupAuthList,String authorization, String userId) {
        this.activity = activity;
        this.groupAuthList = groupAuthList;
        this.userId=userId;
        this.authorization=authorization;
        deleteUtilitiesApi= ConfigRetrofit.configRetrofit(DeleteUtilitiesApi.class);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.show_groupauth_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
     holder.authName.setText(groupAuthList.get(position).getName());
     holder.authEmail.setText(groupAuthList.get(position).getEmail());
     holder.authContact.setText(groupAuthList.get(position).getContactNo());


    }

    @Override
    public int getItemCount() {
        return groupAuthList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //TextView textCompanyName,textBiilingCity,textGSTNo,textPanNo,textEntityName,textContactNo,textContactPerson,textAddress;
        TextView authName,authEmail,authContact;
        LinearLayout entity_more;
        ImageView show_details,edit_entity,delete_entity;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            authName= itemView.findViewById(R.id.tv_authName);
            authEmail=itemView.findViewById(R.id.tv_authEmail);
            authContact=itemView.findViewById(R.id.tv_authContact);

  //          show_details=itemView.findViewById(R.id.btn_showDetails);
            entity_more=itemView.findViewById(R.id.entity_more);
//            show_details.setOnClickListener(this);
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
            String entityId= groupAuthList.get(position).getId().toString();
            switch (id) {
                case R.id.btn_editEntity:
                    Toast.makeText(activity,"Edit Function will be Called "+entityId,Toast.LENGTH_LONG).show();
                    break;
                case R.id.btn_deleteEntity:

                    Toast.makeText(activity,"Clicked Deleted Id :"+entityId,Toast.LENGTH_LONG).show();

                    break;
            }
        }
    }
}
