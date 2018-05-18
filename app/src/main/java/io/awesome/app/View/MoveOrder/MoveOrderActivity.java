package io.awesome.app.View.MoveOrder;

import android.app.Fragment;
import android.app.ProgressDialog;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.awesome.app.General.SetFont;
import io.awesome.app.Model.Ordered;
import io.awesome.app.Model.Table;
import io.awesome.app.Presenter.MenuTabs.MenuTabsPresenterImp;
import io.awesome.app.Presenter.MoveOrdered.MoveOrderedPresenterImp;
import io.awesome.app.R;
import io.awesome.app.View.Fragment.MoveOrdered.FragmentMoveFromOrdered;
import io.awesome.app.View.Fragment.MoveOrdered.FragmentMoveOrdered;
import io.awesome.app.View.Fragment.MoveOrdered.FragmentMoveToOrdered;
import io.awesome.app.View.Fragment.MoveOrdered.MoveOrderedI;
import io.awesome.app.View.Table.TableActivity;

import static io.awesome.app.View.Main.MainActivity.onTableActivity;
import static io.awesome.app.View.Main.MainActivity.receiptId;
import static io.awesome.app.View.Main.MainActivity.receiptToOrdered;
import static io.awesome.app.View.Table.TableActivity.listOrdered;
import static io.awesome.app.View.Table.TableActivity.listToOrdered;
import static io.awesome.app.View.Table.TableActivity.onClickMoveOrdered;

public class MoveOrderActivity extends AppCompatActivity implements MoveOrderedI, MoveOrderedView {

    private Toolbar toolbar;
    private FragmentTransaction transaction;

    private MoveOrderedPresenterImp moveOrderedPresenterImp;

    public static final String MyPREFERENCES = "capuccino" ;
    private SharedPreferences sharedPreferences;
    private MenuTabsPresenterImp menuTabsPresenterImp;
    private String token;

    public static ArrayList<Table> listChooseTable = new ArrayList<Table>();
    public static HashMap<String, List<Ordered>> lstChooseTable = new HashMap<String, List<Ordered>>();

    private ProgressDialog dialog ;
    private ImageView syncTransfer, undoTransfer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_ordered);

        onTableActivity = false;

        sharedPreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        token = sharedPreferences.getString("token", null);

        dialog = new ProgressDialog(this,R.style.AppTheme_Dark_Dialog);
        showProgress();


        SetFont setFont = new SetFont("Roboto-Regular.ttf");
        setFont.getFont();



        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tách - gộp bàn");

        // Set ActionBar
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_back);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        moveOrderedPresenterImp = new MoveOrderedPresenterImp(this,this, token);
        //getMenuOrdered();
        initFragment();
        if (receiptToOrdered.length() != 0) {
            if(receiptToOrdered.startsWith("tableId")){
                FragmentToOrdered();
            }else{
                getMenuToOrdered();
            }
        }

        syncTransfer = (ImageView) findViewById(R.id.syncTransfer);
        syncTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("AAA", listOrdered.size()+"");
                for (final Map.Entry<String, List<Ordered>> item: lstChooseTable.entrySet()){
                    Log.v("AAA", lstChooseTable.get(item.getKey()).toString()+"");
                }
            }
        });

        undoTransfer = (ImageView) findViewById(R.id.undoTransfer);
        undoTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress();
                onClickMoveOrdered = false;
                receiptToOrdered = "";
                listToOrdered = new ArrayList<Ordered>();
                lstChooseTable = new HashMap<String, List<Ordered>>();
                getMenuOrdered();
            }
        });


    }

    // Quay về màn hình trước đó là tableActivity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(this, TableActivity.class));
            onTableActivity = true;
            onClickMoveOrdered = false;
            receiptToOrdered = "";
            listToOrdered = new ArrayList<Ordered>();
            lstChooseTable = new HashMap<String, List<Ordered>>();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        finish();
    }

    public void toast(String message){
        Toast.makeText(getBaseContext(),message,Toast.LENGTH_SHORT).show();
    }

    /*Hàm xử lí dữ liệu khi món được chuyển*/
    @Override
    public void moveOrdered(Ordered ordered, String statusOrdered) {
        if(statusOrdered == "AtoB"){
            showProgress();
            /*Bên chuyển giảm số lượng đi 1*/
            ordered.setQuantity(ordered.getQuantity()-1);

            /*Vị trí của món được nhận trong danh sách
            * Nếu có thì trả về vị trí
            * Không đúng trả về -1
            * */
            int positionInListToOrdered=checkListToOrdered(ordered.getItemId(), lstChooseTable.get(receiptToOrdered));

            /*Trường hợp bên nhận không có trong danh sách*/
            if(positionInListToOrdered == -1){
                Ordered newToOrdered = new Ordered(ordered.getItemId(),ordered.getName(),ordered.getPrice(),ordered.getUrlImage(),1,ordered.getNote());
                lstChooseTable.get(receiptToOrdered).add(newToOrdered);
            /*Trường hợp bên nhận có món trong danh sách*/
            }else{
                int newQuatity = lstChooseTable.get(receiptToOrdered).get(positionInListToOrdered).getQuantity()+1;
                lstChooseTable.get(receiptToOrdered).get(positionInListToOrdered).setQuantity(newQuatity);

            }
            FragmentMoveToOrdered fragmentMoveToTable = (FragmentMoveToOrdered) getSupportFragmentManager().findFragmentById(R.id.frag_2);
            fragmentMoveToTable.recevieData();

        }else{
            ordered.setQuantity(ordered.getQuantity()-1);
            int positionInListOrdered= checkListToOrdered(ordered.getItemId(), listOrdered);
            if(positionInListOrdered == -1){
                Ordered newOrdered = new Ordered(ordered.getItemId(),ordered.getName(),ordered.getPrice(),ordered.getUrlImage(),1,ordered.getNote());
                listOrdered.add(newOrdered);
            /*Trường hợp bên nhận có món trong danh sách*/
            }else{
                int newQuatity = listOrdered.get(positionInListOrdered).getQuantity()+1;
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
    private int checkListToOrdered(String idItemOrdered,List<Ordered> checkList){
//        lstChooseTable.get(receiptToOrdered)
        for(int i=0; i< checkList.size(); i++){
            if(checkList.get(i).getItemId().equals(idItemOrdered)){
                return i;
            }
        }
        return -1;
    }

    /*Lấy danh sách những món của 1 bàn được nhận*/
    @Override
    public void getMenuToOrdered() {
        moveOrderedPresenterImp.getMenuToOrdered();
    }

    /*Lấy danh sách những món khi click vào 1 bàn muốn chuyển*/
    @Override
    public void onClickGetMenuToOrdered() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frag_2, new FragmentMoveToOrdered()).commit();
    }

    /*Lấy danh sách những món khi chọn bàn muốn chuyển tới*/
    @Override
    public void FragmentToOrdered() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frag_2, new FragmentMoveToOrdered()).commit();
    }

    @Override
    public void undoAllFragment(){
        getSupportFragmentManager().beginTransaction().replace(R.id.frag_1, new FragmentMoveFromOrdered()).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.frag_3, new FragmentMoveOrdered()).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.frag_2, new FragmentMoveToOrdered()).commit();
        dialog.dismiss();
    }

    @Override
    public void createReceiptToOrdered(String idTable, int position, Button buttonToOrdered) {
        moveOrderedPresenterImp.createReceiptToOrdered(idTable, position, buttonToOrdered);
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

    private class Progress extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Đang kết nối...");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
            Log.v("AAA", "Dang ket noi !!!");
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }


}
