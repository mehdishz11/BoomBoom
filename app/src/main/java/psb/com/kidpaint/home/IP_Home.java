package psb.com.kidpaint.home;

import android.content.Context;

import psb.com.kidpaint.home.history.adapter.HistoryViewHolder;
import psb.com.kidpaint.webApi.paint.getLeaderShip.model.ResponseGetLeaderShip;
import psb.com.kidpaint.webApi.paint.getMyPaints.model.ResponseGetMyPaints;
import psb.com.kidpaint.webApi.paint.postPaint.model.ResponsePostPaint;
import psb.com.kidpaint.webApi.prize.PrizeRequest.model.ParamsPrizeRequest;
import psb.com.kidpaint.webApi.userScore.addScore.model.ResponseAddScore;


public interface IP_Home {
    Context getContext();





    void onStartLogout();
    void onLogoutSuccess();
    void onLogoutFailed(String errorMessage);

    void prizeRequest(ParamsPrizeRequest paramsPrizeRequest);

    void prizeRequestSuccess(int score);
    void prizeRequestFailed(String msg);

    int getUnreadMessageCount();

    void postPaint(int position);

    void getMyPaintHistory();
    void onGetMyPaintHistorySuccess();
    void onGetMyPaintHistoryFailed();

    void onBindViewHolder(HistoryViewHolder holder, int position);
    int getArrSize();

    void onSuccessPostPaint(ResponsePostPaint responsePostPaint);
    void onFailedPostPaint(int errorCode,String errorMessage);

    void deletePaint(int position);


    void doAddScore(int addScoreMode);
    void onSuccessAddScore(ResponseAddScore responseAddScore);
    void onFailedAddScore(int errorCode,String errorMessage);

    void onSuccessDelete(int position);

    void getRank();
    void getRankSuccess(ResponseGetLeaderShip responseGetLeaderShip);
    void getRankFailed(String msg);

    void setResponseMyPaints(ResponseGetMyPaints responseMyPaints);

    void getMyPaints();
    void getMyPaintsSuccess();
    void getMyPaintsFailed(int errorCode,String errorMessage);

    void onFailedDelete(String errorMessage);



}
