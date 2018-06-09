package io.awesome.app.Presenter.NewExpense;

/**
 * Created by sung on 09/06/2018.
 */

public interface NewExpensePresenter {
    void validateNewExpense(String name, String price, String quatity);
    void createExpense(String name, String price, String quatity, final String createBy, String note, String token);

}
