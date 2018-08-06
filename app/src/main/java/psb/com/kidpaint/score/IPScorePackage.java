package psb.com.kidpaint.score;

import android.content.Context;

import psb.com.kidpaint.webApi.ScorePackage.GetScorePackage.model.ResponseGetScorePackage;
import psb.com.kidpaint.webApi.ScorePackage.buy.model.ResponseBuyScorePackage;

public interface IPScorePackage {

    Context geContext();

    void getScorePackage();
    void onSuccessGetScorePackage(ResponseGetScorePackage responseGetScorePackage);
    void onFailedGetScorePackage(int errorCode, String errorMessage);


    void doBuyScorePackage(int position);
    void onSuccessBuyScorePackage(ResponseBuyScorePackage responseBuyScorePackage);
    void onFailedBuyScorePackage(int errorCode, String errorMessage);
}
