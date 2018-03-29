package io.awesome.app.View.Login;

import io.awesome.app.View.InterfaceView.AlertMessage_IV;
import io.awesome.app.View.InterfaceView.ErrorEmail_IV;
import io.awesome.app.View.InterfaceView.ErrorPW_IV;
import io.awesome.app.View.InterfaceView.Progress_IV;

/**
 * Created by sung on 10/11/2017.
 */

public interface LoginView extends ErrorEmail_IV, ErrorPW_IV, Progress_IV, AlertMessage_IV {
    void loginSuccessful(String token);
}
