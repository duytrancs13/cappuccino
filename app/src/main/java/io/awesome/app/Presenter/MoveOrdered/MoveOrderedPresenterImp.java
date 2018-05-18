package io.awesome.app.Presenter.MoveOrdered;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.awesome.app.Model.Ordered;
import io.awesome.app.View.MoveOrder.MoveOrderedView;

import static io.awesome.app.View.Main.MainActivity.listTable;
import static io.awesome.app.View.Main.MainActivity.receiptId;
import static io.awesome.app.View.Main.MainActivity.receiptToOrdered;
import static io.awesome.app.View.MoveOrder.MoveOrderActivity.listChooseTable;
import static io.awesome.app.View.MoveOrder.MoveOrderActivity.lstChooseTable;
import static io.awesome.app.View.Table.TableActivity.listOrdered;
//import static io.awesome.app.View.Table.TableActivity.listToOrdered;

/**
 * Created by sung on 23/04/2018.
 */

public class MoveOrderedPresenterImp implements MoveOrderedPresenter {

    private Context context;
    private MoveOrderedView orderedView;
    private String token;

    public MoveOrderedPresenterImp(Context context, MoveOrderedView orderedView , String token) {
        this.context = context;
        this.orderedView = orderedView;
        this.token = token;
    }

    public MoveOrderedPresenterImp() {
    }

    @Override
    public void getMenuOrdered() {
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
                    orderedView.initFragment();
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

    @Override
    public void getMenuToOrdered() {
        String url ="https://cafeteria-service.herokuapp.com/api/v1/receipts/"+receiptToOrdered;

        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest =new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Gson gson = new Gson();
                    List<Ordered> lstToOdered = new ArrayList<Ordered>();
                    JSONObject obj = new JSONObject(response.toString());
                    JSONObject data = obj.getJSONObject("data");
                    JSONArray items = data.getJSONArray("items");
                    TypeToken<List<Ordered>> token = new TypeToken<List<Ordered>>() {};
                    lstToOdered = gson.fromJson(items.toString(), token.getType());
                    lstChooseTable.put(receiptToOrdered,lstToOdered);
                    orderedView.FragmentToOrdered();
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

    @Override
    public void moveItemOrdered(String receiptId, String menuId, final String quality, final String statusOrdered) {
        String url = "https://cafeteria-service.herokuapp.com/api/v1/receipts/"+receiptId+"/items/"+menuId;
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.PATCH, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(statusOrdered == "moveOrdered") {
                    getMenuOrdered();
                }else if(statusOrdered == "toOrdered"){
                    //getMenuToOrdered();
                }
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
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                headers.put("Authorization", token);
                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("quantity",quality);
                return params;
            }
        };

        queue.add(stringRequest);
    }


    @Override
    public void createReceiptToOrdered(final String idTable, final int position, final Button buttonToOrdered) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://cafeteria-service.herokuapp.com/api/v1/receipts";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                receiptToOrdered = listTable.get(position).getReceiptId();
                listChooseTable.get(listChooseTable.size()-1).setReceiptId(receiptToOrdered);
                listChooseTable.get(listChooseTable.size()-1).setStatus("busy");

                buttonToOrdered.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        toast(receiptToOrdered);
                    }
                });
//                listToOrdered = new ArrayList<Ordered>();
                orderedView.FragmentToOrdered();

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

    public void toast(String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
}
