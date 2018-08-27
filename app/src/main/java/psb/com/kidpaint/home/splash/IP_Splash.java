package psb.com.kidpaint.home.splash;

import android.content.Context;

import psb.com.kidpaint.webApi.offerPackage.Get.model.ResponseGetOfferPackage;
import psb.com.kidpaint.webApi.paint.getLeaderShip.model.ResponseGetLeaderShip;
import psb.com.kidpaint.webApi.prize.Get.model.ResponsePrize;
import psb.com.kidpaint.webApi.prize.getDailyPrize.model.ResponseGetDailyPrize;

public interface IP_Splash {
    Context getContext();

    void getStickers();
    void getStickersSuccess();
    void getStickersFailed(String msg);

    void getRank();
    void getRankSuccess(ResponseGetLeaderShip responseGetLeaderShip);
    void getRankFailed(String msg);

    void getPirze();
    void getPirzeSuccess(ResponsePrize responsePrize);
    void getPrizeFailed(String msg,ResponsePrize responsePrize);

    boolean userIsRegistered();


    void updateFcmToken();
    void onSuccessUpdateFcmToken();
    void onFailedUpdateFcmToken(int errorCode,String errorMessage);


    void getOfferPackage();
    void onSuccessGetOfferPackage(ResponseGetOfferPackage responseGetOfferPackage);
    void onFailedGetOfferPackage(int errorCode,String errorMessage);

    void getDailyPrize();
    void onSuccessGetDailyPrize(ResponseGetDailyPrize responseGetDailyPrize);
    void onFailedGetDailyPrize(int errorCode,String errorMessage);

    void getMessage();
    void onSuccessGetMessage();
    void onFailedGetMessage(int errorCode,String errorMessage);

    void getProfile();
    void onSuccessGetProfile();
    void onFailedGetProfile(int errorCode,String errorMessage);
}
