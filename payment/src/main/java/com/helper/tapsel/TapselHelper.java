package com.helper.tapsel;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import ir.tapsell.sdk.Tapsell;
import ir.tapsell.sdk.TapsellAd;
import ir.tapsell.sdk.TapsellAdRequestListener;
import ir.tapsell.sdk.TapsellAdRequestOptions;
import ir.tapsell.sdk.TapsellAdShowListener;
import ir.tapsell.sdk.TapsellRewardListener;
import ir.tapsell.sdk.TapsellShowOptions;

public class TapselHelper {

    String TAG="Tapsel";

    public void initTapsel(Application app){
        Tapsell.initialize(app, "5c271dac2a937400017fc888");
        Tapsell.setDebugMode(app.getApplicationContext(),true);

        Tapsell.setRewardListener(new TapsellRewardListener() {
            @Override
            public void onAdShowFinished(TapsellAd ad, boolean completed) {
                // store user reward if ad.isRewardedAd() and completed is true

                Log.d(TAG, "Tapsel onAdShowFinished: "+ad.isRewardedAd()+"->"+completed);
            }
        });
    }

    public void startWatchAd(final Context context){

        final TapsellAdRequestOptions tapsellAdRequestOptions=new TapsellAdRequestOptions();
        tapsellAdRequestOptions.setCacheType(TapsellAdRequestOptions.CACHE_TYPE_CACHED);

        Tapsell.requestAd(context, null, tapsellAdRequestOptions, new TapsellAdRequestListener() {
            @Override
            public void onError (String error)
            {
                Log.d(TAG, "Tapsel error: "+error);
            }

            @Override
            public void onAdAvailable (TapsellAd ad)
            {
                Log.d(TAG, "Tapsel onAdAvailable: "+ad.isRewardedAd());
                TapsellShowOptions tapsellShowOptions=new TapsellShowOptions();

                ad.show(context, tapsellShowOptions, new TapsellAdShowListener() {
                    @Override
                    public void onOpened (TapsellAd ad)
                    {
                        Log.d(TAG, "Tapsel onOpened: "+ad.isRewardedAd());
                    }
                    @Override
                    public void onClosed (TapsellAd ad)
                    {
                        Log.d(TAG, "Tapsel onClosed: "+ad.isRewardedAd());
                    }
                });
            }

            @Override
            public void onNoAdAvailable ()
            {
                Log.d(TAG, "Tapsel onNoAdAvailable: ");
            }

            @Override
            public void onNoNetwork ()
            {
                Log.d(TAG, "Tapsel onNoNetwork: ");
            }

            @Override
            public void onExpiring (TapsellAd ad)
            {
                Log.d(TAG, "Tapsel onExpiring: "+ad.isRewardedAd());
            }
        });
    }
}
