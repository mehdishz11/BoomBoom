package psb.com.kidpaint.home.splash;

import android.content.Context;

import psb.com.kidpaint.webApi.offerPackage.Get.model.ResponseGetOfferPackage;
import psb.com.kidpaint.webApi.paint.getLeaderShip.model.ResponseGetLeaderShip;
import psb.com.kidpaint.webApi.paint.getMyPaints.model.ResponseGetMyPaints;
import psb.com.kidpaint.webApi.prize.getDailyPrize.model.ResponseGetDailyPrize;

public class P_Splash implements IP_Splash {

    private Context context;
    private IV_Splash ivSplash;
    private M_Splash mSplash;

    public P_Splash(IV_Splash ivSplash) {
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

    @Override
    public void getOfferPackage() {
        mSplash.getOfferPackage();
    }

    @Override
    public void onSuccessGetOfferPackage(ResponseGetOfferPackage responseGetOfferPackage) {
        ivSplash.onSuccessGetOfferPackage(responseGetOfferPackage);
    }

    @Override
    public void onFailedGetOfferPackage(int errorCode, String errorMessage) {
        ivSplash.onFailedGetOfferPackage(errorCode, errorMessage);
    }

    @Override
    public void getDailyPrize() {
        mSplash.getDailyPrize();
    }

    @Override
    public void onSuccessGetDailyPrize(ResponseGetDailyPrize responseGetDailyPrize) {
        ivSplash.onSuccessGetDailyPrize(responseGetDailyPrize);
    }

    @Override
    public void onFailedGetDailyPrize(int errorCode, String errorMessage) {
        ivSplash.onFailedGetDailyPrize(errorCode, errorMessage);
    }

    @Override
    public void getMessage() {
        mSplash.getMessage();
    }

    @Override
    public void onSuccessGetMessage() {
        ivSplash.onSuccessGetMessage();
    }

    @Override
    public void onFailedGetMessage(int errorCode, String errorMessage) {
        ivSplash.onFailedGetMessage(errorCode, errorMessage);
    }

    @Override
    public void getProfile() {
        mSplash.getProfile();
    }

    @Override
    public void onSuccessGetProfile() {
        ivSplash.onSuccessGetProfile();
    }

    @Override
    public void onFailedGetProfile(int errorCode, String errorMessage) {
        ivSplash.onFailedGetProfile(errorCode, errorMessage);

    }

    @Override
    public void getMyPaints() {
        mSplash.getMyPaints();
    }

    @Override
    public void onSuccessGetMyPaints(ResponseGetMyPaints responseGetMyPaints) {
        ivSplash.onSuccessGetMyPaints(responseGetMyPaints);
    }

    @Override
    public void onFailedGetGetMyPaints(int errorCode, String errorMessage) {
        ivSplash.onFailedGetGetMyPaints(errorCode, errorMessage);
    }

    @Override
    public int getLocalPaintsCount() {
        return mSplash.getLocalPaintsCount();
    }

    @Override
    public void onSavePaintsInServer() {
        mSplash.onSavePaintsInServer();
    }

    @Override
    public void onSuccessSavePaintsInServer() {
        ivSplash.onSuccessSavePaintsInServer();
    }

    @Override
    public void onFailedSavePaintsInServer(int errorCode, String errorMessage) {
        ivSplash.onFailedSavePaintsInServer(errorCode, errorMessage);

    }
}
