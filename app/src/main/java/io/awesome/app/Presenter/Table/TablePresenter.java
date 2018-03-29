package io.awesome.app.Presenter.Table;

import android.widget.LinearLayout;

/**
 * Created by sung on 26/11/2017.
 */

public interface TablePresenter {
    void loadTable(String token);
    void dragTable(LinearLayout linearLayout);
    void updateReceiptIdOfTable(String idTable,String token);
}
