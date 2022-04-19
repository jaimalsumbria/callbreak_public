package com.waterbird.callbreak;

import android.content.Context;

import java.util.ArrayList;

public class ThemeFactory {


    private static ThemeFactory themeFactory = new ThemeFactory();

    public ArrayList<ThemeInterface> themes = new ArrayList<>();

    public ThemeFactory() {
    }

    public void setThemes(Context context){
        try {

            themes.add(new ThemeRed(context));
            themes.add(new Themeturquoise(context));
            themes.add(new ThemeBlue(context));
            themes.add(new ThemeGreen(context));
            themes.add(new ThemeYellow(context));

        } catch (Exception e){

        }
    }

    public static ThemeFactory getThemeFactory(){
        if(themeFactory.themes.size()==0) {
            themeFactory.setThemes(WaterBirdActivity.applicationContext);
        }
        return themeFactory;
    }

}
