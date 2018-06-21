package io.awesome.app.View.NewExpense;

import io.awesome.app.View.InterfaceView.AlertMessage_IV;

/**
 * Created by sung on 09/06/2018.
 */

public interface NewExpenseView extends AlertMessage_IV {
    void showProgress();
    void showConfirmCreateExpense();
    void showConfirmCancelExpense();
}
