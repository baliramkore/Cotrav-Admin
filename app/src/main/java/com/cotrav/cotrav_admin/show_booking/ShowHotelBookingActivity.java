package com.cotrav.cotrav_admin.show_booking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LifecycleRegistry;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.cotrav.cotrav_admin.R;
import com.cotrav.cotrav_admin.adapter.HotelTabAdapter;
import com.cotrav.cotrav_admin.ui.home.hotel.AddHotelBookingActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class ShowHotelBookingActivity extends AppCompatActivity {
    LifecycleRegistry lifecycleRegistry;
    private HotelTabAdapter hotelTabAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar mtoolbar;
    private FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pager_fragment);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout_sample);
        lifecycleRegistry = new LifecycleRegistry(this);
        mtoolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setTitle("Hotel Booking");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            mtoolbar.setNavigationIcon(R.drawable.ic_back);
        }
        fab=(FloatingActionButton)findViewById(R.id.fab111);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ShowHotelBookingActivity.this, AddHotelBookingActivity.class);
                startActivity(intent);

            }
        });

        mtoolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
        tabLayout.addTab(tabLayout.newTab().setText("Todays"));
        tabLayout.addTab(tabLayout.newTab().setText("Upcomming"));
        tabLayout.addTab(tabLayout.newTab().setText("Past"));
        tabLayout.addTab(tabLayout.newTab().setText("Cancelled"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) findViewById(R.id.container);
        hotelTabAdapter = new HotelTabAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(hotelTabAdapter);
        viewPager.setOffscreenPageLimit(tabLayout.getTabCount());
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
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
