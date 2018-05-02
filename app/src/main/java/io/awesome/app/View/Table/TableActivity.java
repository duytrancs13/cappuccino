package io.awesome.app.View.Table;

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
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;

import java.util.ArrayList;
import java.util.List;

import io.awesome.app.Model.Ordered;
import io.awesome.app.Model.Table;
import io.awesome.app.Presenter.Table.TablePresenterImpl;
import io.awesome.app.View.Login.LoginActivity;
import io.awesome.app.View.MenuTabs.MenuTabsActivity;
import io.awesome.app.R;
import io.awesome.app.View.MoveOrder.MoveOrderActivity;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static io.awesome.app.View.Main.MainActivity.listTable;
import static io.awesome.app.View.Main.MainActivity.receiptId;

public class TableActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,TableView {




    private DrawerLayout drawer;
    public static final String MyPREFERENCES = "capuccino" ;
    private SharedPreferences sharedPreferences;

    private ViewGroup root;

    private LinearLayout linearLayout;

    private PopupMenu popup;

    private CountUp countUp;

    private Intent intent;

    private TablePresenterImpl tablePresenter;

    private String token;

    public static List<Ordered> listOrdered = new ArrayList<Ordered>();
    public static List<Ordered> listToOrdered = new ArrayList<Ordered>();



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
        getSupportActionBar().setTitle("Sơ đồ bàn");

        //create default navigation drawer toggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //handling navigation view item event
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);

        root = (ViewGroup) findViewById(R.id.root);



        // Lấy biến token ra để sử dụng
        sharedPreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        token = sharedPreferences.getString("token", null);




        tablePresenter = new TablePresenterImpl(getBaseContext(),this);


        // Nhờ TablePresenter để gọi đến API để load dữ liệu của bàn. Cần có token
        tablePresenter.loadTable(token);
    }



    // Dùng list table để hiển thị ra
    // Gồm có 3 trạng thái của bàn: "Rỗng", "đã đặt món", "đã giao món".
    @Override
    public void showTable() {


        for(int i = 0; i < listTable.size() ; i++){

            // Đối tượng của 1 bàn itemTable
            Table itemTable = listTable.get(i);
            final String receiptBusy = itemTable.getReceiptId();

            linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setGravity(Gravity.CENTER);

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(DrawerLayout.LayoutParams.WRAP_CONTENT, DrawerLayout.LayoutParams.WRAP_CONTENT);

            //Hiển thị vị trí của bàn.
            int positionX = itemTable.getX()*150;
            int positionY = itemTable.getY()*150;
            layoutParams.leftMargin = positionX;
            layoutParams.topMargin = positionY;

            linearLayout.setLayoutParams(layoutParams);
            root.addView(linearLayout);



            // Kiểm tra trạng thái của từng bàn
            Button table = new Button(this);
            final int position = i;

            // Bàn màu xanh là bàn ở trạng thái rỗng
            if(itemTable.getStatus().equals("idle")){
                final String idTable= itemTable.getId();
                table.setBackgroundResource(R.drawable.ic_table_idle);

                table.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        /*popupTableFree(view,table,itemTable,timer);*/
                        popupTableFree(view,idTable, position);
                    }
                });
            }
            // Bàn màu vàng là bàn ở trạng thái bận
            else if(itemTable.getStatus().equals("busy")){
                table.setBackgroundResource(R.drawable.ic_table_busy);
                table.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        /*popupTableWaiting(view,table,itemTable.getReceiptId(),itemTable.getId(),timer);*/
                        popupTableBusy(view, receiptBusy);
                    }
                });
            }

            linearLayout.addView(table);

            // hiển thị tên bàn vào trong button của bàn đó.
            TextView nameTable = new TextView(this);
            nameTable.setId(i);
            nameTable.setText(itemTable.getName());
            RelativeLayout.LayoutParams layoutNameTable = new RelativeLayout.LayoutParams(DrawerLayout.LayoutParams.WRAP_CONTENT, DrawerLayout.LayoutParams.WRAP_CONTENT);

            layoutNameTable.addRule(RelativeLayout.BELOW,table.getId());
            nameTable.setLayoutParams(layoutNameTable);
            linearLayout.addView(nameTable);

            // Di chuyển bàn
            //tablePresenter.dragTable(linearLayout);

            // Hiển thị thời gian nhưng bị VISIBLE.
            /*final TextView timer = new TextView(this);
            timer.setId(i);
            timer.setVisibility(View.VISIBLE);
            timer.setText("00:00:00");
            RelativeLayout.LayoutParams layoutTimer = new RelativeLayout.LayoutParams(DrawerLayout.LayoutParams.WRAP_CONTENT, DrawerLayout.LayoutParams.WRAP_CONTENT);
            layoutTimer.addRule(RelativeLayout.BELOW,nameTable.getId());

            timer.setLayoutParams(layoutTimer);
            linearLayout.addView(timer);*/

            // Di chuyển bàn
            //tablePresenter.dragTable(linearLayout);

            // Bắt sự kiện trạng thái của từng bàn.
            /*table.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {

                    popup = new PopupMenu(v.getContext(), table);

                    // Nếu bàn ở trạng thái "rỗng"
                    if( table.getBackground().getConstantState() ==
                            getResources().getDrawable(R.drawable.ic_table_idle).getConstantState() ){

                        // Hiển thị popup của bàn ở trạng thái rỗng
                        //popupTableFree(v,table,itemTable,timer);
                    }
                    // Bắt sự kiện khi bàn ở trạng thái "đợi".
                    else if(table.getBackground().getConstantState() ==
                            getResources().getDrawable(R.drawable.ic_table_waiting).getConstantState() ){

                        // Hiển thị popup của bàn ở trạng thái "đợi"
                        popupTableWaiting(v,table,itemTable.getReceiptId(),itemTable.getId(),timer);

                    // Bắt sự kiện khi bàn ở trạng thái đã giao món
                    }else if(table.getBackground().getConstantState() ==
                            getResources().getDrawable(R.drawable.ic_table_deliver).getConstantState()) {

                        // Hiển thị popup của bàn ở trạng thái "đã giao món"
                        popupTableDeliver(v,table);

                    }
                }
            });*/
        }

    }

    // Hiển thị thông báo hỏi người dùng có muốn thoát ứng dụng không
    // Nếu người dùng logout thì cần phải hủy token trong SharedPreferences
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

    // Navigation gồm 2 phần là Profile và Logout
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

    // Hàm xử lí sự kiện khi gọi bàn ở trạng thái rỗng.
    /*void popupTableFree(View v,final Button btnTable, final Table table, final TextView timer)*/
    void popupTableFree(View v, final String idTable, final int position){
        final CharSequence[] items = {"Đặt món", "Đặt chỗ"};

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setIcon(R.drawable.ic_option);

        builder.setTitle("Tùy chọn");

        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                // Khi chọn vào item "đặt món".

                // Cần sử lý 3 việc:
                    // 1.cập nhật thanh toán
                    // 2. Chuyển trạng thái bàn 1 bàn từ rỗng sang trạng thái đợi
                    // 3. Đếm thời gian tăng lên
                if(item==0){
                    // Cập nhật receipt của bàn đó và đi đến màn hình menu
                    tablePresenter.createReceipt(idTable,token, position);


                    // Thời gian bắt đầu được đếm lên
                    /*countUp = new CountUp(1000) {
                        @Override
                        public void onTick(long millisUntil) {
                            timer.setVisibility(View.VISIBLE);
                            timer.setTextColor(getResources().getColor(R.color.colorPrimary));
                            timer.setText(formatMilliSecondsToTime(millisUntil));
                        }
                    };
                    countUp.start();*/
                }

                // Khi chọn vào item "đặt chỗ".
                else if(item == 1){
                    toast("dat cho");
                    /*intent = new Intent(TableActivity.this, TimerActivity.class);
                    startActivity(intent);

                    new CountDownTimer(3000, 1000) {
                        TextView textTimer = (TextView) findViewById(R.id.timer);
                        public void onTick(long millisUntilFinished) {
                            table.setBackgroundResource(R.drawable.ic_table_busy);
                            table.setEnabled(false);

                            textTimer.setVisibility(View.VISIBLE);
                            textTimer.setTextColor(getResources().getColor(R.color.red));
                            textTimer.setText(formatMilliSecondsToTime(millisUntilFinished));
                        }
                        public void onFinish() {
                            table.setBackgroundResource(R.drawable.ic_table_idle);
                            textTimer.setVisibility(View.INVISIBLE);
                            table.setEnabled(true);
                        }
                    }.start();*/
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    // Hàm xử lí sự kiện khi gọi bàn ở trạng thái bận.
    void popupTableBusy(View v,final String receiptBusy){
        final CharSequence[] items = {"Đặt thêm món","Tách - gộp bàn", "Giao món","Thanh toán"};

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setIcon(R.drawable.ic_option);
        builder.setTitle("Tùy chọn");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int position) {

                // Khi chọn vào item "đặt thêm món"
                if(position == 0){

                    // Đi đến màn hình menu.
                    gotoMenu(receiptBusy,1);

                // Khi chọn vào item "Tách - gộp bàn"
                }else if(position == 1){
                    receiptId = receiptBusy;
                    intent = new Intent(TableActivity.this, MoveOrderActivity.class);
                    startActivity(intent);
                    /*toast("Tách - gộp bàn");*/

                    // Khi chọn vào item "Giao món"
                }else if(position == 2){
                    /*table.setBackgroundResource(R.drawable.ic_table_deliver);
                    countUp.stop();
                    timer.setVisibility(View.VISIBLE);
                    timer.setText("00:00:00");*/
                    toast("Giao mon");


                // Khi chọn vào item "Thanh toán"
                }else if(position == 3){
//                    btnTable.setBackgroundResource(R.drawable.ic_table_idle);
//                    countUp.stop();
//                    TextView textTimer = (TextView) findViewById(R.id.timer);
//                    textTimer.setVisibility(View.INVISIBLE);

//                    receiptId = receiptBusy;
//                    intent.putExtra("statusReceipt",1);
//                    startActivity(intent);

                    gotoMenu(receiptBusy, 2);
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    // Hàm xử lí sự kiện khi gọi bàn ở trạng thái đã đạt món.
    /*void popupTableDeliver(View v, final Button table){
        final CharSequence[] items = {"Đặt thêm món","Thanh toán"};

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setIcon(R.drawable.ic_option);
        builder.setTitle("Tùy chọn");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int position) {
                if(position == 0){
                    intent = new Intent(TableActivity.this, MenuTabsActivity.class);
                    startActivity(intent);

                    table.setBackgroundResource(R.drawable.ic_table_waiting);
                    countUp = new CountUp(1000) {
                        @Override
                        public void onTick(long millisUntil) {
                            TextView textTimer = (TextView) findViewById(R.id.timer);
                            textTimer.setVisibility(View.VISIBLE);
                            textTimer.setTextColor(getResources().getColor(R.color.colorPrimary));
                            textTimer.setText(formatMilliSecondsToTime(millisUntil));
                        }
                    };
                    countUp.start();
                }else if(position == 1){
                    table.setBackgroundResource(R.drawable.ic_table_idle);
                    //popup.getMenuInflater().inflate(R.menu.poupup_menu_free, popup.getMenu());
                    countUp.stop();
                    TextView textTimer = (TextView) findViewById(R.id.timer);
                    textTimer.setVisibility(View.INVISIBLE);


                    intent = new Intent(TableActivity.this,MenuTabsActivity.class);
                    intent.putExtra("statusReceipt",1);
                    startActivity(intent);
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
*/

    // Đến màn hình Menu cần mang theo 2 thông số là receipt và idTable. Sử dụng intent putExtra


    @Override
    public void gotoMenu(String receipt, int statusMenu) {
        receiptId = receipt;
        intent = new Intent(this, MenuTabsActivity.class);
        intent.putExtra("statusReceipt",statusMenu);
        startActivity(intent);
    }


    private String formatMilliSecondsToTime(long milliseconds) {

        int seconds = (int) (milliseconds / 1000) % 60;
        int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
        int hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);
        return twoDigitString(hours) + ":" + twoDigitString(minutes) + ":"
                + twoDigitString(seconds);
    }

    private String twoDigitString(long number) {

        if (number == 0) {
            return "00";
        }

        if (number / 10 == 0) {
            return "0" + number;
        }

        return String.valueOf(number);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
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






}
