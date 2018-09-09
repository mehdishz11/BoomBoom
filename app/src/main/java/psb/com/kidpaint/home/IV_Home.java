package psb.com.kidpaint.home;

import android.content.Context;

import psb.com.kidpaint.webApi.paint.postPaint.model.ResponsePostPaint;
import psb.com.kidpaint.webApi.prize.PrizeRequest.model.ParamsPrizeRequest;
import psb.com.kidpaint.webApi.userScore.addScore.model.ResponseAddScore;


public interface IV_Home {
    Context getContext();

    void onStartLogout();
    void onLogoutSuccess();
    void onLogoutFailed(String errorMessage);

    void startGetPrizeRequest();
    void prizeRequestSuccess(int score);
    void prizeRequestFailed(String msg);

    void onStartMyPaintHistory();
    void onGetMyPaintHistorySuccess();
    void onGetMyPaintHistoryFailed();

    void onSelecteditem(String filePath);


    void onStartPostPaint();
    void onSuccessPostPaint(ResponsePostPaint responsePostPaint);
    void onFailedPostPaint(int errorCode,String errorMessage);
    void showUserRegisterDialog(int position);
    void showDeleteDialog(int position);

   void onOutlineSelected(int resId);
   void onNewPaintSelected();


    void onStartAddScore();
    void onSuccessAddScore(ResponseAddScore responseAddScore);
    void onFailedAddScore(int errorCode,String errorMessage);

    void onSuccessDelete(int position);


}
