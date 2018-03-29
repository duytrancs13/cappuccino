package io.awesome.app.Presenter.Menu;

import java.util.List;

import io.awesome.app.Model.Menu;

/**
 * Created by sung on 11/11/2017.
 */

public interface MenuPresenter {
    /*void getAllMenu(String url, String idCategory , String receiptId, String token);*/
    void loadMenuOfFavorite(String receiptId, String token);
    void loadMenuOfCategory(String receiptId, String idCategory, String token);
    /*void getQualityMenu(String idReceipt, String token, List<Menu> listMenu);*/
    void addReceipt(String receiptId,String token,String key, String value);
    void loadPopupNoteAdd();

}
