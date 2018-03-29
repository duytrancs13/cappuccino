package io.awesome.app.Presenter.Receipt;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.awesome.app.Model.Receipt;
import io.awesome.app.View.Fragment.Receipt.FragmentReceipt;

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
    public void getMenuReceipt(String receiptId,String token) {

        final String url ="https://cappuccino-hello.herokuapp.com/api/receipt/"+receiptId+"?token="+token;
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest =new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Gson gson = new Gson();
                    JSONObject respo = response.getJSONObject("response");
                    receipt= gson.fromJson(respo.toString(),Receipt.class);
                    fragmentReceipt.loadMenuReceipt(receipt.getItems());


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(jsonObjectRequest);
    }

    @Override
    public void updateReceipt(String tableId, String token) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url="https://cappuccino-hello.herokuapp.com/api/table/"+tableId+"?token="+token;
        Log.v("url", url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("AAA",error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("remove", "true");
                return params;
            }
        };
        queue.add(jsonObjectRequest);
    }

    @Override
    public void addReceipt(String receiptId, String token, final String key, final String value) {
        String url = "https://cappuccino-hello.herokuapp.com/api/receipt/"+receiptId+"?token="+token;
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
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


}
