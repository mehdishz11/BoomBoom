package psb.com.kidpaint.utils.toolbarHandler;

import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


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
    public static void makeTansluteToolbar(Context context,Window w,View view){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            w.setStatusBarColor(ContextCompat.getColor(context, android.R.color.transparent));
        }
    }

}
