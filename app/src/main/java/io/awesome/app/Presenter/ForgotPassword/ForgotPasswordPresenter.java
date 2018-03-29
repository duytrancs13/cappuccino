package io.awesome.app.Presenter.ForgotPassword;

import io.awesome.app.Presenter.InterfacePresent.ValidateEmail_IP;

/**
 * Created by sung on 10/11/2017.
 */

public interface ForgotPasswordPresenter extends ValidateEmail_IP{
    void validateForgotPassword(String email);
    void forgot(String email);
}
