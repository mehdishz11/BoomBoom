package psb.com.kidpaint.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import psb.com.kidpaint.utils.UserProfile;
import psb.com.kidpaint.utils.Utils;
import psb.com.kidpaint.utils.database.Database;
import psb.com.kidpaint.webApi.paint.Paint;
import psb.com.kidpaint.webApi.paint.postPaint.iPostPaint;
import psb.com.kidpaint.webApi.paint.postPaint.model.ParamsPostPaint;
import psb.com.kidpaint.webApi.paint.postPaint.model.ResponsePostPaint;
import psb.com.kidpaint.webApi.prize.PrizeRequest.PrizeRequest;
import psb.com.kidpaint.webApi.prize.PrizeRequest.iPrizeRequest;
import psb.com.kidpaint.webApi.prize.PrizeRequest.model.ParamsPrizeRequest;
import psb.com.kidpaint.webApi.prize.PrizeRequest.model.ResponsePrizeRequest;
import psb.com.kidpaint.webApi.register.Register;
import psb.com.kidpaint.webApi.register.logout.iLogout;


public class MHome implements IM_Home {

    private Context context;
    private IP_Home ip_home;
    private UserProfile userProfile;
    private List<File> imageList = new ArrayList<>();

    public MHome(IP_Home ip_home) {
        this.ip_home = ip_home;
        this.context = ip_home.getContext();
        this.userProfile=new UserProfile(context);
    }

    @Override
    public Context getContext() {
        return context;
    }


    @Override
    public void onStartLogout() {
        new Register().logout(new iLogout.iResult() {
            @Override
            public void onSuccessLogout() {
                userProfile.REMOVE_KEY_USER_INFO();
                ip_home.onLogoutSuccess();
            }

            @Override
            public void onFailedLogout(int errorId, String errorMessage) {
                ip_home.onLogoutFailed(errorMessage);

            }
        }).doLogout(userProfile.get_KEY_PHONE_NUMBER(""), Utils.getDeviceId(getContext()));
    }

    @Override
    public void prizeRequest(ParamsPrizeRequest paramsPrizeRequest) {
        new PrizeRequest(new iPrizeRequest.iResult() {
            @Override
            public void onSuccessPrizeRequest(ResponsePrizeRequest responsePrizeRequest) {
                ip_home.prizeRequestSuccess(responsePrizeRequest.getExtra());
            }

            @Override
            public void onFailedPrizeRequest(int errorId, String ErrorMessage) {
                ip_home.prizeRequestFailed(ErrorMessage);
            }
        }).doPrizeRequest(paramsPrizeRequest);
    }

    @Override
    public int getUnreadMessageCount() {
        return new Database().tblMessage(getContext()).getUnreadChatMessageCount();
    }

    @Override
    public void getMyPaintHistory() {
        imageList.clear();
        imageList=new ArrayList<>();
        String path = Environment.getExternalStorageDirectory() + "/kidPaint";
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File[] files = directory.listFiles();

        if (files!=null&& files.length>0) {
            Arrays.sort( files, new Comparator()
            {
                public int compare(Object o1, Object o2) {

                    if (((File)o1).lastModified() > ((File)o2).lastModified()) {
                        return -1;
                    } else if (((File)o1).lastModified() < ((File)o2).lastModified()) {
                        return +1;
                    } else {
                        return 0;
                    }
                }

            });



             imageList.add(null);
            for (int i = 0; files != null && i < files.length; i++) {
                imageList.add(files[i]);
            }
        }


        ip_home.onGetMyPaintHistorySuccess();
    }

    @Override
    public void postPaint(int position) {
        ParamsPostPaint paramsPostPaint = new ParamsPostPaint();
        paramsPostPaint.setMobile(userProfile.get_KEY_PHONE_NUMBER("0"));
        paramsPostPaint.setTitle("");

        Bitmap bitmap = BitmapFactory.decodeFile(imageList.get(position).getAbsolutePath());

        new Paint().postPaint(new iPostPaint.iResult() {
            @Override
            public void onSuccessPostPaint(ResponsePostPaint responsePostPaint) {
                ip_home.onSuccessPostPaint(responsePostPaint);

            }

            @Override
            public void onFailedPostPaint(int errorId, String ErrorMessage) {
                ip_home.onFailedPostPaint(errorId, ErrorMessage);

            }
        }).doPostPaint(paramsPostPaint, bitmap);

    }

    @Override
    public void deletePaint(int position) {

        File file=imageList.get(position);
        imageList.remove(position);
        if (file.exists()) {
            file.delete();
        }

        ip_home.onGetMyPaintHistorySuccess();


    }

    @Override
    public File getPositionAt(int position) {
        return imageList.get(position);
    }

    @Override
    public File getLastPaintFile() {
        return imageList.size()<=0?null:imageList.get(0);
    }

    @Override
    public int getArrSize() {
        return imageList.size();
    }

    @Override
    public boolean userIsRegistered() {
        return userProfile.get_KEY_PHONE_NUMBER("").isEmpty() ? false : true;
    }
}
