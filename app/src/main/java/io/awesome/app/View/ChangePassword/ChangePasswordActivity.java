package io.awesome.app.View.ChangePassword;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import io.awesome.app.General.SetFont;
import io.awesome.app.Presenter.ChangePassword.ChangePasswordPresenterImp;
import io.awesome.app.R;
import io.awesome.app.View.CustomEditText.CustomEditTextPWTextWatcher;

public class ChangePasswordActivity extends AppCompatActivity implements ChangePasswordView {
    private Toolbar toolbar;
    public static final String MyPREFERENCES = "capuccino" ;
    private SharedPreferences sharedPreferences;
    private String token;


    private EditText editOldChangePW,editNewChangePW;
    private ImageView visibilityOldChangePW,visibilityNewChangePW;

    private Button btnConfirm;

    private ProgressDialog dialog ;
    private ChangePasswordPresenterImp changePasswordPresenterImp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        SetFont setFont = new SetFont("Roboto-Regular.ttf");
        setFont.getFont();


        sharedPreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        token = sharedPreferences.getString("token", null);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Đổi mật khẩu");


        // Set ActionBar
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_back);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dialog = new ProgressDialog(this,R.style.AppTheme_Dark_Dialog);
        changePasswordPresenterImp = new ChangePasswordPresenterImp(this, this.getBaseContext());

        editOldChangePW = (EditText) findViewById(R.id.editOldChangePW);
        visibilityOldChangePW = (ImageView) findViewById(R.id.visibilityOldChangePW);

        editNewChangePW = (EditText) findViewById(R.id.editNewChangePW);
        visibilityNewChangePW = (ImageView) findViewById(R.id.visibilityNewChangePW);

        //Bắt sự kiện editText Password VISIBLE va click vào sẽ xóa text trong editText Password
        editOldChangePW.addTextChangedListener(new CustomEditTextPWTextWatcher(editOldChangePW,visibilityOldChangePW));


        //Bắt sự kiện editText ConfirmPassword VISIBLE va click vào sẽ xóa text trong editText ConfirmPassword
        editNewChangePW.addTextChangedListener(new CustomEditTextPWTextWatcher(editNewChangePW,visibilityNewChangePW));

        btnConfirm = (Button) findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPassword = editOldChangePW.getText().toString();
                String newPassword = editNewChangePW.getText().toString();
                changePasswordPresenterImp.validateChange(oldPassword, newPassword);
            }
        });

    }

    // ĐỢi xử lý quá trình RESET
    @Override
    public void showProgress() {
        new Progress().execute();
    }

    @Override
    public void alertMessage(String titleError, String textError, int responseCode) {

    }

    @Override
    public void errorPW(String errorMessage) {
        editOldChangePW.setError(errorMessage);
    }

    @Override
    public void errorChangePW(String errorMessage) {
        editNewChangePW.setError(errorMessage);
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
            String oldPassword = editOldChangePW.getText().toString();
            String newPassword = editNewChangePW.getText().toString();
            changePasswordPresenterImp.change(oldPassword, newPassword,token);
            dialog.dismiss();
            return null;
        }
    }


}
