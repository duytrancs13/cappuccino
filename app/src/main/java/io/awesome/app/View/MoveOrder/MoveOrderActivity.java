package io.awesome.app.View.MoveOrder;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import io.awesome.app.General.SetFont;
import io.awesome.app.Model.Ordered;
import io.awesome.app.Presenter.MenuTabs.MenuTabsPresenterImp;
import io.awesome.app.Presenter.MoveReceipt.MoveOrderedPresenterImp;
import io.awesome.app.R;
import io.awesome.app.View.Fragment.MoveOrdered.FragmentMoveFromTable;
import io.awesome.app.View.Fragment.MoveOrdered.FragmentMoveTable;
import io.awesome.app.View.Fragment.MoveOrdered.FragmentMoveToTable;
import io.awesome.app.View.Fragment.MoveOrdered.MoveOrderedI;

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
        setContentView(R.layout.activity_move_order);

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

        moveOrderedPresenterImp = new MoveOrderedPresenterImp(this,this);
        moveOrderedPresenterImp.getMenuOrdered(token);

    }

    // Quay về màn hình trước đó là tableActivity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
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
    public void moveOrdered(Ordered ordered) {
        FragmentMoveToTable fragmentMoveToTable =
                (FragmentMoveToTable) getSupportFragmentManager().findFragmentById(R.id.frag_2);
        fragmentMoveToTable.recevieData(ordered);
    }


    @Override
    public void initFragment() {
        FragmentMoveFromTable fragmentMoveFromTable = new FragmentMoveFromTable();
        FragmentMoveTable fragmentMoveTable = new FragmentMoveTable();
        FragmentMoveToTable fragmentMoveToTable = new FragmentMoveToTable();


        transaction =getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frag_1, fragmentMoveFromTable);
        transaction.add(R.id.frag_3, fragmentMoveTable);
        transaction.add(R.id.frag_2, fragmentMoveToTable);

        transaction.commit();
    }
}
