package psb.com.kidpaint.home;

import android.content.Context;

import psb.com.kidpaint.webApi.prize.PrizeRequest.model.ParamsPrizeRequest;


public interface IV_Home {
    Context getContext();

    void onStartLogout();
    void onLogoutSuccess();
    void onLogoutFailed(String errorMessage);

    void startGetPrizeRequest();
    void prizeRequestSuccess(int score);
    void prizeRequestFailed(String msg);

}
