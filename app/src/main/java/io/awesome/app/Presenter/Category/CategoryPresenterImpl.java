package io.awesome.app.Presenter.Category;

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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.awesome.app.Model.Category;
import io.awesome.app.View.Fragment.Category.FragmentCategory;

import static io.awesome.app.View.Main.MainActivity.listCategory;

/**
 * Created by sung on 26/11/2017.
 */

public class CategoryPresenterImpl implements CategoryPresenter {
    private Context context;
    private FragmentCategory fragmentCategory;

    public CategoryPresenterImpl(Context context, FragmentCategory fragmentCategory) {
        this.context = context;
        this.fragmentCategory = fragmentCategory;
    }

    @Override
    public void loadMenuCategory(final String token) {
        String url ="https://cafeteria-service.herokuapp.com/api/v1/categories";

        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest =  new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

               //fragmentCategory.showMenuCategory();
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
