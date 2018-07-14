package psb.com.kidpaint.home.history;

import android.content.Context;

import psb.com.kidpaint.home.history.adapter.HistoryViewHolder;

public interface IPHistory {

    Context getContext();

    void getMyPaintHistory();
    void onGetMyPaintHistorySuccess();
    void onGetMyPaintHistoryFailed();

    void onBindViewHolder(HistoryViewHolder holder, int position);
    int getArrSize();

}
