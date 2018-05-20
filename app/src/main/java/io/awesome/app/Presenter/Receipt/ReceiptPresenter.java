package io.awesome.app.Presenter.Receipt;

/**
 * Created by sung on 26/11/2017.
 */

public interface ReceiptPresenter {
    void getMenuReceipt(String token);
    void updateReceipt(String token);
    void addReceipt(String receiptId, String token, String key, String value);
    void printReceipt();
}