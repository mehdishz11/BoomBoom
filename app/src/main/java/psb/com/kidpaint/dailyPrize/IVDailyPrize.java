package psb.com.kidpaint.dailyPrize;

import android.content.Context;

import psb.com.kidpaint.webApi.prize.buyDailyPrize.model.ResponseBuyDailyPrize;

public interface IVDailyPrize {

    Context getContext();


    void startBuyDailyPrize();
    void onSuccessBuyDailyPrize(int prize, ResponseBuyDailyPrize responseBuyDailyPrize);
    void onFailedBuyDailyPrize(int errorCode, String errorMessage);
}
