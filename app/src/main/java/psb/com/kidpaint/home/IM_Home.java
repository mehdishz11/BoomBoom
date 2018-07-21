package psb.com.kidpaint.home;

import android.content.Context;

import psb.com.kidpaint.webApi.prize.PrizeRequest.model.ParamsPrizeRequest;

public interface IM_Home {
    Context getContext();



    void onStartLogout();

    void prizeRequest(ParamsPrizeRequest paramsPrizeRequest);

}
