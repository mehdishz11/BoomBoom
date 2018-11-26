package psb.com.kidpaint.utils.customView.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;

import psb.com.kidpaint.R;
import psb.com.kidpaint.utils.soundHelper.SoundHelper;
import psb.com.kidpaint.webApi.paint.Paint;
import psb.com.kidpaint.webApi.paint.score.iScore;
import psb.com.kidpaint.webApi.paint.score.model.ParamsScore;

public class DialogRate extends Dialog {

    private int paintId;
    private String phoneNumber;
    private iScore.iResult iResult;

    public DialogRate(@NonNull Context context) {
        super(context);
        init();
    }

    public DialogRate(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    protected DialogRate(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
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


        setContentView(R.layout.dialog_rate);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        findViewById(R.id.img_btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoundHelper.playSound(R.raw.click_bubbles_1);
                cancel();
            }
        });

        findViewById(R.id.rel_smile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRate(5);
            }
        });

        findViewById(R.id.rel_weird).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRate(3);
            }
        });

        findViewById(R.id.rel_sad).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRate(1);
            }
        });

    }

    private void sendRate(int rate){
        ParamsScore paramsScore=new ParamsScore();
        paramsScore.setMobile(phoneNumber);
        paramsScore.setPaintId(paintId);
        paramsScore.setScore(rate);
        new Paint().score(getiResult()).doScore(paramsScore);
    }

    public iScore.iResult getiResult() {
        return iResult;
    }

    public void setiResult(iScore.iResult iResult) {
        this.iResult = iResult;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getPaintId() {
        return paintId;
    }

    public void setPaintId(int paintId) {
        this.paintId = paintId;
    }

}
