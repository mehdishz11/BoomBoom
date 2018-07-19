package psb.com.kidpaint.painting.palette.sticker;

import android.content.Context;

public interface IV_Stickers {
    Context getContext();

    void getStickersSuccess();
    void getStickersFailed();

    void showStickers();
}
