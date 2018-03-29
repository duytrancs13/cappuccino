package io.awesome.app.View.CustomEditText;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import io.awesome.app.R;


/**
 * Created by sung on 06/03/2018.
 */

public class CustomEditTextPWTextWatcher implements TextWatcher {
    private EditText mEditTextPW;
    private ImageView visibilityLoginPW;

    public CustomEditTextPWTextWatcher(EditText mEditTextPW, ImageView visibilityLoginPW) {
        this.mEditTextPW = mEditTextPW;
        this.visibilityLoginPW = visibilityLoginPW;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if(charSequence.length() > 0){
            visibilityLoginPW.setVisibility(View.VISIBLE);
            visibilityLoginPW.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mEditTextPW.getTransformationMethod() instanceof PasswordTransformationMethod){
                        mEditTextPW.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        mEditTextPW.setSelection(mEditTextPW.getText().length());
                        visibilityLoginPW.setImageResource(R.drawable.ic_visibility_off);
                    }else{
                        mEditTextPW.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        mEditTextPW.setSelection(mEditTextPW.getText().length());
                        visibilityLoginPW.setImageResource(R.drawable.ic_visibility_on);
                    }
                }
            });
        }else{
            visibilityLoginPW.setVisibility(View.GONE);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
