package io.awesome.app.Presenter.UpdateExpense;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import io.awesome.app.View.DetailExpense.DetailExpenseView;

/**
 * Created by sung on 15/06/2018.
 */

public class UpdateExpensePresenterImp implements UpdateexpensePresenter {
    private Context context;
    private DetailExpenseView detailExpenseView;
    private String token;

    public UpdateExpensePresenterImp(Context context, DetailExpenseView detailExpenseView, String token) {
        this.context = context;
        this.detailExpenseView = detailExpenseView;
        this.token = token;
    }


    @Override
    public void updateExpense(final HashMap<String, String> hashMapUpdateExpense, String idExpense) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://cafeteria-service.herokuapp.com/api/v1/expenses/"+idExpense;
        StringRequest request = new StringRequest(Request.Method.PATCH, url, new Response.Listener<String>() {
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
                headers.put("Authorization",token);
                headers.put("Content-Type","application/x-www-form-urlencoded");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                for(Map.Entry<String, String> entry : hashMapUpdateExpense.entrySet()) {
                    params.put(entry.getKey(),entry.getValue());
                }
                return params;
            }
        };
        queue.add(request);
    }
}
