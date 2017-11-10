package info.devexchanges.navvp.Presenter.Login;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sung on 10/11/2017.
 */

public class Login implements InterfaceLogin {

    info.devexchanges.navvp.View.Login.InterfaceLogin viewLoginActivity;
    Context context;

    public Login(info.devexchanges.navvp.View.Login.InterfaceLogin viewLoginActivity, Context context) {
        this.viewLoginActivity=viewLoginActivity;
        this.context=context.getApplicationContext();
        ;
    }

    @Override
    public boolean validate(String email, String password) {
        boolean valid = true;

        if(email.isEmpty()){
            viewLoginActivity.alertErrorEmptyEmail();
            valid = false;
            return valid;
        }else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            viewLoginActivity.alertErrorFormatEmail();
            valid = false;
            return valid;
        }else{
            viewLoginActivity.alertSuccessEmail();
        }

        if(password.isEmpty() ){
            viewLoginActivity.alertErrorEmptyPassword();
            valid = false;
            return valid;
        }else if(password.length() < 6){
            viewLoginActivity.alertErrorMinPassword();
            valid = false;
        }else{
            viewLoginActivity.alertSuccessPassword();
        }
        return valid;
    }

    @Override
    public void login(String email,String password) {
        if(!validate(email,password)){
            return;
        }
        viewLoginActivity.showProgress();
        attemptLogin(email,password);

    }

    @Override
    public void attemptLogin(final String email, final String password) {
        String url = "https://cappuccino-hello.herokuapp.com/api/login";
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int flag = jsonObject.getInt("flag");
                    if(flag == 1){
                        String token = jsonObject.getJSONObject("response").getString("token");
                        viewLoginActivity.loginSuccessful(token);

                    }else{
                        viewLoginActivity.alertFailed();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }



        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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
    /*private void attemptLogin(final String email, final String password){
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

    }*/




}
