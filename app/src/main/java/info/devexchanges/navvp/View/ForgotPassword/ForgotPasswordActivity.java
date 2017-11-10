package info.devexchanges.navvp.View.ForgotPassword;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.tapadoo.alerter.Alerter;


import info.devexchanges.navvp.General.SetFont;
import info.devexchanges.navvp.Presenter.ForgotPassword.ForgotPassword;

import info.devexchanges.navvp.R;

public class ForgotPasswordActivity extends AppCompatActivity implements InterfaceForgotPassword, View.OnClickListener{

    private Toolbar toolbar;

    private EditText editEmailForgotPW;

    private Button btnEmailForgotPW;

    private ImageView clearFogotPW;


    ForgotPassword forgotPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        SetFont setFont = new SetFont("Roboto-Regular.ttf");
        setFont.getFont();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set ActionBar
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_back);
        upArrow.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editEmailForgotPW = (EditText) findViewById(R.id.editEmailForgotPW);
        clearFogotPW = (ImageView) findViewById(R.id.clearFogotPW);



        editEmailForgotPW.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() > 0){
                    clearFogotPW.setVisibility(View.VISIBLE);
                    clearFogotPW.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            editEmailForgotPW.setText("");
                        }
                    });
                }else{
                    clearFogotPW.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnEmailForgotPW = (Button) findViewById(R.id.btnEmailForgotPW);
        btnEmailForgotPW.setOnClickListener(this);

        forgotPassword = new ForgotPassword(this,this.getBaseContext());



    }



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
    public void alertErrorEmptyEmail() {
        editEmailForgotPW.setError("Vui lòng nhập Email");
    }

    @Override
    public void alertErrorFormatEmail() {
        editEmailForgotPW.setError("Sai định dạng Email");
    }

    @Override
    public void alertSuccessEmail() {
        editEmailForgotPW.setError(null);
    }

    @Override
    public void showProgress() {
        final ProgressDialog progressDialog = new ProgressDialog(ForgotPasswordActivity.this,
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
    public void alertSuccessful(){
        Alerter.create(this)
                .setTitle("Thành công")
                .setText("Vui lòng xác nhận Email")
                .setBackgroundColorRes(R.color.colorPrimary) // or setBackgroundColorInt(Color.CYAN)
                .show();

        editEmailForgotPW.setText("");
        clearFogotPW.setVisibility(View.GONE);

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
    public void onClick(View view) {
        int r = view.getId();
        switch (r){
            case R.id.btnEmailForgotPW:
                String email = editEmailForgotPW.getText().toString();
                forgotPassword.forgot(email);
                break;
        }
    }
}
