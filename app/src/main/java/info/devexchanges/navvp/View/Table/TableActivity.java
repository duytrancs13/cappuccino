package info.devexchanges.navvp.View.Table;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import info.devexchanges.navvp.View.Login.LoginActivity;
import info.devexchanges.navvp.View.Main.MainActivity;
import info.devexchanges.navvp.View.MenuTabs.MenuTabsActivity;
import info.devexchanges.navvp.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class TableActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,TableView {


    private DrawerLayout drawer;
    public static final String MyPREFERENCES = "capuccino" ;
    private SharedPreferences sharedPreferences;



    private ViewGroup root;
    private LinearLayout linearLayout;

    private PopupMenu popup;

    private CountUp countUp;

    private Intent intent;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        CalligraphyConfig.initDefault(
                new CalligraphyConfig.Builder()
                        .setDefaultFontPath("Roboto-Regular.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawerLayout);

        setSupportActionBar(toolbar);

        //create default navigation drawer toggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //handling navigation view item event
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);


        sharedPreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);


        root = (ViewGroup) findViewById(R.id.root);

        linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.CENTER);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(DrawerLayout.LayoutParams.WRAP_CONTENT, DrawerLayout.LayoutParams.WRAP_CONTENT);

        int positionX = 1*150;
        int positionY = 1*150;
        layoutParams.leftMargin = positionX;
        layoutParams.topMargin = positionY;




        linearLayout.setLayoutParams(layoutParams);
        root.addView(linearLayout);

        final Button table = new Button(this);
        table.setId(R.id.table);
        table.setBackgroundResource(R.drawable.ic_table_free);
        linearLayout.addView(table);

        TextView nameTable = new TextView(this);
        nameTable.setId(R.id.nameTable);
        nameTable.setText("B1");
        RelativeLayout.LayoutParams layoutNameTable = new RelativeLayout.LayoutParams(DrawerLayout.LayoutParams.WRAP_CONTENT, DrawerLayout.LayoutParams.WRAP_CONTENT);
        layoutNameTable.addRule(RelativeLayout.BELOW,R.id.table);

        nameTable.setLayoutParams(layoutNameTable);
        linearLayout.addView(nameTable);

        TextView timer = new TextView(this);
        timer.setId(R.id.timer);
        timer.setVisibility(View.INVISIBLE);
        timer.setText("00:00:00");
        RelativeLayout.LayoutParams layoutTimer = new RelativeLayout.LayoutParams(DrawerLayout.LayoutParams.WRAP_CONTENT, DrawerLayout.LayoutParams.WRAP_CONTENT);
        layoutTimer.addRule(RelativeLayout.BELOW,R.id.nameTable);

        timer.setLayoutParams(layoutTimer);
        linearLayout.addView(timer);








    }



    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.profile) {
            toast("profile ");

        } else if (id == R.id.logout) {
            alertLogout();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void toast(String message){
        Toast.makeText(getBaseContext(),message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        assert drawer != null;
        if (drawer.isDrawerOpen(GravityCompat.START)) {

            moveTaskToBack(true);


            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void alertLogout() {
        new AlertDialog.Builder(TableActivity.this,R.style.AppTheme_Dark_Dialog)
                .setTitle("Thoát")
                .setMessage("Bạn có muốn thoát không?")
                .setNegativeButton(android.R.string.cancel, null) // dismisses by default
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear().commit();
                        intent = new Intent(TableActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                })
                .create()
                .show();
    }
}
