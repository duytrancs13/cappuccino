package io.awesome.app.Presenter.MoveOrdered;

import android.widget.Button;

import java.util.List;

import io.awesome.app.Model.Ordered;

/**
 * Created by sung on 23/04/2018.
 */

public interface MoveOrderedPresenter {
    void getMenuOrdered();
    void getMenuToOrdered();
    void moveItemOrdered(String receiptId, String menuId, String quality, String statusOrdered);
    void createReceiptToOrdered(String idTable, int position, Button buttonToOrdered);
}
