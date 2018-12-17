package psb.com.kidpaint.home;

import android.content.Context;

import psb.com.kidpaint.webApi.paint.getMyPaints.model.ResponseGetMyPaints;
import psb.com.kidpaint.webApi.prize.PrizeRequest.model.ParamsPrizeRequest;
import psb.com.kidpaint.webApi.shareModel.HistoryModel;

public interface IM_Home {
    Context getContext();



    void onStartLogout();

    void prizeRequest(ParamsPrizeRequest paramsPrizeRequest);

    int getUnreadMessageCount();

    void getMyPaintHistory();

    void postPaint(int position);
    void deletePaint(int position);

    HistoryModel getPositionAt(int position);
    int getArrSize();

    boolean userIsRegistered();

    void doAddScore(int addScoreMode);

    void getRank();

    void setResponseMyPaints(ResponseGetMyPaints responseMyPaints);

    void getMyPaints();



}
