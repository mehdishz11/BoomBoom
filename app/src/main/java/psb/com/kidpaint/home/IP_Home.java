package psb.com.kidpaint.home;

import android.content.Context;

import psb.com.kidpaint.webApi.prize.PrizeRequest.model.ParamsPrizeRequest;


public interface IP_Home {
    Context getContext();



    void onStartLogout();
    void onLogoutSuccess();
    void onLogoutFailed(String errorMessage);

    void prizeRequest(ParamsPrizeRequest paramsPrizeRequest);

    void prizeRequestSuccess(int score);
    void prizeRequestFailed(String msg);
}
