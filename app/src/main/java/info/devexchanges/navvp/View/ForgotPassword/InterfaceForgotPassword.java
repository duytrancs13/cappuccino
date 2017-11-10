package info.devexchanges.navvp.View.ForgotPassword;

/**
 * Created by sung on 10/11/2017.
 */

public interface InterfaceForgotPassword {
    void alertErrorEmptyEmail();
    void alertErrorFormatEmail();
    void alertSuccessEmail();

    void showProgress();

    void alertSuccessful();
    void alertFailed();
}
