package psb.com.kidpaint.competition.leaderBoard;

import android.content.Context;

import psb.com.kidpaint.webApi.paint.getLeaderShip.model.LeaderModel;
import psb.com.kidpaint.webApi.paint.getLeaderShip.model.ResponseGetLeaderShip;

public interface IMLeaderShip {

    Context getContext();


    void onGetLeaderShip(int page, int size,int matchId, int level);
    void setResponseGetLeaderShip(ResponseGetLeaderShip responseGetLeaderShip);


     int getArrSizeLeaderShip();
     int getServerLeaderShipSize();

    LeaderModel getLeaderShipPositionAt(int position);

    void onSendScore(int position);
    boolean userIsRegistered();


}
