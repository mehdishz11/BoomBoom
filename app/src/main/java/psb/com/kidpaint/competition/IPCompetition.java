package psb.com.kidpaint.competition;

import android.content.Context;

import psb.com.kidpaint.webApi.paint.getAllPaints.model.ResponseGetAllPaints;
import psb.com.kidpaint.webApi.paint.getMyPaints.model.ResponseGetMyPaints;

public interface IPCompetition {

    Context getContext();

    void onGetMyPaints();
    void onSuccessGetMyPaints(ResponseGetMyPaints responseGetMyPaints);
    void onFailedGetMyPaints(int errorCode,String errorMessage);

    void onGetAllPaints();
    void onSuccessGetAllPaints(ResponseGetAllPaints responseGetAllPaints);
    void onFailedGetAllPaints(int errorCode,String errorMessage);

    void onGetLeaderBoard();
    void onSuccessGetLeaderBoard();
    void onFailedGetLeaderBoard(int errorCode,String errorMessage);

}
