package info.devexchanges.navvp.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import info.devexchanges.navvp.Fragment.FragmentMenu;
import info.devexchanges.navvp.Fragment.FragmentReceipt;

public class ViewPagerAdapterMenuTabs extends FragmentPagerAdapter {

    public ViewPagerAdapterMenuTabs(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position ==0) {
            return new FragmentMenu();
        } else if (position == 1) {
            return new FragmentReceipt();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
