package psb.com.kidpaint.home;

import android.content.Context;

import java.io.File;

import psb.com.kidpaint.home.history.adapter.HistoryViewHolder;
import psb.com.kidpaint.webApi.paint.postPaint.model.ResponsePostPaint;
import psb.com.kidpaint.webApi.prize.PrizeRequest.model.ParamsPrizeRequest;


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

    File getLastPaintFile();

}
