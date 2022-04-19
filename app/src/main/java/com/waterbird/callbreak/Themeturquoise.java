package com.waterbird.callbreak;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.RelativeLayout;

public class Themeturquoise implements ThemeInterface {

    Drawable bgd ;

    Themeturquoise(Context context){

        bgd = context.getResources().getDrawable(R.drawable.background_theme_1);

    }

    @Override
    public void setTheme(RelativeLayout layout) {
        layout.setBackground(bgd);
    }
}
