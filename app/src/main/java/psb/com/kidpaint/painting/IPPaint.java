package psb.com.kidpaint.painting;

import android.content.Context;
import android.graphics.Bitmap;

import psb.com.kidpaint.webApi.ScorePackage.GetScorePackage.model.ResponseGetScorePackage;
import psb.com.kidpaint.webApi.ScorePackage.buy.model.ResponseBuyScorePackage;
import psb.com.kidpaint.webApi.paint.savePaints.model.ResponseSavePaint;
import psb.com.kidpaint.webApi.userScore.buySticker.model.ResponseBuySticker;

public interface IPPaint {

    Context geContext();

    void doBuySticker(int usedCoinCount);
    void onSuccessBuySticker(ResponseBuySticker responseBuySticker);
    void onFailedBuySticker(int errorCode, String errorMessage);

    void onSavePaint(Bitmap bitmap);
    void onSuccessSavePaint(ResponseSavePaint responseSavePaint);
    void onFailedSavePaint(int errorCode, String errorMessage);
}
