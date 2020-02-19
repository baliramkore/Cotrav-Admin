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
import com.cotrav.cotrav_admin.model.employees_model.Employee;
import com.cotrav.cotrav_admin.model.entities_model.DeleteEntityResponse;
import com.cotrav.cotrav_admin.retrofit.ConfigRetrofit;
import com.cotrav.cotrav_admin.retrofit.DeleteUtilitiesApi;
import com.cotrav.cotrav_admin.ui.utilities.employees.AddEmployeeFragment;
import com.cotrav.cotrav_admin.ui.utilities.employees.AddEmployeesActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeesAdapter extends RecyclerView.Adapter<EmployeesAdapter.MyViewHolder>{
    private FragmentActivity activity;
    private List<Employee> employeeList;
    SharedPreferences loginpref;
    Context context;
    String userId;
    String authorization="";

    private DeleteUtilitiesApi deleteUtilitiesApi;
    public EmployeesAdapter(FragmentActivity activity, List<Employee> employeeList, String authorization,String userId)
    {
        this.activity = activity;
        this.employeeList = employeeList;
        this.userId=userId;
        this.authorization=authorization;
        deleteUtilitiesApi= ConfigRetrofit.configRetrofit(DeleteUtilitiesApi.class);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.show_employee_item, null);
        return new MyViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        //holder.textOperatorName.setText(employeeList.get(position).getCorporateName().toString());
        holder.textAdminName.setText(employeeList.get(position).getEmployeeName());
        holder.textAdminEmail.setText(employeeList.get(position).getEmployeeEmail().toString());
        holder.textAdminContact.setText(employeeList.get(position).getEmployeeContact().toString());

    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textOperatorName,textAdminName,textAdminEmail,textAdminContact;
        LinearLayout entity_more;
        String entityId;
        ImageView show_details,edit_entity,delete_entity;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textAdminName=itemView.findViewById(R.id.tv_adminName);
            textAdminEmail=itemView.findViewById(R.id.tv_adminEmail);
            textAdminContact=itemView.findViewById(R.id.tv_adminContact);
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
            entityId= employeeList.get(position).getId().toString();

            switch (id) {
                case R.id.btn_showDetails:
                    Toast.makeText(activity,"Details Button Clicked "+ position,Toast.LENGTH_LONG).show();

                    break;
                case R.id.btn_editEntity:
                    Toast.makeText(activity,"Edit Function will be Called "+entityId,Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(activity, AddEmployeesActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("EntityTask", "UpdateEntity");
                    bundle.putString("EntityId", entityId);
                    bundle.putString("details", GsonStringConvertor.gsonToString(employeeList.get(position)));
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
                            deleteEmployee();
                            dialog.dismiss();
                        }
                    });

                    dialog.show();


                    break;
            }

        }

        private void deleteEmployee()
        {
                        deleteUtilitiesApi.deleteEmployees(authorization,"1",entityId,userId).enqueue(new Callback<DeleteEntityResponse>() {
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
                                                new AddEmployeeFragment()).commit();
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
