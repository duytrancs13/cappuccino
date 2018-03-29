package io.awesome.app.Presenter.Receipt;

/**
 * Created by sung on 26/11/2017.
 */

public interface ReceiptPresenter {
    void getMenuReceipt(String receiptId ,String token);
    void updateReceipt(String receiptId, String token);
    void addReceipt(String receiptId, String token, String key, String value);
}