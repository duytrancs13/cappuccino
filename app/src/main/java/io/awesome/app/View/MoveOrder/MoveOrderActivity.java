package io.awesome.app.View.MoveOrder;

import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import io.awesome.app.General.SetFont;
import io.awesome.app.Model.Ordered;
import io.awesome.app.R;
import io.awesome.app.View.Fragment.MoveTable.FragmentMoveFromTable;
import io.awesome.app.View.Fragment.MoveTable.FragmentMoveToTable;
import io.awesome.app.View.Fragment.MoveTable.MoveOrderedI;

public class MoveOrderActivity extends AppCompatActivity implements MoveOrderedI {

    private Toolbar toolbar;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_order);

        SetFont setFont = new SetFont("Roboto-Regular.ttf");
        setFont.getFont();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tách - gộp bàn");

        // Set ActionBar
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_back);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        FragmentMoveFromTable fragmentMoveFromTable = new FragmentMoveFromTable();
        FragmentMoveToTable fragmentMoveToTable = new FragmentMoveToTable();

        transaction =getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frag_1, fragmentMoveFromTable);
        transaction.add(R.id.frag_2, fragmentMoveToTable);
        transaction.commit();



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
        fragmentMoveToTable.setMessage(ordered);
    }



}
