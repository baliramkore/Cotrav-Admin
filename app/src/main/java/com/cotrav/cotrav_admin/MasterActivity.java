package com.cotrav.cotrav_admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.cotrav.cotrav_admin.ui.utilities.addcode.AddAssCodeFragment;
import com.cotrav.cotrav_admin.ui.utilities.admins.AddAdminFragment;
import com.cotrav.cotrav_admin.ui.utilities.asscity.AddAssCityFragment;
import com.cotrav.cotrav_admin.ui.utilities.employees.AddEmployeeFragment;
import com.cotrav.cotrav_admin.ui.utilities.entities.AddEntityFragment;
import com.cotrav.cotrav_admin.ui.utilities.groups.AddGroupFragment;
import com.cotrav.cotrav_admin.ui.utilities.rates.AddRateFragment;
import com.cotrav.cotrav_admin.ui.utilities.spocs.AddSpocFragment;
import com.cotrav.cotrav_admin.ui.utilities.subgroups.AddSubGroupFragment;

public class MasterActivity extends AppCompatActivity {

    private Toolbar mtoolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);

        mtoolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);


        //Intent mIntent = new Intent(this, MasterActivity.class);
        String value = getIntent().getExtras().getString("Value");
        if (value.equals("Entity")){

            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle("Entity Section");
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                mtoolbar.setNavigationIcon(R.drawable.ic_back);
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new AddEntityFragment()).commit();

        } if (value.equals("Rates")){
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle("Rates Section");
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                mtoolbar.setNavigationIcon(R.drawable.ic_back);
            }


            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new AddRateFragment()).commit();
        } if (value.equals("addAssCity")){

            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle("Assesment City");
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                mtoolbar.setNavigationIcon(R.drawable.ic_back);
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new AddAssCityFragment()).commit();
        } if (value.equals("addAssCode")){
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle("Assesment Code");
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                mtoolbar.setNavigationIcon(R.drawable.ic_back);
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new AddAssCodeFragment()).commit();
        } if (value.equals("addGroup")){
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle("Group Section");
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                mtoolbar.setNavigationIcon(R.drawable.ic_back);            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new AddGroupFragment()).commit();
        } if (value.equals("addSubGroup")){

            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle("Subgroup Section");
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                mtoolbar.setNavigationIcon(R.drawable.ic_back);            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new AddSubGroupFragment()).commit();
        } if (value.equals("addAdmin")){
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle("Admin Section");
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                mtoolbar.setNavigationIcon(R.drawable.ic_back);
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new AddAdminFragment()).commit();
        } if (value.equals("addSpoc")){
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle("Spocs Section");
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                mtoolbar.setNavigationIcon(R.drawable.ic_back);            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new AddSpocFragment()).commit();
        }if (value.equals("addEmployees")){
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle("Employee Section");
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                mtoolbar.setNavigationIcon(R.drawable.ic_back);            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new AddEmployeeFragment()).commit();
        }

        mtoolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
