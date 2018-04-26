package io.awesome.app.View.Fragment.MoveOrdered;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import io.awesome.app.R;
import io.awesome.app.View.Adapter.CustomToOrderedAdapter;

import static io.awesome.app.View.Table.TableActivity.listToOrdered;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMoveToOrdered extends Fragment{


    /*private ListView lvMoveToTable;
    private ArrayList<String> arrayName;
    private ArrayAdapter arrayAdapter;*/

    private ListView lvMoveToTable;
    private CustomToOrderedAdapter customToOrderedAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_move_to_ordered, container, false);

        lvMoveToTable = (ListView) view.findViewById(R.id.lvMoveToTable);

        customToOrderedAdapter = new CustomToOrderedAdapter(view.getContext(), listToOrdered);

        lvMoveToTable.setAdapter(customToOrderedAdapter);

        return view;
    }


    public void recevieData() {
        //listToOrdered.add(ordered);
        customToOrderedAdapter.notifyDataSetChanged();
    }
}
