package psb.com.kidpaint.home.splash;

import android.content.Context;

public interface IV_Splash {
    Context getContext();

    void startGetStickers();
    void getStickersSuccess();
    void getStickersFailed(String msg);
}
