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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.awesome.app.Model.Expense;
import io.awesome.app.View.NewExpense.NewExpenseView;

import static io.awesome.app.View.Table.TableActivity.listExpense;

/**
 * Created by sung on 09/06/2018.
 */

public class NewExpensePresenterImp implements NewExpensePresenter {

    private NewExpenseView newExpenseView;
    private Context context;
    private String token;

    public NewExpensePresenterImp(NewExpenseView newExpenseView, Context context, String token) {
        this.newExpenseView = newExpenseView;
        this.context = context;
        this.token = token;
    }

    public boolean validate(String name, String price, String quantity, String unit) {
        boolean valid = true;
        if(name.isEmpty()){
            newExpenseView.alertMessage("Thông báo", "Vui lòng nhập tên hàng", 500);
            valid = false;
            return valid;
        }else if(price.isEmpty()){
            newExpenseView.alertMessage("Thông báo", "Vui lòng nhập giá", 500);
            valid = false;
            return valid;
        }else if(quantity.isEmpty()){
            newExpenseView.alertMessage("Thông báo", "Vui lòng nhập số lượng", 500);
            valid = false;
            return valid;
        }else if(unit.isEmpty()){
            newExpenseView.alertMessage("Thông báo", "Vui lòng nhập đơn vị hàng", 500);
            valid = false;
            return valid;
        }
        return valid;
    }

    @Override
    public void validateNewExpense(String name, String price, String quantity, String unit) {
        if(!validate(name, price, quantity, unit)){
            return;
        }
    }

    @Override
    public void createExpense(final String name, final String price, final String quantity, final String unit, final String createBy, final String note, final String token) {
        validateNewExpense(name, price, quantity, unit);
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://cafeteria-service.herokuapp.com/api/v1/expenses";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("message").equals("Successful.")) {
                        newExpenseView.alertMessage("Thành công", "Bạn đã tạo mới chi tiêu thành công", 200);
                    } else {
                        newExpenseView.alertMessage("Thất bại", "Vui lòng thử lại", 500);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                newExpenseView.alertMessage("Lỗi server","Vui lòng thử lại", 500);
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
                params.put("name", name);
                params.put("price", price);
                params.put("note", note);
                params.put("createBy", createBy);
                params.put("quantity", quantity);
                params.put("unit", unit);
                return params;
            }
        };

        queue.add(stringRequest);

    }
}
