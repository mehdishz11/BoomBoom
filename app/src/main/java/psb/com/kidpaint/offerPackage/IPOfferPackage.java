package psb.com.kidpaint.offerPackage;

import android.content.Context;

import psb.com.kidpaint.webApi.offerPackage.buy.model.ResponseBuyOfferPackage;

public interface IPOfferPackage {

    Context geContext();


    void doBuyOfferPackage(int offerPackageId);
    void onSuccessBuyOfferPackage(ResponseBuyOfferPackage responseBuyOfferPackage);
    void onFailedBuyOfferPackage(int errorCode, String errorMessage);
}
