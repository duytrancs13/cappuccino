package info.devexchanges.navvp.General;

import android.content.Context;

import info.devexchanges.navvp.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by sung on 10/11/2017.
 */

public class SetFont {
    private String nameFont;
    public SetFont(String nameFont) {
        this.nameFont=nameFont;
    }

    public void getFont(){
        CalligraphyConfig.initDefault(
                new CalligraphyConfig.Builder()
                        .setDefaultFontPath(this.nameFont)
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
    }
}
