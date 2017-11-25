package info.devexchanges.navvp.View.Login;

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
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.tapadoo.alerter.Alerter;

import info.devexchanges.navvp.General.SetFont;
import info.devexchanges.navvp.Presenter.Login.LoginPresenterImpl;
import info.devexchanges.navvp.R;
import info.devexchanges.navvp.View.Main.MainActivity;
import info.devexchanges.navvp.View.Table.TableActivity;
import info.devexchanges.navvp.View.ForgotPassword.ForgotPasswordActivity;

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

        // go to page FORGOT PASSWORD
        tvForgotPw = (TextView) findViewById(R.id.tvForgotPw);
        tvForgotPw.setText(Html.fromHtml("<u>Quên mật khẩu</u>"));
        tvForgotPw.setOnClickListener(this);


        // Mapping
        dialog = new ProgressDialog(LoginActivity.this,R.style.AppTheme_Dark_Dialog);

        editEmailLogin = (EditText) findViewById(R.id.editEmailLogin);
        editPwLogin = (EditText) findViewById(R.id.editPwLogin);
        clearLogin = (ImageView) findViewById(R.id.clearLogin);
        visibilityLoginPW = (ImageView) findViewById(R.id.visibilityLoginPW);


        // handle event clear all text in editText Email
        editEmailLogin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() > 0){
                    clearLogin.setVisibility(View.VISIBLE);
                    clearLogin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            editEmailLogin.setText("");
                        }
                    });
                }else{
                    clearLogin.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        //handle event hide or show text in editText Password
        editPwLogin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() > 0){
                    visibilityLoginPW.setVisibility(View.VISIBLE);
                    visibilityLoginPW.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(editPwLogin.getTransformationMethod() instanceof PasswordTransformationMethod){
                                editPwLogin.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                                editPwLogin.setSelection(editPwLogin.getText().length());
                                visibilityLoginPW.setImageResource(R.drawable.ic_visibility_off);
                            }else{
                                editPwLogin.setTransformationMethod(PasswordTransformationMethod.getInstance());
                                editPwLogin.setSelection(editPwLogin.getText().length());
                                visibilityLoginPW.setImageResource(R.drawable.ic_visibility_on);
                            }
                        }
                    });
                }else{
                    visibilityLoginPW.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        // handle event button LOGIN
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        // initialize LoginPresenterImpl()
        loginPresenterImpl = new LoginPresenterImpl(this,this.getBaseContext());

    }

    @Override
    public void alertErrorEmptyEmail() {
        editEmailLogin.setError("Vui lòng nhập Email");
    }

    @Override
    public void alertErrorFormatEmail() {
        editEmailLogin.setError("Sai định dạng Email");
    }

    @Override
    public void alertSuccessEmail() {
        editEmailLogin.setError(null);
    }

    @Override
    public void alertErrorEmptyPassword() {
        editPwLogin.setError("Vui lòng nhập mật khẩu");
    }

    @Override
    public void alertErrorMinPassword() {
        editPwLogin.setError("Ít nhất 5 kí tự");
    }

    @Override
    public void alertSuccessPassword() {
        editPwLogin.setError(null);
    }

    @Override
    public void loginSuccessful(String token){
        dialog.dismiss();

        editEmailLogin.setText("");
        clearLogin.setVisibility(View.GONE);

        editPwLogin.setText("");
        visibilityLoginPW.setVisibility(View.GONE);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString("token", token);
        editor.commit();

        Intent intent = new Intent(LoginActivity.this, TableActivity.class);
        startActivity(intent);
    }

    @Override
    public void loginFailed(){
        dialog.dismiss();

        Alerter.create(this)
                .setTitle("Lỗi")
                .setText("Email chưa đăng kí")
                .setBackgroundColorRes(R.color.red) // or setBackgroundColorInt(Color.CYAN)
                .show();
    }

    @Override
    public void loginErrorServer() {
        dialog.dismiss();

        Alerter.create(this)
                .setTitle("Lỗi server")
                .setText("Vui lòng thử lại!!!")
                .setBackgroundColorRes(R.color.red) // or setBackgroundColorInt(Color.CYAN)
                .show();
    }

    @Override
    public void showProgress() {
        new Progress().execute();
    }

    // Back page
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
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
                loginPresenterImpl.login(email,password);
                break;

            case R.id.tvForgotPw:
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
                break;
        }
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
            loginPresenterImpl.attemptLogin(email,password);
            return null;
        }
    }


}


