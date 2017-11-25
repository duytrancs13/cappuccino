package info.devexchanges.navvp.View.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import info.devexchanges.navvp.View.Fragment.Menu.FragmentMenu;
import info.devexchanges.navvp.View.Fragment.Fragment5;
import info.devexchanges.navvp.View.Fragment.RootFragment;


public class ViewPagerFragmentChooseMenuAdapter extends FragmentPagerAdapter {

    public ViewPagerFragmentChooseMenuAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position ==0) {
            return new FragmentMenu();
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
