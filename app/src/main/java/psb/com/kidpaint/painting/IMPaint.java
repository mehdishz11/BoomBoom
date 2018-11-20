package psb.com.kidpaint.painting;

import android.content.Context;
import android.graphics.Bitmap;

public interface IMPaint {

    Context geContext();


    void doBuySticker(int usedCoinCount);
    void onSavePaint(Bitmap bitmap);



}
