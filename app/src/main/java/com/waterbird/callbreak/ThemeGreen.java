package com.waterbird.callbreak;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.RelativeLayout;

public class ThemeGreen implements ThemeInterface {

    Drawable bgd ;

    ThemeGreen(Context context){

        bgd = context.getResources().getDrawable(R.drawable.background_theme_green);

    }

    @Override
    public void setTheme(RelativeLayout layout) {
        layout.setBackground(bgd);
    }
}
