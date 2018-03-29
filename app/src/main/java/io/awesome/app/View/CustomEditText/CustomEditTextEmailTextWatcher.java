package io.awesome.app.View.CustomEditText;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;


/**
 * Created by sung on 06/03/2018.
 */

public class CustomEditTextEmailTextWatcher implements TextWatcher {

    private EditText mEditTextEmail;
    private ImageView clearLogin;

    public CustomEditTextEmailTextWatcher(EditText mEditTextEmail, ImageView clearLogin) {
        this.mEditTextEmail = mEditTextEmail;
        this.clearLogin = clearLogin;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if(charSequence.length() > 0){
            clearLogin.setVisibility(View.VISIBLE);
            clearLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mEditTextEmail.setText("");
                }
            });
        }else{
            clearLogin.setVisibility(View.GONE);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
