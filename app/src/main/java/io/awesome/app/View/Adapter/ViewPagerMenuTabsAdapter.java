package io.awesome.app.View.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import io.awesome.app.View.Fragment.ChooseMenu.FragmentChooseMenu;
import io.awesome.app.View.Fragment.Receipt.FragmentReceipt;


public class ViewPagerMenuTabsAdapter extends FragmentPagerAdapter {


    public ViewPagerMenuTabsAdapter(FragmentManager fm) {
        super(fm);
    }



    @Override
    public Fragment getItem(int position) {
        if (position ==0) {
            return new FragmentChooseMenu();
        } else if (position == 1) {
            return new FragmentReceipt();
        }
        return null;
    }


    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public int getItemPosition(Object object) {

        if (object instanceof FragmentChooseMenu) {
            return POSITION_UNCHANGED; // don't force a reload
            //
        } else {
            // POSITION_NONE means something like: this fragment is no longer valid
            // triggering the ViewPager to re-build the instance of this fragment.
            return POSITION_NONE;
        }
    }


}
