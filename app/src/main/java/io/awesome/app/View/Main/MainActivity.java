package io.awesome.app.View.Main;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.awesome.app.General.SetFont;
import io.awesome.app.Model.Account;
import io.awesome.app.Model.Category;
import io.awesome.app.Model.Menu;
import io.awesome.app.Model.Table;
import io.awesome.app.Presenter.Pusher.PusherCategory;
import io.awesome.app.Presenter.Pusher.PusherMenu;
import io.awesome.app.Presenter.Pusher.PusherTable;
import io.awesome.app.R;
import io.awesome.app.View.Adapter.CustomCategoryAdapter;
import io.awesome.app.View.ForgotPassword.ForgotPasswordActivity;
import io.awesome.app.View.Login.LoginActivity;
import io.awesome.app.View.Table.TableActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    // Ánh xạ
    private TextView tvNameLogo,tvForgotPw;
    private Button btnLoginMain;
    // Cách để lưu token cho app
    private SharedPreferences sharedpreferences;

    // Khai báo MyPREFERENCES phải trùng tên với tên project là capuccino
    public static final String MyPREFERENCES = "capuccino" ;


    public static List<Table> listTable = new ArrayList<Table>();

    public static List<Menu> listMenu = new ArrayList<Menu>();

    public static List<Category> listCategory = new ArrayList<Category>();
    public static boolean onSubcribeCategory = true;

    public static String receiptId = "";
    public static String receiptToOrdered = "";
    public static BluetoothSocket bluetoothSocket = null;

    public static Account account;


//    private PusherTable pusherTable;
    public static boolean onTableActivity = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Để xác thực người dùng đã đăng nhập hay chưa. Kiểm tra token
        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        String token = prefs.getString("token", "");
        String objectAccount =  prefs.getString("objectAccount", "");

//        pusherTable = new PusherTable(TableActivity.class);
//
//        pusherTable.subcribe();
        PusherMenu.subcribe();
//        PusherCategory.subcribe();

        // Nếu token còn tồn tại thì chuyển người dùng thẳng đến màn hình đặt bàn không thì thôi
        if( !token.equals("") ) {
            Gson gson = new Gson();
            account = gson.fromJson(objectAccount.toString(), Account.class);
            Intent intent = new Intent(MainActivity.this, TableActivity.class);
            startActivity(intent);
        }







        // Set font chữ
        // Set kiểu này là set font cho toàn bộ màn hình
        SetFont setFont = new SetFont("Roboto-Regular.ttf");
        setFont.getFont();


        // Set tên logo "CAPUCCINO"
        // Set kiểu này là set cho 1 font bất kì
        tvNameLogo = (TextView) findViewById(R.id.tvNameLogo);
        Typeface mFont = Typeface.createFromAsset(getAssets(),"Lobster.otf");
        tvNameLogo.setText("Cappuccino");
        tvNameLogo.setTypeface(mFont);


        // Đi đến màn hình LOGIN
        btnLoginMain = (Button)findViewById(R.id.btnLoginMain);
        btnLoginMain.setOnClickListener(this);


        // Đi đến màn FORGOT PASSWORD
        tvForgotPw = (TextView) findViewById(R.id.tvForgotPw);
        tvForgotPw.setText(Html.fromHtml("<u>Quên mật khẩu</u>"));
        tvForgotPw.setOnClickListener(this);
    }


    // Bắt sự kiện khi click vào Button LOGIN
    // Bắt sự kiện khi click vào textview "Quên mật khẩu"
    @Override
    public void onClick(View view) {
        int r = view.getId();
        Intent intent;
        switch (r){
            case R.id.btnLoginMain:
                intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.tvForgotPw:
                intent = new Intent(MainActivity.this,ForgotPasswordActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void toast(String message){
        Toast.makeText(getBaseContext(),message,Toast.LENGTH_SHORT).show();
    }

}
