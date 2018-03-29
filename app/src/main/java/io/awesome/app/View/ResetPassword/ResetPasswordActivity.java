package io.awesome.app.View.ResetPassword;

import android.app.ProgressDialog;
import android.content.Intent;
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

import com.tapadoo.alerter.Alerter;

import io.awesome.app.General.SetFont;
import io.awesome.app.Presenter.ResetPassword.ResetPasswordPresenterImpl;
import io.awesome.app.R;
import io.awesome.app.View.CustomEditText.CustomEditTextPWTextWatcher;
import io.awesome.app.View.Login.LoginActivity;

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


        // Đi đến màn hinh LOGIN
        tvLogin = (TextView) findViewById(R.id.tvLogin);
        tvLogin.setText(Html.fromHtml("<u>Đăng nhập</u>"));
        tvLogin.setOnClickListener(this);


        // Ánh Xạ
        dialog = new ProgressDialog(ResetPasswordActivity.this,R.style.AppTheme_Dark_Dialog);

        editResetPW = (EditText) findViewById(R.id.editResetPW);
        visibilityResetPW = (ImageView) findViewById(R.id.visibilityResetPW);

        editConfirmResetPW = (EditText)findViewById(R.id.editConfirmResetPW);
        visibilityConfirmResetPW = (ImageView) findViewById(R.id.visibilityConfirmResetPW);


        //Bắt sự kiện editText Password VISIBLE va click vào sẽ xóa text trong editText Password
        editResetPW.addTextChangedListener(new CustomEditTextPWTextWatcher(editResetPW,visibilityResetPW));


        //Bắt sự kiện editText ConfirmPassword VISIBLE va click vào sẽ xóa text trong editText ConfirmPassword
        editConfirmResetPW.addTextChangedListener(new CustomEditTextPWTextWatcher(editConfirmResetPW,visibilityConfirmResetPW));

        btnConfirm = (Button) findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(this);

        //initialize ResetPasswordPresenterImpl()
        resetPasswordPresenterImpl = new ResetPasswordPresenterImpl(this, this.getBaseContext());



    }

    // Interface errorPassword khi editText rỗng, hoặc không đúng định dạng Password.
    @Override
    public void errorPW(String errorMessage) {
        editResetPW.setError(errorMessage);
    }

    // Interface errorConfirmPassword khi editText rỗng, hoặc không đúng định dạng Password.
    @Override
    public void errorConfirmPW(String errorConfirmPW) {
        editConfirmResetPW.setError(errorConfirmPW);
    }



    // Thông Báo khi email chưa đăng kí, hoặc lỗi SERVER
    @Override
    public void alertMessage(String titleError, String textError, int responseCode){
        dialog.dismiss();
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
                resetPasswordPresenterImpl.validateReset(password, confirmPassword);
                break;
        }
    }



    // ĐỢi xử lý quá trình RESET
    @Override
    public void showProgress() {
        new Progress().execute();
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
            String password = editResetPW.getText().toString();
            String confirmPassword = editConfirmResetPW.getText().toString();
            String token = getIntent().getData().getQueryParameter("t");
            resetPasswordPresenterImpl.reset(password, confirmPassword,token);
            return null;
        }
    }


    // Bắt sự kiện để trở lại màn hình trước
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
