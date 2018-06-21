package io.awesome.app.View.MoveOrder;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.tapadoo.alerter.Alerter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.awesome.app.General.SetFont;
import io.awesome.app.Model.Ordered;
import io.awesome.app.Model.Table;
import io.awesome.app.Presenter.MoveOrdered.MoveOrderedPresenterImp;
import io.awesome.app.R;
import io.awesome.app.View.Fragment.MoveOrdered.FragmentMoveFromOrdered;
import io.awesome.app.View.Fragment.MoveOrdered.FragmentMoveOrdered;
import io.awesome.app.View.Fragment.MoveOrdered.FragmentMoveToOrdered;
import io.awesome.app.View.Fragment.MoveOrdered.MoveOrderedI;
import io.awesome.app.View.Table.TableActivity;

import static io.awesome.app.View.Main.MainActivity.receiptId;
import static io.awesome.app.View.Main.MainActivity.receiptToOrdered;
import static io.awesome.app.View.Table.TableActivity.listOrdered;
import static io.awesome.app.View.Table.TableActivity.listToOrdered;
import static io.awesome.app.View.Table.TableActivity.nameTableMoveOrdered;
import static io.awesome.app.View.Table.TableActivity.onClickMoveOrdered;

public class MoveOrderActivity extends AppCompatActivity implements MoveOrderedI, MoveOrderedView {

    private Toolbar toolbar;
    private FragmentTransaction transaction;

    private MoveOrderedPresenterImp moveOrderedPresenterImp;

    public static final String MyPREFERENCES = "capuccino";
    private SharedPreferences sharedPreferences;
    private String token;

    public static ArrayList<Table> listChooseTable = new ArrayList<Table>();
    public static HashMap<String, List<Ordered>> lstChooseTable = new HashMap<String, List<Ordered>>();

    private ProgressDialog dialog;
    private Button syncTransfer, undoTransfer;

    /*private static final int TIME_DELAY = 2000;*/
    private static long back_pressed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_ordered);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        token = sharedPreferences.getString("token", null);

        dialog = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
        showProgress();

        SetFont setFont = new SetFont("Roboto-Regular.ttf");
        setFont.getFont();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(nameTableMoveOrdered);

        // Set ActionBar
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_back);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        moveOrderedPresenterImp = new MoveOrderedPresenterImp(this, this, token);

        /*Khởi tạo 3 fragment*/
        initFragment();

        /*Bắt sự kiện khi đồng bộ dữ liệu chuyển bàn*/
        syncTransfer = (Button) findViewById(R.id.syncTransfer);
        syncTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lstChooseTable.size() == 0) {
                    toast("Vui lòng chọn bàn để chuyển !!!");
                } else {
                    if(checkListChooseTable()){
                        confirmSyncMoveOrdered();
                    }else{
                        toast("Vui lòng chọn món để chuyển !!!");
                    }

                }
            }

        });

        /*Bắt sự kiện khi người dùng không muốn chuyển món quay lại trạng thái ban đầu*/
        undoTransfer = (Button) findViewById(R.id.undoTransfer);
        undoTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmUndoTransfer();
            }
        });


    }

    // Quay về màn hình trước đó là tableActivity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            /*if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
                gotoBackTableActivity();
            } else {
                toast("Chạm 2 lần liên tiếp để thoát");
            }
            back_pressed = System.currentTimeMillis();*/
            gotoBackTableActivity();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        gotoBackTableActivity();
    }

    public void toast(String message) {
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
    }

    /*Hàm xử lí dữ liệu khi món được chuyển*/
    @Override
    public void moveOrdered(Ordered ordered, String statusOrdered, int quatity) {
        if (statusOrdered == "AtoB") {
            showProgress();
            /*Bên chuyển giảm số lượng đi 1*/
            ordered.setQuantity(ordered.getQuantity() - quatity);

            /*Vị trí của món được nhận trong danh sách
            * Nếu có thì trả về vị trí
            * Không đúng trả về -1
            * */
            int positionInListToOrdered = checkListToOrdered(ordered.getItemId(), lstChooseTable.get(receiptToOrdered));

            /*Trường hợp bên nhận không có trong danh sách*/
            if (positionInListToOrdered == -1) {
                Ordered newToOrdered = new Ordered(ordered.getItemId(), ordered.getName(), ordered.getPrice(), ordered.getUrlImage(), quatity, ordered.getNote());
                lstChooseTable.get(receiptToOrdered).add(newToOrdered);
            /*Trường hợp bên nhận có món trong danh sách*/
            } else {
                int newQuatity = lstChooseTable.get(receiptToOrdered).get(positionInListToOrdered).getQuantity() + quatity;
                lstChooseTable.get(receiptToOrdered).get(positionInListToOrdered).setQuantity(newQuatity);

            }
            FragmentMoveToOrdered fragmentMoveToTable = (FragmentMoveToOrdered) getSupportFragmentManager().findFragmentById(R.id.frag_2);
            fragmentMoveToTable.recevieData();

        } else {
            ordered.setQuantity(ordered.getQuantity() - quatity);
            int positionInListOrdered = checkListToOrdered(ordered.getItemId(), listOrdered);
            if (positionInListOrdered == -1) {
                Ordered newOrdered = new Ordered(ordered.getItemId(), ordered.getName(), ordered.getPrice(), ordered.getUrlImage(), quatity, ordered.getNote());
                listOrdered.add(newOrdered);
            /*Trường hợp bên nhận có món trong danh sách*/
            } else {
                int newQuatity = listOrdered.get(positionInListOrdered).getQuantity() + quatity;
                listOrdered.get(positionInListOrdered).setQuantity(newQuatity);
            }
            FragmentMoveFromOrdered fragmentMoveFromTable = (FragmentMoveFromOrdered) getSupportFragmentManager().findFragmentById(R.id.frag_1);
            fragmentMoveFromTable.recevieData();
        }
        dialog.dismiss();
    }

    @Override
    public void getMenuOrdered() {
        moveOrderedPresenterImp.getMenuOrdered();
    }

    /*Hàm kiểm trả xem trong danh nhận coi có hay không */
    private int checkListToOrdered(String idItemOrdered, List<Ordered> checkList) {
        for (int i = 0; i < checkList.size(); i++) {
            if (checkList.get(i).getItemId().equals(idItemOrdered)) {
                return i;
            }
        }
        return -1;
    }

    /*Lấy danh sách những món khi click vào 1 bàn muốn chuyển*/
    @Override
    public void onClickGetMenuToOrdered() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frag_3, new FragmentMoveOrdered()).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.frag_2, new FragmentMoveToOrdered()).commit();
        dialog.dismiss();
    }

    @Override
    public void undoAllFragment() {
        onClickMoveOrdered = false;
        receiptToOrdered = "";
        listToOrdered = new ArrayList<Ordered>();
        lstChooseTable = new HashMap<String, List<Ordered>>();
        getSupportFragmentManager().beginTransaction().replace(R.id.frag_1, new FragmentMoveFromOrdered()).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.frag_3, new FragmentMoveOrdered()).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.frag_2, new FragmentMoveToOrdered()).commit();
        dialog.dismiss();
    }

    @Override
    public void gotoBackTableActivity() {
        onClickMoveOrdered = false;
        receiptToOrdered = "";
        listToOrdered = new ArrayList<Ordered>();
        lstChooseTable = new HashMap<String, List<Ordered>>();
        dialog.dismiss();

        /*startActivity(new Intent(this, TableActivity.class));*/
        finish();
    }


    /*Khởi tạo các fragment trong 1 activity*/
    @Override
    public void initFragment() {
        FragmentMoveFromOrdered fragmentMoveFromTable = new FragmentMoveFromOrdered();
        FragmentMoveOrdered fragmentMoveTable = new FragmentMoveOrdered();
        FragmentMoveToOrdered fragmentMoveToTable = new FragmentMoveToOrdered();


        transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frag_1, fragmentMoveFromTable);
        transaction.add(R.id.frag_3, fragmentMoveTable);
        transaction.add(R.id.frag_2, fragmentMoveToTable);

        transaction.commit();
        dialog.dismiss();
    }


    @Override
    public void showProgress() {
        new Progress().execute();
    }

    @Override
    public void alertMessage(String titleError, String textError, int responseCode) {
        if(responseCode == 500) {
            Alerter.create(this)
                    .setTitle(titleError)
                    .setText(textError)
                    .setBackgroundColorRes(R.color.red) // or setBackgroundColorInt(Color.CYAN)
                    .show();
        }else{
            Alerter.create(this)
                    .setTitle(titleError)
                    .setText(textError)
                    .setBackgroundColorRes(R.color.colorPrimary) // or setBackgroundColorInt(Color.CYAN)
                    .show();
        }
        dialog.dismiss();
    }

    private class Progress extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Đang kết nối...");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }

    private void confirmSyncMoveOrdered(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Món sẽ được chuyển !!!");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    showProgress();
                        /*Json Object Source*/
                    JSONObject jsonObjectItemsSource = new JSONObject();
                    JSONArray jsonArrayItemsSource = new JSONArray();
                    for (Ordered ordered : listOrdered) {
                        JSONObject itemSource = new JSONObject()
                                .put("itemId", ordered.getItemId())
                                .put("quantity", ordered.getQuantity());
                        jsonArrayItemsSource.put(itemSource);
                    }
                    jsonObjectItemsSource.put("items", jsonArrayItemsSource);


                        /*Json object Destinations*/
                    JSONArray jsonArrayDestinations = new JSONArray();
                    for (Map.Entry<String, List<Ordered>> item : lstChooseTable.entrySet()) {
                        String tableId = item.getKey();
                        JSONObject itemDestination = new JSONObject()
                                .put("tableId", tableId);

                        JSONArray jsonArrayItemsDestinations = new JSONArray();
                        List<Ordered> listToOrdered = item.getValue();
                        for (Ordered ordered : listToOrdered) {
                            JSONObject jsonObjectItemDestination = new JSONObject()
                                    .put("itemId", ordered.getItemId())
                                    .put("quantity", ordered.getQuantity());
                            jsonArrayItemsDestinations.put(jsonObjectItemDestination);
                        }
                        itemDestination.put("items", jsonArrayItemsDestinations);
                        jsonArrayDestinations.put(itemDestination);
                    }
                    JSONObject object = new JSONObject().put("source", jsonObjectItemsSource)
                            .put("destinations", jsonArrayDestinations);
                    moveOrderedPresenterImp.syncMoveOrdered(object);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.show();
    }

    private void confirmUndoTransfer(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Các món chuyển sẽ được trả lại bàn cũ");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                showProgress();
                getMenuOrdered();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.show();
    }

    private boolean checkListChooseTable(){
        for (Map.Entry<String, List<Ordered>> item : lstChooseTable.entrySet()) {
            if(item.getValue().size() != 0 ){
                return true;
            }else{
                continue;
            }
        }
        return false;
    }



}
