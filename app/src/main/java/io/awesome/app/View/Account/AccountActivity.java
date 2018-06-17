package io.awesome.app.View.Account;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tapadoo.alerter.Alerter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import io.awesome.app.General.SetFont;
import io.awesome.app.Presenter.Account.AccountPresenterImp;
import io.awesome.app.R;
import io.awesome.app.View.ChangePassword.ChangePasswordActivity;
import io.awesome.app.View.NewExpense.NewExpenseActivity;
import io.awesome.app.View.Table.TableActivity;

import static io.awesome.app.View.Main.MainActivity.account;

public class AccountActivity extends AppCompatActivity implements AccountView{

    private static final int RESULT_LOAD_IMAGE = 1;
    private Toolbar toolbar;

    private ImageView imageAccount;
    private Button btnAddImageAccount;
    private EditText edNameAccount;
    private EditText edEmailAccount;
    private TextView tvNameAccount, tvEmailAccount, tvSaveAccount;


    public static final String MyPREFERENCES = "capuccino" ;
    private SharedPreferences sharedPreferences;
    private String token;

    private AccountPresenterImp accountPresenterImp;
    private ProgressDialog dialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        showProgressAccount();
        imageAccount = (ImageView) findViewById(R.id.imageAccount);
//        btnAddImageAccount = (Button) findViewById(R.id.btnAddImageAccount);
        edNameAccount = (EditText) findViewById(R.id.edNameAccount);
        edEmailAccount = (EditText) findViewById(R.id.edEmailAccount);


        SetFont setFont = new SetFont("Roboto-Regular.ttf");
        setFont.getFont();


        sharedPreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        token = sharedPreferences.getString("token", null);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle("Tài khoản");

        // Set ActionBar
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_back);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        accountPresenterImp = new AccountPresenterImp(this, this);


        tvSaveAccount = (TextView) findViewById(R.id.tvSaveAccount);



        Picasso.with(this).load(account.getAvatarUrl()).into(imageAccount);
        imageAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent,RESULT_LOAD_IMAGE);
            }
        });
        Typeface mFont = Typeface.createFromAsset(getAssets(), "Roboto-ThinItalic.ttf");

        tvNameAccount = (TextView) findViewById(R.id.tvNameAccount);
        tvNameAccount.setText(account.getDisplayName());

        tvEmailAccount = (TextView) findViewById(R.id.tvEmailAccount);
        tvEmailAccount.setText(account.getEmail());



        edNameAccount.setText(account.getDisplayName());
        edNameAccount.setTypeface(mFont);
        edNameAccount.addTextChangedListener(new TextWatcher() {

            @SuppressLint("ResourceAsColor")
            public void onTextChanged(final CharSequence s, int start, int before,
                                      int count) {
                if(s.toString().equals(account.getDisplayName()) || s.toString().equals("")) {
                    if(s.toString().equals("")){
                        edNameAccount.setBackgroundResource(R.drawable.bgr_edittext_error);
                    }else{
                        edNameAccount.setBackgroundResource(R.drawable.bgr_edittext);
                    }
                    tvSaveAccount.setEnabled(false);
                    tvSaveAccount.setTextColor(getResources().getColor(R.color.colorPrimary));
                }else{
                    edNameAccount.setBackgroundResource(R.drawable.bgr_edittext);
                    tvSaveAccount.setEnabled(true);
                    tvSaveAccount.setTextColor(getResources().getColor(R.color.white));
                }

                tvSaveAccount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showConfirmChangeAccount();
                    }
                });
            }



            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {

            }
        });

        edEmailAccount.setText(account.getEmail());
        edEmailAccount.setTypeface(mFont);

        dialog.dismiss();

    }
    public void toast(String message){
        Toast.makeText(getBaseContext(),message,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            try {
                Uri uri = data.getData();
                /*String realPath = getRealPathFromURI(uri);

                File file = new File(realPath);
                String imagePath = file.getAbsolutePath();*/


                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imageAccount.setImageBitmap(bitmap);

                accountPresenterImp.uploadImage(bitmap, token);
                /*accountPresenterImp.uploadFile(imagePath, token);*/
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }



    @Override
    public void uploadImageSuccessful() {

    }
    /////////////////////////////////// showProgressAccount //////////////////////////////
    @Override
    public void showProgressAccount() {
        new ProgressAccount().execute();
    }
    private class ProgressAccount extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(AccountActivity.this,R.style.AppTheme_Dark_Dialog);
            dialog.setMessage("Đang tải...");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
    /////////////////////////////////// Finish showProgressAccount //////////////////////////////


    /////////////////////////////////// showProgressChangeAccount //////////////////////////////
    @Override
    public void showProgressChangeAccount() {
        new ProgressChangeAccount().execute();
    }
    private class ProgressChangeAccount extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog.setMessage("Đang cập nhật...");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            String name = edNameAccount.getText().toString();
            accountPresenterImp.change(name, token);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            tvNameAccount.setText(account.getDisplayName());
            edNameAccount.setText(account.getDisplayName());
            tvSaveAccount.setClickable(false);
            tvSaveAccount.setTextColor(getResources().getColor(R.color.colorPrimary));
            dialog.dismiss();
        }
    }
    /////////////////////////////////// Finish ProgressChangeAccount //////////////////////////////



    /////////////////////////////////// Confirm Change Account //////////////////////////////
    @Override
    public void showConfirmChangeAccount() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AccountActivity.this);
        builder.setMessage("Chi tiêu sẽ được tạo mới");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                showProgressChangeAccount();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.show();
    }



    public String getRealPathFromURI (Uri contentUri) {
        String path = null;
        String[] proj = { MediaStore.MediaColumns.DATA };
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }

    @Override
    public void alertMessage(String titleError, String textError, int responseCode) {
        if(responseCode == 500) {
            Alerter.create(this)
                    .setTitle(titleError)
                    .setText(textError)
                    .setBackgroundColorRes(R.color.red) // or setBackgroundColorInt(Color.CYAN)
                    .show();
            edNameAccount.setText(account.getDisplayName());
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

}
