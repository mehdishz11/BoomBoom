package com.helper.tapsel;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;

public class TapselHelper {

    private OnTapselResult onTapselResult;
    static boolean isCompleted=false;
    private ProgressDialog pDialog;

    public void initTapsel(Application app ) {


    }

    public void setOnTapselResult(OnTapselResult onTapselResult) {

    }

    public void startWatchAd(final Context context) {

    }

    public interface OnTapselResult{
        void onSuccess();
        void onFailed(String message);
        void progressClosed();
    }
}
