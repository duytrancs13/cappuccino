package io.awesome.app.View.Reserve;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.tapadoo.alerter.Alerter;

import java.util.Calendar;

import io.awesome.app.General.SetFont;
import io.awesome.app.Presenter.Reserve.ReservePresenterImp;
import io.awesome.app.R;
import io.awesome.app.View.Table.TableActivity;

import static io.awesome.app.View.Table.TableActivity.nameTableMoveOrdered;

public class ReserveActivity extends AppCompatActivity implements ReserveView{
    public static final String MyPREFERENCES = "capuccino";
    private SharedPreferences sharedPreferences;
    private String token;

    private Toolbar toolbar;

    /*private CardView cVChooseDateReserve, cVChooseFromReserve, cVChooseToReserve;
    private TextView tvChooseDateReserve, tvChooseFromReserve, tvChooseToReserve;*/

    private EditText etNameReserve, etNumberPhoneReserve, etQuantityReserve, etDateReserve, etTimeComeReserve, etNoteReserve;

    private int dayFinal, monthFinal, yearFinal, hourFromReserve, minuteFromReserve, hourToReserve, minuteToReserve;

    private Button btnConfirmReserve;

    private ProgressDialog dialog ;

    private ReservePresenterImp reservePresenterImp;

    private boolean onClickBtnConfirmReserve = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        token = sharedPreferences.getString("token", null);

        SetFont setFont = new SetFont("Roboto-Regular.ttf");
        setFont.getFont();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Đặt chỗ");

        // Set ActionBar
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_back);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dialog = new ProgressDialog(ReserveActivity.this,R.style.AppTheme_Dark_Dialog);

        /*tvChooseDateReserve = (TextView) findViewById(R.id.tvChooseDateReserve);

        cVChooseDateReserve = (CardView) findViewById(R.id.cVChooseDateReserve);*/

        etNameReserve = (EditText) findViewById(R.id.etNameReserve);
        etNumberPhoneReserve = (EditText) findViewById(R.id.etNumberPhoneReserve);
        etQuantityReserve = (EditText) findViewById(R.id.etQuantityReserve);
        etDateReserve = (EditText) findViewById(R.id.etDateReserve);
        etTimeComeReserve = (EditText) findViewById(R.id.etTimeComeReserve);
        etNoteReserve = (EditText) findViewById(R.id.etNoteReserve);

        reservePresenterImp = new ReservePresenterImp(this,this, token);

        final Calendar c = Calendar.getInstance();
        yearFinal = c.get(Calendar.YEAR);
        monthFinal = c.get(Calendar.MONTH)+1;
        dayFinal = c.get(Calendar.DAY_OF_MONTH);

        /*cVChooseDateReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/

        etDateReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ReserveActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                yearFinal = i;
                                monthFinal = i1+1;
                                dayFinal = i2;
                                etDateReserve.setText(dayFinal+"/"+monthFinal+"/"+yearFinal);
                            }
                        }
                        , yearFinal, monthFinal-1, dayFinal);
                datePickerDialog.show();
            }
        });

        /*tvChooseFromReserve = (TextView) findViewById(R.id.tvChooseFromReserve);

        cVChooseFromReserve = (CardView) findViewById(R.id.cVChooseFromReserve);*/

        hourFromReserve = c.get(Calendar.HOUR);
        minuteFromReserve = c.get(Calendar.MINUTE);

        /*cVChooseFromReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(ReserveActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                hourFromReserve = i;
                                minuteFromReserve = i1;
                                tvChooseFromReserve.setText(hourFromReserve+":"+minuteFromReserve);
                            }
                        }
                        , hourFromReserve, minuteFromReserve, true);
                timePickerDialog.show();
            }
        });*/

        etTimeComeReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(ReserveActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                hourFromReserve = i;
                                minuteFromReserve = i1;
                                etTimeComeReserve.setText(hourFromReserve+":"+minuteFromReserve);
                            }
                        }
                        , hourFromReserve, minuteFromReserve, true);
                timePickerDialog.show();
            }
        });

        /*tvChooseToReserve = (TextView) findViewById(R.id.tvChooseToReserve);

        cVChooseToReserve = (CardView) findViewById(R.id.cVChooseToReserve);
        cVChooseToReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(ReserveActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                hourToReserve = i;
                                minuteToReserve = i1;
                                tvChooseToReserve.setText(hourToReserve+":"+minuteToReserve);
                            }
                        }
                        , hourFromReserve, minuteFromReserve, true);
                timePickerDialog.show();
            }
        });*/



        btnConfirmReserve = (Button) findViewById(R.id.btnConfirmReserve);
        btnConfirmReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*if(tvChooseDateReserve.getText().equals("Vui lòng chọn ngày")){
                    toast("Vui lòng chọn ngày");
                }else if(yearFinal< c.get(Calendar.YEAR)
                        || monthFinal < c.get(Calendar.MONTH)+1
                        || dayFinal < c.get(Calendar.DAY_OF_MONTH)){
                    toast("Vui lòng chọn sau ngày hiện tại");
                }else if(tvChooseFromReserve.getText().equals("Vui lòng chọn thời gian đặt")) {
                    toast("Vui lòng chọn thời gian đặt");
                }else if(hourToReserve<hourFromReserve){
                    toast("Thời gian đến không được sau thời gian đặt");

                }else{
                    if(hourToReserve==hourFromReserve){
                        if(minuteToReserve<minuteFromReserve){
                            toast("Thời gian đến không được sau thời gian đặt");
                        }else{
                            toast("ok");
                        }
                    }else{
                        toast("ok");
                    }
                }*/
                showProgress();
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

    public void toast(String mgs){
        Toast.makeText(getBaseContext(), mgs, Toast.LENGTH_LONG).show();
    }


    @Override
    public void showProgress() {
        new Progress().execute();
    }

    @Override
    public void alertMessage(String titleError, String textError, int responseCode) {
        if(responseCode == 500) {
            Alerter.create(this)
                    .setTitle(titleError)
                    .setText(textError)
                    .setBackgroundColorRes(R.color.red) // or setBackgroundColorInt(Color.CYAN)
                    .show();
            dialog.dismiss();
        }else{
            Alerter.create(this)
                    .setTitle(titleError)
                    .setText(textError)
                    .setBackgroundColorRes(R.color.colorPrimary) // or setBackgroundColorInt(Color.CYAN)
                    .show();
            dialog.dismiss();
            /*finish();*/
        }
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

            String name = etNameReserve.getText().toString();
            String numberPhone = etNumberPhoneReserve.getText().toString();
            String quantity = etQuantityReserve.getText().toString();
            String date = etDateReserve.getText().toString();
            String time = etTimeComeReserve.getText().toString();
            String note = etNoteReserve.getText().toString();
            reservePresenterImp.createReserve(name, numberPhone, quantity, date, time, note);
            /*newExpensePresenterImp.createExpense(name, price, quantity, unit, createBy, note, token);*/
            return null;
        }
    }
}
