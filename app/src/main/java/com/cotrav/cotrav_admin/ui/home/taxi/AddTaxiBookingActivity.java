package com.cotrav.cotrav_admin.ui.home.taxi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.cotrav.cotrav_admin.R;
import com.cotrav.cotrav_admin.adapter.ViewPagerAdapter;
import com.cotrav.cotrav_admin.ui.home.taxi.addTaxiFragment.FragmentLocalTaxi;
import com.cotrav.cotrav_admin.ui.home.taxi.addTaxiFragment.FragmentOutstationTaxi;
import com.cotrav.cotrav_admin.ui.home.taxi.addTaxiFragment.FragmentRadioTaxi;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import es.dmoral.toasty.Toasty;

public class AddTaxiBookingActivity extends AppCompatActivity {

    private Toolbar toolbar;
    BottomNavigationView bottomNavigationView;
    ViewPager viewPager;
    Toolbar mtoolbar;
    MenuItem prevMenuItem;
    FragmentLocalTaxi fragmentLocalTaxi;
    FragmentOutstationTaxi fragmentOutstationTaxi;
    FragmentRadioTaxi fragmentRadioTaxi;
    SharedPreferences loginpref;
    Context context =this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_taxi_booking);
        overridePendingTransition(R.anim.slidein, R.anim.slideout);
        toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setTitle("Add Taxi Booking");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar.setNavigationIcon(R.drawable.ic_back);
        }
        loginpref = getSharedPreferences("login_info", MODE_PRIVATE);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.navigation_radio:
                        if (loginpref.getString("is_radio","n").equals("1")){
                            viewPager.setCurrentItem(0);
                        }
                        else
                            Toasty.error(context, "Access Denied", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navigation_local:
                        if (loginpref.getString("is_local","n").equals("1")) {
                            viewPager.setCurrentItem(1);
                        }
                        else
                            Toasty.error(context, "Access Denied", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navigation_outstation:
                        if (loginpref.getString("is_outstation","n").equals("1")){
                            viewPager.setCurrentItem(2);
                        }
                        else
                            Toasty.error(context, "Access Denied", Toast.LENGTH_SHORT).show();
                        break;
                }

                return false;
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: " + position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);

            }


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        setupViewPager(viewPager);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        fragmentRadioTaxi = new FragmentRadioTaxi();
        fragmentLocalTaxi = new FragmentLocalTaxi();
        fragmentOutstationTaxi = new FragmentOutstationTaxi();
        viewPagerAdapter.addFragment(fragmentRadioTaxi);
        viewPagerAdapter.addFragment(fragmentLocalTaxi);

        viewPagerAdapter.addFragment(fragmentOutstationTaxi);
        viewPager.setAdapter(viewPagerAdapter);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void onBackPressed() {
        super.onBackPressed();
    }
}
