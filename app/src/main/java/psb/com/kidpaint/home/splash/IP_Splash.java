package psb.com.kidpaint.home.splash;

import android.content.Context;

import psb.com.kidpaint.webApi.paint.getLeaderShip.model.ResponseGetLeaderShip;
import psb.com.kidpaint.webApi.prize.Get.model.ResponsePrize;

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
}
