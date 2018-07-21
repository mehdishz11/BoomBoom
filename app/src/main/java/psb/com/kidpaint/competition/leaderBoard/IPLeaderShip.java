package psb.com.kidpaint.competition.leaderBoard;

import android.content.Context;

import psb.com.kidpaint.competition.allPaint.adapter.ViewHolder_AllPaints;
import psb.com.kidpaint.competition.leaderBoard.adapter.ViewHolder_LeaderShip;
import psb.com.kidpaint.webApi.paint.getAllPaints.model.ResponseGetAllPaints;
import psb.com.kidpaint.webApi.paint.getLeaderShip.model.ResponseGetLeaderShip;

public interface IPLeaderShip {

    Context getContext();


    void onGetLeaderShip(int page, int size);
    void setResponseGetLeaderShip(ResponseGetLeaderShip responseGetLeaderShip);
    void onSuccessGetLeaderShip(ResponseGetLeaderShip responseGetLeaderShip);
    void onFailedGetLeaderShip(int errorCode, String errorMessage);

     void onBindViewHolder_GetLeaderShip(ViewHolder_LeaderShip holder, int position);

     int getArrSizeGetLeaderShip();
    int getServerGetLeaderShipSize();

}