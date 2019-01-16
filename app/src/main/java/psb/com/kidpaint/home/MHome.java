package psb.com.kidpaint.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import psb.com.kidpaint.utils.UserProfile;
import psb.com.kidpaint.utils.Utils;
import psb.com.kidpaint.utils.Value;
import psb.com.kidpaint.utils.database.Database;
import psb.com.kidpaint.webApi.paint.Paint;
import psb.com.kidpaint.webApi.paint.deletePaint.iDeletePaint;
import psb.com.kidpaint.webApi.paint.deletePaint.model.ResponseDeletePaint;
import psb.com.kidpaint.webApi.paint.getLeaderShip.GetLeaderShip;
import psb.com.kidpaint.webApi.paint.getLeaderShip.iGetLeaderShip;
import psb.com.kidpaint.webApi.paint.getLeaderShip.model.ResponseGetLeaderShip;
import psb.com.kidpaint.webApi.paint.getMyPaints.iGetMyPaints;
import psb.com.kidpaint.webApi.paint.getMyPaints.model.ResponseGetMyPaints;
import psb.com.kidpaint.webApi.paint.paintToMatch.iPaintToMatch;
import psb.com.kidpaint.webApi.paint.paintToMatch.model.ResponsePaintToMatch;
import psb.com.kidpaint.webApi.paint.postPaint.iPostPaint;
import psb.com.kidpaint.webApi.paint.postPaint.model.ParamsPostPaint;
import psb.com.kidpaint.webApi.paint.postPaint.model.ResponsePostPaint;
import psb.com.kidpaint.webApi.prize.PrizeRequest.PrizeRequest;
import psb.com.kidpaint.webApi.prize.PrizeRequest.iPrizeRequest;
import psb.com.kidpaint.webApi.prize.PrizeRequest.model.ParamsPrizeRequest;
import psb.com.kidpaint.webApi.prize.PrizeRequest.model.ResponsePrizeRequest;
import psb.com.kidpaint.webApi.register.Register;
import psb.com.kidpaint.webApi.register.logout.iLogout;
import psb.com.kidpaint.webApi.shareModel.HistoryModel;
import psb.com.kidpaint.webApi.userScore.UserScore;
import psb.com.kidpaint.webApi.userScore.addScore.iAddScore;
import psb.com.kidpaint.webApi.userScore.addScore.model.ResponseAddScore;


public class MHome implements IM_Home {

    private Context context;
    private IP_Home ip_home;
    private UserProfile userProfile;
    private List<HistoryModel> imageList = new ArrayList<>();
    private ResponseGetMyPaints mResponseGetMyPaints;

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

        File directory = Value.getPaintsDir(context);


        if (!directory.exists()) {
            directory.mkdirs();
        }
        File[] files = directory.listFiles();
        imageList.add(null);

        if (files!=null&& files.length>0) {
            Log.d("TAG", "getMyPaintHistory: "+files.length);
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



            for (int i = 0; files != null && i < files.length; i++) {
                if (files[i].getAbsolutePath().toLowerCase().contains("jpg")) {
                    HistoryModel historyModel=new HistoryModel();
                    historyModel.setFile(files[i]);
                    imageList.add(historyModel);
                }
            }
        }

        if (mResponseGetMyPaints!=null&& mResponseGetMyPaints.getMyPaint().size()>0) {
            for (int i = 0; i <mResponseGetMyPaints.getMyPaint().size() ; i++) {
                HistoryModel historyModel=new HistoryModel();
                historyModel.setPaintModel(mResponseGetMyPaints.getMyPaint().get(i));
                imageList.add(historyModel);
            }

        }
        Log.d("TAG", "getMyPaintHistory: "+imageList.size());


        ip_home.onGetMyPaintHistorySuccess();
    }

    @Override
    public void postPaint(final int position) {

        Log.d("TAG", "postPaint: "+position);
        if (imageList.get(position).getFile()!=null) {

            ParamsPostPaint paramsPostPaint = new ParamsPostPaint();
            paramsPostPaint.setMobile(userProfile.get_KEY_PHONE_NUMBER("0"));
            paramsPostPaint.setTitle("");

            Bitmap bitmap = BitmapFactory.decodeFile(imageList.get(position).getFile().getAbsolutePath());

            new Paint().postPaint(new iPostPaint.iResult() {
                @Override
                public void onSuccessPostPaint(ResponsePostPaint responsePostPaint) {

                    File file=imageList.get(position).getFile();
                    if (file!=null && file.exists()) {
                        file.delete();
                    }
                    ip_home.onSuccessPostPaint(responsePostPaint);

                }

                @Override
                public void onFailedPostPaint(int errorId, String ErrorMessage) {
                    ip_home.onFailedPostPaint(errorId, ErrorMessage);

                }
            }).doPostPaint(paramsPostPaint, bitmap);
        }else{

            if (imageList.get(position).getPaintModel().getCode().isEmpty()) {
                new Paint().paintToMatch(new iPaintToMatch.iResult() {
                    @Override
                    public void onSuccessPaintToMatch(ResponsePaintToMatch responsePaintToMatch) {

                        ResponsePostPaint responsePostPaint =new ResponsePostPaint();
                        responsePostPaint.setExtra(responsePaintToMatch.getExtra());

                        ip_home.onSuccessPostPaint(responsePostPaint);

                    }

                    @Override
                    public void onFailedPaintToMatch(int errorId, String ErrorMessage) {
                        ip_home.onFailedPostPaint(errorId, ErrorMessage);

                    }
                }).doPaintToMatch(userProfile.get_KEY_PHONE_NUMBER("0"),imageList.get(position).getPaintModel().getId());

            }else{

                ip_home.onFailedPostPaint(0, "این نقاشی قبلا به مسابقه ارسال شده است");
            }

        }

    }
//
    @Override
    public void deletePaint(final int position) {

        if (imageList.size()>position&&imageList.get(position).getFile()!=null) {

            File file=imageList.get(position).getFile();
            imageList.remove(position);
            if (file.exists()) {
                file.delete();
            }

            ip_home.onSuccessDelete(position);

        }else{
            new Paint().deletePaint(new iDeletePaint.iResult() {
                @Override
                public void onSuccessDelete(ResponseDeletePaint responseDeletePaint) {
                    imageList.remove(position);
                    ip_home.onSuccessDelete(position);

                }

                @Override
                public void onFailedDelete(int errorId, String ErrorMessage) {

                    ip_home.onFailedDelete(ErrorMessage);


                }
            }).doDelete(userProfile.get_KEY_PHONE_NUMBER(""),imageList.get(position).getPaintModel().getId());

        }



    }

    @Override
    public HistoryModel getPositionAt(int position) {
        return imageList.get(position);
    }



    @Override
    public int getArrSize() {
        return imageList.size();
    }

    @Override
    public boolean userIsRegistered() {
        return userProfile.get_KEY_PHONE_NUMBER("").isEmpty() ? false : true;
    }

    @Override
    public void doAddScore(int addScoreMode) {

        new UserScore().addScore(new iAddScore.iResult() {
            @Override
            public void onSuccessAddScore(ResponseAddScore responseAddScore) {
                userProfile.set_KEY_SCORE(responseAddScore.getExtra());
                ip_home.onSuccessAddScore(responseAddScore);
            }

            @Override
            public void onFailedAddScore(int errorCode, String ErrorMessage) {
                ip_home.onFailedAddScore(errorCode, ErrorMessage);

            }
        }).doAddScore(userProfile.get_KEY_PHONE_NUMBER(""),addScoreMode);
    }

    @Override
    public void getRank() {
        UserProfile userProfile = new UserProfile(getContext());
        new GetLeaderShip(new iGetLeaderShip.iResult() {
            @Override
            public void onSuccessGetLeaderShip(ResponseGetLeaderShip responseGetLeaderShip) {
                ip_home.getRankSuccess(responseGetLeaderShip);
            }

            @Override
            public void onFailedGetLeaderShip(int errorId, String ErrorMessage) {
                ip_home.getRankFailed(ErrorMessage);
            }
        }).doGetLeaderShip(userProfile.get_KEY_PHONE_NUMBER(""),1,3,0,userProfile.get_KEY_LEVEL(1));
    }

    @Override
    public void setResponseMyPaints(ResponseGetMyPaints responseMyPaints) {
        mResponseGetMyPaints=responseMyPaints;
    }

    @Override
    public void getMyPaints() {
        new Paint().getMyPaints(new iGetMyPaints.iResult() {
            @Override
            public void onSuccessGetMyPaints(ResponseGetMyPaints responseGetMyPaints) {
                mResponseGetMyPaints=responseGetMyPaints;
                ip_home.getMyPaintsSuccess();

            }

            @Override
            public void onFailedGetMyPaints(int errorId, String ErrorMessage) {

                ip_home.getMyPaintsFailed(errorId, ErrorMessage);

            }
        }).doGetMyPaints(userProfile.get_KEY_PHONE_NUMBER("0"),false);
    }

}
