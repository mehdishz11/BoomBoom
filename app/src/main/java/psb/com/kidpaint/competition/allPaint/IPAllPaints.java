package psb.com.kidpaint.competition.allPaint;

import android.content.Context;

import psb.com.kidpaint.competition.allPaint.adapter.ViewHolder_AllPaints;
import psb.com.kidpaint.webApi.paint.getAllPaints.model.ResponseGetAllPaints;
import psb.com.kidpaint.webApi.paint.score.model.ResponseScore;

public interface IPAllPaints {

    Context getContext();


    void onGetAllPaints(String text, int page, int size);
    void setResponseGetAllPaints(ResponseGetAllPaints responseGetAllPaints);
    void onSuccessGetAllPaints(ResponseGetAllPaints responseGetAllPaints);
    void onFailedGetAllPaints(int errorCode, String errorMessage);

     void onBindViewHolder_AllPaints(ViewHolder_AllPaints holder, int position);

     int getArrSizeAllPaints();


    int getServerAllPaintsSize();

    void onSendScore(int position);
    void onSuccessSendScore(int position);
    void onFailedSendScore(int errorCode, String errorMessage);

}
