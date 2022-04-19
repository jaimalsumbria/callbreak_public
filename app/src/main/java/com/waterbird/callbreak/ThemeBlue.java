package com.waterbird.callbreak;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.RelativeLayout;

public class ThemeBlue implements ThemeInterface {

    Drawable bgd ;

    ThemeBlue(Context context){

        bgd = context.getResources().getDrawable(R.drawable.background_theme_blue);

    }

    @Override
    public void setTheme(RelativeLayout layout) {
        layout.setBackground(bgd);
    }
}
