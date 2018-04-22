package io.awesome.app.Presenter.MenuTabs;

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
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.awesome.app.Model.Ordered;
import io.awesome.app.View.MenuTabs.MenuTabsView;

import static io.awesome.app.View.MenuTabs.MenuTabsActivity.listOrdered;
import static io.awesome.app.View.Main.MainActivity.receiptId;

/**
 * Created by sung on 18/04/2018.
 */

public class MenuTabsPresenterImp implements MenuTabsPresenter {

    private Context context;
    private MenuTabsView tabsView;


    public MenuTabsPresenterImp(Context context, MenuTabsView tabsView) {
        this.context = context;
        this.tabsView = tabsView;
    }

    @Override
    public void getMenuReceipt(final String token, final int statusReceipt) {
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
                    tabsView.goToMenu(statusReceipt);
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
}
