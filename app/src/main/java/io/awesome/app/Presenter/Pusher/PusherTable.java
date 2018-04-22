package io.awesome.app.Presenter.Pusher;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;

import java.util.List;

import io.awesome.app.Model.Table;
import io.awesome.app.View.Table.TableView;

import static io.awesome.app.View.Main.MainActivity.listTable;

/**
 * Created by sung on 12/04/2018.
 */


public class PusherTable {
    private static final String API_KEY = "aeadf645a84df411d55d";
    private static final String APP_CLUSTER = "ap1";
    private static final String CHANEL_NAME = "tables";
    private static final String EVENT_NAME = "all-tables";

    public static void subcribe(){
        PusherOptions options = new PusherOptions();
        options.setCluster(APP_CLUSTER);

        Pusher pusher = new Pusher(API_KEY,options);

        Channel channel = pusher.subscribe(CHANEL_NAME);

        channel.bind(EVENT_NAME, new SubscriptionEventListener() {
            @Override
            public void onEvent(String channel, String event, String data) {
                Gson gson = new Gson();
                TypeToken<List<Table>> token = new TypeToken<List<Table>>() {};
                listTable = gson.fromJson(data, token.getType());
            }
        });
        pusher.connect();
    }





}
