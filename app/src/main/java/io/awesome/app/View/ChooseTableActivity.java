package io.awesome.app.View;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.Serializable;

import io.awesome.app.Model.Table;
import io.awesome.app.R;
import io.awesome.app.View.Adapter.CustomChooseTableAdapter;
import io.awesome.app.View.Fragment.MoveOrdered.FragmentMoveOrdered;
import io.awesome.app.View.MoveOrder.MoveOrderActivity;

import static io.awesome.app.View.Main.MainActivity.listTable;

public class ChooseTableActivity extends AppCompatActivity {
    private ListView lvChooseTable;

    private CustomChooseTableAdapter customChooseTableAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_table);

        lvChooseTable = (ListView) findViewById(R.id.lvChooseTable);

        customChooseTableAdapter = new CustomChooseTableAdapter(this, listTable);

        lvChooseTable.setAdapter(customChooseTableAdapter);

        lvChooseTable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(getBaseContext(), MoveOrderActivity.class);
                intent.putExtra("positionTable", position);
                startActivity(intent);
            }
        });

    }

}
