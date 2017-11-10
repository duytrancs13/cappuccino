package info.devexchanges.navvp.View.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
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
import info.devexchanges.navvp.Presenter.Login.Login;
import info.devexchanges.navvp.R;
import info.devexchanges.navvp.View.Table.TableActivity;
import info.devexchanges.navvp.View.ForgotPassword.ForgotPasswordActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, InterfaceLogin {

    private Toolbar toolbar;
    private TextView tvForgotPw;
    private ImageView clearLogin,visibilityLoginPW;



    private EditText editEmailLogin, editPwLogin;
    private Button btnLogin;

    private Login login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SetFont setFont = new SetFont("Lobster.otf");
        setFont.getFont();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set ActionBar
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_back);
        upArrow.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //tvForgotPw
        tvForgotPw = (TextView) findViewById(R.id.tvForgotPw);
        tvForgotPw.setText(Html.fromHtml("<u>Quên mật khẩu</u>"));
        tvForgotPw.setOnClickListener(this);


        editEmailLogin = (EditText) findViewById(R.id.editEmailLogin);
        editPwLogin = (EditText) findViewById(R.id.editPwLogin);
        clearLogin = (ImageView) findViewById(R.id.clearLogin);

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

        visibilityLoginPW = (ImageView) findViewById(R.id.visibilityLoginPW);
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



        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        login = new Login(this,this.getBaseContext());

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
        Intent intent = new Intent(LoginActivity.this, TableActivity.class);
        intent.putExtra("token", token);
        startActivity(intent);
    }

    @Override
    public void alertFailed(){
        Alerter.create(this)
                .setTitle("Lỗi")
                .setText("Email không chưa đăng kí")
                .setBackgroundColorRes(R.color.red) // or setBackgroundColorInt(Color.CYAN)
                .show();
    }

    @Override
    public void showProgress() {
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Đang kết nối...");
        progressDialog.show();


        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        progressDialog.dismiss();
                    }
                }, 2000);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void toast(String message){
        Toast.makeText(getBaseContext(),message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onClick(View view) {
        int r = view.getId();
        switch (r){
            case R.id.btnLogin:
                String email = editEmailLogin.getText().toString();
                String password = editPwLogin.getText().toString();
                login.login(email,password);
                break;

            case R.id.tvForgotPw:
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
                break;
        }
    }
}
