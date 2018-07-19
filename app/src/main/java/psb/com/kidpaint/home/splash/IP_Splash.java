package psb.com.kidpaint.home.splash;

import android.content.Context;

public interface IP_Splash {
    Context getContext();

    void getStickers();
    void getStickersSuccess();
    void getStickersFailed(String msg);

}
