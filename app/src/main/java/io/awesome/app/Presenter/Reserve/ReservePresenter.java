package io.awesome.app.Presenter.Reserve;

/**
 * Created by sung on 26/06/2018.
 */

public interface ReservePresenter{
    void validateNewReserve(String name, String numberPhone, String quantity, String date, String time);
    void createReserve(String name, String numberPhone, String quantity, String date, String time, String note);
}
