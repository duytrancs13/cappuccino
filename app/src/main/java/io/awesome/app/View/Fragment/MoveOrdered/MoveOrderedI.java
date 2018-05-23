package io.awesome.app.View.Fragment.MoveOrdered;

import android.widget.Button;

import java.util.HashMap;
import java.util.List;

import io.awesome.app.Model.Ordered;
import io.awesome.app.Model.Table;

/**
 * Created by sung on 20/03/2018.
 */

public interface MoveOrderedI {
    public void moveOrdered(Ordered ordered, String statusOrdered, int quatity);
    public void getMenuOrdered();
    public void onClickGetMenuToOrdered();
    public void showProgress();
}
