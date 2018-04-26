package io.awesome.app.Presenter.MoveOrdered;

/**
 * Created by sung on 23/04/2018.
 */

public interface MoveOrderedPresenter {
    void getMenuOrdered();
    void getMenuToOrdered();
    void moveItemOrdered(String receiptId, String menuId, String quality, String statusOrdered);
}
