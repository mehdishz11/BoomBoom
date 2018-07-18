package psb.com.kidpaint.home.history;

import android.content.Context;

import psb.com.kidpaint.home.history.adapter.HistoryViewHolder;
import psb.com.kidpaint.webApi.paint.postPaint.ResponsePostPaint;

public interface IPHistory {

    Context getContext();
    void postPaint(int position);

    void getMyPaintHistory();
    void onGetMyPaintHistorySuccess();
    void onGetMyPaintHistoryFailed();

    void onBindViewHolder(HistoryViewHolder holder, int position);
    int getArrSize();

    void onSuccessPostPaint(ResponsePostPaint responsePostPaint);
    void onFailedPostPaint(int errorCode,String errorMessage);

}
