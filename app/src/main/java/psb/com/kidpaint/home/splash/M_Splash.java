package psb.com.kidpaint.home.splash;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import psb.com.kidpaint.App;
import psb.com.kidpaint.utils.UserProfile;
import psb.com.kidpaint.utils.Utils;
import psb.com.kidpaint.utils.Value;
import psb.com.kidpaint.utils.database.Database;
import psb.com.kidpaint.utils.database.TblContent.TblCategory;
import psb.com.kidpaint.utils.database.TblContent.TblStickers;
import psb.com.kidpaint.utils.database.TblMessage.TblMessage;
import psb.com.kidpaint.utils.prizePrefrence.SavePrize;
import psb.com.kidpaint.utils.sharePrefrence.SharePrefrenceHelper;
import psb.com.kidpaint.webApi.Category.Category;
import psb.com.kidpaint.webApi.Category.GetCategory.iGetCategory;
import psb.com.kidpaint.webApi.Category.GetCategory.model.ResponseStickers;
import psb.com.kidpaint.webApi.Category.GetCategory.model.Sticker;
import psb.com.kidpaint.webApi.chat.Chat;
import psb.com.kidpaint.webApi.chat.Get.iGetChat;
import psb.com.kidpaint.webApi.chat.Get.model.ResponseMyMessages;
import psb.com.kidpaint.webApi.offerPackage.Get.iGetOfferPackage;
import psb.com.kidpaint.webApi.offerPackage.Get.model.ResponseGetOfferPackage;
import psb.com.kidpaint.webApi.offerPackage.OfferPackage;
import psb.com.kidpaint.webApi.paint.Paint;
import psb.com.kidpaint.webApi.paint.getLeaderShip.GetLeaderShip;
import psb.com.kidpaint.webApi.paint.getLeaderShip.iGetLeaderShip;
import psb.com.kidpaint.webApi.paint.getLeaderShip.model.ResponseGetLeaderShip;
import psb.com.kidpaint.webApi.paint.getMyPaints.iGetMyPaints;
import psb.com.kidpaint.webApi.paint.getMyPaints.model.ResponseGetMyPaints;
import psb.com.kidpaint.webApi.paint.savePaints.iSavePaints;
import psb.com.kidpaint.webApi.paint.savePaints.model.ParamsSavePaint;
import psb.com.kidpaint.webApi.paint.savePaints.model.ResponseSavePaint;
import psb.com.kidpaint.webApi.prize.Prize;
import psb.com.kidpaint.webApi.prize.getDailyPrize.iGetDailyPrize;
import psb.com.kidpaint.webApi.prize.getDailyPrize.model.ResponseGetDailyPrize;
import psb.com.kidpaint.webApi.register.Register;
import psb.com.kidpaint.webApi.register.fcmToken.iFcmToken;
import psb.com.kidpaint.webApi.register.registerUserInfo.iProfile;
import psb.com.kidpaint.webApi.register.registerUserInfo.model.UserInfo;

public class M_Splash implements IM_Splash {

    private Context context;
    private IP_Splash ipSplash;

    private List<Sticker> stickerList = new ArrayList<>();
    private List<psb.com.kidpaint.webApi.Category.GetCategory.model.Category> categoryList = new ArrayList<>();
    private TblStickers tblStickers;
    private TblCategory tblCategory;

    private SavePrize savePrize;
    private UserProfile userProfile;

    private List<File> localPaintFiles=new ArrayList<>();

    public M_Splash(IP_Splash ipSplash) {
        this.context = ipSplash.getContext();
        this.ipSplash = ipSplash;
        tblStickers = new TblStickers(getContext());
        tblCategory = new TblCategory(getContext());
        savePrize = new SavePrize(getContext());
        this.userProfile = new UserProfile(getContext());
    }

    public void setFirstUserScore(int firstScore){
        UserProfile userProfile=new UserProfile(getContext());
        if(
                userProfile.get_KEY_PHONE_NUMBER("").isEmpty() //user not register
                && SharePrefrenceHelper.getFirstRun())//use for just first time
        {
            SharePrefrenceHelper.setFirstRun(false);
            userProfile.set_KEY_SCORE(firstScore);
        }
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void getStickers() {
        stickerList.clear();
        categoryList.clear();

        String fromDate = tblStickers.getStickerLastUpdateTime();

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

    private void addStickersToDataBase(List<Sticker> stickerList) {
        tblStickers.insert(stickerList);

    }

    private void addCategoryToDataBase(List<psb.com.kidpaint.webApi.Category.GetCategory.model.Category> categoryList) {
        tblCategory.insert(categoryList);
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
        }).doGetLeaderShip(userProfile.get_KEY_PHONE_NUMBER(""), 1, 3, 0, userProfile.get_KEY_LEVEL(1));
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

    @Override
    public void getOfferPackage() {
        new OfferPackage().getOfferPackage(new iGetOfferPackage.iResult() {
            @Override
            public void onSuccessGetOfferPackage(ResponseGetOfferPackage responseGetOfferPackage) {
                ipSplash.onSuccessGetOfferPackage(responseGetOfferPackage);
            }

            @Override
            public void onFailedGetOfferPackage(int errorCode, String ErrorMessage) {
                ipSplash.onFailedGetOfferPackage(errorCode, ErrorMessage);
            }
        }).doGetOfferPackage(userProfile.get_KEY_PHONE_NUMBER(""));
    }

    @Override
    public void getDailyPrize() {
        new Prize().getDailyPrize(new iGetDailyPrize.iResult() {
            @Override
            public void onSuccessGetDailyPrize(ResponseGetDailyPrize responseGetDailyPrize) {
                ipSplash.onSuccessGetDailyPrize(responseGetDailyPrize);

            }

            @Override
            public void onFailedGetDailyPrize(int errorCode, String ErrorMessage) {
                ipSplash.onFailedGetDailyPrize(errorCode, ErrorMessage);
            }
        }).doGetDailyPrize(userProfile.get_KEY_PHONE_NUMBER("0"));
    }

    @Override
    public void getMessage() {
        final String time = new Database().tblMessage(getContext()).getMessageLastUpdateTime();
        new Chat().getChat(new iGetChat.iResult() {
            @Override
            public void onSuccessGetChat(ResponseMyMessages responseMyMessages) {
                new Database().tblMessage(getContext()).insertMessageList(responseMyMessages.getExtra(), ("0".equals(time) ? true : false), new TblMessage.interFaceDB_InsertChats() {
                    @Override
                    public void onSuccessInsertChats(int size) {
                        ipSplash.onSuccessGetMessage();
                    }
                });
            }

            @Override
            public void onFailedGetChat(int errorId, String ErrorMessage) {
                ipSplash.onFailedGetMessage(errorId, ErrorMessage);

            }
        }).doGetChat(Utils.getDeviceId(getContext()), userProfile.get_KEY_PHONE_NUMBER(""), time);

    }

    @Override
    public void getProfile() {
        new Register().profile(new iProfile.iResult() {
            @Override
            public void onSuccessGetUserInfo(UserInfo userInfo) {
                userProfile.set_KEY_USER_INFO(userInfo);
                ipSplash.onSuccessGetProfile();
            }

            @Override
            public void onFailedGetUserInfo(int ErrorId, String ErrorMessage) {
                ipSplash.onFailedGetProfile(ErrorId, ErrorMessage);

            }

            @Override
            public void onSuccessSetUserInfo(UserInfo userInfo) {

            }

            @Override
            public void onFailedSetUserInfo(int ErrorId, String ErrorMessage) {

            }
        }).startGetUserInfo(userProfile.get_KEY_JWT("-1"), userProfile.get_KEY_PHONE_NUMBER("0"));
    }

    @Override
    public void getMyPaints() {
       /* new Paint().getMyPaints(new iGetMyPaints.iResult() {
            @Override
            public void onSuccessGetMyPaints(ResponseGetMyPaints responseGetMyPaints) {
                ipSplash.onSuccessGetMyPaints(responseGetMyPaints);
            }

            @Override
            public void onFailedGetMyPaints(int errorId, String ErrorMessage) {
                 ipSplash.onFailedGetGetMyPaints(errorId, ErrorMessage);
            }
        }).doGetMyPaints(userProfile.get_KEY_PHONE_NUMBER("0"),false);
*/

        new Paint().getMyPaints(new iGetMyPaints.iResult() {
            @Override
            public void onSuccessGetMyPaints(ResponseGetMyPaints responseGetMyPaints) {
                ipSplash.onSuccessGetMyPaints(responseGetMyPaints);
            }

            @Override
            public void onFailedGetMyPaints(int errorId, String ErrorMessage) {
                ipSplash.onFailedGetGetMyPaints(errorId, ErrorMessage);
            }
        }).doGetMyPaints(userProfile.get_KEY_PHONE_NUMBER("0"), false);
    }

    @Override
    public int getLocalPaintsCount() {


        File directory = Value.getPaintsDir(context);

        if (!directory.exists()) {
            directory.mkdirs();
        }

       File[] files = directory.listFiles();
        if (files != null && files.length > 0) {


            for (int i = 0; files != null && i < files.length; i++) {
                if (files[i].getAbsolutePath().toLowerCase().contains("jpg")) {
                    localPaintFiles.add(files[i]);
                    Log.d(App.TAG, "onSavePaintsInServer: Added-->"+files[i].getAbsolutePath());

                }
            }
        }


        return localPaintFiles.size();
    }


    private void testAfterCleanFolder(){
        File directory = Value.getPaintsDir(context);
        Log.d(App.TAG, "onSavePaintsInServer: after removed folder path-->"+directory.getAbsolutePath());
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File[] files = directory.listFiles();
        if (files != null && files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].getAbsolutePath().toLowerCase().contains("jpg")) {
                    Log.d(App.TAG, "onSavePaintsInServer: after removed-->"+files[i].getAbsolutePath());

                }
            }
        }
    }

    @Override
    public void onSavePaintsInServer() {
        if (localPaintFiles.size()>0) {

            final String filePath = localPaintFiles.get(0).getAbsolutePath();
            Bitmap bitmap = BitmapFactory.decodeFile(filePath);

            ParamsSavePaint paramsPostPaint = new ParamsSavePaint();
            paramsPostPaint.setMobile(userProfile.get_KEY_PHONE_NUMBER("0"));
            paramsPostPaint.setTitle("");

            new Paint().savePaints(new iSavePaints.iResult() {
                @Override
                public void onSuccessSavePaint(ResponseSavePaint responseSavePaint) {
                    File file=localPaintFiles.get(0);
                    String fileName=file.getAbsolutePath();
                    if (file.exists()) {
                        boolean resultDelete=file.delete();
                        Log.d(App.TAG, "onSavePaintsInServer: "+fileName+"-Deleted->"+resultDelete);
                    }
                    Log.d(App.TAG, "onSavePaintsInServer: size before -->"+localPaintFiles.size());
                    localPaintFiles.remove(0);
                    Log.d(App.TAG, "onSavePaintsInServer: size after -->"+localPaintFiles.size());
                    testAfterCleanFolder();
                    if (localPaintFiles.size()>0) {
                        onSavePaintsInServer();
                    }else{
                        ipSplash.onSuccessSavePaintsInServer();
                    }

                }

                @Override
                public void onFailedSavePaint(int errorId, String ErrorMessage) {
                   // ipSplash.onFailedSavePaintsInServer(errorId, ErrorMessage);

                    localPaintFiles.remove(0);
                    if (localPaintFiles.size()>0) {
                        onSavePaintsInServer();
                    }else{
                        ipSplash.onSuccessSavePaintsInServer();
                    }
                }
            }).doSavePaint(paramsPostPaint,bitmap);
        }else {
            ipSplash.onSuccessSavePaintsInServer();

        }

    }
}
