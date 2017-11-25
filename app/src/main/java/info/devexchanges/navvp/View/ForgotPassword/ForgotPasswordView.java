package info.devexchanges.navvp.View.ForgotPassword;

/**
 * Created by sung on 10/11/2017.
 */

public interface ForgotPasswordView {
    void alertErrorEmptyEmail();
    void alertErrorFormatEmail();
    void alertSuccessEmail();

    void showProgress();

    void forgotPasswordSuccessful();
    void forgotPasswordFailed();
    void forgotPasswordErrorServer();
}
