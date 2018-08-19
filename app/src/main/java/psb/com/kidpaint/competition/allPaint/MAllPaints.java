package psb.com.kidpaint.competition.allPaint;

import android.content.Context;

import psb.com.kidpaint.utils.UserProfile;
import psb.com.kidpaint.webApi.paint.Paint;
import psb.com.kidpaint.webApi.paint.getAllPaints.iGetAllPaints;
import psb.com.kidpaint.webApi.paint.getLeaderShip.model.LeaderModel;
import psb.com.kidpaint.webApi.paint.score.iScore;
import psb.com.kidpaint.webApi.paint.score.model.ParamsScore;
import psb.com.kidpaint.webApi.paint.score.model.ResponseScore;
import psb.com.kidpaint.webApi.shareModel.PaintModel;
import psb.com.kidpaint.webApi.paint.getAllPaints.model.ResponseGetAllPaints;

public class MAllPaints implements IMAllPaints {

    private Context context;
    private IPAllPaints ipAllPaints;
    private ResponseGetAllPaints mResponseGetAllPaints;
    private UserProfile userProfile;

    public MAllPaints(IPAllPaints ipAllPaints) {
        this.ipAllPaints = ipAllPaints;
        this.context= ipAllPaints.getContext();
        this.userProfile=new UserProfile(getContext());

    }

    @Override
    public Context getContext() {
        return context;
    }



    @Override
    public void onGetAllPaints(String text, final int page, int size,int matchId, int level) {
        new Paint().getAllPaints(new iGetAllPaints.iResult() {
            @Override
            public void onSuccessGetAllPaints(ResponseGetAllPaints responseGetAllPaints) {

                if (page==1) {
                    mResponseGetAllPaints=responseGetAllPaints;
                    ipAllPaints.onSuccessGetAllPaints(mResponseGetAllPaints);
                }else {
                    mResponseGetAllPaints.getExtra().getPaintModel().addAll(responseGetAllPaints.getExtra().getPaintModel());
                    ipAllPaints.onSuccessGetAllPaints(mResponseGetAllPaints);
                }
            }

            @Override
            public void onFailedGetAllPaints(int errorId, String ErrorMessage) {
                ipAllPaints.onFailedGetAllPaints(errorId, ErrorMessage);

            }
        }).doGetAllPaints(text,page,size,matchId,level);
    }

    @Override
    public void setResponseGetAllPaints(ResponseGetAllPaints responseGetAllPaints) {
        mResponseGetAllPaints=responseGetAllPaints;

    }





    @Override
    public int getArrSizeAllPaints() {
        return mResponseGetAllPaints==null?0:mResponseGetAllPaints.getExtra().getPaintModel().size();
    }

    @Override
    public int getServerAllPaintsSize() {
        return mResponseGetAllPaints==null?0:mResponseGetAllPaints.getExtra().getTotal();
    }


    @Override
    public LeaderModel getAllPaintsPositionAt(int position) {
        return mResponseGetAllPaints.getExtra().getPaintModel().get(position);
    }

    @Override
    public void onSendScore(final int position) {
        ParamsScore paramsScore=new ParamsScore();
        paramsScore.setMobile(userProfile.get_KEY_PHONE_NUMBER(""));
        paramsScore.setPaintId(mResponseGetAllPaints.getExtra().getPaintModel().get(position).getId());
        paramsScore.setScore(1);

        new Paint().score(new iScore.iResult() {
            @Override
            public void onSuccessScore(ResponseScore responseScore) {
                mResponseGetAllPaints.getExtra().getPaintModel().get(position).setScore(responseScore.getExtra());
                ipAllPaints.onSuccessSendScore(position);
            }

            @Override
            public void onFailedScore(int errorId, String ErrorMessage) {

                ipAllPaints.onFailedSendScore(errorId, ErrorMessage);

            }
        }).doScore(paramsScore);

    }

    @Override
    public boolean userIsRegistered() {
        return !userProfile.get_KEY_PHONE_NUMBER("").isEmpty();
    }

    @Override
    public LeaderModel getFirstPaintModel() {
        return mResponseGetAllPaints==null||mResponseGetAllPaints.getExtra().getPaintModel().size()<=0?null:mResponseGetAllPaints.getExtra().getPaintModel().get(0);
    }
}
