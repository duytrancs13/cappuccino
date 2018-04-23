package io.awesome.app.View.Fragment.MoveOrdered;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
;

import io.awesome.app.Model.Ordered;
import io.awesome.app.R;
import io.awesome.app.View.Adapter.CustomMoveFromTableAdapter;

import static io.awesome.app.View.Table.TableActivity.listOrdered;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMoveFromTable extends Fragment {

    /*private Button btnGui;
    private EditText guiDuLieu;
    private OnButtonPressListener buttonListener;*/

    private CustomMoveFromTableAdapter customMoveFromTableAdapter;
    private ListView lvMoveFromTable;
    /*private List<Ordered> listOrdered;*/
    private MoveOrderedI moveOrderedI;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_move_from_table, container, false);

        lvMoveFromTable = (ListView) view.findViewById(R.id.lvMoveFromTable);
        moveOrderedI = (MoveOrderedI) getActivity();



        /*listOrdered = new ArrayList<Ordered>();
        *//*Ordered(String itemId, String name, int price, String urlImage, int quantity, String note)*//*
        listOrdered.add(new Ordered("","ca phe", 10000,"abc",1,""));
        listOrdered.add(new Ordered("","ca phe sua", 10000,"abc",1,""));
        listOrdered.add(new Ordered("","sinh to", 10000,"abc",1,""));
        listOrdered.add(new Ordered("","matcha", 10000,"abc",1,""));*/

        customMoveFromTableAdapter = new CustomMoveFromTableAdapter(view.getContext(), listOrdered);

        lvMoveFromTable.setAdapter(customMoveFromTableAdapter);


        lvMoveFromTable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                confirmDelete(position,listOrdered.get(position));
            }
        });
        return view;
    }
    public void toast(String message){
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }

    private void confirmDelete(final int position,final Ordered ordered){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Ban co muốn xóa " + ordered.getName()+" không?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                listOrdered.remove(position);
                customMoveFromTableAdapter.notifyDataSetChanged();
                moveOrderedI.moveOrdered(ordered);
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
