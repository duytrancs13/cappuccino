package io.awesome.app.View.Fragment.MoveOrdered;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import io.awesome.app.Model.Ordered;
import io.awesome.app.Model.Table;
import io.awesome.app.Presenter.MenuTabs.MenuTabsPresenterImp;
import io.awesome.app.Presenter.MoveOrdered.MoveOrderedPresenterImp;
import io.awesome.app.R;
import io.awesome.app.View.ChooseTableActivity;
import io.awesome.app.View.MoveOrder.MoveOrderActivity;

;import static android.content.Intent.getIntent;
import static io.awesome.app.View.Main.MainActivity.listTable;
import static io.awesome.app.View.Main.MainActivity.receiptId;
import static io.awesome.app.View.Main.MainActivity.receiptToOrdered;
import static io.awesome.app.View.Table.TableActivity.onClickMoveOrdered;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMoveOrdered extends Fragment {

    private LinearLayout linearLayoutOrdered;
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

        int position = getActivity().getIntent().getIntExtra("positionTable", -1);

        if(position == -1){
            tvChooseTable.setText("Chọn bàn muốn chuyển");

        }else{

            tvChooseTable.setText(listTable.get(position).getName());
            if(listTable.get(position).getReceiptId().length() == 0 ){
                moveOrderedI.createReceiptToOrdered(listTable.get(position).getId(),position);
            }else{
                receiptToOrdered = listTable.get(position).getReceiptId();
                moveOrderedI.getMenuToOrdered();
            }
        }

        cVChooseTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickMoveOrdered = true;
                Intent intent = new Intent(getActivity(), ChooseTableActivity.class);
                intent.putExtra("positionTable", -1);
                startActivity(intent);
            }
        });




        return view;
    }
    public void toast(String message){
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }









}
