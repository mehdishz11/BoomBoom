package psb.com.kidpaint.home.history;

import android.content.Context;

import psb.com.kidpaint.webApi.paint.postPaint.model.ResponsePostPaint;

public interface IVHistory {

    Context getContext();

    void onStartMyPaintHistory();
    void onGetMyPaintHistorySuccess();
    void onGetMyPaintHistoryFailed();

    void onSelecteditem(String filePath);


    void onStartPostPaint();
    void onSuccessPostPaint(ResponsePostPaint responsePostPaint);
    void onFailedPostPaint(int errorCode,String errorMessage);
    void showUserRegisterDialog(int position);
    void showDeleteDialog(int position);

}
