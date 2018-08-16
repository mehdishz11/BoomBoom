package psb.com.kidpaint.competition.leaderBoard;

import android.content.Context;

import psb.com.kidpaint.utils.UserProfile;
import psb.com.kidpaint.webApi.paint.Paint;
import psb.com.kidpaint.webApi.paint.getLeaderShip.iGetLeaderShip;
import psb.com.kidpaint.webApi.paint.getLeaderShip.model.LeaderModel;
import psb.com.kidpaint.webApi.paint.getLeaderShip.model.ResponseGetLeaderShip;

public class MLeaderShip implements IMLeaderShip {

    private Context context;
    private IPLeaderShip ipLeaderShip;
    private ResponseGetLeaderShip mResponseGetLeaderShip;
    private UserProfile userProfile;

    public MLeaderShip(IPLeaderShip ipLeaderShip) {
        this.ipLeaderShip = ipLeaderShip;
        this.context= ipLeaderShip.getContext();
        this.userProfile=new UserProfile(getContext());

    }

    @Override
    public Context getContext() {
        return context;
    }



    @Override
    public void onGetLeaderShip(final int page, int size,int matchId, int level) {
        new Paint().getLeaderShip(new iGetLeaderShip.iResult() {
            @Override
            public void onSuccessGetLeaderShip(ResponseGetLeaderShip responseGetLeaderShip) {

                if (page==1) {
                    mResponseGetLeaderShip=responseGetLeaderShip;
                    ipLeaderShip.onSuccessGetLeaderShip(mResponseGetLeaderShip);
                }else {
                    mResponseGetLeaderShip.getExtra().getLeaderModel().addAll(responseGetLeaderShip.getExtra().getLeaderModel());
                    mResponseGetLeaderShip.getExtra().setTotal(responseGetLeaderShip.getExtra().getTotal());
                    ipLeaderShip.onSuccessGetLeaderShip(mResponseGetLeaderShip);
                }
            }

            @Override
            public void onFailedGetLeaderShip(int errorId, String ErrorMessage) {
                ipLeaderShip.onFailedGetLeaderShip(errorId, ErrorMessage);

            }
        }).doGetLeaderShip(userProfile.get_KEY_PHONE_NUMBER(""),page,size,matchId,level);
    }

    @Override
    public void setResponseGetLeaderShip(ResponseGetLeaderShip responseGetLeaderShip) {
        mResponseGetLeaderShip=responseGetLeaderShip;

    }


    @Override
    public int getArrSizeLeaderShip() {
        return mResponseGetLeaderShip.getExtra().getLeaderModel().size();
    }

    @Override
    public int getServerLeaderShipSize() {
        return mResponseGetLeaderShip.getExtra().getTotal();
    }


    @Override
    public LeaderModel getLeaderShipPositionAt(int position) {
        return mResponseGetLeaderShip.getExtra().getLeaderModel().get(position);
    }
}
