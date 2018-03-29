package io.awesome.app.Presenter.ResetPassword;


import io.awesome.app.Presenter.InterfacePresent.ValidatePW_IP;

/**
 * Created by sung on 11/11/2017.
 */

public interface ResetPasswordPresenter extends ValidatePW_IP{

    public boolean validateConfirmPassword(String password, String confirmPassword);
    public void validateReset(String password, String confirmPassword);
    public void reset(String password, String confirmPassword,String token);
}
