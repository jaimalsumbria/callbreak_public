package com.waterbird.callbreak;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class ThemeRed implements ThemeInterface {

    Drawable bgd ;

    ThemeRed(Context context){

        bgd = context.getResources().getDrawable(R.drawable.background_theme_2);

    }

    @Override
    public void setTheme(RelativeLayout layout) {
        layout.setBackground(bgd);
    }
}
