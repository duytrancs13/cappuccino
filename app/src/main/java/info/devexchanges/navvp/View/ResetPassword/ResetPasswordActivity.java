package info.devexchanges.navvp.View.ResetPassword;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tapadoo.alerter.Alerter;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import info.devexchanges.navvp.General.SetFont;
import info.devexchanges.navvp.Presenter.ResetPassword.ResetPasswordPresenterImpl;
import info.devexchanges.navvp.R;
import info.devexchanges.navvp.View.Login.LoginActivity;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ResetPasswordActivity extends AppCompatActivity implements ResetPasswordView, View.OnClickListener {

    private Toolbar toolbar;

    private EditText editResetPW,editConfirmResetPW;

    private TextView tvLogin;

    private Button btnConfirm;

    private ImageView visibilityResetPW,visibilityConfirmResetPW;

    private ResetPasswordPresenterImpl resetPasswordPresenterImpl;

    private ProgressDialog dialog ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        // set font
        SetFont setFont = new SetFont("Roboto-Regular.ttf");
        setFont.getFont();

        //set toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set ActionBar
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_back);
        upArrow.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // go to page LOGIN
        tvLogin = (TextView) findViewById(R.id.tvLogin);
        tvLogin.setText(Html.fromHtml("<u>Đăng nhập</u>"));
        tvLogin.setOnClickListener(this);


        // Match
        dialog = new ProgressDialog(ResetPasswordActivity.this,R.style.AppTheme_Dark_Dialog);

        editResetPW = (EditText) findViewById(R.id.editResetPW);
        editConfirmResetPW = (EditText)findViewById(R.id.editConfirmResetPW);
        visibilityResetPW = (ImageView) findViewById(R.id.visibilityResetPW);
        visibilityConfirmResetPW = (ImageView) findViewById(R.id.visibilityConfirmResetPW);


        // handle event hide or show text in editText password
        editResetPW.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() > 0){
                    visibilityResetPW.setVisibility(View.VISIBLE);
                    visibilityResetPW.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(editResetPW.getTransformationMethod() instanceof PasswordTransformationMethod){
                                editResetPW.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                                editResetPW.setSelection(editResetPW.getText().length());
                                visibilityResetPW.setImageResource(R.drawable.ic_visibility_off);
                            }else{
                                editResetPW.setTransformationMethod(PasswordTransformationMethod.getInstance());
                                editResetPW.setSelection(editResetPW.getText().length());
                                visibilityResetPW.setImageResource(R.drawable.ic_visibility_on);
                            }
                        }
                    });
                }else{
                    visibilityResetPW.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        // handle event hide or show text in editText password
        editConfirmResetPW.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() > 0){
                    visibilityConfirmResetPW.setVisibility(View.VISIBLE);
                    visibilityConfirmResetPW.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(editConfirmResetPW.getTransformationMethod() instanceof PasswordTransformationMethod){
                                editConfirmResetPW.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                                editConfirmResetPW.setSelection(editConfirmResetPW.getText().length());
                                visibilityConfirmResetPW.setImageResource(R.drawable.ic_visibility_off);
                            }else{
                                editConfirmResetPW.setTransformationMethod(PasswordTransformationMethod.getInstance());
                                editConfirmResetPW.setSelection(editConfirmResetPW.getText().length());
                                visibilityConfirmResetPW.setImageResource(R.drawable.ic_visibility_on);
                            }
                        }
                    });
                }else{
                    visibilityResetPW.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //initialize ResetPasswordPresenterImpl()
        resetPasswordPresenterImpl = new ResetPasswordPresenterImpl(this, this.getBaseContext());

        btnConfirm = (Button) findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(this);

    }


    @Override
    public void alertErrorEmptyPassword() {
        editResetPW.setError("Vui lòng nhập mật khẩu mới");
    }

    @Override
    public void alertErrorMinPassword() {
        editResetPW.setError("Ít nhất 5 kí tự");
    }

    @Override
    public void alertSuccessPassword() {
        editResetPW.setError(null);
    }

    @Override
    public void alertErrorEmptyConfirmPassword() {
        editConfirmResetPW.setError("Vui lòng nhập xác nhận mật khẩu mới");
    }

    @Override
    public void alertErrorMatchConfirmPassword() {
        editConfirmResetPW.setError("Không khớp");
    }

    @Override
    public void alertSuccessConfirmPassword() {
        editConfirmResetPW.setError(null);
    }

    @Override
    public void showProgress() {
        /*final ProgressDialog progressDialog = new ProgressDialog(ResetPasswordActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Đang kết nối...");
        progressDialog.show();


        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        progressDialog.dismiss();
                    }
                }, 2000);*/
        new Progress().execute();
    }

    @Override
    public void resetPasswordSuccessful(){
        Alerter.create(this)
                .setTitle("Thành công")
                .setText("Vui lòng đăng nhập!!!")
                .setBackgroundColorRes(R.color.colorPrimary) // or setBackgroundColorInt(Color.CYAN)
                .show();

        dialog.dismiss();
    }

    @Override
    public void resetPasswordFailed(){
        dialog.dismiss();
        Alerter.create(this)
                .setTitle("Lỗi")
                .setText("Email không chưa đăng kí!!!")
                .setBackgroundColorRes(R.color.red) // or setBackgroundColorInt(Color.CYAN)
                .show();


    }

    @Override
    public void resetPasswordErrorServer() {
        Alerter.create(this)
                .setTitle("Lỗi server")
                .setText("Vui long thử lại!!!")
                .setBackgroundColorRes(R.color.red) // or setBackgroundColorInt(Color.CYAN)
                .show();

        dialog.dismiss();
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
    public void onClick(View view) {
        int r = view.getId();
        switch (r){
            case R.id.tvLogin:
                Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
                break;

            case R.id.btnConfirm:
                String password = editResetPW.getText().toString();
                String confirmPassword = editConfirmResetPW.getText().toString();
                String token = getIntent().getData().getQueryParameter("t");
                resetPasswordPresenterImpl.confirm(password, confirmPassword, token);
                break;
        }
    }
    private class Progress extends AsyncTask<Void, Void, Void> {
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
            return null;
        }
    }

}
