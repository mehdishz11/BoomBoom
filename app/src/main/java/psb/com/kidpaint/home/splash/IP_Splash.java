package psb.com.kidpaint.home.splash;

import android.content.Context;

import psb.com.kidpaint.webApi.paint.getLeaderShip.model.ResponseGetLeaderShip;

public interface IP_Splash {
    Context getContext();

    void getStickers();
    void getStickersSuccess();
    void getStickersFailed(String msg);

    void getRank();
    void getRankSuccess(ResponseGetLeaderShip responseGetLeaderShip);
    void getRankFailed(String msg);
}
