package psb.com.kidpaint.painting.palette.sticker;

import android.content.Context;

public interface IP_Stickers {
    Context getContext();

    void getStickers();
    void getStickersSuccess();
    void getStickersFailed();

    void showStickers();
}
