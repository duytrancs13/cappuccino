package info.devexchanges.navvp.Presenter.Login;

/**
 * Created by sung on 10/11/2017.
 */

public interface LoginPresenter {
    boolean validate(String email, String password);
    void login(String email, String password);
    void attemptLogin(String email, String password);
}
