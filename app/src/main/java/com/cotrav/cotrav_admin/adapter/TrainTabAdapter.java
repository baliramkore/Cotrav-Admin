package com.cotrav.cotrav_admin.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.cotrav.cotrav_admin.ui.home.train.train_fragments.CancelledTrainFragment;
import com.cotrav.cotrav_admin.ui.home.train.train_fragments.CompletedTrainFragment;
import com.cotrav.cotrav_admin.ui.home.train.train_fragments.TodaysTrainFragment;
import com.cotrav.cotrav_admin.ui.home.train.train_fragments.UpcommingTrainFragment;

public class TrainTabAdapter extends FragmentPagerAdapter {
    private FragmentManager fragmentManager;
    private int tabCount;


    public TrainTabAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.fragmentManager = fm;
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment tab;
        switch (position){
            case 0:
                tab = new TodaysTrainFragment();
                break;
            case 1:
                tab = new UpcommingTrainFragment();
                break;
            case 2:
                tab = new CompletedTrainFragment();
                break;
            case 3:
                tab = new CancelledTrainFragment();
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
