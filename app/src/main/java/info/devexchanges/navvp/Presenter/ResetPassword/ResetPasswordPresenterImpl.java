package info.devexchanges.navvp.Presenter.ResetPassword;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import info.devexchanges.navvp.View.ResetPassword.ResetPasswordView;

/**
 * Created by sung on 11/11/2017.
 */

public class ResetPasswordPresenterImpl implements ResetPasswordPresenter {

    private ResetPasswordView resetPasswordView;
    private Context context;

    public ResetPasswordPresenterImpl(ResetPasswordView resetPasswordView, Context context) {
        this.resetPasswordView = resetPasswordView;
        this.context = context;
    }

    @Override
    public boolean validate(String password, String confirmPassword) {
        boolean valid = true;
        if(password.isEmpty() ){
            resetPasswordView.alertErrorEmptyPassword();
            valid = false;
            return valid;
        }else if(password.length() < 6){
            resetPasswordView.alertErrorMinPassword();
            valid = false;
            return valid;
        }else{
            resetPasswordView.alertSuccessPassword();
        }

        if(confirmPassword.isEmpty() ){
            resetPasswordView.alertErrorEmptyConfirmPassword();
            valid = false;
            return valid;
        }else if(!confirmPassword.equals(password)){
            resetPasswordView.alertErrorMatchConfirmPassword();
            valid = false;
        }else{
            resetPasswordView.alertSuccessConfirmPassword();
        }
        return valid;
    }

    @Override
    public void confirm(final String password, final String confirmPassword, final String token) {
        if(!validate(password, confirmPassword)){
            return;
        }
        resetPasswordView.showProgress();

        String url = "https://cappuccino-hello.herokuapp.com/api/user/confirm";
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int flag = jsonObject.getInt("flag");
                    if(flag == 1){
                        resetPasswordView.resetPasswordSuccessful();
                    }else{
                        resetPasswordView.resetPasswordFailed();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                resetPasswordView.resetPasswordErrorServer();
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




}
