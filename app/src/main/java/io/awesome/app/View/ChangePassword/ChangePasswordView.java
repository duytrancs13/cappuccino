package io.awesome.app.View.ChangePassword;

import io.awesome.app.View.InterfaceView.AlertMessage_IV;
import io.awesome.app.View.InterfaceView.ErrorPW_IV;
import io.awesome.app.View.InterfaceView.Progress_IV;

/**
 * Created by sung on 11/11/2017.
 */

public interface ChangePasswordView extends ErrorPW_IV, Progress_IV, AlertMessage_IV {
    public void errorChangePW(String errorConfirmPW);
    public void changePwSuccessful();
}
