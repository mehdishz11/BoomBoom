package psb.com.kidpaint.painting.palette.sticker;

import android.content.Context;
import android.graphics.Bitmap;

public interface IV_Stickers {
    Context getContext();

    void getStickersSuccess();
    void getStickersFailed();

    void showStickers();

    void onStickerSelected(Bitmap stickerBitmap,int price,String stickerSound);

    void startGetStickersFromServer();
    void getStickersSuccessFromServer();
    void getStickersFailedFromServer(int errorId, String ErrorMessage);
}
