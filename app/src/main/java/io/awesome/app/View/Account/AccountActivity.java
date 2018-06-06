package io.awesome.app.View.Account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import io.awesome.app.General.SetFont;
import io.awesome.app.R;
import io.awesome.app.View.ChangePassword.ChangePasswordActivity;
import io.awesome.app.View.Table.TableActivity;

import static io.awesome.app.View.Main.MainActivity.account;

public class AccountActivity extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 1;
    private Toolbar toolbar;

    private ImageView imageAccount;
    private Button btnAddImageAccount;
    private TextView tvNameAccount;
    private TextView tvEmailAccount;
    private Button btnChangePassword;

    private int hadPhoto = 1;

    public static final String MyPREFERENCES = "capuccino" ;
    private SharedPreferences sharedPreferences;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        imageAccount = (ImageView) findViewById(R.id.imageAccount);
        btnAddImageAccount = (Button) findViewById(R.id.btnAddImageAccount);
        tvNameAccount = (TextView) findViewById(R.id.tvNameAccount);
        tvEmailAccount = (TextView) findViewById(R.id.tvEmailAccount);
        btnChangePassword = (Button) findViewById(R.id.btnChangePassword) ;


        SetFont setFont = new SetFont("Roboto-Regular.ttf");
        setFont.getFont();


        sharedPreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        token = sharedPreferences.getString("token", null);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tài khoản");

        // Set ActionBar
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_back);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if(hadPhoto==0){
            imageAccount.setBackgroundResource(R.drawable.ic_profile);
            btnAddImageAccount.setText(" Thêm ảnh");
        }else{
            toast("Set anh vao!!!");
            btnAddImageAccount.setText(" Thay đổi ảnh ");
        }

        btnAddImageAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent,RESULT_LOAD_IMAGE);
            }
        });

        tvNameAccount.setText(account.getDisplayName());
        tvEmailAccount.setText(account.getEmail());




        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ChangePasswordActivity.class);
                startActivity(intent);
            }
        });
    }
    public void toast(String message){
        Toast.makeText(getBaseContext(),message,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            toast("Call api");
            Uri selectImage = data.getData();
            imageAccount.setImageURI(selectImage);
            btnAddImageAccount.setText(" Thay đổi ảnh");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(this, TableActivity.class));
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
