package com.cotrav.cotrav_admin.ui.profile;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cotrav.cotrav_admin.R;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {

    TextView adminName,adminId,companyName,adminContact,adminEmail;
    private ProfileViewModel profileViewModel;
    SharedPreferences loginpref;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        loginpref = getContext().getSharedPreferences("login_info", MODE_PRIVATE);
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        adminName=root.findViewById(R.id.txt_admin_name);
        adminId=root.findViewById(R.id.txt_admin_id);
        companyName=root.findViewById(R.id.txt_comapany_name);
        adminContact=root.findViewById(R.id.txt_admin_contact);
        adminEmail=root.findViewById(R.id.txt_admin_email);

        adminName.setText(loginpref.getString("user_name", "n"));
        adminId.setText(loginpref.getString("admin_id", "n"));
        companyName.setText(loginpref.getString("user_name", "n"));
        adminContact.setText(loginpref.getString("user_contact", "n"));
        adminEmail.setText(loginpref.getString("email", "n"));

        return root;
    }
}