package io.awesome.app.View.Fragment.MoveTable;


import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
;
import java.util.ArrayList;
import java.util.List;

import io.awesome.app.Model.Ordered;
import io.awesome.app.Presenter.Receipt.ReceiptPresenterImpl;
import io.awesome.app.R;
import io.awesome.app.View.Adapter.CustomMoveFromTableAdapter;
import io.awesome.app.View.MoveOrder.MoveOrderActivity;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMoveFromTable extends Fragment {

    /*private Button btnGui;
    private EditText guiDuLieu;
    private OnButtonPressListener buttonListener;*/

    private CustomMoveFromTableAdapter customMoveFromTableAdapter;
    private ListView lvMoveFromTable;
    private List<Ordered> listOrdered;
    private MoveOrderedI moveOrderedI;

    /*private ArrayList<String> arrayName;
    private MoveOrderedI moveOrderedI;
    private ArrayAdapter arrayAdapter;*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_move_from_table, container, false);

        /*btnGui = (Button) view.findViewById(R.id.btnGui);
        guiDuLieu = (EditText) view.findViewById(R.id.guiDuLieu);
        buttonListener = (OnButtonPressListener) getActivity();

        btnGui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonListener.onButtonPressed(guiDuLieu.getText().toString().trim());
            }
        });*/

        lvMoveFromTable = (ListView) view.findViewById(R.id.lvMoveFromTable);
        moveOrderedI = (MoveOrderedI) getActivity();

        listOrdered = new ArrayList<Ordered>();
        /*Ordered(String itemId, String name, int price, String urlImage, int quantity)*/
        listOrdered.add(new Ordered("","Cafe sua",29000,"https://raw.githubusercontent.com/hiepvv/Image/master/ibc.png",2));
        listOrdered.add(new Ordered("","Sinh to dau",29000,"https://raw.githubusercontent.com/hiepvv/Image/master/ibc.png",3));
        listOrdered.add(new Ordered("","Cappuciano",29000,"https://raw.githubusercontent.com/hiepvv/Image/master/ibc.png",1));
        listOrdered.add(new Ordered("","Cafe sua",29000,"https://raw.githubusercontent.com/hiepvv/Image/master/ibc.png",2));
        listOrdered.add(new Ordered("","Sinh to dau",29000,"https://raw.githubusercontent.com/hiepvv/Image/master/ibc.png",3));
        listOrdered.add(new Ordered("","Cappuciano",29000,"https://raw.githubusercontent.com/hiepvv/Image/master/ibc.png",1));

        listOrdered.add(new Ordered("1","Cafe sua",29000,"https://raw.githubusercontent.com/hiepvv/Image/master/ibc.png",2));
        listOrdered.add(new Ordered("2","Sinh to dau",29000,"https://raw.githubusercontent.com/hiepvv/Image/master/ibc.png",3));
        listOrdered.add(new Ordered("3","Cappuciano",29000,"https://raw.githubusercontent.com/hiepvv/Image/master/ibc.png",1));
        listOrdered.add(new Ordered("4","Cafe sua",29000,"https://raw.githubusercontent.com/hiepvv/Image/master/ibc.png",2));
        listOrdered.add(new Ordered("5","Sinh to dau",29000,"https://raw.githubusercontent.com/hiepvv/Image/master/ibc.png",3));
        listOrdered.add(new Ordered("6","Cappuciano",29000,"https://raw.githubusercontent.com/hiepvv/Image/master/ibc.png",1));

        customMoveFromTableAdapter = new CustomMoveFromTableAdapter(view.getContext(), listOrdered);

        lvMoveFromTable.setAdapter(customMoveFromTableAdapter);


        lvMoveFromTable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                xacNhanXoa(i,listOrdered.get(i));
            }
        });

        /*arrayName = new ArrayList<String>();
        arrayName.add("duy");
        arrayName.add("an");
        arrayName.add("hiep");
        arrayName.add("cuong");
        arrayName.add("hai");
        arrayName.add("hanh");
        arrayName.add("duong");


        arrayAdapter = new ArrayAdapter(view.getContext(),android.R.layout.simple_expandable_list_item_1, arrayName);
        lvMoveFromTable.setAdapter(arrayAdapter);

        moveOrderedI = (MoveOrderedI) getActivity();

        lvMoveFromTable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                xacNhanXoa(arrayName.get(i));
            }
        });*/
        return view;
    }
    public void toast(String message){
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }

    private void xacNhanXoa(final int position,final Ordered ordered){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Ban co muon xoa " + ordered.getName()+" khong?");
        builder.setPositiveButton("Co", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                /*arrayName.remove(ten);
                arrayAdapter.notifyDataSetChanged();
                moveOrderedI.moveOrdered(ten);*/

                listOrdered.remove(position);
                customMoveFromTableAdapter.notifyDataSetChanged();
                moveOrderedI.moveOrdered(ordered);
            }
        });
        builder.setNegativeButton("Khong", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }



}
