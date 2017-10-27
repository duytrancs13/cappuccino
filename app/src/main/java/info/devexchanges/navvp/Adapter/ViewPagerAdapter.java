package info.devexchanges.navvp.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import info.devexchanges.navvp.Fragment.Fragment1;
import info.devexchanges.navvp.Fragment.Fragment2;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position ==0) {
            return new Fragment1();
        } else if (position == 1) {
            return new Fragment2();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
