package io.awesome.app.View.MenuTabs;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.view.MenuItem;
import android.view.Window;


import io.awesome.app.View.Adapter.ViewPagerMenuTabsAdapter;
import io.awesome.app.General.SetFont;
import io.awesome.app.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MenuTabsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private String[] pageTitle = {"CHỌN MÓN", "THANH TOÁN"};
    private ViewPager viewPager;

    private ViewPagerMenuTabsAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_tabs);



        SetFont setFont = new SetFont("Roboto-Regular.ttf");
        setFont.getFont();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        // Set ActionBar
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_back);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //setting Tab layout (number of Tabs = number of ViewPager pages)
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        for (int i = 0; i < 2; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(pageTitle[i]));
        }

        //set gravity for tab bar
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager)findViewById(R.id.view_pager);

        //set viewpager adapter
        // Gồm 2 fragemnt đó là fragment FragmentChooseMenu và FragmentReceipt
        pagerAdapter = new ViewPagerMenuTabsAdapter(getSupportFragmentManager());

        viewPager.setAdapter(pagerAdapter);





        //change Tab selection when swipe ViewPager
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));




        //change ViewPager page when tab selected
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                pagerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



        int statusReceipt = getIntent().getIntExtra("statusReceipt",0);
        if(statusReceipt == 0){
            viewPager.setCurrentItem(0);
        }else{
            viewPager.setCurrentItem(1);
        }

    }

    // Quay về màn hình trước đó là tableActivity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }


    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }



}
