package io.awesome.app.Presenter.ForgotPassword;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import io.awesome.app.View.ForgotPassword.*;

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


    // validate Email Gọi đến viewLoginActivity để thông báo lỗi
    @Override
    public boolean validateEmail(String email){
        boolean valid = true;
        if(email.isEmpty()){
            forgotPasswordActivity.errorEmail("Vui lòng nhập email !!!");
            valid = false;
            return valid;
        }else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            forgotPasswordActivity.errorEmail("Sai định dạng email !!!");
            valid = false;
            return valid;
        }else{
            forgotPasswordActivity.errorEmail(null);
        }
        return valid;
    }


    // Bắt đầu quá trình xử lí quên mật khẩu
    @Override
    public void validateForgotPassword(String email) {
        if(!validateEmail(email)){
            return;
        }
        forgotPasswordActivity.showProgress();

    }

    // gọi đến API QUÊN MẬT KHẨU trả 1 trong 3 điều kiện

    // NẾu thành công hiển thị thông báo
    // Lỗi tài khoản  hiển thị thông báo
    // Hoặc lỗi server hiển thị thông báo
    @Override
    public void forgot(String email) {

        String url = "https://cappuccino-hello.herokuapp.com/api/forgot?email="+email;
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int flag = jsonObject.getInt("flag");
                    if(flag == 1){
                        forgotPasswordActivity.alertMessage("Thành công", "Vui lòng xác nhận Email",200);
                    }else if(flag == 0){
                        forgotPasswordActivity.alertMessage("Lỗi tài khoản", "Email chưa được đăng kí !!!", 500);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                forgotPasswordActivity.alertMessage("Lỗi server", "Vui lòng thử lại !!!", 500);
            }
        });
        queue.add(stringRequest);

    }
}
