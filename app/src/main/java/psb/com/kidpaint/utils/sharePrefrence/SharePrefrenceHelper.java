package psb.com.kidpaint.utils.sharePrefrence;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;

import psb.com.kidpaint.App;
import psb.com.kidpaint.R;

public class SharePrefrenceHelper {

    ///////////////////////////////////////////////////////////////////////////
    // painting color
    ///////////////////////////////////////////////////////////////////////////
    public static void setColor(int color){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(App.getContext());
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("Color", color);
        editor.commit();
    }

    public static int getColor(){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(App.getContext());
        return settings.getInt("Color", ContextCompat.getColor(App.getContext(),R.color.palette_color_11));
    }

    ///////////////////////////////////////////////////////////////////////////
    // Painting size
    ///////////////////////////////////////////////////////////////////////////
    public static void setSize(int size){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(App.getContext());
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("Size", size);
        editor.commit();
    }

    public static int getSize(){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(App.getContext());
        return settings.getInt("Size", 2);
    }

    ///////////////////////////////////////////////////////////////////////////
    // SoundEffect
    ///////////////////////////////////////////////////////////////////////////
    public static void setSoundEffect(boolean effect){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(App.getContext());
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("SoundEffect", effect);
        editor.commit();
    }

    public static boolean getSoundEffect(){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(App.getContext());
        return settings.getBoolean("SoundEffect", true);
    }

    ///////////////////////////////////////////////////////////////////////////
    // SoundEffect
    ///////////////////////////////////////////////////////////////////////////
    public static void setMusic(boolean enable){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(App.getContext());
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("MusicSound", enable);
        editor.commit();
    }

    public static boolean getMusic(){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(App.getContext());
        return settings.getBoolean("MusicSound", true);
    }
}
