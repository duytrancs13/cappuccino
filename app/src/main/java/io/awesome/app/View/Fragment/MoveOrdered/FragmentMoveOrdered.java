package io.awesome.app.View.Fragment.MoveOrdered;


import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import io.awesome.app.Model.Ordered;
import io.awesome.app.Model.Table;
import io.awesome.app.Presenter.MenuTabs.MenuTabsPresenterImp;
import io.awesome.app.Presenter.MoveOrdered.MoveOrderedPresenterImp;
import io.awesome.app.R;

;import static io.awesome.app.View.Main.MainActivity.listTable;
import static io.awesome.app.View.Main.MainActivity.receiptId;
import static io.awesome.app.View.Main.MainActivity.receiptToOrdered;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMoveOrdered extends Fragment {

    private LinearLayout linearLayoutOrdered;
    private MoveOrderedI moveOrderedI;

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_move_ordered, container, false);
        moveOrderedI = (MoveOrderedI) getActivity();

        linearLayoutOrdered = (LinearLayout) view.findViewById(R.id.linearLayoutOrdered);
        for(final Table table: listTable){
            if(table.getReceiptId() == receiptId){
                continue;
            }

            Button buttonOrdered = new Button(getContext());
            LinearLayout.LayoutParams layoutButtonOrdered = new LinearLayout.LayoutParams(DrawerLayout.LayoutParams.WRAP_CONTENT, DrawerLayout.LayoutParams.WRAP_CONTENT);
            layoutButtonOrdered.rightMargin = 10;
            buttonOrdered.setLayoutParams(layoutButtonOrdered);

            buttonOrdered.setText(table.getName());
            if(table.getStatus() == "idle"){
                buttonOrdered.setBackgroundColor(R.color.colorPrimary);
            }else{
                buttonOrdered.setBackgroundResource(R.drawable.buttonquality);
            }

            buttonOrdered.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                receiptToOrdered = table.getReceiptId();
                moveOrderedI.getMenuToOrdered();

                }
            });
            linearLayoutOrdered.addView(buttonOrdered);
        }




        return view;
    }
    public void toast(String message){
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }









}
