package io.awesome.app.Presenter.ResetPassword;

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

import io.awesome.app.View.ResetPassword.ResetPasswordView;

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
    public boolean validatePW(String password){
        boolean valid = true;
        if(password.isEmpty() ){
            resetPasswordView.errorPW("Vui lòng nhập mật khẩu !!!");
            valid = false;
            return valid;
        }else if(password.length() < 6){
            resetPasswordView.errorPW("Ít nhất 5 kí tự !!!");
            valid = false;
            return valid;
        }else{
            resetPasswordView.errorPW(null);
        }
        return valid;
    }

    @Override
    public boolean validateConfirmPassword(String password, String confirmPassword) {
        boolean valid = true;

        if( !validatePW(password)){
            valid = false;
            return false;
        }

        if(confirmPassword.isEmpty() ){
            resetPasswordView.errorConfirmPW("Vui lòng nhập xác nhận mật khẩu mới");
            valid = false;
            return valid;
        }else if(!confirmPassword.equals(password)){
            resetPasswordView.errorConfirmPW("Không khớp");
            valid = false;
            return valid;
        }else{
            resetPasswordView.errorConfirmPW(null);
        }
        return valid;
    }

    @Override
    public void validateReset(String password, String confirmPassword) {
        if(!validateConfirmPassword(password, confirmPassword)){
            return;
        }
        resetPasswordView.showProgress();
    }


    @Override
    public void reset(final String password, final String confirmPassword, final String token) {

        String url = "https://cappuccino-hello.herokuapp.com/api/user/confirm";
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int flag = jsonObject.getInt("flag");
                    if(flag == 1){
                        resetPasswordView.alertMessage("Thành công","Vui lòng đăng nhập!!!", 200);
                    }else{
                        resetPasswordView.alertMessage("Lỗi","Email không chưa đăng kí!!!", 500);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                resetPasswordView.alertMessage("Lỗi server","Vui lòng thử lại !!!", 500);
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
