package psb.com.kidpaint.home.splash;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import psb.com.kidpaint.utils.UserProfile;
import psb.com.kidpaint.utils.database.TblContent.TblCategory;
import psb.com.kidpaint.utils.database.TblContent.TblStickers;
import psb.com.kidpaint.utils.prizePrefrence.SavePrize;
import psb.com.kidpaint.webApi.Category.Category;
import psb.com.kidpaint.webApi.Category.GetCategory.iGetCategory;
import psb.com.kidpaint.webApi.Category.GetCategory.model.ResponseStickers;
import psb.com.kidpaint.webApi.Category.GetCategory.model.Sticker;
import psb.com.kidpaint.webApi.paint.getLeaderShip.GetLeaderShip;
import psb.com.kidpaint.webApi.paint.getLeaderShip.iGetLeaderShip;
import psb.com.kidpaint.webApi.paint.getLeaderShip.model.ResponseGetLeaderShip;
import psb.com.kidpaint.webApi.prize.Get.GetPrize;
import psb.com.kidpaint.webApi.prize.Get.iGetPrize;
import psb.com.kidpaint.webApi.prize.Get.model.ResponsePrize;
import psb.com.kidpaint.webApi.register.Register;
import psb.com.kidpaint.webApi.register.fcmToken.iFcmToken;

public class M_Splash implements IM_Splash {

    private Context context;
    private IP_Splash ipSplash;

    private List<Sticker> stickerList = new ArrayList<>();
    private List<psb.com.kidpaint.webApi.Category.GetCategory.model.Category> categoryList = new ArrayList<>();
    private TblStickers tblStickers;
    private TblCategory tblCategory;

    private SavePrize savePrize;
    private UserProfile userProfile;

    public M_Splash(IP_Splash ipSplash) {
        this.context = ipSplash.getContext();
        this.ipSplash = ipSplash;
        tblStickers = new TblStickers(getContext());
        tblCategory = new TblCategory(getContext());
        savePrize = new SavePrize(getContext());
        this.userProfile=new UserProfile(getContext());
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void getStickers() {
        stickerList.clear();
        categoryList.clear();
        String fromDate=tblStickers.getStickerLastUpdateTime();
        new Category().getCategory(new iGetCategory.iResult() {
            @Override
            public void onSuccessGetCategory(ResponseStickers responseStickers) {
                categoryList = responseStickers.getExtra();
                addCategoryToDataBase(categoryList);
                for (int i = 0; i < responseStickers.getExtra().size(); i++) {
                    for (int j = 0; j < responseStickers.getExtra().get(i).getStickers().size(); j++) {
                        stickerList.add(responseStickers.getExtra().get(i).getStickers().get(j));
                    }
                }
                addStickersToDataBase(stickerList);
                ipSplash.getStickersSuccess();
            }

            @Override
            public void onFailedGetCategory(int errorId, String ErrorMessage) {
                ipSplash.getStickersFailed(ErrorMessage);
            }
        }).doGetCategory(fromDate);
    }



    private void addStickersToDataBase(List<Sticker> stickerList){
        for (int i = 0; i < stickerList.size(); i++) {
            tblStickers.insert(stickerList.get(i));
        }
    }

    private void addCategoryToDataBase(List<psb.com.kidpaint.webApi.Category.GetCategory.model.Category> categoryList){
        for (int i = 0; i < categoryList.size(); i++) {
            tblCategory.insert(categoryList.get(i));
        }
    }

    @Override
    public void getRank() {
        UserProfile userProfile = new UserProfile(getContext());
        new GetLeaderShip(new iGetLeaderShip.iResult() {
            @Override
            public void onSuccessGetLeaderShip(ResponseGetLeaderShip responseGetLeaderShip) {
                ipSplash.getRankSuccess(responseGetLeaderShip);
            }

            @Override
            public void onFailedGetLeaderShip(int errorId, String ErrorMessage) {
                ipSplash.getRankFailed(ErrorMessage);
            }
        }).doGetLeaderShip(userProfile.get_KEY_PHONE_NUMBER(""),1,3);
    }

    @Override
    public void getPirze() {
        new GetPrize(new iGetPrize.iResult() {
            @Override
            public void onSuccessGetPrize(ResponsePrize responsePrize) {
                savePrize.savePrizeToPrefrence(responsePrize);
                ipSplash.getPirzeSuccess(responsePrize);
            }

            @Override
            public void onFailedGetPrize(int errorId, String ErrorMessage) {
                ipSplash.getPrizeFailed(ErrorMessage, savePrize.getResponsePrize());
            }
        }).doGetPrize();
    }

    @Override
    public boolean userIsRegistered() {
        return !userProfile.get_KEY_PHONE_NUMBER("").isEmpty();
    }

    @Override
    public void updateFcmToken() {
        if (userProfile.get_KEY_FCM("").isEmpty()) {
            ipSplash.onSuccessUpdateFcmToken();
        } else {
            new Register().fcmToken(new iFcmToken.iResult() {
                @Override
                public void onSuccessSendFcmToken() {
                    ipSplash.onSuccessUpdateFcmToken();

                }

                @Override
                public void onFailedSendFcmToken(int ErrorId, String ErrorMessage) {
                      ipSplash.onFailedUpdateFcmToken(ErrorId, ErrorMessage);
                }
            }).startSendFcmToken(userProfile.get_KEY_JWT(""), userProfile.get_KEY_FCM(""), userProfile.get_KEY_PHONE_NUMBER(""));
        }
    }
}
