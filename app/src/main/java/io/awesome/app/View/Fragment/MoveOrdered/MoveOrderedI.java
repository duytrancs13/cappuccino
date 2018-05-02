package io.awesome.app.View.Fragment.MoveOrdered;

import io.awesome.app.Model.Ordered;

/**
 * Created by sung on 20/03/2018.
 */

public interface MoveOrderedI {
    public void moveOrdered(String menuId, String quality, String statusOrdered);
    public void getMenuToOrdered();
    public void createReceiptToOrdered(String idTable, int position);
}
