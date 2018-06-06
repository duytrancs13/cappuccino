package io.awesome.app.View.Fragment.Category;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.awesome.app.Presenter.Category.CategoryPresenterImpl;
import io.awesome.app.Presenter.Pusher.PusherCategory;
import io.awesome.app.View.Adapter.CustomCategoryAdapter;
import io.awesome.app.Model.Category;
import io.awesome.app.R;
import io.awesome.app.View.Fragment.Menu.FragmentMenu;

import static android.content.Context.MODE_PRIVATE;
import static io.awesome.app.View.Main.MainActivity.listCategory;
import static io.awesome.app.View.Main.MainActivity.onSubcribeCategory;

public class FragmentCategory extends Fragment implements FragmentCategoryView{
    private ListView lvCategory;


    private static final String API_KEY = "aeadf645a84df411d55d";
    private static final String APP_CLUSTER = "ap1";
    private static final String CHANEL_NAME = "categories";
    private static final String EVENT_NAME = "all-categories";



    public static final String MyPREFERENCES = "capuccino" ;

    private CategoryPresenterImpl categoryPresenter;

    private CustomCategoryAdapter categoryAdapter;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        lvCategory = (ListView) view.findViewById(R.id.lvCategory);

        SharedPreferences prefs = this.getActivity().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        String token = prefs.getString("token", null);

        categoryPresenter = new CategoryPresenterImpl(view.getContext(),this);

        categoryAdapter = new CustomCategoryAdapter(getContext(), listCategory);
        lvCategory.setAdapter(categoryAdapter);





        if(onSubcribeCategory){

            onSubcribeCategory = false;


            PusherOptions options = new PusherOptions();

            options.setCluster(APP_CLUSTER);

            Pusher pusher = new Pusher(API_KEY,options);

            Channel channel = pusher.subscribe(CHANEL_NAME);


            channel.bind(EVENT_NAME, new SubscriptionEventListener() {
                @Override
                public void onEvent(String channel, String event, final String data) {

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            // this will run in the main thread

                            Gson gson = new Gson();
                            TypeToken<List<Category>> token = new TypeToken<List<Category>>() {};
                            listCategory = gson.fromJson(data, token.getType());
                            categoryAdapter.realTimeCategory(listCategory);
                        }
                    });


                }
            });
            pusher.connect();
        }else{
            categoryPresenter.loadMenuCategory(token);
        }


        return view;
    }

    public void toast(String message){
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }






    @Override
    public void showMenuCategory() {

        categoryAdapter.realTimeCategory(listCategory);
        lvCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                FragmentMenu fragmentMenu = new FragmentMenu(1,listCategory.get(position).getId());
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_root,fragmentMenu).commit();
            }
        });


    }



}
