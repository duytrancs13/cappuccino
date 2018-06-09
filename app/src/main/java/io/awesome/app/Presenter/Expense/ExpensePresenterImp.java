package io.awesome.app.Presenter.Expense;

import android.content.Context;

import io.awesome.app.View.Expense.ExpenseActivity;
import io.awesome.app.View.Expense.ExpenseView;

/**
 * Created by sung on 09/06/2018.
 */

public class ExpensePresenterImp implements ExpensePresenter {

    private Context context;
    private ExpenseView expenseView;

    public ExpensePresenterImp(Context context, ExpenseView expenseView) {
        this.context = context;
        this.expenseView = expenseView;
    }

    @Override
    public void getExpenseByAccount() {

    }
}
