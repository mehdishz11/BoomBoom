package psb.com.kidpaint.competition;

import android.content.Context;

import psb.com.kidpaint.webApi.paint.getAllPaints.model.ResponseGetAllPaints;
import psb.com.kidpaint.webApi.paint.getMyPaints.model.ResponseGetMyPaints;

public interface IVCompetition {

    Context getContext();

    void onStartGetMyPaints();
    void onSuccessGetMyPaints(ResponseGetMyPaints responseGetMyPaints);
    void onFailedGetMyPaints(int errorCode,String errorMessage);

    void onStartGetAllPaints();
    void onSuccessGetAllPaints(ResponseGetAllPaints responseGetAllPaints);
    void onFailedGetAllPaints(int errorCode,String errorMessage);

    void onStartGetLeaderBoard();
    void onSuccessGetLeaderBoard();
    void onFailedGetLeaderBoard(int errorCode,String errorMessage);

}
