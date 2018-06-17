package io.awesome.app.Presenter.NewExpense;

/**
 * Created by sung on 09/06/2018.
 */

public interface NewExpensePresenter {
    void validateNewExpense(String name, String price, String quatity, String unit);
    void createExpense(String name, String price, String quatity, String unit, String createBy, String note, String token);

}
