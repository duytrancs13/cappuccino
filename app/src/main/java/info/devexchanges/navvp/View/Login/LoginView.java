package info.devexchanges.navvp.View.Login;

/**
 * Created by sung on 10/11/2017.
 */

public interface LoginView {

    void alertErrorEmptyEmail();
    void alertErrorFormatEmail();
    void alertSuccessEmail();

    void alertErrorEmptyPassword();
    void alertErrorMinPassword();
    void alertSuccessPassword();

    void showProgress();

    void loginSuccessful(String token);
    void loginFailed();
    void loginErrorServer();


}
