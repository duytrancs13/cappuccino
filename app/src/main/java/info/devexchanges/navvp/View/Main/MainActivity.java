package info.devexchanges.navvp.View.Main;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import info.devexchanges.navvp.General.SetFont;
import info.devexchanges.navvp.R;
import info.devexchanges.navvp.View.ForgotPassword.ForgotPasswordActivity;
import info.devexchanges.navvp.View.Login.LoginActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tvNameLogo,tvForgotPw;
    private Button btnLoginMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SetFont setFont = new SetFont("Roboto-Regular.ttf");
        setFont.getFont();


        //tvNameLogo
        tvNameLogo = (TextView) findViewById(R.id.tvNameLogo);
        Typeface mFont = Typeface.createFromAsset(getAssets(),"Lobster.otf");
        tvNameLogo.setText("Cappuccino");
        tvNameLogo.setTypeface(mFont);

        //btnLoginMain
        btnLoginMain = (Button)findViewById(R.id.btnLoginMain);
        btnLoginMain.setOnClickListener(this);


        //tvForgotPw
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
