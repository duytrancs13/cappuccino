
package io.awesome.app.View.Table;

import io.awesome.app.View.InterfaceView.AlertMessage_IV;

/**
 * Created by sung on 12/11/2017.
 */

public interface TableView extends AlertMessage_IV {
    public void alertLogout();
    public void showTables();
    public void gotoMenu(String receipt ,int statusMenu);
    public void gotoTransferOrdered();
}
