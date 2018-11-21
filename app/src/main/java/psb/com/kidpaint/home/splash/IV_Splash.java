package psb.com.kidpaint.home.splash;

import android.content.Context;

import psb.com.kidpaint.webApi.offerPackage.Get.model.ResponseGetOfferPackage;
import psb.com.kidpaint.webApi.paint.getLeaderShip.model.ResponseGetLeaderShip;
import psb.com.kidpaint.webApi.paint.getMyPaints.model.ResponseGetMyPaints;
import psb.com.kidpaint.webApi.prize.getDailyPrize.model.ResponseGetDailyPrize;

public interface IV_Splash {
    Context getContext();

    void startGetStickers();
    void getStickersSuccess();
    void getStickersFailed(String msg);

    void startGetRank();
    void getRankSuccess(ResponseGetLeaderShip responseGetLeaderShip);
    void getRankFailed(String msg);

    void onSuccessUpdateFcmToken();
    void onFailedUpdateFcmToken(int errorCode,String errorMessage);

    void onSuccessGetOfferPackage(ResponseGetOfferPackage responseGetOfferPackage);
    void onFailedGetOfferPackage(int errorCode,String errorMessage);

    void onSuccessGetDailyPrize(ResponseGetDailyPrize responseGetDailyPrize);
    void onFailedGetDailyPrize(int errorCode,String errorMessage);

    void onSuccessGetMessage();
    void onFailedGetMessage(int errorCode,String errorMessage);

    void onSuccessGetProfile();
    void onFailedGetProfile(int errorCode,String errorMessage);

    void onSuccessGetMyPaints(ResponseGetMyPaints responseGetMyPaints);
    void onFailedGetGetMyPaints(int errorCode,String errorMessage);


    void onSuccessSavePaintsInServer();
    void onFailedSavePaintsInServer(int errorCode,String errorMessage);
}
