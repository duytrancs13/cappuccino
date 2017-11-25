package info.devexchanges.navvp.View.ResetPassword;

/**
 * Created by sung on 11/11/2017.
 */

public interface ResetPasswordView {
    void alertErrorEmptyPassword();
    void alertErrorMinPassword();
    void alertSuccessPassword();

    void alertErrorEmptyConfirmPassword();
    void alertErrorMatchConfirmPassword();
    void alertSuccessConfirmPassword();

    void showProgress();

    void resetPasswordSuccessful();
    void resetPasswordFailed();
    void resetPasswordErrorServer();
}
