package psb.com.kidpaint.home.splash;

import android.content.Context;

import psb.com.kidpaint.webApi.paint.getLeaderShip.model.ResponseGetLeaderShip;
import psb.com.kidpaint.webApi.prize.Get.model.ResponsePrize;

public class P_Splash implements IP_Splash {

    private Context context;
    private IV_Splash ivSplash;
    private M_Splash mSplash;

    public P_Splash(IV_Splash ivSplash){
        this.context = ivSplash.getContext();
        this.ivSplash = ivSplash;
        mSplash = new M_Splash(this);
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void getStickers() {
        mSplash.getStickers();
    }

    @Override
    public void getStickersSuccess() {
        ivSplash.getStickersSuccess();
    }

    @Override
    public void getStickersFailed(String msg) {
        ivSplash.getStickersFailed(msg);
    }

    @Override
    public void getRank() {
        ivSplash.startGetRank();
        mSplash.getRank();
    }

    @Override
    public void getRankSuccess(ResponseGetLeaderShip responseGetLeaderShip) {
        ivSplash.getRankSuccess(responseGetLeaderShip);
    }

    @Override
    public void getRankFailed(String msg) {
        ivSplash.getRankFailed(msg);
    }

    @Override
    public void getPirze() {
        mSplash.getPirze();
    }

    @Override
    public void getPirzeSuccess(ResponsePrize responsePrize) {
        ivSplash.getPirzeSuccess(responsePrize);
    }

    @Override
    public void getPrizeFailed(String msg,ResponsePrize responsePrize) {
        ivSplash.getPrizeFailed(msg,responsePrize);
    }

    @Override
    public boolean userIsRegistered() {
        return mSplash.userIsRegistered();
    }

    @Override
    public void updateFcmToken() {
         mSplash.updateFcmToken();
    }

    @Override
    public void onSuccessUpdateFcmToken() {
     ivSplash.onSuccessUpdateFcmToken();
    }

    @Override
    public void onFailedUpdateFcmToken(int errorCode, String errorMessage) {
      ivSplash.onFailedUpdateFcmToken(errorCode, errorMessage);
    }
}
