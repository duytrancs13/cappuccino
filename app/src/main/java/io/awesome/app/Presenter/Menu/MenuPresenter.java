package io.awesome.app.Presenter.Menu;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import java.util.List;

import io.awesome.app.Model.Menu;
import io.awesome.app.Model.Ordered;
import io.awesome.app.View.Adapter.CustomMenuAdapter;

/**
 * Created by sung on 11/11/2017.
 */

public interface MenuPresenter {
    void loadMenuOfFavorite(String token);
    void loadMenuOfCategory(String idCategory, String token);
    //void addItemForReceipt(String token,String note, String menuId, String quality);
    void setTextPopupNoteAdd(String textNote, Ordered ordered);
    void showPopupNoteAdd();
   /* void addNoteForReceipt(String textNote, String menuId, String token);
    void qualityForReceipt(String menuId, String token, String quatity);*/
    void getMenuReceipt(String token);

}
