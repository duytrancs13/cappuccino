package io.awesome.app.Presenter.Pusher;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;

import java.util.List;

import io.awesome.app.Model.Category;
import io.awesome.app.View.Fragment.Category.FragmentCategory;
//import static io.awesome.app.View.Fragment.Category.FragmentCategory.categoryAdapter;
import static io.awesome.app.View.Main.MainActivity.listCategory;
import static io.awesome.app.View.Main.MainActivity.listMenu;

/**
 * Created by sung on 12/04/2018.
 */

public class PusherCategory {
    private static final String API_KEY = "aeadf645a84df411d55d";
    private static final String APP_CLUSTER = "ap1";
    private static final String CHANEL_NAME = "categories";
    private static final String EVENT_NAME = "all-categories";

    private FragmentCategory fragmentCategory;
    public PusherCategory(FragmentCategory fragmentCategory) {
        this.fragmentCategory = fragmentCategory;
    }

    public void subcribe(){

        PusherOptions options = new PusherOptions();

        options.setCluster(APP_CLUSTER);

        Pusher pusher = new Pusher(API_KEY,options);

        Channel channel = pusher.subscribe(CHANEL_NAME);


        channel.bind(EVENT_NAME, new SubscriptionEventListener() {
            @Override
            public void onEvent(String channel, String event, String data) {
                Gson gson = new Gson();
                TypeToken<List<Category>> token = new TypeToken<List<Category>>() {};
                listCategory = gson.fromJson(data, token.getType());
                fragmentCategory.showMenuCategory();
            }
        });
        pusher.connect();
    }
}
