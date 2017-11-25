package info.devexchanges.navvp.View.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import info.devexchanges.navvp.View.Fragment.FragmentChooseMenu;
import info.devexchanges.navvp.View.Fragment.FragmentReceipt;

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
}
