package io.awesome.app.Presenter.ChangePassword;

import android.content.Context;

import io.awesome.app.View.ChangePassword.ChangePasswordView;

/**
 * Created by sung on 06/06/2018.
 */

public class ChangePasswordPresenterImp implements ChangePasswordPresenter{

    private ChangePasswordView changePasswordView;
    private Context context;

    public ChangePasswordPresenterImp(ChangePasswordView changePasswordView, Context context) {
        this.changePasswordView = changePasswordView;
        this.context = context;
    }
    public boolean validatePW(String password) {
        boolean valid = true;
        if(password.isEmpty() ){
            changePasswordView.errorPW("Vui lòng nhập mật khẩu !!!");
            valid = false;
            return valid;
        }else if(password.length() < 6){
            changePasswordView.errorPW("Ít nhất 5 kí tự !!!");
            valid = false;
            return valid;
        }else{
            changePasswordView.errorPW(null);
        }
        return valid;
    }
    public boolean validateNewPW(String password) {
        boolean valid = true;
        if(password.isEmpty() ){
            changePasswordView.errorChangePW("Vui lòng nhập mật khẩu !!!");
            valid = false;
            return valid;
        }else if(password.length() < 6){
            changePasswordView.errorChangePW("Ít nhất 5 kí tự !!!");
            valid = false;
            return valid;
        }else{
            changePasswordView.errorChangePW(null);
        }
        return valid;
    }

    @Override
    public void validateChange(String oldPassword, String newPassword) {
        if(!validatePW(oldPassword) || !validateNewPW(newPassword)){
            return;
        }
        changePasswordView.showProgress();
    }

    @Override
    public void change(String oldPassword, String newPassword, String token) {
        changePasswordView.alertMessage("Thành công","Goi API", 200);
        /*changePasswordView.alertMessage("Thành công","Vui lòng đăng nhập!!!", 200);*/
    }
}
