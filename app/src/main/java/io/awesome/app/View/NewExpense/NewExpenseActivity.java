package io.awesome.app.View.NewExpense;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tapadoo.alerter.Alerter;

import io.awesome.app.General.SetFont;
import io.awesome.app.Presenter.NewExpense.NewExpensePresenterImp;
import io.awesome.app.R;
import io.awesome.app.View.Expense.ExpenseActivity;
import io.awesome.app.View.Login.LoginActivity;

import static io.awesome.app.View.Main.MainActivity.account;

public class NewExpenseActivity extends AppCompatActivity implements NewExpenseView {
    private Toolbar toolbar;
    public static final String MyPREFERENCES = "capuccino" ;
    private SharedPreferences sharedPreferences;
    private String token;
    private EditText etNameExpense, etPriceExpense, etQuantityExpense, etUnitExpense, etNoteExpense;
    private ProgressDialog dialog ;
    private NewExpensePresenterImp newExpensePresenterImp;
    private Button btnCancelExpense,btnConfirmCreateExpense;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_expense);
        SetFont setFont = new SetFont("Roboto-Regular.ttf");
        setFont.getFont();


        sharedPreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        token = sharedPreferences.getString("token", null);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Thêm mới chi tiêu");

        // Set ActionBar
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_back);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        etNameExpense = (EditText) findViewById(R.id.etNameExpense);
        etPriceExpense = (EditText) findViewById(R.id.etPriceExpense);
        etQuantityExpense = (EditText) findViewById(R.id.etQuantityExpense);
        etUnitExpense = (EditText) findViewById(R.id.etUnitExpense);
        etNoteExpense = (EditText) findViewById(R.id.etNoteExpense);
        dialog = new ProgressDialog(NewExpenseActivity.this,R.style.AppTheme_Dark_Dialog);
        newExpensePresenterImp = new NewExpensePresenterImp(this, this, token);

        btnCancelExpense = (Button) findViewById(R.id.btnCancelExpense);
        btnCancelExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelExpens();
            }
        });


        btnConfirmCreateExpense = (Button) findViewById(R.id.btnConfirmCreateExpense);
        btnConfirmCreateExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            confirmCreateExpense();
            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            /*Intent intent = new Intent(this, ExpenseActivity.class);
            startActivity(intent);*/
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
    public void alertMessage(String titleError, String textError, int responseCode) {
        if(responseCode == 500) {
            Alerter.create(this)
                    .setTitle(titleError)
                    .setText(textError)
                    .setBackgroundColorRes(R.color.red) // or setBackgroundColorInt(Color.CYAN)
                    .show();
        }else{
            Alerter.create(this)
                    .setTitle(titleError)
                    .setText(textError)
                    .setBackgroundColorRes(R.color.colorPrimary) // or setBackgroundColorInt(Color.CYAN)
                    .show();
        }
        dialog.dismiss();
    }

    @Override
    public void showProgressCreateExpense() {
        new ProgressCreateExpense().execute();
    }

    @Override
    public void createExpenseSuccessful() {

    }

    private class ProgressCreateExpense extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Đang kết nối...");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            String name = etNameExpense.getText().toString();
            String price = etPriceExpense.getText().toString();
            String quantity = etQuantityExpense.getText().toString();
            String unit = etUnitExpense.getText().toString();
            String note = etNoteExpense.getText().toString();
            String createBy = account.getUserId();
            newExpensePresenterImp.createExpense(name, price, quantity, unit, createBy, note, token);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            finish();
        }
    }

    private void confirmCreateExpense(){
        AlertDialog.Builder builder = new AlertDialog.Builder(NewExpenseActivity.this);
        builder.setMessage("Chi tiêu sẽ được tạo mới");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String nameExpense = etNameExpense.getText().toString();
                String priceExpense = etPriceExpense.getText().toString();
                String quantityExpense = etQuantityExpense.getText().toString();
                String unitExpense = etUnitExpense.getText().toString();
                newExpensePresenterImp.validateNewExpense(nameExpense, priceExpense, quantityExpense, unitExpense);
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.show();
    }

    private void cancelExpens(){
        AlertDialog.Builder builder = new AlertDialog.Builder(NewExpenseActivity.this);
        builder.setMessage("Hủy tạo mới chi tiêu");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                etNameExpense.setText("");
                etPriceExpense.setText("");
                etQuantityExpense.setText("");
                etUnitExpense.setText("");
                etNoteExpense.setText("");
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.show();
    }

}
