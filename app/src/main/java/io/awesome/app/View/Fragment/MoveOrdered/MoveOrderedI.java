package io.awesome.app.View.Fragment.MoveOrdered;

import android.widget.Button;

import java.util.HashMap;
import java.util.List;

import io.awesome.app.Model.Ordered;
import io.awesome.app.Model.Table;

/**
 * Created by sung on 20/03/2018.
 */

public interface MoveOrderedI {
    public void moveOrdered(Ordered ordered, String statusOrdered );
    public void getMenuOrdered();
    public void getMenuToOrdered();
    public void onClickGetMenuToOrdered();
    public void createReceiptToOrdered(String idTable, int position, Button buttonToOrdered);
}
