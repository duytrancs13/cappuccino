package io.awesome.app.View.Expense;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;

import java.util.List;

import io.awesome.app.Model.Expense;
import io.awesome.app.Presenter.Expense.ExpensePresenterImp;
import io.awesome.app.R;
import io.awesome.app.View.Adapter.CustomExpenseAdapter;
import io.awesome.app.View.NewExpense.NewExpenseActivity;
import io.awesome.app.View.DetailExpense.DetailExpenseActivity;

import static io.awesome.app.View.Main.MainActivity.account;
import static io.awesome.app.View.Main.MainActivity.onSubcribeExpense;
import static io.awesome.app.View.Table.TableActivity.listExpense;

public class ExpenseActivity extends AppCompatActivity implements ExpenseView{

    private Toolbar toolbar;
    public static final String MyPREFERENCES = "capuccino" ;
    private SharedPreferences sharedPreferences;
    private String token;

    private Button btnNewExpense;
    private ListView lvExpense;
    private ExpensePresenterImp expensePresenterImp;
    private CustomExpenseAdapter customExpenseAdapter;

    private ProgressDialog dialog ;

    private static final String API_KEY = "aeadf645a84df411d55d";
    private static final String APP_CLUSTER = "ap1";
    private static final String CHANEL_NAME = "staff-expenses";
    private static final String EVENT_NAME = "expense-"+account.getUserId();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        token = sharedPreferences.getString("token", null);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Quản lý chi tiêu");
        dialog = new ProgressDialog(ExpenseActivity.this,R.style.AppTheme_Dark_Dialog);

        // Set ActionBar
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_back);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lvExpense = (ListView) findViewById(R.id.lvExpense);
        expensePresenterImp = new ExpensePresenterImp(this, this, token);

        customExpenseAdapter = new CustomExpenseAdapter(this, listExpense);


        if(onSubcribeExpense){

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

                            Gson gson = new Gson();
                            TypeToken<List<Expense>> token = new TypeToken<List<Expense>>() {};
                            listExpense = gson.fromJson(data.toString(), token.getType());
                            if(!onSubcribeExpense){
                                showExpense();
                            }

                        }
                    });
                }
            });
            pusher.connect();
        }

        showProgress();

        btnNewExpense = (Button) findViewById(R.id.btnNewExpense);
        btnNewExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExpenseActivity.this, NewExpenseActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    // Quay về màn hình tableActivity bằng nút back của android
    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void showProgress() {
        new Progress().execute();
    }

    private class Progress extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Đang tải...");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            expensePresenterImp.getExpense();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            lvExpense.setAdapter(customExpenseAdapter);
            dialog.dismiss();
        }
    }

    @Override
    public void showExpense() {
        customExpenseAdapter.realTimeExpense(listExpense);
        lvExpense.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            Intent intent = new Intent(ExpenseActivity.this, DetailExpenseActivity.class);
            intent.putExtra("objectExpense", listExpense.get(position));
            startActivity(intent);
            }
        });
        onSubcribeExpense = false;

    }

    public void toast(String message) {
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
    }



}
