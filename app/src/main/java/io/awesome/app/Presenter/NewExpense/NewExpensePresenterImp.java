package io.awesome.app.Presenter.NewExpense;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import io.awesome.app.View.NewExpense.NewExpenseView;

/**
 * Created by sung on 09/06/2018.
 */

public class NewExpensePresenterImp implements NewExpensePresenter {

    private NewExpenseView newExpenseView;
    private Context context;

    public NewExpensePresenterImp(NewExpenseView newExpenseView, Context context) {
        this.newExpenseView = newExpenseView;
        this.context = context;
    }

    public boolean validate(String name, String price, String quatity) {
        boolean valid = true;
        if(name.isEmpty()){
            newExpenseView.alertMessage("Lỗi", "Vui lòng nhập tên hàng", 404);
            valid = false;
            return valid;
        }else if(price.isEmpty()){
            newExpenseView.alertMessage("Lỗi", "Vui lòng nhập giá", 404);
            valid = false;
            return valid;
        }else if(quatity.isEmpty()){
            newExpenseView.alertMessage("Lỗi", "Vui lòng nhập số lượng", 404);
            valid = false;
            return valid;
        }
        return valid;
    }

    @Override
    public void validateNewExpense(String name, String price, String quatity) {
        if(!validate(name, price, quatity)){
            return;
        }
        newExpenseView.showProgress();

    }

    @Override
    public void createExpense(final String name, final String price, final String quantity, final String createBy, final String note, final String token) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://cafeteria-service.herokuapp.com/api/v1/expenses";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("AAA","Created expense successful "+response.toString());
                newExpenseView.alertMessage("Thành công","Món hàng đã được thêm vào chi tiêu", 200);
                newExpenseView.createExpenseSuccessful();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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
                params.put("name",name);
                params.put("price",price);
                params.put("note",note);
                params.put("createBy",createBy);
                params.put("quantity",quantity);
                return params;
            }
        };

        queue.add(stringRequest);

    }
}
