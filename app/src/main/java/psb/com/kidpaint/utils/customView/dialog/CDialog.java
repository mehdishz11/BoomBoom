package psb.com.kidpaint.utils.customView.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import psb.com.customView.CButton;
import psb.com.customView.CustomTextViewBold;
import psb.com.kidpaint.R;
import psb.com.kidpaint.utils.soundHelper.SoundHelper;

public abstract class CDialog extends Dialog {
    private Button save;

    public CDialog(@NonNull Context context) {
        super(context);
        init();
    }

    public CDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    protected CDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }


    public void setTitle(String message) {
        ((CustomTextViewBold) findViewById(R.id.text_title_1)).setText(message);
        ((CustomTextViewBold) findViewById(R.id.text_title_2)).setText(message);
    }

    public void setAcceptButtonMessage(String message) {
        ((CButton) findViewById(R.id.btn_save)).setText(message);
    }


    private void init() {
        setCanceledOnTouchOutside(false);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);


        setContentView(R.layout.dialog);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        inflateChildView((LinearLayout) findViewById(R.id.rel_content));

        save = findViewById(R.id.btn_save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoundHelper.playSound(R.raw.click_bubbles_1);
                onAccept();
                if (getOnCLickListener() != null) {
                    getOnCLickListener().onPosetiveClicked();
                }
                cancel();
            }
        });

        findViewById(R.id.img_btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoundHelper.playSound(R.raw.click_bubbles_1);
                if (getOnCLickListener() != null) {
                    getOnCLickListener().onNegativeClicked();
                }
                onCancel();
                cancel();
            }
        });


    }

    protected abstract void inflateChildView(LinearLayout relParent);

    protected abstract void onAccept();

    protected abstract void onCancel();

    protected abstract OnCLickListener getOnCLickListener();

    public void goneBtnSave() {
        save.setVisibility(View.GONE);
    }

    public interface OnCLickListener {
        void onPosetiveClicked();

        void onNegativeClicked();

    }

}
