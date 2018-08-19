package psb.com.kidpaint.dailyPrize;

import android.content.Context;

import psb.com.kidpaint.webApi.ScorePackage.GetScorePackage.model.ResponseGetScorePackage;
import psb.com.kidpaint.webApi.ScorePackage.buy.model.ResponseBuyScorePackage;
import psb.com.kidpaint.webApi.prize.buyDailyPrize.model.ResponseBuyDailyPrize;

public interface IPDailyPrize {

    Context geContext();

    void doBuyDailyPrize(int id,int prize);
    void onSuccessBuyDailyPrize(int prize, ResponseBuyDailyPrize responseBuyDailyPrize);
    void onFailedBuyDailyPrize(int errorCode, String errorMessage);
}
