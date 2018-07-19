package psb.com.kidpaint.home.splash;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import psb.com.kidpaint.webApi.Category.Category;
import psb.com.kidpaint.webApi.Category.GetCategory.iGetCategory;
import psb.com.kidpaint.webApi.Category.GetCategory.model.ResponseStickers;
import psb.com.kidpaint.webApi.Category.GetCategory.model.Sticker;

public class M_Splash implements IM_Splash {

    private Context context;
    private IP_Splash ipSplash;

    private List<Sticker> stickerList = new ArrayList<>();
    private List<psb.com.kidpaint.webApi.Category.GetCategory.model.Category> categoryList = new ArrayList<>();

    public M_Splash(IP_Splash ipSplash) {
        this.context = ipSplash.getContext();
        this.ipSplash = ipSplash;
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void getStickers() {
        stickerList.clear();
        categoryList.clear();
        new Category().getCategory(new iGetCategory.iResult() {
            @Override
            public void onSuccessGetCategory(ResponseStickers responseStickers) {
                categoryList = responseStickers.getExtra();
                for (int i = 0; i < responseStickers.getExtra().size(); i++) {
                    for (int j = 0; j < responseStickers.getExtra().get(i).getStickers().size(); j++) {
                        stickerList.add(responseStickers.getExtra().get(i).getStickers().get(j));
                    }
                }
                ipSplash.getStickersSuccess();
            }

            @Override
            public void onFailedGetCategory(int errorId, String ErrorMessage) {
                ipSplash.getStickersFailed(ErrorMessage);
            }
        }).doGetCategory();
    }
}
