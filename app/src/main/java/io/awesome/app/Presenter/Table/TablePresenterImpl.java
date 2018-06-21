package io.awesome.app.Presenter.Table;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import java.util.List;
import java.util.Map;


import io.awesome.app.Model.Ordered;
import io.awesome.app.View.Table.TableView;

import static io.awesome.app.View.Main.MainActivity.listTable;
import static io.awesome.app.View.Main.MainActivity.receiptId;
import static io.awesome.app.View.Table.TableActivity.listOrdered;


/**
 * Created by sung on 26/11/2017.
 */





public class TablePresenterImpl implements TablePresenter {
    private Context context;
    private TableView tableView;
    private String token;


    public TablePresenterImpl(Context context, TableView tableView, String token) {
        this.context = context;
        this.tableView = tableView;
        this.token = token;
    }

    // Gọi API của bàn, trả về list table rồi truyền vào hàm showTable()
    @Override
    public void loadTable() {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url= "https://cafeteria-service.herokuapp.com/api/v1/tables";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.v("AAA", "Get table successfull "+response.toString());
                try {
                    if(response.getString("message").equals("Successful.")){
                        tableView.showTables();
                    }else{
                        tableView.alertMessage("Lỗi !!!", "Vui lòng thử lại", 500);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tableView.alertMessage("Lỗi !!!", "Vui lòng thử lại", 500);
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

    @Override
    public void dragTable(LinearLayout linearLayout) {
        linearLayout.setOnTouchListener(new OnDragTouchListener(linearLayout));
    }

    // Gọi API cập nhật receipt cần có 2 param là idTable và token
    @Override
    public void createReceipt(final String idTable, final String token, final int position) {

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://cafeteria-service.herokuapp.com/api/v1/receipts";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("AAA","Created receipt successful "+response.toString());
                tableView.gotoMenu(listTable.get(position).getReceiptId(), 0);
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
                params.put("tableId",idTable);
                return params;
            }
        };

        queue.add(stringRequest);
    }

    @Override
    public void getMenuOrdered(final String token) {
        String url ="https://cafeteria-service.herokuapp.com/api/v1/receipts/"+receiptId;
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
                    tableView.gotoTransferOrdered();
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
        queue.add(jsonObjectRequest);
    }


    public void toast(String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }

}