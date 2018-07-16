package psb.com.kidpaint.utils.customView.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import psb.com.kidpaint.R;

public class DialogPaintingSettings extends Dialog {
    public DialogPaintingSettings(@NonNull Context context) {
        super(context);
        init();
    }

    public DialogPaintingSettings(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    protected DialogPaintingSettings(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }

    private void init(){

        setContentView(R.layout.dialog_parent);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

    }

}
