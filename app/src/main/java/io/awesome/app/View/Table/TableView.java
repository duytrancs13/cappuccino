package io.awesome.app.View.Table;

import java.util.List;

import io.awesome.app.Model.Table;

/**
 * Created by sung on 12/11/2017.
 */

public interface TableView {
    public void alertLogout();
    public void showTable(List<Table> listTable);
    public void goToPageMenu(String receiptId, String tableId);
}
