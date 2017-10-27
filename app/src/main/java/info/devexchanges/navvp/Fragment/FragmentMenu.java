package info.devexchanges.navvp.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import info.devexchanges.navvp.Adapter.ViewPagerAdapterFragmentMenu;
import info.devexchanges.navvp.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class FragmentMenu extends Fragment {
    private TabLayout tab_layout_fragment;
    private ViewPager view_pager_fragment;
    private String[] pageTitle = {"Chọn nhiều", "Theo loại", "Tìm kiếm"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fagment1, container, false);

        CalligraphyConfig.initDefault(
                new CalligraphyConfig.Builder()
                        .setDefaultFontPath("Roboto-Regular.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );

        tab_layout_fragment = (TabLayout) view.findViewById(R.id.tab_layout_fragment);
        view_pager_fragment = (ViewPager)view.findViewById(R.id.view_pager_fragment);

        for (int i = 0; i < 3; i++) {
            tab_layout_fragment.addTab(tab_layout_fragment.newTab().setText(pageTitle[i]));
        }
        tab_layout_fragment.setTabGravity(TabLayout.GRAVITY_FILL);

        ViewPagerAdapterFragmentMenu pagerAdapter = new ViewPagerAdapterFragmentMenu(getFragmentManager());
        view_pager_fragment.setAdapter(pagerAdapter);

        view_pager_fragment.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab_layout_fragment));

        tab_layout_fragment.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                view_pager_fragment.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        return view;
    }

    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
        super.onAttach(CalligraphyContextWrapper.wrap(newBase));
    }

}

