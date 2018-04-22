package io.awesome.app.View.Fragment.Menu;

import java.util.List;

import io.awesome.app.Model.Menu;
import io.awesome.app.Model.Ordered;
import io.awesome.app.View.InterfaceView.Progress_IV;

/**
 * Created by sung on 11/11/2017.
 */

public interface FragmentMenuView extends Progress_IV {
    void showMenu(List<Menu> listMenu);
    void setTextPopupNodeAdd(String textNote, String menuId);
    void showPopupNodeAdd();

}
