package info.devexchanges.navvp.Presenter.ForgotPassword;

/**
 * Created by sung on 10/11/2017.
 */

public interface InterfaceForgotPassword {
    boolean validate(String email);
    void forgot(String email);
    void attemptForgot(String email);
}
