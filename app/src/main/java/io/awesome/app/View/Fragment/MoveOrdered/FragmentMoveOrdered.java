package io.awesome.app.View.Fragment.MoveOrdered;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.awesome.app.Model.Ordered;
import io.awesome.app.R;
import io.awesome.app.View.ChooseTableActivity;

;
import static io.awesome.app.View.Main.MainActivity.listTable;
import static io.awesome.app.View.Main.MainActivity.receiptToOrdered;
//import static io.awesome.app.View.MoveOrder.MoveOrderActivity.listChooseTable;
import static io.awesome.app.View.MoveOrder.MoveOrderActivity.lstChooseTable;
import static io.awesome.app.View.Table.TableActivity.onClickMoveOrdered;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMoveOrdered extends Fragment {

    private LinearLayout llButtonOrdered;
    private Button btnAddButtonOrdered;
    private MoveOrderedI moveOrderedI;
    private CardView cVChooseTable;
    private TextView tvChooseTable;





    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_move_ordered, container, false);


        cVChooseTable = (CardView) view.findViewById(R.id.cVChooseTable);
        tvChooseTable = (TextView) view.findViewById(R.id.tvChooseTable);
        moveOrderedI = (MoveOrderedI) getActivity();



        llButtonOrdered = (LinearLayout) view.findViewById(R.id.llButtonOrdered);
        moveOrderedI = (MoveOrderedI) getActivity();
        int position = getActivity().getIntent().getIntExtra("positionTable",-1);

        /*Lúc khởi tạo activity thì hiển thị "Chọn bàn muốn chuyển"*/
        if(lstChooseTable.size() == 0){
            TextView textViewHint = new TextView(getContext());
            LinearLayout.LayoutParams layoutTextViewOrdered = new LinearLayout.LayoutParams(DrawerLayout.LayoutParams.WRAP_CONTENT, DrawerLayout.LayoutParams.WRAP_CONTENT);
            textViewHint.setLayoutParams(layoutTextViewOrdered);
            textViewHint.setText("Chọn bàn muốn chuyển");
            llButtonOrdered.addView(textViewHint);
        }else{

            for (final Map.Entry<String, List<Ordered>> item: lstChooseTable.entrySet()){


                final Button buttonToOrdered = new Button(getContext());
                LinearLayout.LayoutParams layoutButtonOrdered = new LinearLayout.LayoutParams(DrawerLayout.LayoutParams.WRAP_CONTENT, DrawerLayout.LayoutParams.WRAP_CONTENT);
                layoutButtonOrdered.rightMargin = 10;
                buttonToOrdered.setLayoutParams(layoutButtonOrdered);
                buttonToOrdered.setText(listTable.get(position).getName());

                if(item.getKey().equals(receiptToOrdered)){
                    buttonToOrdered.setBackgroundResource(R.drawable.buttonchoosetransfer);
                }else{
                    buttonToOrdered.setBackgroundResource(R.drawable.buttontransfer);
                }




                buttonToOrdered.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        receiptToOrdered = item.getKey();
                        moveOrderedI.onClickGetMenuToOrdered();
                    }
                });

                llButtonOrdered.addView(buttonToOrdered);
            }

        }





        btnAddButtonOrdered = (Button) view.findViewById(R.id.btnAddButtonOrdered);
        btnAddButtonOrdered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickMoveOrdered = true;
                Intent intent = new Intent(getActivity(), ChooseTableActivity.class);
                startActivity(intent);
            }
        });







        return view;
    }
    private void showChooseTable(){

    }
    public void toast(String message){
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }









}
