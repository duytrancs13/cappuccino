package io.awesome.app.Presenter.Expense;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
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
import io.awesome.app.View.Expense.ExpenseActivity;
import io.awesome.app.View.Expense.ExpenseView;

import static io.awesome.app.View.Main.MainActivity.account;
import static io.awesome.app.View.Table.TableActivity.listExpense;

/**
 * Created by sung on 09/06/2018.
 */

public class ExpensePresenterImp implements ExpensePresenter {

    private Context context;
    private ExpenseView expenseView;
    private String token;

    public ExpensePresenterImp(Context context, ExpenseView expenseView, String token) {
        this.context = context;
        this.expenseView = expenseView;
        this.token = token;
    }

    @Override
    public void getExpense() {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url= "https://cafeteria-service.herokuapp.com/api/v1/users/"+ account.getUserId()+"/expenses";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", token);
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }
}
