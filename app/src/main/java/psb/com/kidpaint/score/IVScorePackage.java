package psb.com.kidpaint.score;

import android.content.Context;

import psb.com.kidpaint.webApi.ScorePackage.GetScorePackage.model.ResponseGetScorePackage;
import psb.com.kidpaint.webApi.ScorePackage.buy.model.ResponseBuyScorePackage;

public interface IVScorePackage {

    Context getContext();

    void startGetScorePackage();
    void onSuccessGetScorePackage(ResponseGetScorePackage responseGetScorePackage);
    void onFailedGetScorePackage(int errorCode, String errorMessage);

    void startBuyScorePackage();
    void onSuccessBuyScorePackage(ResponseBuyScorePackage responseBuyScorePackage);
    void onFailedBuyScorePackage(int errorCode, String errorMessage);
}
