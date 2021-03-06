package psb.com.kidpaint.utils.toolbarHandler;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;


public class ToolbarHandler {

    public static void setToolbarColor(Context context,Window w,View view,int colorResource,boolean isDark){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if(isDark){
                int flags = view.getSystemUiVisibility();
                flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                view.setSystemUiVisibility(flags);
            }else{
                w.clearFlags(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }


            w.setStatusBarColor(ContextCompat.getColor(context, colorResource));

        }
    }

    public static void setNavigationColor(Context context,Window w,View view,int colorResource,boolean isDark){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if(isDark){
                int flags = view.getSystemUiVisibility();
                flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                view.setSystemUiVisibility(flags);
            }else{
                w.clearFlags(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }


            w.setNavigationBarColor(ContextCompat.getColor(context, colorResource));

        }
    }

    public static void makeTansluteToolbar(Context context,Window w,View view){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            w.setStatusBarColor(ContextCompat.getColor(context, android.R.color.transparent));
        }
    }
    public static void makeTansluteNavigation (Context context,Window w,View view){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            w.setStatusBarColor(ContextCompat.getColor(context, android.R.color.transparent));
        }
    }

    public static void makeFullScreen(Window window){
        window.getDecorView().setSystemUiVisibility(
                  View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }
}
