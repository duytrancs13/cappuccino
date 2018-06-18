package io.awesome.app.Presenter.Account;

import android.graphics.Bitmap;

import java.io.File;

/**
 * Created by sung on 09/06/2018.
 */

public interface AccountPresenter {
    void uploadImage(Bitmap bitmap, String token);
    void change(String name, String token);
}
