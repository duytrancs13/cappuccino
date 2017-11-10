package info.devexchanges.navvp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.tapadoo.alerter.Alerter;

import org.json.JSONException;
import org.json.JSONObject;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ForgotPWActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private EditText editEmailForgotPW;

    private Button btnEmailForgotPW;

    private ImageView clearFogotPW;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pw);

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
        btnEmailForgotPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editEmailForgotPW.getText().toString();
                forgot();
            }
        });



    }

    private void alertSuccessfull(){
        Alerter.create(this)
                .setTitle("Thành công")
                .setText("Vui lòng xác nhận Email")
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


    private void forgot(){
        if(!validate()){
            return;
        }
        btnEmailForgotPW.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(ForgotPWActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Đang kết nối...");
        progressDialog.show();

        final String email = editEmailForgotPW.getText().toString();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        attemptForgot(email);
                        progressDialog.dismiss();
                    }
                }, 2000);
    }

    private void attemptForgot(final String email){
        String url = "https://cappuccino-hello.herokuapp.com/api/forgot?email="+email;
        RequestQueue queue = Volley.newRequestQueue(getBaseContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int flag = jsonObject.getInt("flag");
                    if(flag == 1){
                        alertSuccessfull();
                        editEmailForgotPW.setText("");
                        clearFogotPW.setVisibility(View.GONE);

                    }else if(flag == 0){
                        alertFailed();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);
    }


    private boolean validate(){
        boolean valid = true;

        String email = editEmailForgotPW.getText().toString();
        if(email.isEmpty()){
            editEmailForgotPW.setError("Vui lòng nhập Email");
            valid = false;
        }else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editEmailForgotPW.setError("Sai định dạng Email");
            valid = false;
        }else{
            editEmailForgotPW.setError(null);
        }

        return valid;
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

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
