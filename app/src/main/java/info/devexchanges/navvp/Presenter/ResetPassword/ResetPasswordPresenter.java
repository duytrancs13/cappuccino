package info.devexchanges.navvp.Presenter.ResetPassword;

/**
 * Created by sung on 11/11/2017.
 */

public interface ResetPasswordPresenter {
    boolean validate(String password, String confirmPassword);
    void confirm(String password, String confirmPassword,String token);
}
