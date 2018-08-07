package psb.com.kidpaint.painting;

import android.content.Context;

import psb.com.kidpaint.webApi.ScorePackage.GetScorePackage.model.ResponseGetScorePackage;
import psb.com.kidpaint.webApi.ScorePackage.buy.model.ResponseBuyScorePackage;
import psb.com.kidpaint.webApi.userScore.buySticker.model.ResponseBuySticker;

public interface IVPaint {

    Context getContext();



    void startBuySticker();
    void onSuccessBuySticker(ResponseBuySticker responseBuySticker);
    void onFailedBuySticker(int errorCode, String errorMessage);
}
