package io.awesome.app.View.DetailExpense;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import io.awesome.app.General.SetFont;
import io.awesome.app.Model.Expense;
import io.awesome.app.Presenter.UpdateExpense.UpdateExpensePresenterImp;
import io.awesome.app.R;

public class DetailExpenseActivity extends AppCompatActivity implements DetailExpenseView {
    private Toolbar toolbar;
    public static final String MyPREFERENCES = "capuccino" ;
    private SharedPreferences sharedPreferences;
    private String token;


    private ProgressDialog dialog ;

    private TextView tvDetailNameExpense, tvDetailPriceExpense, tvDetailQuantityExpense, tvDetailUnitExpense, tvDetailCreateByExpense, tvDetailDateCreateExpense, tvDetailStatusExpense, tvDetailApproveByExpense, tvDetailApproveAtExpense, tvDetailNoteExpense;
    private LinearLayout llDetailApproveByExpense, llDetailApproveAtExpense;
    private Expense expense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_expense);
        SetFont setFont = new SetFont("Roboto-Regular.ttf");
        setFont.getFont();


        sharedPreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        token = sharedPreferences.getString("token", null);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Xem chi tiết chi tiêu");

        // Set ActionBar
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_back);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        tvDetailNameExpense = (TextView) findViewById(R.id.tvDetailNameExpense);
        tvDetailPriceExpense = (TextView) findViewById(R.id.tvDetailPriceExpense);
        tvDetailQuantityExpense = (TextView) findViewById(R.id.tvDetailQuantityExpense);
        tvDetailUnitExpense = (TextView) findViewById(R.id.tvDetailUnitExpense);
        tvDetailCreateByExpense = (TextView) findViewById(R.id.tvDetailCreateByExpense);
        tvDetailDateCreateExpense = (TextView) findViewById(R.id.tvDetailDateCreateExpense);
        tvDetailStatusExpense = (TextView) findViewById(R.id.tvDetailStatusExpense);

        llDetailApproveByExpense = (LinearLayout) findViewById(R.id.llDetailApproveByExpense);
        tvDetailApproveByExpense = (TextView) findViewById(R.id.tvDetailApproveByExpense);

        llDetailApproveAtExpense = (LinearLayout) findViewById(R.id.llDetailApproveAtExpense);
        tvDetailApproveAtExpense = (TextView) findViewById(R.id.tvDetailApproveAtExpense);

        tvDetailNoteExpense = (TextView) findViewById(R.id.tvDetailNoteExpense);
        showProgress();

    }





    public void toast(String message){
        Toast.makeText(getBaseContext(),message,Toast.LENGTH_SHORT).show();
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
    public void showProgress() {
        new Progress().execute();
    }
    private class Progress extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(DetailExpenseActivity.this,R.style.AppTheme_Dark_Dialog);
            dialog.setMessage("Đang kết nối...");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            expense = (Expense) getIntent().getSerializableExtra("objectExpense");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            tvDetailNameExpense.setText(expense.getName());
            tvDetailPriceExpense.setText(NumberFormat.getNumberInstance(Locale.GERMAN).format(expense.getPrice())+" đ");
            tvDetailQuantityExpense.setText(expense.getQuantity()+"");
            tvDetailUnitExpense.setText(expense.getUnit());
            tvDetailCreateByExpense.setText(expense.getCreateBy());
            android.text.format.DateFormat df = new android.text.format.DateFormat();
            tvDetailDateCreateExpense.setText(df.format("dd-MM-yyyy", expense.getCreateAt()));


            if(expense.getStatus().equals("Pending")){
                tvDetailStatusExpense.setText("Đợi phê duyệt");
            }else{
                tvDetailStatusExpense.setText("Đã phê duyệt");

                llDetailApproveByExpense.setVisibility(View.VISIBLE);
                tvDetailApproveByExpense.setText(expense.getApprovedBy());

                llDetailApproveAtExpense.setVisibility(View.VISIBLE);
                tvDetailApproveAtExpense.setText(df.format("dd-MM-yyyy", expense.getApprovedAt()));

            }
            tvDetailNoteExpense.setText(expense.getNote());
            dialog.dismiss();
        }
    }
}
