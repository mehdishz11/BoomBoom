package psb.com.kidpaint.utils.customView;

import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import psb.com.kidpaint.utils.toolbarHandler.ToolbarHandler;


public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ToolbarHandler.makeFullScreen(getWindow());
        ToolbarHandler.makeTansluteNavigation(this, getWindow(), getWindow().getDecorView());
        createHelperWnd();
        super.onCreate(savedInstanceState);
    }

    public void createHelperWnd() {
        final ViewGroup rootView = findViewById(android.R.id.content);
        final WindowManager.LayoutParams p = new WindowManager.LayoutParams();
        p.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
        p.gravity = Gravity.RIGHT | Gravity.TOP;
        p.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        p.width = 1;
        p.height = RelativeLayout.LayoutParams.MATCH_PARENT;
        p.format = PixelFormat.TRANSPARENT;
        final View helperWnd = new View(this); //View helperWnd;

        rootView.addView(helperWnd, p);
        final ViewTreeObserver vto = helperWnd.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                if (isStatusBarVisible()) {
                    ToolbarHandler.makeFullScreen(getWindow());
                }
            }
        });

    }

    public boolean isStatusBarVisible() {
        Rect rectangle = new Rect();
        Window window = getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
        int statusBarHeight = rectangle.top;
        return statusBarHeight != 0;
    }

    @Override
    protected void onResume() {
        super.onResume();
        createHelperWnd();
    }
}
