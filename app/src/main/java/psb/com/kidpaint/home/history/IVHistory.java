package psb.com.kidpaint.home.history;

import android.content.Context;

import psb.com.kidpaint.home.history.adapter.HistoryViewHolder;

public interface IVHistory {

    Context getContext();

    void onStartMyPaintHistory();
    void onGetMyPaintHistorySuccess();
    void onGetMyPaintHistoryFailed();

    void onSelecteditem(String filePath);

}
