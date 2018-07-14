package psb.com.kidpaint.home.history;

import android.content.Context;

import java.io.File;

public interface IMHistory {

    Context getContext();
    void getMyPaintHistory();

    File getPositionAt(int position);
    int getArrSize();

}
