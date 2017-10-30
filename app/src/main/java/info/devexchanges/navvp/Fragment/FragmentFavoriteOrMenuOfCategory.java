package info.devexchanges.navvp.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
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

import info.devexchanges.navvp.Adapter.CustomFavoriteOrMenuOfCategoryAdapter;
import info.devexchanges.navvp.Model.Menu;
import info.devexchanges.navvp.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class FragmentFavoriteOrMenuOfCategory extends Fragment {

    private GridView gvMenu;

    private List<Menu> listMenu;

    private CustomFavoriteOrMenuOfCategoryAdapter customFavoriteOrMenuOfCategoryAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_or_menu_of_category, container, false);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        gvMenu = (GridView) view.findViewById(R.id.gvMenu);

        listMenu = new ArrayList<Menu>();
        String token = getActivity().getIntent().getStringExtra("token");

        String urlFavorite ="https://cappuccino-hello.herokuapp.com/api/menu/favorite?token="+token;

        getMenu(urlFavorite);


        return view;
    }
    protected void attachBaseContext(Context newBase) {
        super.onAttach(CalligraphyContextWrapper.wrap(newBase));
    }
    public void toast(String message){
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }

    private void getMenu(String url){
        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                listMenu = gson.fromJson(response.toString(),ListMenu.class).getResponse();

                customFavoriteOrMenuOfCategoryAdapter = new CustomFavoriteOrMenuOfCategoryAdapter(getActivity(), listMenu);

                gvMenu.setAdapter(customFavoriteOrMenuOfCategoryAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsonObjectRequest);
    }
    private class ListMenu{
        private List<Menu> response;

        public List<Menu> getResponse() {
            return response;
        }

        public void setResponse(List<Menu> response) {
            this.response = response;
        }
    }
}
