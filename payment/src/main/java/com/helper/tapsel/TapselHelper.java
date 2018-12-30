package com.helper.tapsel;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import ir.tapsell.sdk.Tapsell;
import ir.tapsell.sdk.TapsellAd;
import ir.tapsell.sdk.TapsellAdRequestListener;
import ir.tapsell.sdk.TapsellAdRequestOptions;
import ir.tapsell.sdk.TapsellAdShowListener;
import ir.tapsell.sdk.TapsellRewardListener;
import ir.tapsell.sdk.TapsellShowOptions;

public class TapselHelper {

    private OnTapselResult onTapselResult;

    String TAG = "Tapsel";
    static boolean isCompleted=false;
    private ProgressDialog pDialog;

    public void initTapsel(Application app ) {
        Tapsell.initialize(app, "nsjpcihnctqbtggodqdhgaaktkehibkgjglcmpmgrcltbfiafsdsjtfakdfrsfcrklhhcm");
        Tapsell.setDebugMode(app.getApplicationContext(), true);

        Tapsell.setRewardListener(new TapsellRewardListener() {
            @Override
            public void onAdShowFinished(TapsellAd ad, boolean completed) {
                // store user reward if ad.isRewardedAd() and completed is true

                Log.d(TAG, "Tapsel onAdShowFinished: " + ad.isRewardedAd() + "->" + completed);

                isCompleted=completed;
                if (ad.isRewardedAd() && completed){
                    if (onTapselResult != null) {
                        onTapselResult.onSuccess();
                    }
                }

            }
        });

    }

    public void setOnTapselResult(OnTapselResult onTapselResult) {
        this.onTapselResult = onTapselResult;
    }

    public void startWatchAd(final Context context) {

        pDialog=new ProgressDialog(context);
        pDialog.setMessage("در حال بررسی ...");
        pDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (onTapselResult != null) {
                    onTapselResult.progressClosed();
                }
            }
        });
        pDialog.show();


        isCompleted=false;
        Tapsell.clearBandwidthUsageConstrains(context);

        final TapsellAdRequestOptions tapsellAdRequestOptions = new TapsellAdRequestOptions();
        tapsellAdRequestOptions.setCacheType(TapsellAdRequestOptions.CACHE_TYPE_STREAMED);

        Tapsell.requestAd(context, "5c28584cf1ca0e00016f7e3a", tapsellAdRequestOptions, new TapsellAdRequestListener() {
            @Override
            public void onError(String error) {
               failed("اشکال در سرور مجددا تلاش نمایید.");
                Log.d(TAG, "Tapsel error: " + error);
            }

            @Override
            public void onAdAvailable(TapsellAd ad) {

                if(pDialog.isShowing()){
                    pDialog.cancel();
                }

                TapsellShowOptions tapsellShowOptions = new TapsellShowOptions();
                tapsellShowOptions.setRotationMode(TapsellShowOptions.ROTATION_LOCKED_LANDSCAPE);
                tapsellShowOptions.setShowDialog(true);
//                tapsellShowOptions.setImmersiveMode(true);





                ad.show(context, tapsellShowOptions, new TapsellAdShowListener() {
                    @Override
                    public void onOpened(TapsellAd ad) {
                        Log.d(TAG, "Tapsel onOpened: " + ad.isRewardedAd());
                    }

                    @Override
                    public void onClosed(TapsellAd ad) {
                        Log.d(TAG, "Tapsel onClosed: " + ad.isRewardedAd()+"->"+isCompleted);
                        if (isCompleted) {
                            if (onTapselResult != null) {
                                onTapselResult.onSuccess();
                            }
                        }else{
                            if (onTapselResult != null) {
                                onTapselResult.onFailed("ویدئو را باید کامل ببنید تا سکه بگیرید!");
                            }
                        }
                    }
                });
            }

            @Override
            public void onNoAdAvailable() {
                failed("ویدئویی یافت نگردید، مجددا تلاش نمایید");
                Log.d(TAG, "Tapsel onNoAdAvailable: ");
            }

            @Override
            public void onNoNetwork() {
                failed("اشکال در اتصال اینترنت.");
                Log.d(TAG, "Tapsel onNoNetwork: ");
            }

            @Override
            public void onExpiring(TapsellAd ad) {
                failed("ویدئو در دسترس نمی باشد\nمجددا تلاش نمایید");
                Log.d(TAG, "Tapsel onExpiring: " + ad.isRewardedAd());
            }
        });
    }

    private void failed(String message){
        if(pDialog.isShowing()){
            pDialog.cancel();
        }
        if (onTapselResult != null) {
            onTapselResult.onFailed(message);
        }
    }


    public interface OnTapselResult{
        void onSuccess();
        void onFailed(String message);
        void progressClosed();
    }
}
