package com.cotrav.cotrav_admin.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.cotrav.cotrav_admin.ui.home.hotel.hotel_fragments.CancelledHotelFragment;
import com.cotrav.cotrav_admin.ui.home.hotel.hotel_fragments.CompletedHotelFragment;
import com.cotrav.cotrav_admin.ui.home.hotel.hotel_fragments.TodaysHotelFragment;
import com.cotrav.cotrav_admin.ui.home.hotel.hotel_fragments.UpcommingHotelFragment;

public class HotelTabAdapter extends FragmentPagerAdapter {
    private FragmentManager fragmentManager;
    private int tabCount;


    public HotelTabAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.fragmentManager = fm;
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment tab;
        switch (position){
            case 0:
                tab = new TodaysHotelFragment();
                break;
            case 1:
                tab = new UpcommingHotelFragment();
                break;
            case 2:
                tab = new CompletedHotelFragment();
                break;
            case 3:
                tab = new CancelledHotelFragment();
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
