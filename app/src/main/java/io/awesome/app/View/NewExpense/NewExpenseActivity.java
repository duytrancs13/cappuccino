package io.awesome.app.View.NewExpense;

import android.app.ProgressDialog;
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
import io.awesome.app.View.Login.LoginActivity;

import static io.awesome.app.View.Main.MainActivity.account;

public class NewExpenseActivity extends AppCompatActivity implements NewExpenseView {
    private Toolbar toolbar;
    public static final String MyPREFERENCES = "capuccino" ;
    private SharedPreferences sharedPreferences;
    private String token;
    private EditText etNameExpense, etPriceExpense, etQuatityExpense, etNoteExpense;
    private ProgressDialog dialog ;
    private NewExpensePresenterImp newExpensePresenterImp;
    private Button btnConfirmCreateExpense;
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
        getSupportActionBar().setTitle("Chi tiêu");

        // Set ActionBar
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_back);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        etNameExpense = (EditText) findViewById(R.id.etNameExpense);
        etPriceExpense = (EditText) findViewById(R.id.etPriceExpense);
        etQuatityExpense = (EditText) findViewById(R.id.etQuatityExpense);
        etNoteExpense = (EditText) findViewById(R.id.etNoteExpense);
        dialog = new ProgressDialog(NewExpenseActivity.this,R.style.AppTheme_Dark_Dialog);
        newExpensePresenterImp = new NewExpensePresenterImp(this, this);
        btnConfirmCreateExpense = (Button) findViewById(R.id.btnConfirmCreateExpense);
        btnConfirmCreateExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameExpense = etNameExpense.getText().toString();
                String priceExpense = etPriceExpense.getText().toString();
                String quatityExpense = "";
                if(etQuatityExpense.getText().toString().length()!=0){
                    quatityExpense = etPriceExpense.getText().toString();
                }
                newExpensePresenterImp.validateNewExpense(nameExpense, priceExpense, quatityExpense);
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
    }

    @Override
    public void showProgress() {
        new Progress().execute();
    }

    @Override
    public void createExpenseSuccessful() {
        etNameExpense.setText("");
        etPriceExpense.setText("");
        etQuatityExpense.setText("");
        etNoteExpense.setText("");
        dialog.dismiss();
    }

    private class Progress extends AsyncTask<Void, Void, Void> {


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
            String quatity =etQuatityExpense.getText().toString();
            String note = etNoteExpense.getText().toString();
            String createBy = account.getUserId();
            newExpensePresenterImp.createExpense(name, price, quatity, createBy, note, token);
            return null;
        }
    }

}
