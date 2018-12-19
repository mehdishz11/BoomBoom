package psb.com.kidpaint.competition.leaderBoard;

import android.content.Context;

import psb.com.kidpaint.competition.leaderBoard.adapter.ViewHolder_LeaderShip;
import psb.com.kidpaint.webApi.paint.getLeaderShip.model.ResponseGetLeaderShip;

public interface IPLeaderShip {

    Context getContext();


    void onGetLeaderShip(int page, int size,int matchId, int level);
    void setResponseGetLeaderShip(ResponseGetLeaderShip responseGetLeaderShip);
    void onSuccessGetLeaderShip(ResponseGetLeaderShip responseGetLeaderShip);
    void onFailedGetLeaderShip(int errorCode, String errorMessage);

     void onBindViewHolder_GetLeaderShip(ViewHolder_LeaderShip holder, int position);

     int getArrSizeGetLeaderShip();
    int getServerGetLeaderShipSize();


    void onSendScore(int position);
    void onSuccessSendScore(int position);
    void onFailedSendScore(int errorCode, String errorMessage);

}
