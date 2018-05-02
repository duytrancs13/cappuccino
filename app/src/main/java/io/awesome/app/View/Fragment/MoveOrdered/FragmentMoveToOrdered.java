package io.awesome.app.View.Fragment.MoveOrdered;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import io.awesome.app.R;
import io.awesome.app.View.Adapter.CustomToOrderedAdapter;

import static io.awesome.app.View.Main.MainActivity.receiptId;
import static io.awesome.app.View.Main.MainActivity.receiptToOrdered;
import static io.awesome.app.View.Table.TableActivity.listOrdered;
import static io.awesome.app.View.Table.TableActivity.listToOrdered;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMoveToOrdered extends Fragment{

    private ListView lvMoveToTable;
    private CustomToOrderedAdapter customToOrderedAdapter;
    MoveOrderedI moveOrderedI;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_move_to_ordered, container, false);

        lvMoveToTable = (ListView) view.findViewById(R.id.lvMoveToTable);

        customToOrderedAdapter = new CustomToOrderedAdapter(view.getContext(), listToOrdered);

        moveOrderedI = (MoveOrderedI) getActivity();

        lvMoveToTable.setAdapter(customToOrderedAdapter);

        lvMoveToTable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                moveOrderedI.moveOrdered(listToOrdered.get(position).getItemId(),"-1", "BtoA");
                customToOrderedAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    public void toast(String message){
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }


    public void recevieData() {
        //listToOrdered.add(ordered);
        customToOrderedAdapter.notifyDataSetChanged();
    }
}
