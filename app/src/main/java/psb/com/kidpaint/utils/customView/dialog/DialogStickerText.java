package psb.com.kidpaint.utils.customView.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import psb.com.kidpaint.R;
import psb.com.kidpaint.utils.customView.colorPicker.ColorPicker;

public class DialogStickerText extends Dialog {

    private OnClick onClick;

    public DialogStickerText( Context context) {
        super(context);
        init();
    }

    public DialogStickerText( Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    protected DialogStickerText( Context context, boolean cancelable,  DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }

    public void setOnClick(OnClick onClick) {
        this.onClick = onClick;
    }

    private void init(){
        setCanceledOnTouchOutside(false);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);


        setContentView(R.layout.dialog_text_sticker);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        final EditText textinput=findViewById(R.id.text_input);
        final ColorPicker colorPicker=findViewById(R.id.color_picker);

        findViewById(R.id.img_btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });


        findViewById(R.id.btn_accept).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClick != null) {
                    onClick.onAccept(textinput.getText().toString(),colorPicker.getSwatch().color);
                }
                cancel();
            }
        });


    }


    public interface OnClick{
        void onAccept(String text,int color);
    }
}
