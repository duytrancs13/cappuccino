package io.awesome.app.Presenter.MoveOrdered;

import android.widget.Button;

import org.json.JSONObject;

import java.util.List;

import io.awesome.app.Model.Ordered;

/**
 * Created by sung on 23/04/2018.
 */

public interface MoveOrderedPresenter {
    void getMenuOrdered();
    void syncMoveOrdered(JSONObject object);
}
