package io.awesome.app.Presenter.Menu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
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
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.awesome.app.Model.Menu;
import io.awesome.app.Model.Ordered;
import io.awesome.app.Model.Receipt;
import io.awesome.app.View.Adapter.CustomMenuAdapter;
import io.awesome.app.View.Fragment.Menu.FragmentMenu;

import static io.awesome.app.View.Main.MainActivity.listMenu;
import static io.awesome.app.View.Main.MainActivity.receiptId;
import static io.awesome.app.View.Table.TableActivity.listOrdered;

/**
 * Created by sung on 11/11/2017.
 */

public class MenuPresenterImpl implements MenuPresenter {

    private FragmentMenu fragmentMenu;
    private Context context;


    public MenuPresenterImpl(FragmentMenu fragmentMenu, Context context ) {
        this.fragmentMenu = fragmentMenu;
        this.context = context;
    }

    @Override
    public void loadMenuOfFavorite(final String token) {
        fragmentMenu.showProgress();
        String urlFavorite ="https://cafeteria-service.herokuapp.com/api/v1/menu";
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlFavorite, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.v("AAA","Get menu successfull "+response.toString());
                fragmentMenu.showMenu(listMenu);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("loi",error.toString());
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
    public void loadMenuOfCategory(String idCategory,final String token) {
        fragmentMenu.showProgress();
        String urlMenuOfCategory ="https://cafeteria-service.herokuapp.com/api/v1/categories/"+idCategory;

        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlMenuOfCategory, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                try {
                    JSONObject obj = new JSONObject(response.toString());
                    JSONArray data = obj.getJSONArray("data");
                    TypeToken<List<Menu>> token = new TypeToken<List<Menu>>() {};
                    listMenu = gson.fromJson(data.toString(), token.getType());
                    fragmentMenu.showMenu(listMenu);
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
    public void setTextPopupNoteAdd(String textNote, Ordered ordered) {
        fragmentMenu.setTextPopupNodeAdd(textNote, ordered);
    }

    @Override
    public void showPopupNoteAdd() {
        fragmentMenu.showPopupNodeAdd();
    }

    /*@Override
    public void addNoteForReceipt(final String textNote, String menuId, final String token) {
        String url = "https://cafeteria-service.herokuapp.com/api/v1/receipts/"+receiptId+"/items/"+menuId;
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.PATCH, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                toast("Add item successful !!!");
                getMenuReceipt(token);
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
                params.put("quantity","0");
                params.put("note",textNote);
                return params;
            }
        };

        queue.add(stringRequest);
    }*/



    /*@Override
    public void qualityForReceipt(String menuId, final String token, final String quality) {
        String url = "https://cafeteria-service.herokuapp.com/api/v1/receipts/"+receiptId+"/items/"+menuId;
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.PATCH, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                toast("Add item successful !!!");
                getMenuReceipt(token);
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
    }*/

    @Override
    public void getMenuReceipt(final String token) {
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

    /*public void addItemForReceipt(String url, final String token, final String quantity, final String note, final String menuId){

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.PATCH, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                toast("Add item successful !!!");
                //fragmentMenu.setTextPopupNodeAdd(note,menuId);
                getMenuReceipt(token);
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
                params.put("quantity",quantity);
                params.put("note",note);
                return params;
            }
        };

        queue.add(stringRequest);
    }*/

    public void toast(String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }


}
