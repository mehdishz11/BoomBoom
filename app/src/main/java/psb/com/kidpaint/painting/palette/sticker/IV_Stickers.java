package psb.com.kidpaint.painting.palette.sticker;

import android.content.Context;
import android.graphics.Bitmap;

public interface IV_Stickers {
    Context getContext();

    void getStickersSuccess();
    void getStickersFailed();

    void showStickers(int catPosition);
    void unSelectCat(int position);

    void onStickerSelected(Bitmap stickerBitmap,int price,String stickerSound);
    void onTextStickerClicked();


    void startGetStickersFromServer();
    void getStickersSuccessFromServer();
    void getStickersFailedFromServer(int errorId, String ErrorMessage);
}
