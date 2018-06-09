package io.awesome.app.View.Expense;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import io.awesome.app.Presenter.Expense.ExpensePresenterImp;
import io.awesome.app.R;
import io.awesome.app.View.NewExpense.NewExpenseActivity;

public class ExpenseActivity extends AppCompatActivity implements ExpenseView{

    private FloatingActionButton fab;
    private ListView lvExpense;
    private ExpensePresenterImp expensePresenterImp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        lvExpense = (ListView) findViewById(R.id.lvExpense);
        expensePresenterImp = new ExpensePresenterImp(this, this);
        expensePresenterImp.getExpenseByAccount();


//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, my_array);
//        lvExpense.setAdapter(adapter);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExpenseActivity.this, NewExpenseActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    // Quay về màn hình tableActivity bằng nút back của android
    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void showExpense() {

    }

}
