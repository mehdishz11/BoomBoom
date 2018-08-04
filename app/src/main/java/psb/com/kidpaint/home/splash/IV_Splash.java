package psb.com.kidpaint.home.splash;

import android.content.Context;

import psb.com.kidpaint.webApi.paint.getLeaderShip.model.ResponseGetLeaderShip;
import psb.com.kidpaint.webApi.prize.Get.model.ResponsePrize;

public interface IV_Splash {
    Context getContext();

    void startGetStickers();
    void getStickersSuccess();
    void getStickersFailed(String msg);

    void startGetRank();
    void getRankSuccess(ResponseGetLeaderShip responseGetLeaderShip);
    void getRankFailed(String msg);

    void getPirzeSuccess(ResponsePrize responsePrize);
    void getPrizeFailed(String msg,ResponsePrize responsePrize);

    void onSuccessUpdateFcmToken();
    void onFailedUpdateFcmToken(int errorCode,String errorMessage);
}
