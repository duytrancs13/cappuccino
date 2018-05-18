package io.awesome.app.Presenter.Table;

import android.content.Context;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import io.awesome.app.View.Table.TableActivity;
import io.awesome.app.View.Table.TableView;

/**
 * Created by sung on 26/11/2017.
 */

public interface TablePresenter {
    void loadTable(String token);
    void dragTable(LinearLayout linearLayout);
    void createReceipt(String idTable,String token, int position);
    void getMenuOrdered(String token);
}
