package info.devexchanges.navvp.Presenter.ForgotPassword;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import info.devexchanges.navvp.View.ForgotPassword.*;

/**
 * Created by sung on 10/11/2017.
 */

public class ForgotPasswordPresenterImpl implements ForgotPasswordPresenter {

    private ForgotPasswordActivity forgotPasswordActivity;
    private Context context;

    public ForgotPasswordPresenterImpl(ForgotPasswordActivity forgotPasswordActivity, Context context) {
        this.forgotPasswordActivity = forgotPasswordActivity;
        this.context = context;
    }

    @Override
    public boolean validate(String email) {
        boolean valid = true;
        if(email.isEmpty()){
            forgotPasswordActivity.alertErrorEmptyEmail();
            valid = false;
        }else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            forgotPasswordActivity.alertErrorFormatEmail();
            valid = false;
        }else{
            forgotPasswordActivity.alertSuccessEmail();
        }
        return valid;
    }

    @Override
    public void forgot(String email) {
        if(!validate(email)){
            return;
        }
        forgotPasswordActivity.showProgress();

    }

    @Override
    public void attemptForgot(String email) {

        String url = "https://cappuccino-hello.herokuapp.com/api/forgot?email="+email;
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int flag = jsonObject.getInt("flag");
                    if(flag == 1){
                        forgotPasswordActivity.forgotPasswordSuccessful();
                    }else if(flag == 0){
                        forgotPasswordActivity.forgotPasswordFailed();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                forgotPasswordActivity.forgotPasswordErrorServer();
            }
        });
        queue.add(stringRequest);

    }
}
