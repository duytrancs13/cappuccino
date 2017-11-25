package info.devexchanges.navvp.Presenter.Menu;

import android.content.Context;
import android.content.Intent;

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

import info.devexchanges.navvp.Model.Menu;
import info.devexchanges.navvp.View.Fragment.Menu.FragmentMenu;

/**
 * Created by sung on 11/11/2017.
 */

public class MenuPresenterImpl implements MenuPresenter {

    private FragmentMenu fragmentMenu;
    private Context context;
    private List<Menu> listMenu;

    public MenuPresenterImpl(FragmentMenu fragmentFavoriteOrMenuOfCategory, Context context) {
        this.fragmentMenu = fragmentFavoriteOrMenuOfCategory;
        this.context = context;
    }


    @Override
    public void loadMenu(String token) {

        fragmentMenu.showProgress();
        listMenu = new ArrayList<Menu>();
        String urlFavorite ="https://cappuccino-hello.herokuapp.com/api/menu/favorite?token="+token;

        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlFavorite, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                listMenu = gson.fromJson(response.toString(),Menu.ListMenu.class).getResponse();
                fragmentMenu.showMenu(listMenu);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        queue.add(jsonObjectRequest);
    }

}
