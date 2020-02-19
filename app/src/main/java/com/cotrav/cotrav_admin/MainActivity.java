package com.cotrav.cotrav_admin;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.cotrav.cotrav_admin.asynctask.LogOutAsyncTask;
import com.cotrav.cotrav_admin.ui.home.HomeFragment;
import com.cotrav.cotrav_admin.ui.profile.ProfileFragment;
import com.cotrav.cotrav_admin.ui.support.SupportFragment;
import com.cotrav.cotrav_admin.ui.utilities.ManagementFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    NavigationView navigationView;DrawerLayout drawer;
    private AppBarConfiguration mAppBarConfiguration;
    SharedPreferences loginpref;
    String token,adminName,adminEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginpref = getSharedPreferences("login_info", MODE_PRIVATE);
        token = loginpref.getString("access_token", "n");
        adminName=loginpref.getString("user_name","n");
        adminEmail=loginpref.getString("email","n");

        overridePendingTransition(R.anim.slidein, R.anim.slideout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimaryDark));
        toolbar.setTitle("CoTrav Welcomes You");
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle;
        toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        toggle.setDrawerIndicatorEnabled(false);

        toggle.setHomeAsUpIndicator(R.drawable.ic_menu);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerVisible(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });
/*
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
        *//*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*//*
       TextView name = (TextView)header.findViewById(R.id.text_admin_name);
       TextView email = (TextView)header.findViewById(R.id.text_admin_email);
        name.setText("chnaged");
        email.setText("changed");*/
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                    new HomeFragment()).commit();


        }

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        View header=navigationView.getHeaderView(0);
        /*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*/
        TextView name = (TextView)header.findViewById(R.id.text_admin_name);
        TextView email = (TextView)header.findViewById(R.id.text_admin_email);
        name.setText(adminName);
        email.setText(adminEmail);

    }


    @Override
    public boolean onSupportNavigateUp()
    {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.nav_home) {

            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                    new HomeFragment()).commit();

        }
        if (id == R.id.nav_gallery) {

            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                    new ProfileFragment()).commit();

        }

        if (id == R.id.nav_slideshow) {

            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                    new ManagementFragment()).commit();

        }
        if (id == R.id.nav_tools) {

            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                    new SupportFragment()).commit();

        }
        if (id == R.id.nav_send)
        {
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.custom_dialog_box);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            TextView mDialogmsg = dialog.findViewById(R.id.dialog_message);
            mDialogmsg.setText("Are you sure you want to Logout ?");

            TextView mDialogtitle = dialog.findViewById(R.id.dialog_title);
            mDialogtitle.setText("Logout");

            Button myes = dialog.findViewById(R.id.yes_txt);
            Button mNo = dialog.findViewById(R.id.no_txt);

            mNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            myes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new LogOutAsyncTask(MainActivity.this, token).execute(MainActivity.this);
                    dialog.dismiss();
                }
            });

            dialog.show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private static long back_pressed;
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (back_pressed + 1000 > System.currentTimeMillis()) {
                super.onBackPressed();
                finishAffinity();
                finish();
            } else {

                if (navigationView.getMenu().getItem(0).isChecked()) {
                    Toast.makeText(getBaseContext(), "Press once again to exit",
                            Toast.LENGTH_SHORT).show();

                } else {
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                            new HomeFragment()).commit();
                    navigationView.getMenu().getItem(0).setChecked(true);
                }

                //fabAddBookingMenu.close(true);
                back_pressed = System.currentTimeMillis();
            }
        }
    }

}
