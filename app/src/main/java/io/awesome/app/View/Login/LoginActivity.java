package io.awesome.app.View.Login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tapadoo.alerter.Alerter;

import org.json.JSONException;
import org.json.JSONObject;

import io.awesome.app.General.SetFont;
import io.awesome.app.Model.Account;
import io.awesome.app.Presenter.Login.LoginPresenterImpl;
import io.awesome.app.R;
import io.awesome.app.View.CustomEditText.CustomEditTextEmailTextWatcher;
import io.awesome.app.View.CustomEditText.CustomEditTextPWTextWatcher;
import io.awesome.app.View.Main.MainActivity;
import io.awesome.app.View.Table.TableActivity;
import io.awesome.app.View.ForgotPassword.ForgotPasswordActivity;
import static io.awesome.app.View.Main.MainActivity.account;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, LoginView {

    public static final String MyPREFERENCES = "capuccino" ;
    private SharedPreferences sharedpreferences;

    private Toolbar toolbar;

    private TextView tvForgotPw;
    private ImageView clearLogin,visibilityLoginPW;


    private EditText editEmailLogin, editPwLogin;
    private Button btnLogin;

    private LoginPresenterImpl loginPresenterImpl;

    private ProgressDialog dialog ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //set font chữ
        SetFont setFont = new SetFont("Lobster.otf");
        setFont.getFont();


        // set Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Set ActionBar
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_back);
        upArrow.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Ánh xạ
        dialog = new ProgressDialog(LoginActivity.this,R.style.AppTheme_Dark_Dialog);

        editEmailLogin = (EditText) findViewById(R.id.editEmailLogin);
        clearLogin = (ImageView) findViewById(R.id.clearLogin);

        editPwLogin = (EditText) findViewById(R.id.editPwLogin);
        visibilityLoginPW = (ImageView) findViewById(R.id.visibilityLoginPW);


        // đi đến màn hình hình FORGOT PASSWORD
        tvForgotPw = (TextView) findViewById(R.id.tvForgotPw);
        tvForgotPw.setText(Html.fromHtml("<u>Quên mật khẩu</u>"));
        tvForgotPw.setOnClickListener(this);


        // Bắt sự kiện editText Email VISIBLE va click vào sẽ xóa text trong editText Email
        editEmailLogin.addTextChangedListener(new CustomEditTextEmailTextWatcher(editEmailLogin,clearLogin));


        //Bắt sự kiện editText Password VISIBLE va click vào sẽ xóa text trong editText Password
        editPwLogin.addTextChangedListener(new CustomEditTextPWTextWatcher(editPwLogin, visibilityLoginPW));




        // Bắt sự kiện khi bấm vào nút LOGIN
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        // initialize LoginPresenterImpl()
        loginPresenterImpl = new LoginPresenterImpl(this,this.getBaseContext());

    }

    ////// VALIDATE EMAIL & PASSWORD

    // Interface errorEmail khi editText rỗng, hoặc không đúng định dạng Email.
    @Override
    public void errorEmail(String errorMessage) {
        editEmailLogin.setError(errorMessage);
    }


    // Interface errorPassword khi editText rỗng, hoặc không đúng định dạng Password.
    @Override
    public void errorPW(String errorMessage) {
        editPwLogin.setError(errorMessage);
    }



    ////////// Thông Báo khi email chưa đăng kí, hoặc lỗi SERVER
    @Override
    public void alertMessage(String titleError, String textError, int responseCode){
        if(responseCode == 500) {
            Alerter.create(this)
                    .setTitle(titleError)
                    .setText(textError)
                    .setBackgroundColorRes(R.color.red) // or setBackgroundColorInt(Color.CYAN)
                    .show();
        }else{
            Alerter.create(this)
                    .setTitle(titleError)
                    .setText(textError)
                    .setBackgroundColorRes(R.color.colorPrimary) // or setBackgroundColorInt(Color.CYAN)
                    .show();
        }
        dialog.dismiss();

    }


    // Khi login thành công. Trả về token và gán vào biến token bằng SharedPreferences để sử dụng trong toàn bộ app.
    @Override
    public void loginSuccessful(String objectAccount){
        editEmailLogin.setText("");
        clearLogin.setVisibility(View.GONE);

        editPwLogin.setText("");
        visibilityLoginPW.setVisibility(View.GONE);
        try {
            JSONObject jsonObject = new JSONObject(objectAccount);
            JSONObject data = jsonObject.getJSONObject("data");
            String token = data.getString("token");
            Gson gson = new Gson();
            account = gson.fromJson(data.toString(), Account.class);

            sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();

            editor.putString("token", token);
            editor.putString("objectAccount", data.toString());

            editor.commit();

            dialog.dismiss();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Intent intent = new Intent(LoginActivity.this, TableActivity.class);
        startActivity(intent);
    }

    public void toast(String message){
        Toast.makeText(getBaseContext(),message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        int r = view.getId();
        switch (r){
            case R.id.btnLogin:
                String email = editEmailLogin.getText().toString();
                String password = editPwLogin.getText().toString();
                loginPresenterImpl.validateLogin(email,password);
                break;

            case R.id.tvForgotPw:
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
                break;
        }
    }


    // ĐỢi xử lý quá trình login
    public void showProgress() {
        new Progress().execute();
    }

    private class Progress extends AsyncTask<Void, Void, Void>{


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Đang kết nối...");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            String email = editEmailLogin.getText().toString();
            String password = editPwLogin.getText().toString();
            loginPresenterImpl.login(email,password);
            return null;
        }
    }


    // Bắt sự kiện để trở lại màn hình trước
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

}


