package psb.com.kidpaint.competition.leaderBoard;

import android.content.Context;

import psb.com.kidpaint.webApi.paint.getAllPaints.model.ResponseGetAllPaints;
import psb.com.kidpaint.webApi.paint.getLeaderShip.model.ResponseGetLeaderShip;

public interface IVLeaderShip {

    Context getContext();

    void onStartGetLeaderShip();
    void onSuccessGetLeaderShip(ResponseGetLeaderShip responseGetLeaderShip);
    void onFailedGetLeaderShip(int errorCode, String errorMessage);



}
