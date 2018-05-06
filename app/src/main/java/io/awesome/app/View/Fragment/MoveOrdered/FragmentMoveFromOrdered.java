package io.awesome.app.View.Fragment.MoveOrdered;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
;

import io.awesome.app.Model.Ordered;
import io.awesome.app.R;
import io.awesome.app.View.Adapter.CustomMoveOrderedAdapter;

import static io.awesome.app.View.Main.MainActivity.receiptId;
import static io.awesome.app.View.Table.TableActivity.listOrdered;
import static io.awesome.app.View.Table.TableActivity.onClickMoveOrdered;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMoveFromOrdered extends Fragment {

    private CustomMoveOrderedAdapter customMoveOrderedAdapter;
    private ListView lvMoveFromTable;
    private MoveOrderedI moveOrderedI;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_move_from_ordered, container, false);

        lvMoveFromTable = (ListView) view.findViewById(R.id.lvMoveFromTable);
        moveOrderedI = (MoveOrderedI) getActivity();

        customMoveOrderedAdapter = new CustomMoveOrderedAdapter(view.getContext(), listOrdered);

        lvMoveFromTable.setAdapter(customMoveOrderedAdapter);



        if(onClickMoveOrdered){
            lvMoveFromTable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                confirmDelete(position,listOrdered.get(position));
                }
            });
        }else{
            lvMoveFromTable.setFocusable(onClickMoveOrdered);
        }



        return view;
    }
    public void toast(String message){
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }

    public void recevieData() {
        customMoveOrderedAdapter.notifyDataSetChanged();
    }

    private void confirmDelete(final int position,final Ordered ordered){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Ban co muốn chuyển " + ordered.getName()+" không?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                moveOrderedI.moveOrdered(ordered.getItemId(),"-1", "AtoB");
                customMoveOrderedAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.show();
    }







}
