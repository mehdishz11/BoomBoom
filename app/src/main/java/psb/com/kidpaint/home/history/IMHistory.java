package psb.com.kidpaint.home.history;

import android.content.Context;

import java.io.File;

public interface IMHistory {

    Context getContext();
    void getMyPaintHistory();

    void postPaint(int position);
    void deletePaint(int position);

    File getPositionAt(int position);
    File getLastPaintFile();
    int getArrSize();

    boolean userIsRegistered();

}
