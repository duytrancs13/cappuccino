package io.awesome.app.Presenter.ChangePassword;

import io.awesome.app.Presenter.InterfacePresent.ValidatePW_IP;

/**
 * Created by sung on 06/06/2018.
 */

public interface ChangePasswordPresenter{
    public void validateChange(String oldPassword, String newPassword);
    public void change(String oldPassword, String newPassword,String token);
}
