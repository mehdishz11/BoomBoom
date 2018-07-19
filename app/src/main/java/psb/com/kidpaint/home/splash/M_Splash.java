package psb.com.kidpaint.home.splash;

import android.content.Context;

import psb.com.kidpaint.webApi.Category.Category;
import psb.com.kidpaint.webApi.Category.GetCategory.iGetCategory;
import psb.com.kidpaint.webApi.Category.GetCategory.model.ResponseStickers;

public class M_Splash implements IM_Splash {

    private Context context;
    private IP_Splash ipSplash;

    public M_Splash(IP_Splash ipSplash){
        this.context = ipSplash.getContext();
        this.ipSplash = ipSplash;
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void getStickers() {
        new Category().getCategory(new iGetCategory.iResult() {
            @Override
            public void onSuccessGetCategory(ResponseStickers responseStickers) {
                ipSplash.getStickersSuccess();
            }

            @Override
            public void onFailedGetCategory(int errorId, String ErrorMessage) {
                ipSplash.getStickersFailed(ErrorMessage);
            }
        }).doGetCategory();
    }
}
