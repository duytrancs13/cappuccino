package io.awesome.app.View.Fragment.Receipt;

import java.util.List;

import io.awesome.app.Model.Ordered;
import io.awesome.app.View.InterfaceView.AlertMessage_IV;

/**
 * Created by sung on 26/11/2017.
 */

public interface FragmentReceiptView extends AlertMessage_IV {
    void loadMenuReceipt(List<Ordered> listOrdered);
    void showProgress();
}
