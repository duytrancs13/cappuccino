package io.awesome.app.Presenter.Category;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;

import io.awesome.app.Model.Category;
import io.awesome.app.View.Fragment.Category.FragmentCategory;

/**
 * Created by sung on 26/11/2017.
 */

public class CategoryPresenterImpl implements CategoryPresenter {
    private List<Category> listCategory;
    private Context context;
    private FragmentCategory fragmentCategory;

    public CategoryPresenterImpl(Context context, FragmentCategory fragmentCategory) {
        this.context = context;
        this.fragmentCategory = fragmentCategory;
    }

    @Override
    public void loadMenuCategory(String token) {
        String url ="https://cappuccino-hello.herokuapp.com/api/menu/category?token="+token;

        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest =  new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                listCategory = gson.fromJson(response.toString(),Category.ListCategory.class).getListCategory();
                fragmentCategory.showMenuCategory(listCategory);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        queue.add(jsonObjectRequest);
    }
}
