package io.awesome.app.View;


import android.content.Intent;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import android.widget.ListView;


import java.util.ArrayList;

import io.awesome.app.General.SetFont;
import io.awesome.app.Model.Ordered;
import io.awesome.app.R;
import io.awesome.app.View.Adapter.CustomChooseTableMoveAdapter;

import io.awesome.app.View.MoveOrder.MoveOrderActivity;

import static io.awesome.app.View.Main.MainActivity.listTable;
import static io.awesome.app.View.Main.MainActivity.receiptToOrdered;
//import static io.awesome.app.View.MoveOrder.MoveOrderActivity.listChooseTable;
import static io.awesome.app.View.MoveOrder.MoveOrderActivity.lstChooseTable;

public class ChooseTableMoveActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ListView lvChooseTable;

    private CustomChooseTableMoveAdapter customChooseTableAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_table_move);

//        CalligraphyConfig.initDefault(
//                new CalligraphyConfig.Builder()
//                        .setDefaultFontPath("Roboto-Regular.ttf")
//                        .setFontAttrId(R.attr.fontPath)
//                        .build()
//        );






        SetFont setFont = new SetFont("Roboto-Regular.ttf");
        setFont.getFont();


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Chọn bàn muốn chuyển");

        // Set ActionBar
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_back);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        lvChooseTable = (ListView) findViewById(R.id.lvChooseTable);

        customChooseTableAdapter = new CustomChooseTableMoveAdapter(this, listTable);

        lvChooseTable.setAdapter(customChooseTableAdapter);

        lvChooseTable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(getBaseContext(), MoveOrderActivity.class);
                intent.putExtra("positionTable", position);
                /*if(listTable.get(position).getReceiptId().length()==0){
                    receiptToOrdered = "tableId"+listTable.get(position).getId();
                    lstChooseTable.put("tableId"+listTable.get(position).getId(),new ArrayList<Ordered>());
                }else {
                    receiptToOrdered = listTable.get(position).getReceiptId();
                    lstChooseTable.put(listTable.get(position).getReceiptId(), new ArrayList<Ordered>());
                }*/
                receiptToOrdered = listTable.get(position).getId();
                lstChooseTable.put(listTable.get(position).getId(), new ArrayList<Ordered>());
                startActivity(intent);
            }
        });

    }

    // Quay về màn hình trước đó bằng icon là tableActivity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
//            startActivity(new Intent(this, MoveOrderActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    // Quay về màn hình tableActivity bằng nút back của android
    @Override
    public void onBackPressed() {
        finish();
    }


}
