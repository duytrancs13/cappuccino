package io.awesome.app.View.MoveOrder;

import io.awesome.app.View.InterfaceView.AlertMessage_IV;

/**
 * Created by sung on 23/04/2018.
 */

public interface MoveOrderedView extends AlertMessage_IV {
    public void initFragment();
    public void undoAllFragment();
    public void gotoBackTableActivity();

}
