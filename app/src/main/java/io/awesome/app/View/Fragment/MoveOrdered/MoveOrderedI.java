package io.awesome.app.View.Fragment.MoveOrdered;

import io.awesome.app.Model.Ordered;

/**
 * Created by sung on 20/03/2018.
 */

public interface MoveOrderedI {
    public void moveOrdered(String receiptId, String menuId, String quality);
    public void getMenuToOrdered();
}
