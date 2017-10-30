package info.devexchanges.navvp.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import info.devexchanges.navvp.Fragment.FragmentFavoriteOrMenuOfCategory;
import info.devexchanges.navvp.Fragment.FragmentCategory;
import info.devexchanges.navvp.Fragment.Fragment5;
import info.devexchanges.navvp.Fragment.RootFragment;


public class ViewPagerAdapterFragmentMenu extends FragmentPagerAdapter {

    public ViewPagerAdapterFragmentMenu(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position ==0) {
            return new FragmentFavoriteOrMenuOfCategory();
        } else if (position == 1) {
            return new RootFragment();
        }else if (position == 2)
            return new Fragment5();
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
