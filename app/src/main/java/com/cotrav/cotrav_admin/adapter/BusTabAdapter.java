package com.cotrav.cotrav_admin.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.cotrav.cotrav_admin.ui.home.bus.bus_fragments.CancelledBusFragment;
import com.cotrav.cotrav_admin.ui.home.bus.bus_fragments.CompletedBusFragment;
import com.cotrav.cotrav_admin.ui.home.bus.bus_fragments.TodaysBusFragment;
import com.cotrav.cotrav_admin.ui.home.bus.bus_fragments.UpcommingBusFragment;

public class BusTabAdapter extends FragmentPagerAdapter {
    private FragmentManager fragmentManager;
    private int tabCount;


    public BusTabAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.fragmentManager = fm;
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment tab;
        switch (position){
            case 0:
                tab = new TodaysBusFragment();
                break;
            case 1:
                tab = new UpcommingBusFragment();
                break;
            case 2:
                tab = new CompletedBusFragment();
                break;
            case 3:
                tab = new CancelledBusFragment();
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
