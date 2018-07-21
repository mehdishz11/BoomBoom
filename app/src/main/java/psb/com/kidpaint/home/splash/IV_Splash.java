package psb.com.kidpaint.home.splash;

import android.content.Context;

import psb.com.kidpaint.webApi.paint.getLeaderShip.model.ResponseGetLeaderShip;

public interface IV_Splash {
    Context getContext();

    void startGetStickers();
    void getStickersSuccess();
    void getStickersFailed(String msg);

    void startGetRank();
    void getRankSuccess(ResponseGetLeaderShip responseGetLeaderShip);
    void getRankFailed(String msg);
}
