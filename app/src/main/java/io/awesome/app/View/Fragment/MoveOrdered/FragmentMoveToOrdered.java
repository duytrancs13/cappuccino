package io.awesome.app.View.Fragment.MoveOrdered;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import io.awesome.app.Model.Ordered;
import io.awesome.app.R;
import io.awesome.app.View.Adapter.CustomToOrderedAdapter;

import static io.awesome.app.View.Main.MainActivity.receiptId;
import static io.awesome.app.View.Main.MainActivity.receiptToOrdered;
import static io.awesome.app.View.MoveOrder.MoveOrderActivity.lstChooseTable;
import static io.awesome.app.View.Table.TableActivity.listOrdered;
import static io.awesome.app.View.Table.TableActivity.listToOrdered;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMoveToOrdered extends Fragment {

    private ListView lvMoveToTable;
    private CustomToOrderedAdapter customToOrderedAdapter;
    private MoveOrderedI moveOrderedI;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_move_to_ordered, container, false);

        lvMoveToTable = (ListView) view.findViewById(R.id.lvMoveToTable);
        moveOrderedI = (MoveOrderedI) getActivity();

        if (receiptToOrdered.length() == 0) {
            customToOrderedAdapter = new CustomToOrderedAdapter(view.getContext(), new ArrayList<Ordered>());
        } else {
            customToOrderedAdapter = new CustomToOrderedAdapter(view.getContext(), lstChooseTable.get(receiptToOrdered));
        }
        moveOrderedI = (MoveOrderedI) getActivity();
        lvMoveToTable.setAdapter(customToOrderedAdapter);
        lvMoveToTable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

//          confirmDelete(lstChooseTable.get(receiptToOrdered).get(position));
            moveOrderedI.moveOrdered(lstChooseTable.get(receiptToOrdered).get(position), "BtoA", 1);
            customToOrderedAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    public void toast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }


    public void recevieData() {
        customToOrderedAdapter.notifyDataSetChanged();
    }

    /*private void confirmDelete(final Ordered toOrdered){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Bạn có muốn chuyển " +toOrdered.getName()+" không?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                moveOrderedI.moveOrdered(toOrdered, "BtoA");
                customToOrderedAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.show();
    }*/
}
