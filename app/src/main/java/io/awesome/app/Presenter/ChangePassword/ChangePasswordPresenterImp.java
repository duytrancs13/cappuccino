package io.awesome.app.Presenter.ChangePassword;

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

import io.awesome.app.View.ChangePassword.ChangePasswordView;

import static io.awesome.app.View.Main.MainActivity.account;
import static io.awesome.app.View.Main.MainActivity.receiptId;

/**
 * Created by sung on 06/06/2018.
 */

public class ChangePasswordPresenterImp implements ChangePasswordPresenter{

    private ChangePasswordView changePasswordView;
    private Context context;

    public ChangePasswordPresenterImp(ChangePasswordView changePasswordView, Context context) {
        this.changePasswordView = changePasswordView;
        this.context = context;
    }
    public boolean validatePW(String password) {
        boolean valid = true;
        if(password.isEmpty() ){
            changePasswordView.errorPW("Vui lòng nhập mật khẩu !!!");
            valid = false;
            return valid;
        }else if(password.length() < 6){
            changePasswordView.errorPW("Ít nhất 5 kí tự !!!");
            valid = false;
            return valid;
        }else{
            changePasswordView.errorPW(null);
        }
        return valid;
    }
    public boolean validateNewPW(String password) {
        changePasswordView.showProgress();
        boolean valid = true;
        if(password.isEmpty() ){
            changePasswordView.errorChangePW("Vui lòng nhập mật khẩu !!!");
            valid = false;
            return valid;
        }else if(password.length() < 6){
            changePasswordView.errorChangePW("Ít nhất 5 kí tự !!!");
            valid = false;
            return valid;
        }else{
            changePasswordView.errorChangePW(null);
        }
        return valid;
    }

    @Override
    public void validateChange(String oldPassword, String newPassword) {
        if(!validatePW(oldPassword) || !validateNewPW(newPassword)){
            return;
        }
        changePasswordView.showProgress();
    }

    @Override
    public void change(final String oldPassword, final String newPassword, final String token) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://cafeteria-service.herokuapp.com/api/v1/users/profile/"+account.getUserId();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.get("message").equals("Successful.")) {
                        changePasswordView.alertMessage("Thành công", "Đổi mật khẩu thành công", 200);
                        changePasswordView.changePwSuccessful();
                    } else {
                        changePasswordView.alertMessage("Thất bại", "Đổi mật khẩu thất bại", 500);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                changePasswordView.alertMessage("Lỗi server","Vui lòng thử lại", 500);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", token);
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("oldPassword",oldPassword);
                params.put("newPassword",newPassword);
                return params;
            }
        };
        queue.add(stringRequest);
    }
}
