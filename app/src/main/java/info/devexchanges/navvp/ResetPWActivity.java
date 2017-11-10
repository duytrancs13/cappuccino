package info.devexchanges.navvp;

import android.app.ProgressDialog;
import android.content.Context;
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

import info.devexchanges.navvp.View.Login.LoginActivity;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ResetPWActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private EditText editResetPW,editConfirmResetPW;

    TextView tvLogin;

    private Button btnConfirm;

    private ImageView visibilityResetPW,visibilityConfirmResetPW;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pw);

        CalligraphyConfig.initDefault(
                new CalligraphyConfig.Builder()
                        .setDefaultFontPath("Roboto-Regular.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set ActionBar
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_back);
        upArrow.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvLogin = (TextView) findViewById(R.id.tvLogin);
        tvLogin.setText(Html.fromHtml("<u>Đăng nhập</u>"));
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResetPWActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });



        editResetPW = (EditText) findViewById(R.id.editResetPW);
        editConfirmResetPW = (EditText)findViewById(R.id.editConfirmResetPW);


        visibilityResetPW = (ImageView) findViewById(R.id.visibilityResetPW);

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


        visibilityConfirmResetPW = (ImageView) findViewById(R.id.visibilityConfirmResetPW);
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

        btnConfirm = (Button) findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirm();
            }
        });




    }

    private void confirm(){
        if(!validate()){
            return;
        }

        btnConfirm.setEnabled(false);


        final ProgressDialog progressDialog = new ProgressDialog(ResetPWActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Đang kết nối...");
        progressDialog.show();

        final String password = editResetPW.getText().toString();
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        attemptReset(password);
                        progressDialog.dismiss();
                    }
                }, 2000);

    }

    private void alertSuccessfull(){
        Alerter.create(this)
                .setTitle("Thành công")
                .setText("Vui lòng đăng nhập")
                .setBackgroundColorRes(R.color.colorPrimary) // or setBackgroundColorInt(Color.CYAN)
                .show();
    }
    private void alertFailed(){
        Alerter.create(this)
                .setTitle("Lỗi")
                .setText("Email không chưa đăng kí")
                .setBackgroundColorRes(R.color.red) // or setBackgroundColorInt(Color.CYAN)
                .show();
    }

    private void attemptReset(final String password){
        String url = "https://cappuccino-hello.herokuapp.com/api/user/confirm";
        final String token = getIntent().getData().getQueryParameter("t");
        RequestQueue queue = Volley.newRequestQueue(getBaseContext());
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int flag = jsonObject.getInt("flag");
                    if(flag == 1){
                        editResetPW.setText("");
                        visibilityResetPW.setVisibility(View.GONE);

                        editConfirmResetPW.setText("");
                        visibilityConfirmResetPW.setVisibility(View.GONE);
                        alertSuccessfull();
                    }else{
                        alertFailed();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                toast(error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("password", password);
                params.put("token", token);
                return params;
            }
        };

        queue.add(stringRequest);
    }

    private boolean validate(){
        boolean valid = true;
        String PW = editResetPW.getText().toString();
        String confirmPW = editConfirmResetPW.getText().toString();
        if(PW.isEmpty() ){
            editResetPW.setError("Vui lòng nhập mật khẩu mới");
            valid = false;
            return valid;
        }else if(PW.length() < 6){
            editResetPW.setError("Ít nhất 5 kí tự");
            valid = false;
            return valid;
        }else{
            editResetPW.setError(null);
        }

        if(confirmPW.isEmpty() ){
            editConfirmResetPW.setError("Vui lòng nhập xác nhận mật khẩu mới");
            valid = false;
            return valid;
        }else if(!confirmPW.equals(PW)){
            editConfirmResetPW.setError("Không khớp");
            valid = false;
        }else{
            editConfirmResetPW.setError(null);
        }

        return valid;
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

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
