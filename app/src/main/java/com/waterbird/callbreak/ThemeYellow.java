package com.waterbird.callbreak;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.RelativeLayout;

public class ThemeYellow implements ThemeInterface {

    Drawable bgd ;

    ThemeYellow(Context context){

        bgd = context.getResources().getDrawable(R.drawable.background_theme_yellow);

    }

    @Override
    public void setTheme(RelativeLayout layout) {
        layout.setBackground(bgd);
    }
}
