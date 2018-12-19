package psb.com.kidpaint.painting;

import android.content.Context;

import psb.com.kidpaint.webApi.paint.savePaints.model.ResponseSavePaint;
import psb.com.kidpaint.webApi.userScore.buySticker.model.ResponseBuySticker;

public interface IVPaint {

    Context getContext();



    void startBuySticker();
    void onSuccessBuySticker(ResponseBuySticker responseBuySticker);
    void onFailedBuySticker(int errorCode, String errorMessage);

    void startSavePaint();
    void onSuccessSavePaint(ResponseSavePaint responseSavePaint);
    void onFailedSavePaint(int errorCode, String errorMessage);
}
