package io.awesome.app.View.Fragment.MoveTable;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import io.awesome.app.Model.Ordered;
import io.awesome.app.R;
import io.awesome.app.View.Adapter.CustomMoveFromTableAdapter;
import io.awesome.app.View.Adapter.CustomMoveToTableAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMoveToTable extends Fragment{


    /*private ListView lvMoveToTable;
    private ArrayList<String> arrayName;
    private ArrayAdapter arrayAdapter;*/

    private ListView lvMoveToTable;
    private ArrayList<Ordered> listOrdered;
    private CustomMoveToTableAdapter customMoveToTableAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_move_to_table, container, false);

        /*lvMoveToTable = (ListView) view.findViewById(R.id.lvMoveToTable);
        arrayName = new ArrayList<String>();
        arrayName.add("a");
        arrayName.add("b");
        arrayName.add("c");

        arrayAdapter = new ArrayAdapter(view.getContext(),android.R.layout.simple_expandable_list_item_1, arrayName);
        lvMoveToTable.setAdapter(arrayAdapter);*/

        lvMoveToTable = (ListView) view.findViewById(R.id.lvMoveToTable);
        listOrdered = new ArrayList<Ordered>();
        /*Ordered(String itemId, String name, int price, String urlImage, int quantity)*/
        listOrdered.add(new Ordered("","Cafe sua",29000,"https://raw.githubusercontent.com/hiepvv/Image/master/ibc.png",2));
        customMoveToTableAdapter = new CustomMoveToTableAdapter(view.getContext(), listOrdered);

        lvMoveToTable.setAdapter(customMoveToTableAdapter);

        return view;
    }


    public void setMessage(Ordered ordered) {
        listOrdered.add(ordered);
        customMoveToTableAdapter.notifyDataSetChanged();
    }
}
