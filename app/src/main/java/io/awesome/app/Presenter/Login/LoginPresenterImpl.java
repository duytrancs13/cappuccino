package io.awesome.app.Presenter.Login;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.awesome.app.Model.Account;
import io.awesome.app.View.Login.LoginView;

import static io.awesome.app.View.Main.MainActivity.account;

/**
 * Created by sung on 10/11/2017.
 */

public class LoginPresenterImpl implements LoginPresenter {

    private LoginView viewLoginActivity;
    private Context context;

    public LoginPresenterImpl(LoginView viewLoginActivity, Context context) {
        this.viewLoginActivity=viewLoginActivity;
        this.context=context.getApplicationContext();
    }


    // validate Email Gọi đến viewLoginActivity để thông báo lỗi
    @Override
    public boolean validateEmail(String email){
        boolean valid = true;
        if(email.isEmpty()){
            viewLoginActivity.errorEmail("Vui lòng nhập email !!!");
            valid = false;
            return valid;
        }else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            viewLoginActivity.errorEmail("Sai định dạng email !!!");
            valid = false;
            return valid;
        }else{
            viewLoginActivity.errorEmail(null);
        }
        return valid;
    }


    // validate Password Gọi đến viewLoginActivity để thông báo lỗi
    @Override
    public boolean validatePW(String password){
        boolean valid = true;
        if(password.isEmpty() ){
            viewLoginActivity.errorPW("Vui lòng nhập mật khẩu !!!");
            valid = false;
            return valid;
        }else if(password.length() < 6){
            viewLoginActivity.errorPW("Ít nhất 5 kí tự !!!");
            valid = false;
            return valid;
        }else{
            viewLoginActivity.errorPW(null);
        }
        return valid;
    }


    // Bắt đầu quá trình xử lí login
    @Override
    public void validateLogin(String email, String password) {
        if( !validateEmail(email) || !validatePW(password)){
            return;
        }
        viewLoginActivity.showProgress();
    }



    // gọi đến API LOGIN trả 1 trong 3 điều kiện

    // NẾu thành công trả về 1 token đến màn hình table
    // Lỗi tài khoản  hiển thị thông báo
    // Hoặc lỗi server hiển thị thông báo
    @Override
    public void login(final String email, final String password) {
        String url = "https://cafeteria-service.herokuapp.com/api/v1/users/login";
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String token = jsonObject.getString("token");
                    Gson gson = new Gson();
                    account = gson.fromJson(jsonObject.toString(), Account.class);
                    viewLoginActivity.loginSuccessful(token);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                viewLoginActivity.alertMessage("Lỗi server", "Vui lòng thử lại !!!", 500);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }

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



}
