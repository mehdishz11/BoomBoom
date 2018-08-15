package psb.com.kidpaint.offerPackage;

import android.content.Context;

import psb.com.kidpaint.webApi.ScorePackage.GetScorePackage.model.ResponseGetScorePackage;
import psb.com.kidpaint.webApi.ScorePackage.buy.model.ResponseBuyScorePackage;
import psb.com.kidpaint.webApi.offerPackage.buy.model.ResponseBuyOfferPackage;

public interface IVOfferPackage {

    Context getContext();

    void startBuyOfferPackage();
    void onSuccessBuyOfferPackage(ResponseBuyOfferPackage responseBuyOfferPackage);
    void onFailedBuyOfferPackage(int errorCode, String errorMessage);
}
