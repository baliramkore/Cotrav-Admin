package com.cotrav.cotrav_admin.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.cotrav.cotrav_admin.ui.home.flight.flight_fragments.CancelledFlightFragment;
import com.cotrav.cotrav_admin.ui.home.flight.flight_fragments.CompletedFlightFragment;
import com.cotrav.cotrav_admin.ui.home.flight.flight_fragments.TodaysFlightFragmet;
import com.cotrav.cotrav_admin.ui.home.flight.flight_fragments.UpcommingFlightFragment;

public class FlightTabAdapter extends FragmentPagerAdapter {
    private FragmentManager fragmentManager;
    private int tabCount;


    public FlightTabAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.fragmentManager = fm;
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment tab;
        switch (position){
            case 0:
                tab = new TodaysFlightFragmet();
                break;
            case 1:
                tab = new UpcommingFlightFragment();
                break;
            case 2:
                tab = new CompletedFlightFragment();
                break;
            case 3:
                tab = new CancelledFlightFragment();
                break;
            default:
                return null;
        }
        return tab;

    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
