package io.awesome.app.Presenter.ChooseMenu;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.awesome.app.View.Fragment.ChooseMenu.FragmentChooseMenu;

import static io.awesome.app.View.Main.MainActivity.receiptId;

/**
 * Created by sung on 16/06/2018.
 */



public class ChooseMenuPresenterImp implements ChooseMenuPresenter {
    private Context context;
    private FragmentChooseMenu fragmentChooseMenu;
    private String token;

    public ChooseMenuPresenterImp(Context context, FragmentChooseMenu fragmentChooseMenu, String token) {
        this.context = context;
        this.fragmentChooseMenu = fragmentChooseMenu;
        this.token = token;
    }

    @Override
    public void confirmOrdered(JSONObject object) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url="https://cafeteria-service.herokuapp.com/api/v1/receipts/"+receiptId+"/items";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getString("message").equals("Successful.")){
                        fragmentChooseMenu.alertMessage("Thành công", "Món đã được cập nhật", 200);
                    }else{
                        fragmentChooseMenu.alertMessage("Thất bại", "Vui lòng thử lại", 500);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                fragmentChooseMenu.alertMessage("Lỗi server", "Vui lòng thử lại", 500);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", token);
                headers.put("Content-Type","application/json");
                return headers;
            }
        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonObjectRequest);
    }
}
