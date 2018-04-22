package io.awesome.app.Presenter.Receipt;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.awesome.app.Model.Ordered;
import io.awesome.app.Model.Receipt;
import io.awesome.app.View.Fragment.Receipt.FragmentReceipt;

import static io.awesome.app.View.Main.MainActivity.receiptId;
import static io.awesome.app.View.MenuTabs.MenuTabsActivity.listOrdered;

/**
 * Created by sung on 26/11/2017.
 */

public class ReceiptPresenterImpl implements ReceiptPresenter {
    private Receipt receipt;
    private Context context;
    private FragmentReceipt fragmentReceipt;

    public ReceiptPresenterImpl(Context context, FragmentReceipt fragmentReceipt) {
        this.context = context;
        this.fragmentReceipt = fragmentReceipt;
    }

    @Override
    public void getMenuReceipt(final String token) {
        /*String url ="https://cafeteria-service.herokuapp.com/api/v1/receipts/"+receiptId;
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest =new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Gson gson = new Gson();
                    JSONObject obj = new JSONObject(response.toString());
                    JSONObject data = obj.getJSONObject("data");
                    JSONArray items = data.getJSONArray("items");
                    TypeToken<List<Ordered>> token = new TypeToken<List<Ordered>>() {};
                    listOrdered = gson.fromJson(items.toString(), token.getType());

                    fragmentReceipt.loadMenuReceipt(listOrdered);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", token);
                return headers;
            }
        };
        queue.add(jsonObjectRequest);*/
        fragmentReceipt.loadMenuReceipt(listOrdered);

    }

    @Override
    public void updateReceipt(final String token) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url="https://cafeteria-service.herokuapp.com/api/v1/receipts/"+receiptId;
        StringRequest stringRequest = new StringRequest(Request.Method.PATCH, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("AAA", "Update pay receipt success: " +response);
                fragmentReceipt.gotoTable();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

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
                params.put("isPay","true");
                return params;
            }
        };
        queue.add(stringRequest);
    }

    @Override
    public void addReceipt(String receiptId, String token, final String key, final String value) {
        String url = "https://cappuccino-hello.herokuapp.com/api/receipt/"+receiptId+"?token="+token;
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("AAAA", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("BBB","duy");
                Log.v("AAAError:",error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(key,value);
                return params;
            }
        };

        queue.add(stringRequest);
    }

    public void toast(String msg) {
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }


}
