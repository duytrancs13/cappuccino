package io.awesome.app.Presenter.Account;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.awesome.app.View.Account.AccountView;

import static io.awesome.app.View.Main.MainActivity.account;

/**
 * Created by sung on 09/06/2018.
 */

public class AccountPresenterImp implements AccountPresenter {
    private Context context;
    private AccountView accountView;

    public AccountPresenterImp(Context context, AccountView accountView) {
        this.context = context;
        this.accountView = accountView;
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    @Override
    public void uploadImage(final Bitmap bitmap, final String token) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://cafeteria-service.herokuapp.com/api/v1/users/profile/"+account.getUserId();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("AAA", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("AAA", error.toString());
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
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                String image = getStringImage(bitmap);
                params.put("image", image);
                return params;
            }
        };

        queue.add(stringRequest);



    }

    @Override
    public void change(final String name, final String token) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://cafeteria-service.herokuapp.com/api/v1/users/profile/"+account.getUserId();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.get("message").equals("Successful.")){
                        accountView.alertMessage("Thành công","Thông tin đã được cập nhật thành công", 200);
                        account.setDisplayName(jsonObject.getJSONObject("data").getString("displayName"));
                    }else{
                        accountView.alertMessage("Thất bại","Vui lòng thử lại", 500);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                accountView.alertMessage("Lỗi server","Vui lòng thử lại", 500);
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
                params.put("displayName",name);
                return params;
            }
        };
        queue.add(stringRequest);
    }

    @Override
    public void uploadFile(String imagePath, String token) {
        String url = "https://cafeteria-service.herokuapp.com/api/v1/users/profile/"+account.getUserId();

        SimpleMultiPartRequest smr = new SimpleMultiPartRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("AAA", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("AAA", error.toString());
            }
        });
        smr.addFile("image", imagePath);
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        mRequestQueue.add(smr);
    }
}