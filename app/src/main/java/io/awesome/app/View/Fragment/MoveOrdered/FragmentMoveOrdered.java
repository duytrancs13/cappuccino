package io.awesome.app.View.Fragment.MoveOrdered;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.awesome.app.Model.Ordered;
import io.awesome.app.R;
import io.awesome.app.View.ChooseTableMoveActivity;
import io.awesome.app.View.MoveOrder.MoveOrderedView;

;
import static io.awesome.app.View.Main.MainActivity.listTable;
import static io.awesome.app.View.Main.MainActivity.receiptToOrdered;
//import static io.awesome.app.View.MoveOrder.MoveOrderActivity.listChooseTable;
import static io.awesome.app.View.MoveOrder.MoveOrderActivity.lstChooseTable;
import static io.awesome.app.View.Table.TableActivity.listToOrdered;
import static io.awesome.app.View.Table.TableActivity.onClickMoveOrdered;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMoveOrdered extends Fragment {

    private LinearLayout llButtonOrdered;
    private Button btnAddButtonOrdered;
    private MoveOrderedI moveOrderedI;
    private CardView cVChooseTable;
    private MoveOrderedView moveOrderedView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_move_ordered, container, false);


        cVChooseTable = (CardView) view.findViewById(R.id.cVChooseTable);
        moveOrderedI = (MoveOrderedI) getActivity();


        llButtonOrdered = (LinearLayout) view.findViewById(R.id.llButtonOrdered);
        moveOrderedI = (MoveOrderedI) getActivity();
        int position = getActivity().getIntent().getIntExtra("positionTable", -1);

        /*Lúc khởi tạo activity thì hiển thị "Chọn bàn muốn chuyển"*/
        if (lstChooseTable.size() == 0) {
            TextView textViewHint = new TextView(getContext());
            LinearLayout.LayoutParams layoutTextViewOrdered = new LinearLayout.LayoutParams(DrawerLayout.LayoutParams.WRAP_CONTENT, DrawerLayout.LayoutParams.WRAP_CONTENT);
            textViewHint.setLayoutParams(layoutTextViewOrdered);
            textViewHint.setText("Chọn bàn muốn chuyển");
            llButtonOrdered.addView(textViewHint);
        } else {
            for (final Map.Entry<String, List<Ordered>> item : lstChooseTable.entrySet()) {

                LinearLayout.LayoutParams llChooseTableMove = new LinearLayout.LayoutParams(DrawerLayout.LayoutParams.WRAP_CONTENT, DrawerLayout.LayoutParams.WRAP_CONTENT);

                /*Set Button*/
                final Button buttonToOrdered = new Button(getContext());
                buttonToOrdered.setText(listTable.get(position).getName());
                if (item.getKey().equals(receiptToOrdered)) {
                    buttonToOrdered.setBackgroundResource(R.drawable.buttonchoosetransfer);
                } else {
                    buttonToOrdered.setBackgroundResource(R.drawable.buttontransfer);
                }
                buttonToOrdered.setLayoutParams(llChooseTableMove);

                buttonToOrdered.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        moveOrderedI.showProgress();
                        receiptToOrdered = item.getKey();
                        moveOrderedI.onClickGetMenuToOrdered();
                    }
                });

                /*Set ImageView*/
                final ImageView imageViewRemoveToOrdered = new ImageView(getContext());
                imageViewRemoveToOrdered.setImageDrawable(getResources().getDrawable(R.drawable.ic_delete));
                imageViewRemoveToOrdered.setLayoutParams(llChooseTableMove);

                imageViewRemoveToOrdered.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        receiptToOrdered = item.getKey();
                        confirmDelete();
                    }
                });

                llButtonOrdered.addView(buttonToOrdered);
                llButtonOrdered.addView(imageViewRemoveToOrdered);
            }

        }

        btnAddButtonOrdered = (Button) view.findViewById(R.id.btnAddButtonOrdered);
        btnAddButtonOrdered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickMoveOrdered = true;
                Intent intent = new Intent(getActivity(), ChooseTableMoveActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void confirmDelete(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Bạn có muốn chuyển xóa không?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                moveOrderedI.showProgress();
                if (lstChooseTable.size() == 1) {
                    onClickMoveOrdered = false;
                    receiptToOrdered = "";
                    listToOrdered = new ArrayList<Ordered>();
                    lstChooseTable = new HashMap<String, List<Ordered>>();
                    moveOrderedI.getMenuOrdered();
                } else if (lstChooseTable.size() > 1) {
                    for (Ordered ordered : lstChooseTable.get(receiptToOrdered)) {
                        moveOrderedI.moveOrdered(ordered, "BtoA", ordered.getQuantity());
                    }
                    lstChooseTable.remove(receiptToOrdered);
                    Map.Entry<String, List<Ordered>> entry = lstChooseTable.entrySet().iterator().next();
                    receiptToOrdered = entry.getKey();
                    moveOrderedI.onClickGetMenuToOrdered();
                }
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.show();
    }
    public void toast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }


}
