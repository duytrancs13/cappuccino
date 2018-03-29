package io.awesome.app.Presenter.Table;

import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.awesome.app.Model.Table;
import io.awesome.app.View.Table.TableView;

/**
 * Created by sung on 26/11/2017.
 */

public class TablePresenterImpl implements TablePresenter {

    private Context context;
    private TableView tableView;
    private List<Table> listTable;

    public TablePresenterImpl(Context context, TableView tableView) {
        this.context = context;
        this.tableView = tableView;
    }

    // Gọi API của bàn, trả về list table rồi truyền vào hàm showTable()
    @Override
    public void loadTable(String token) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url= "https://cappuccino-hello.herokuapp.com/api/table?token="+token;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Gson gson = new Gson();
                listTable = new ArrayList<Table>();
                listTable = gson.fromJson(response.toString(),Table.ListTable.class).getResponse();
                tableView.showTable(listTable);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(jsonObjectRequest);

    }

    @Override
    public void dragTable(LinearLayout linearLayout) {
        linearLayout.setOnTouchListener(new OnDragTouchListener(linearLayout));
    }


    // Gọi API cập nhật receipt cần có 2 param là idTable và token
    @Override
    public void updateReceiptIdOfTable(final String idTable, String token) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://cappuccino-hello.herokuapp.com/api/table/"+idTable+"?token="+token;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject = response.getJSONObject("response");
                    String receiptId = jsonObject.getString("receiptId");

                    // ĐI đến màn hình Menu
                    tableView.goToPageMenu(receiptId,idTable);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("AAA",error.toString());
            }
        })
        /*{
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("remove", "true");
                return params;
            }
        }*/
        ;
        queue.add(jsonObjectRequest);
    }

    public void toast(String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }

}




