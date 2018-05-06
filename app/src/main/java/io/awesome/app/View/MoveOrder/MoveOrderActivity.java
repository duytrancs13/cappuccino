package io.awesome.app.View.MoveOrder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.awesome.app.General.SetFont;
import io.awesome.app.Model.Ordered;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_ordered);

        onTableActivity = false;

        sharedPreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        token = sharedPreferences.getString("token", null);

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
        moveOrderedPresenterImp.getMenuOrdered();

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

    @Override
    public void moveOrdered(String menuId, String quality, String statusOrdered) {
        if(statusOrdered == "AtoB"){
            moveOrderedPresenterImp.moveItemOrdered(receiptId, menuId,quality,"moveOrdered");
            moveOrderedPresenterImp.moveItemOrdered(receiptToOrdered, menuId,"1", "toOrdered");
            FragmentMoveToOrdered fragmentMoveToTable =
                    (FragmentMoveToOrdered) getSupportFragmentManager().findFragmentById(R.id.frag_2);
            fragmentMoveToTable.recevieData();
        }else{
            moveOrderedPresenterImp.moveItemOrdered(receiptToOrdered, menuId,quality,"toOrdered");
            moveOrderedPresenterImp.moveItemOrdered(receiptId, menuId,"1", "moveOrdered");
            FragmentMoveFromOrdered fragmentMoveFromOrdered =
                    (FragmentMoveFromOrdered) getSupportFragmentManager().findFragmentById(R.id.frag_1);
            fragmentMoveFromOrdered.recevieData();
        }
    }

    @Override
    public void getMenuToOrdered() {
        moveOrderedPresenterImp.getMenuToOrdered();
    }

    @Override
    public void createReceiptToOrdered(String idTable, int position) {
        moveOrderedPresenterImp.createReceiptToOrdered(idTable, position);
    }


    @Override
    public void initFragment() {
        FragmentMoveFromOrdered fragmentMoveFromTable = new FragmentMoveFromOrdered();
        FragmentMoveOrdered fragmentMoveTable = new FragmentMoveOrdered();
        FragmentMoveToOrdered fragmentMoveToTable = new FragmentMoveToOrdered();


        transaction =getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frag_1, fragmentMoveFromTable);
        transaction.add(R.id.frag_3, fragmentMoveTable);
        transaction.add(R.id.frag_2, fragmentMoveToTable);

        transaction.commit();
    }

    @Override
    public void FragmentToOrdered() {
        FragmentMoveToOrdered fragmentMoveToTable = (FragmentMoveToOrdered) getSupportFragmentManager().findFragmentById(R.id.frag_2);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.detach(fragmentMoveToTable);
        transaction.attach(fragmentMoveToTable);
        transaction.commit();
    }


}
