package info.devexchanges.navvp.View.Main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import info.devexchanges.navvp.General.SetFont;
import info.devexchanges.navvp.R;
import info.devexchanges.navvp.View.ForgotPassword.ForgotPasswordActivity;
import info.devexchanges.navvp.View.Login.LoginActivity;
import info.devexchanges.navvp.View.Table.TableActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tvNameLogo,tvForgotPw;
    private Button btnLoginMain;

    public static final String MyPREFERENCES = "capuccino" ;

    private SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        String token = prefs.getString("token", "");
        if( !token.equals("") ) {
            Intent intent = new Intent(MainActivity.this, TableActivity.class);
            startActivity(intent);
        }

        //Set font chữ
        SetFont setFont = new SetFont("Roboto-Regular.ttf");
        setFont.getFont();


        // set name logo "CAPUCCINO"
        tvNameLogo = (TextView) findViewById(R.id.tvNameLogo);
        Typeface mFont = Typeface.createFromAsset(getAssets(),"Lobster.otf");
        tvNameLogo.setText("Cappuccino");
        tvNameLogo.setTypeface(mFont);


        // go to page LOGIN
        btnLoginMain = (Button)findViewById(R.id.btnLoginMain);
        btnLoginMain.setOnClickListener(this);


        // go to page FORGOT PASSWORD
        tvForgotPw = (TextView) findViewById(R.id.tvForgotPw);
        tvForgotPw.setText(Html.fromHtml("<u>Quên mật khẩu</u>"));
        tvForgotPw.setOnClickListener(this);





    }

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
