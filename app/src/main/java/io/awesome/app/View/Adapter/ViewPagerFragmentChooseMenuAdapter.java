package io.awesome.app.View.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import io.awesome.app.View.Fragment.Menu.FragmentMenu;
import io.awesome.app.View.Fragment.Fragment5;
import io.awesome.app.View.Fragment.RootFragment;


public class ViewPagerFragmentChooseMenuAdapter extends FragmentPagerAdapter {

    public ViewPagerFragmentChooseMenuAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position ==0) {
            return new FragmentMenu(0,"");
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
    @Override
    public int getItemPosition(Object object) {
        // POSITION_NONE makes it possible to reload the PagerAdapter
        return POSITION_NONE;

    }
}