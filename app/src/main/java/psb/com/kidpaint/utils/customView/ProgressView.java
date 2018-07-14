package psb.com.kidpaint.utils.customView;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import psb.com.kidpaint.R;


/**
 * Created by Mehdi on 1/25/2018 AD.
 */

public class ProgressView extends RelativeLayout {

    private int mode = 0;

    private RelativeLayout relProgress;
    private RelativeLayout relError;
    private TextView textError;
    private View btnRetry;
    private ProgressBar progress;




    public ProgressView(Context context) {
        super(context);
        init();

    }

    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ProgressView);
        mode = a.getInt(R.styleable.ProgressView_mode,0);
        a.recycle();
        init();
    }

    public ProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ProgressView);
        mode = a.getInt(R.styleable.ProgressView_mode,0);
        a.recycle();
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ProgressView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ProgressView);
        mode = a.getInt(R.styleable.ProgressView_mode,0);
        a.recycle();
        init();
    }

    private void init() {

        if (mode == 0) {//full screen mode
            inflate(getContext(), R.layout.progress_mode_0, this);
        }else if(mode == 1){
          //  inflate(getContext(), R.layout.progress_mode_1, this);
        }

        relProgress = findViewById(R.id.rel_progress);
        relError = findViewById(R.id.rel_error);
       textError = findViewById(R.id.text_error);
        btnRetry = findViewById(R.id.btn_retry);
        progress = findViewById(R.id.progressBar);
    }



    public void setTextError(String message) {
        if (textError != null) {
            textError.setText(message);
        }
    }




    public void setOnRetryClick(OnClickListener onclick) {
        if (btnRetry != null) {
            btnRetry.setOnClickListener(onclick);
        }
    }


    public void showProgress() {
        relError.setVisibility(GONE);
        relProgress.setVisibility(VISIBLE);
    }


    public void showProgress(String progressMessage, boolean isShowExtraProgress) {
        if (progress != null) {
            progress.setVisibility(isShowExtraProgress ? VISIBLE : GONE);
        }
    }

    public void showProgress( boolean isShowExtraProgress) {
        if (progress != null) {
            progress.setVisibility(isShowExtraProgress ? VISIBLE : GONE);
        }

        showProgress();
    }


    public void showError() {
        relProgress.setVisibility(GONE);
        relError.setVisibility(VISIBLE);
    }

    public void showError(String message) {
        setTextError(message);
        showError();
    }

    public void showError(String message, OnClickListener onclickRetry) {
        setOnRetryClick(onclickRetry);
        showError(message);
    }

    public void showError(String message, String retryBtnText, OnClickListener onclickRetry) {
        if(btnRetry!=null){
            if(btnRetry instanceof TextView){
                ((TextView) btnRetry).setText(retryBtnText);
            }
        }
        showError(message,onclickRetry);
    }
}
