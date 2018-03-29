package io.awesome.app.View.ForgotPassword;

import android.app.ProgressDialog;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.tapadoo.alerter.Alerter;


import io.awesome.app.General.SetFont;
import io.awesome.app.Presenter.ForgotPassword.ForgotPasswordPresenterImpl;

import io.awesome.app.R;
import io.awesome.app.View.CustomEditText.CustomEditTextEmailTextWatcher;

public class ForgotPasswordActivity extends AppCompatActivity implements ForgotPasswordView, View.OnClickListener{

    private Toolbar toolbar;

    private EditText editEmailForgotPW;

    private Button btnEmailForgotPW;

    private ImageView clearFogotPW;


    private ForgotPasswordPresenterImpl forgotPasswordPresenterImpl;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        //Set font
        SetFont setFont = new SetFont("Roboto-Regular.ttf");
        setFont.getFont();

        //Set toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set ActionBar
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_back);
        upArrow.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Ánh xạ
        dialog = new ProgressDialog(ForgotPasswordActivity.this,R.style.AppTheme_Dark_Dialog);

        editEmailForgotPW = (EditText) findViewById(R.id.editEmailForgotPW);
        clearFogotPW = (ImageView) findViewById(R.id.clearFogotPW);



        // Bắt sự kiện editText Email VISIBLE va click vào sẽ xóa text trong editText Email
        editEmailForgotPW.addTextChangedListener(new CustomEditTextEmailTextWatcher(editEmailForgotPW,clearFogotPW));


        // Bắt sự kiện khi bấm vào nút "GỬI"
        btnEmailForgotPW = (Button) findViewById(R.id.btnEmailForgotPW);
        btnEmailForgotPW.setOnClickListener(this);


        //Init ForgotPasswordPresenterImpl()
        forgotPasswordPresenterImpl = new ForgotPasswordPresenterImpl(this,this.getBaseContext());



    }



    // Interface errorEmail khi editText rỗng, hoặc không đúng định dạng Email.
    @Override
    public void errorEmail(String errorEmail) {
        editEmailForgotPW.setError(errorEmail);
    }


    ////////// Thông Báo khi email chưa đăng kí, hoặc lỗi SERVER
    @Override
    public void alertMessage(String titleError, String textError, int responseCode){
        dialog.dismiss();

        if(responseCode == 500){
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
            case R.id.btnEmailForgotPW:
                String email = editEmailForgotPW.getText().toString();
                forgotPasswordPresenterImpl.validateForgotPassword(email);
                break;
        }
    }


    // Đợi xử lý quên mật khẩu
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
            String email = editEmailForgotPW.getText().toString();
            forgotPasswordPresenterImpl.forgot(email);
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
