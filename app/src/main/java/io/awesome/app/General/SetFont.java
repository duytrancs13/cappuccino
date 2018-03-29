package io.awesome.app.General;

import io.awesome.app.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

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
