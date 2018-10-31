package psb.com.kidpaint.home;

import android.content.Context;

import java.io.File;

import psb.com.kidpaint.webApi.prize.PrizeRequest.model.ParamsPrizeRequest;

public interface IM_Home {
    Context getContext();



    void onStartLogout();

    void prizeRequest(ParamsPrizeRequest paramsPrizeRequest);

    int getUnreadMessageCount();

    void getMyPaintHistory();

    void postPaint(int position);
    void deletePaint(int position);

    File getPositionAt(int position);
    File getLastPaintFile();
    int getArrSize();

    boolean userIsRegistered();

    void doAddScore(int addScoreMode);

    void getRank();


}
