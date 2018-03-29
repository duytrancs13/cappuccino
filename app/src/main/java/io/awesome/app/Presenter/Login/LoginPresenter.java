package io.awesome.app.Presenter.Login;

import io.awesome.app.Presenter.InterfacePresent.ValidateEmail_IP;
import io.awesome.app.Presenter.InterfacePresent.ValidatePW_IP;

/**
 * Created by sung on 10/11/2017.
 */

public interface LoginPresenter extends ValidateEmail_IP, ValidatePW_IP{
    void validateLogin(String email, String password);
    void login(String email, String password);
}
