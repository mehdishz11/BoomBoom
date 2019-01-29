package psb.com.kidpaint.utils.customView.dialog;

import android.content.Context;
import android.content.DialogInterface;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import psb.com.customView.CustomTextViewBold;
import psb.com.kidpaint.R;
import psb.com.kidpaint.utils.soundHelper.SoundHelper;

public class MessageDialog extends CDialog {

    private OnCLickListener onCLickListener;
    private int soundId = -1;

    public MessageDialog(@NonNull Context context) {
        super(context);
    }

    public MessageDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected MessageDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    @Override
    protected void inflateChildView(LinearLayout relParent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        CustomTextViewBold inflatedLayout = (CustomTextViewBold) inflater.inflate(R.layout.dialog_message, null, false);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        inflatedLayout.setLayoutParams(params);
        inflatedLayout.setGravity(Gravity.CENTER);
        relParent.addView(inflatedLayout);


        setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {

                if (soundId != -1) SoundHelper.playSound(soundId);

            }
        });
    }


    @Override
    protected void onAccept() {

    }

    @Override
    protected void onCancel() {

    }

    @Override
    protected OnCLickListener getOnCLickListener() {
        return this.onCLickListener;
    }

    public void setMessage(String message) {
        ((CustomTextViewBold) findViewById(R.id.text_desc)).setText(message);
    }

    public MessageDialog setSoundId(int soundId) {
        this.soundId = soundId;
        return this;
    }

    public void setOnCLickListener(OnCLickListener onCLickListener) {
        this.onCLickListener = onCLickListener;
    }
}
