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


        linearLayout.setOnTouchListener(new OnDragTouchListener(linearLayout));





    }

    public static class OnDragTouchListener implements View.OnTouchListener {

        /**
         * Callback used to indicate when the drag is finished
         */
        public interface OnDragActionListener {
            /**
             * Called when drag event is started
             *
             * @param view The view dragged
             */
            void onDragStart(View view);

            /**
             * Called when drag event is completed
             *
             * @param view The view dragged
             */
            void onDragEnd(View view);
        }

        private View mView;
        private View mParent;
        private boolean isDragging;
        private boolean isInitialized = false;

        private int width;
        private float xWhenAttached;
        private float maxLeft;
        private float maxRight;
        private float dX;

        private int height;
        private float yWhenAttached;
        private float maxTop;
        private float maxBottom;
        private float dY;

        private OnDragActionListener mOnDragActionListener;

        public OnDragTouchListener(View view) {
            this(view, (View) view.getParent(), null);
        }

        public OnDragTouchListener(View view, View parent) {
            this(view, parent, null);
        }

        public OnDragTouchListener(View view, OnDragActionListener onDragActionListener) {
            this(view, (View) view.getParent(), onDragActionListener);
        }

        public OnDragTouchListener(View view, View parent, OnDragActionListener onDragActionListener) {
            initListener(view, parent);
            setOnDragActionListener(onDragActionListener);
        }

        public void setOnDragActionListener(OnDragActionListener onDragActionListener) {
            mOnDragActionListener = onDragActionListener;
        }

        public void initListener(View view, View parent) {
            mView = view;
            mParent = parent;
            isDragging = false;
            isInitialized = false;
        }

        public void updateBounds() {
            updateViewBounds();
            updateParentBounds();
            isInitialized = true;
        }

        public void updateViewBounds() {
            width = mView.getWidth();
            xWhenAttached = mView.getX();
            dX = 0;

            height = mView.getHeight();
            yWhenAttached = mView.getY();
            dY = 0;
        }

        public void updateParentBounds() {
            maxLeft = 0;
            maxRight = maxLeft + mParent.getWidth();

            maxTop = 0;
            maxBottom = maxTop + mParent.getHeight();
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (isDragging) {
                float[] bounds = new float[4];
                // LEFT
                bounds[0] = event.getRawX() + dX;
                if (bounds[0] < maxLeft) {
                    bounds[0] = maxLeft;
                }
                // RIGHT
                bounds[2] = bounds[0] + width;
                if (bounds[2] > maxRight) {
                    bounds[2] = maxRight;
                    bounds[0] = bounds[2] - width;
                }
                // TOP
                bounds[1] = event.getRawY() + dY;
                if (bounds[1] < maxTop) {
                    bounds[1] = maxTop;
                }
                // BOTTOM
                bounds[3] = bounds[1] + height;
                if (bounds[3] > maxBottom) {
                    bounds[3] = maxBottom;
                    bounds[1] = bounds[3] - height;
                }

                switch (event.getAction()) {
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        onDragFinish();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mView.animate().x(bounds[0]).y(bounds[1]).setDuration(0).start();
                        break;
                }
                return true;
            } else {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isDragging = true;
                        if (!isInitialized) {
                            updateBounds();
                        }
                        dX = v.getX() - event.getRawX();
                        dY = v.getY() - event.getRawY();
                        if (mOnDragActionListener != null) {
                            mOnDragActionListener.onDragStart(mView);
                        }
                        return true;
                }
            }
            return false;
        }

        private void onDragFinish() {
            if (mOnDragActionListener != null) {
                mOnDragActionListener.onDragEnd(mView);
            }

            dX = 0;
            dY = 0;
            isDragging = false;
        }
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
