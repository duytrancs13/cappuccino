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
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.view.MotionEvent;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginActivity extends AppCompatActivity  {

    private Toolbar toolbar;
    private TextView tvForgotPw;
    private ImageView clearLogin,visibilityLoginPW;



    private EditText editEmailLogin, editPwLogin;
    private Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

        //tvForgotPw
        tvForgotPw = (TextView) findViewById(R.id.tvForgotPw);
        tvForgotPw.setText(Html.fromHtml("<u>Quên mật khẩu</u>"));
        tvForgotPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgotPWActivity.class);
                startActivity(intent);
            }
        });


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
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

    }

    private void login() {
        if(!validate()){
            return;
        }

        btnLogin.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Đang kết nối...");
        progressDialog.show();

        final String email = editEmailLogin.getText().toString();
        final String password = editPwLogin.getText().toString();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        attemptLogin(email,password);
                        progressDialog.dismiss();
                    }
                }, 2000);

    }


    private void attemptLogin(final String email, final String password){
        String url = "https://cappuccino-hello.herokuapp.com/api/login";

        RequestQueue queue = Volley.newRequestQueue(getBaseContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int flag = jsonObject.getInt("flag");
                    if(flag == 1){
                        editEmailLogin.setText("");
                        clearLogin.setVisibility(View.GONE);

                        editPwLogin.setText("");
                        visibilityLoginPW.setVisibility(View.GONE);

                        String token = jsonObject.getJSONObject("response").getString("token");
                        btnLogin.setEnabled(true);
                        Intent intent = new Intent(LoginActivity.this, TableActivity.class);
                        intent.putExtra("token",token);
                        startActivity(intent);
                    }else{
                        alertFailed();
                    }
                } catch (JSONException e) {
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
                params.put("email",email);
                params.put("password",password);
                return params;
            }
        };
        queue.add(stringRequest);

    }

    private void alertFailed(){
        Alerter.create(this)
                .setTitle("Lỗi")
                .setText("Email không chưa đăng kí")
                .setBackgroundColorRes(R.color.red) // or setBackgroundColorInt(Color.CYAN)
                .show();
    }




    private boolean validate(){
        boolean valid = true;

        String email = editEmailLogin.getText().toString();
        String password = editPwLogin.getText().toString();

        if(email.isEmpty()){
            editEmailLogin.setError("Vui lòng nhập Email");
            valid = false;
            return valid;
        }else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editEmailLogin.setError("Sai định dạng Email");
            valid = false;
            return valid;
        }else{
            editEmailLogin.setError(null);
        }

        if(password.isEmpty() ){
            editPwLogin.setError("Vui lòng nhập mật khẩu");
            valid = false;
            return valid;
        }else if(password.length() < 6){
            editPwLogin.setError("Ít nhất 5 kí tự");
            valid = false;
        }else{
            editPwLogin.setError(null);
        }

        return valid;
    }

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
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
}
