package com.example.soundmusik.model;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.Array;

/**
 *  Class for managing user settings of text color/size.
 * UserSettings handles view settings such as text color and size
 *
 * @author Ashley Adeniyi
 *
 */
public class UserSettings {

    /**
     * Sets the text color in user preferences.
     * @param context the application context
     * @param color   the color to set
     */
    public static void setColorText(Context context, int color){
        SharedPreferences preferences = context.getSharedPreferences("UserSett", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("textColor",color);
        editor.apply();
    }

    /**
     * Gets the saved text color from user preferences.
     * @param context the application context
     * @return the saved text color, or 0 if not found
     */
    public static int getTextColot(Context context){
        SharedPreferences prefences = context.getSharedPreferences("UserSett",Context.MODE_PRIVATE);
        return prefences.getInt("textColor",0);
    }

    /**
     * Sets the text size in user preferences.
     * @param con the application context
     * @param size the size to set
     */
    public static void setTextSize(Context con, float size){
        SharedPreferences preferences = con.getSharedPreferences("UserSize", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat("textSize", size);
        editor.apply();
    }
    /**
     * Gets the saved text size from user preferences.
     * @param cont the application context
     * @return the saved text size, or the default size (14.0f) if not found
     */
    public static float getTextSize(Context cont){
        SharedPreferences preferences = cont.getSharedPreferences("UserSize",Context.MODE_PRIVATE);
        return preferences.getFloat("textSize",14.0f);
    }
}