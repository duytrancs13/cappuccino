package io.awesome.app.Presenter.Menu;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.awesome.app.Model.Menu;
import io.awesome.app.Model.Ordered;
import io.awesome.app.Model.Receipt;
import io.awesome.app.View.Fragment.Menu.FragmentMenu;

/**
 * Created by sung on 11/11/2017.
 */

public class MenuPresenterImpl implements MenuPresenter {

    private FragmentMenu fragmentMenu;
    private Context context;

    public MenuPresenterImpl(FragmentMenu fragmentMenu, Context context) {
        this.fragmentMenu = fragmentMenu;
        this.context = context;
    }


    // Hàm này được viết dùng chung cho "get Menu" và "get menu of categoriy"
    //Trả về 1 list Menu rồi gọi đến hàm getQualityMenu()

    public void getAllMenu(String url ,String idCategory, final String receiptId, final String token) {

        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                List<Menu> listMenu = new ArrayList<Menu>();
                listMenu = gson.fromJson(response.toString(),Menu.ListMenu.class).getResponse();

                getQualityMenu(receiptId,token,listMenu);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("loi",error.toString());
            }
        });

        queue.add(jsonObjectRequest);

    }

    // Hàm này trả về list Menu và lấy số lượng những món đã đặt từ receipt API.
    public void getQualityMenu(String receiptId, String token, final List<Menu> listMenu) {
        final String url ="https://cappuccino-hello.herokuapp.com/api/receipt/"+receiptId+"?token="+token;
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest =new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Gson gson = new Gson();
                    JSONObject respo = response.getJSONObject("response");

                    Receipt receipt = gson.fromJson(respo.toString(),Receipt.class);
                    List<Ordered> listOrdered = new ArrayList<Ordered>();
                    listOrdered = receipt.getItems();

                    for(Menu itemMenu: listMenu){
                        for(Ordered itemOrdered: listOrdered){
                            if(itemMenu.get_id().equals(itemOrdered.getItemId())){
                                itemMenu.setQuantity(itemOrdered.getQuantity());
                            }
                        }
                    }

                    fragmentMenu.showMenu(listMenu);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(jsonObjectRequest);
    }

    @Override
    public void loadMenuOfFavorite(String receiptId, String token) {
        fragmentMenu.showProgress();
        String urlFavorite ="https://cappuccino-hello.herokuapp.com/api/menu/favorite?token="+token;
        getAllMenu(urlFavorite, "",receiptId, token);
    }

    @Override
    public void loadMenuOfCategory(String receiptId, String idCategory, String token) {
        fragmentMenu.showProgress();
        /*listMenu = new ArrayList<Menu>();*/
        String urlMenuOfCategory ="https://cappuccino-hello.herokuapp.com/api/menu/category/"+idCategory+"?token="+token;
        getAllMenu(urlMenuOfCategory, idCategory, receiptId, token);
        //fragmentMenu.showMenu(listMenu);

        /*RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlMenuOfCategory, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                listMenu = gson.fromJson(response.toString(),Menu.ListMenu.class).getResponse();
                //getQualityMenu(idReceipt,token);
                fragmentMenu.showMenu(listMenu,listOrdered);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        queue.add(jsonObjectRequest);*/
    }

    @Override
    public void addReceipt(final String receiptId,String token,final String key,final String value) {
        String url = "https://cappuccino-hello.herokuapp.com/api/receipt/"+receiptId+"?token="+token;
        Log.v("url", value);
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("AAAA", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("BBB","duy");
                Log.v("AAAError:",error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(key,value);
                return params;
            }
        };

        queue.add(stringRequest);
    }

    @Override
    public void loadPopupNoteAdd() {
        fragmentMenu.showPopupNodeAdd();
    }

}
