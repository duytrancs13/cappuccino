package io.awesome.app.View.MenuTabs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import io.awesome.app.Model.Ordered;
import io.awesome.app.Presenter.MenuTabs.MenuTabsPresenterImp;
import io.awesome.app.Presenter.Pusher.PusherReceipt;
import io.awesome.app.View.Adapter.ViewPagerMenuTabsAdapter;
import io.awesome.app.General.SetFont;
import io.awesome.app.R;

import io.awesome.app.View.Table.TableActivity;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static io.awesome.app.View.Table.TableActivity.checkConfirmChangedOrdered;
import static io.awesome.app.View.Table.TableActivity.listOrdered;



public class MenuTabsActivity extends AppCompatActivity implements MenuTabsView {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private String[] pageTitle = {"CHỌN MÓN", "THANH TOÁN"};
    private ViewPager viewPager;


    private ViewPagerMenuTabsAdapter pagerAdapter;


    public static final String MyPREFERENCES = "capuccino" ;
    private SharedPreferences sharedPreferences;
    private MenuTabsPresenterImp menuTabsPresenterImp;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_tabs);





        SetFont setFont = new SetFont("Roboto-Regular.ttf");
        setFont.getFont();


        sharedPreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        token = sharedPreferences.getString("token", null);

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
        menuTabsPresenterImp = new MenuTabsPresenterImp(this, this);

//        PusherReceipt.subcribe();

        if(statusReceipt == 0){
            goToMenu(statusReceipt);
        }else if(statusReceipt == 1){
            menuTabsPresenterImp.getMenuReceipt(token, statusReceipt);
        }else{
            menuTabsPresenterImp.getMenuReceipt(token, statusReceipt);
        }









    }





    public void toast(String message){
        Toast.makeText(getBaseContext(),message,Toast.LENGTH_SHORT).show();
    }

    // Quay về màn hình trước đó bằng icon là tableActivity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            /*startActivity(new Intent(this, TableActivity.class));*/
            checkConfirmChangedOrdered = true;
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    // Quay về màn hình tableActivity bằng nút back của android
    @Override
    public void onBackPressed() {
        checkConfirmChangedOrdered = true;
        finish();
    }


    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    public void goToMenu(int statusReceipt) {
        if(statusReceipt == 0){
             listOrdered = new ArrayList<Ordered>();
            viewPager.setCurrentItem(0);
        }else if(statusReceipt == 1){
            viewPager.setCurrentItem(0);
        }else{
            viewPager.setCurrentItem(1);
        }
    }
}
